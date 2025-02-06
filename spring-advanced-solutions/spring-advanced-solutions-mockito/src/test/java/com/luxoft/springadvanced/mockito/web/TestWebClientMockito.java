package com.luxoft.springadvanced.mockito.web;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ConnectionFactoryTestConfiguration.class,
                                 InputStreamTestConfiguration.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TestWebClientMockito {

  ConnectionFactory factory;
  InputStream mockStream;

  @Test
  @Disabled
  @DirtiesContext
  public void testGetContentOk() throws Exception {
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
  @DirtiesContext
  public void testGetContentCannotCloseInputStream() {

    when(factory.getData()).thenReturn(mockStream);
    when(mockStream.read()).thenReturn(-1);
    doThrow(new IOException("cannot close")).when(mockStream).close();

    val client = new WebClient();
    val workingContent = client.getContent(factory);

    assertNull(workingContent);
  }
}
