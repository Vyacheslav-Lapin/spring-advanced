package com.luxoft.data.examples.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.ZoneId;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/admin")
public class ArgumentsInjectionDemoController {

  @GetMapping("/headers")
  public String headers(@RequestHeader Map<String, String> headers) {
    return headers.entrySet().stream()
                  .map(entry -> "%s: %s</br>".formatted(entry.getKey(), entry.getValue()))
                  .collect(Collectors.joining());
  }

  @GetMapping("/headers/ct")
  public String headers(@RequestHeader("Content-Type") String contentType) {
    return "Content-Type: " + contentType;
  }

  @GetMapping("/cookies")
  public String cookie(@CookieValue("JSESSIONID") String sessionId) {
    return "JSESSIONID: " + sessionId;
  }

  @GetMapping("/request")
  public void requestAndSession(WebRequest request,
                                NativeWebRequest nativeWebRequest,
                                HttpSession session) {
    log.info("/request endpoint called!");
  }

  @GetMapping("/locale")
  public String locale(Locale locale) {
    return "Your locale: " + locale.toString();
  }

  @GetMapping("/timezone")
  public String timeZone(TimeZone timeZone, ZoneId zoneId) {
    return timeZone + "</br>"
           + "zoneId: " + zoneId;
  }
}
