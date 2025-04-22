package ru.ibs.training.java.spring.data.rest.reloader;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestRunner {

  Environment environment;

  public static List<Method> getMethodsAnnotatedWith(final Class<?> type, final Class<? extends Annotation> annotation) {
    final List<Method> methods = new ArrayList<Method>();
    Class<?> klass = type;
    while (klass != Object.class) { // need to iterated thought hierarchy in order to retrieve methods from above the current instance
      // iterate though the list of methods declared in the class represented by klass variable, and add those annotated with the specified annotation
      for (final Method method : klass.getDeclaredMethods()) {
        if (method.isAnnotationPresent(annotation)) {
          Annotation annotInstance = method.getAnnotation(annotation);
          // TODO process annotInstance
          methods.add(method);
        }
      }
      // move to the upper class in the hierarchy in search for more methods
      klass = klass.getSuperclass();
    }
    return methods;
  }

  public String getRunResults(final Class<?> clazz) {
    val methods = getMethodsAnnotatedWith(clazz, Run.class);
    val results = new StringBuilder();
    for (val method : methods) {
      val run = method.getAnnotation(Run.class);
      val request = run.value();
      try {
        results.append(doGet(request));
      } catch (IOException e) {
        results.append("Was not able to execute request [")
               .append(request)
               .append("]:  ")
               .append(e.getMessage());
        e.printStackTrace();
      }
    }
    return results.toString();
  }

  private String doGet(String urlStr) throws IOException {
    if (!urlStr.startsWith("http")) {
      String host = "http://" + InetAddress.getLocalHost().getHostName();
      String port = environment.getProperty("server.port");
      if (port != null && port.length() > 1) host += ":" + port;
      if (!urlStr.startsWith("/")) host += "/";
      urlStr = host + urlStr;
    }
    URL url = new URL(urlStr);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");
    BufferedReader in = new BufferedReader(
        new InputStreamReader(con.getInputStream()));
    int status = con.getResponseCode();
    String inputLine;
    StringBuffer content = new StringBuffer();
    content.append("\n" + urlStr + "\n");
    while ((inputLine = in.readLine()) != null) {
      content.append(inputLine);
    }
    in.close();
    con.disconnect();
    return content.toString();
  }

}
