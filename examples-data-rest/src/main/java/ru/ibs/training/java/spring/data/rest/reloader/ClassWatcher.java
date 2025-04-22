package ru.ibs.training.java.spring.data.rest.reloader;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.tools.ToolProvider;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.LinkOption.*;
import static java.nio.file.StandardWatchEventKinds.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClassWatcher {
  DynamicClassLoader dynamicClassLoader;
  @NonFinal WatchService watcher;
  @NonFinal Map<WatchKey, Path> keys;

  @SuppressWarnings("unchecked")
  static <T> WatchEvent<T> cast(WatchEvent<?> event) {
    return (WatchEvent<T>) event;
  }

  private void compile(String path) {
    val classpathStr = System.getProperty("java.class.path");
    val compiler = ToolProvider.getSystemJavaCompiler();
    compiler.run(null, null, null,
                 "-d", "target/classes",
                 "-cp", "\"%s\":target/classes".formatted(classpathStr),
                 "src/main/java/" + path);
  }

  @SneakyThrows
  @PostConstruct
  public void init() {
    val url = ClassWatcher.class.getProtectionDomain().getCodeSource().getLocation();
    this.watcher = FileSystems.getDefault().newWatchService();
    this.keys = new HashMap<>();

    // registerFolders(Path.of(url.toURI()));
    registerFolders(Path.of("src/main/java/"));
    new Thread(this::processEvents).start();
  }

  private void registerFolders(Path path) throws IOException {
    Files.walkFileTree(path, new SimpleFileVisitor<>() {
      @Override
      @SneakyThrows
      public FileVisitResult preVisitDirectory(Path dir, @NotNull BasicFileAttributes attrs) {
        log.info("added watcher for folder{}", dir);
        val key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        keys.put(key, dir);
        return FileVisitResult.CONTINUE;
      }
    });
  }

  /**
   * Process all events for keys queued to the watcher
   */
  void processEvents() {
    while (true) {

      // wait for key to be signalled
      WatchKey key;
      try {
        key = watcher.take();
      } catch (InterruptedException x) {
        return;
      }

      Path dir = keys.get(key);
      if (dir == null) {
        log.error("WatchKey not recognized!!");
        continue;
      }

      for (WatchEvent<?> event : key.pollEvents()) {
        val kind = event.kind();

        // TBD - provide example of how OVERFLOW event is handled
        if (kind == OVERFLOW) continue;

        // Context for directory entry event is the file name of entry
        WatchEvent<Path> ev = cast(event);
        val name = ev.context();
        val child = dir.resolve(name);

        // print out event
        //System.out.format("%s: %s\n", event.kind().name(), child);

        String filePath = child.toString();
        if (kind == ENTRY_MODIFY && Files.isRegularFile(child) && filePath.endsWith(".java")) {
          String[] split = filePath.split("src/main/java/");
          String path = split[1];
          log.info("Modified class:{}", path);
          compile(path);
          String clazz = path.substring(0, path.length() - ".java".length()).replaceAll("/", ".");
          log.info("Compiled class:{}", path);
          DynamicClassLoader.ReloadClassLoader.addClassToReload(clazz);

          //String[] split = filePath.split("target/classes/");
          //String fileName = split[1].replaceAll("/",".");
          //String clazz = fileName.substring(0, fileName.length()-".class".length());
          //System.out.println("Modified class:"+clazz);
          //try {
          //    dynamicClassLoader.reloadClass(clazz);
          //} catch (ClassNotFoundException | MalformedURLException e) {
          //    System.out.println("Cannot reload class "+clazz);
          //}
        }

        // if directory is created, and watching recursively, then
        // register it and its sub-directories
        if (kind == ENTRY_CREATE) {
          try {
            if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
              registerFolders(child);
            }
          } catch (IOException x) {
            // ignore to keep sample readbale
          }
        }
      }

      // reset key and remove from set if directory no longer accessible
      boolean valid = key.reset();
      if (!valid) {
        keys.remove(key);

        // all directories are inaccessible
        if (keys.isEmpty()) {
          break;
        }
      }
    }
  }
}
