package ru.ibs.training.java.spring.advanced.mockito.web;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
    ConnectionFactoryTestConfiguration.class,
    InputStreamTestConfiguration.class,
})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class TestWebClientMockito {

  ConnectionFactory factory;

  InputStream mockStream;

  @BeforeEach
  void resetMocks() {
    reset(factory);
    reset(mockStream);
  }

  @Test
  @SneakyThrows
  void testGetContentOk()  {
    when(factory.getData()).thenReturn(mockStream);
    when(mockStream.read()).thenReturn((int) 'W')
                           .thenReturn((int) 'o')
                           .thenReturn((int) 'r')
                           .thenReturn((int) 'k')
                           .thenReturn((int) 's')
                           .thenReturn((int) '!')
                           .thenReturn(-1);

    val client = new WebClient();

    val workingContent = client.getContent(factory);

    assertEquals("Works!", workingContent);
  }

  @Test
  @SneakyThrows
  void testGetContentCannotCloseInputStream() {
    when(factory.getData()).thenReturn(mockStream);
    when(mockStream.read()).thenReturn(-1);
    doThrow(new IOException("cannot close")).when(mockStream).close();

    val client = new WebClient();

    val workingContent = client.getContent(factory);

    assertNull(workingContent);
  }
}
