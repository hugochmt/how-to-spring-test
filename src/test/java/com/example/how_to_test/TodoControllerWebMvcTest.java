package com.example.how_to_test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@WebMvcTest(TodoController.class)
class TodoControllerWebMvcTest {

  @Autowired private MockMvcTester mockMvcTester;

  @MockitoBean private TodoService todoService;

  @Test
  void testGetAllTodos() {
    mockMvcTester.get().uri("").assertThat().hasStatus(200);
  }
}
