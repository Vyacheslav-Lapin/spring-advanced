package ru.ibs.training.java.spring.data.rest.reloader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.IOException;

@Slf4j
@RestController("plugin")
@RequiredArgsConstructor
public class PluginController {
  DynamicClassLoader dynamicClassLoader;
  TestRunner testRunner;
  ApplicationContext applicationContext;

  @GetMapping("/reload")
  String reloadPlugin(@RequestParam("file") String file) {
    log.info("going to compile {}", file);
    compile(file);
    val clazz = file
        .replaceAll("/", ".")
        .replace(".java", "");
    log.info("clazz = {}", clazz);
    // if the clazz is not a Spring bean, do not need to reload it
    try {
      DynamicClassLoader.ReloadClassLoader.addClassToReload(clazz);

      // TODO: improve mechanism to check if there's a bean of this type - clazz
//            Class<?> loadedClass = Class.forName(clazz);
      val classSimpleName = clazz.substring(clazz.lastIndexOf(".") + 1);
      val beanName = Character.toLowerCase(classSimpleName.charAt(0))
                        + classSimpleName.substring(1);
      if (applicationContext.containsBean(beanName)) {
        val reloadedClass = dynamicClassLoader.reloadBean(clazz);
        return testRunner.getRunResults(reloadedClass);
      }
    } catch (ClassNotFoundException | IOException e) {
      return "failed to reload " + clazz;
    }
    return "class recompiled (but it has no sense)";
  }

  @GetMapping(value = "/add/{clazz}")
  String registerPlugin(@PathVariable("clazz") String clazz) {
    try {
      Class<?> loadedClass = Class.forName(clazz);
      dynamicClassLoader.addBean(loadedClass);
      return "success!";
    } catch (ClassNotFoundException e) {
      return "failed to find " + clazz + " in the classpath";
    }
  }

  @GetMapping(value = "/remove/{clazz}")
  String removePlugin(@PathVariable String clazz) {
    try {
      Class<?> loadedClass = Class.forName(clazz);
      dynamicClassLoader.removeBean(loadedClass);
      return "success!";
    } catch (ClassNotFoundException e) {
      return "failed to find " + clazz + " in the classpath";
    }
  }

  private void compile(String path) {
    String classpathStr = System.getProperty("java.class.path");
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    compiler.run(null, null, null,
                 "-g", // generate debug information
                 "-d", "target/classes",
                 "-cp", "\"" + classpathStr + "\":target/classes",
                 "src/main/java/" + path);
  }

}
