package ru.ibs.training.java.spring.advanced.mockito.web;

import lombok.val;

import static java.lang.Character.*;

public class WebClient {

    public String getContent(ConnectionFactory connectionFactory) {
        String workingContent;

        val content = new StringBuilder();
        try (val is = connectionFactory.getData()) {
            int count;
            while (-1 != (count = is.read()))
              content.append(new String(toChars(count)));

            workingContent = content.toString();
        } catch (Exception e) {
            workingContent = null;
        }


        return workingContent;
    }
}
