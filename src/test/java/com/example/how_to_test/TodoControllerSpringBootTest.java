package com.example.how_to_test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerSpringBootTest {
  @Autowired private MockMvcTester mockMvcTester;

  @MockitoBean private TodoService todoService;

  @Test
  void testGetAllTodos() {
    mockMvcTester.get().uri("").assertThat().hasStatus(200);
  }
}
