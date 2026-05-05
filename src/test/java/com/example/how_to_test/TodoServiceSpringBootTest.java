package com.example.how_to_test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(classes = {TodoService.class, RandomService.class})
class TodoServiceSpringBootTest {

  @MockitoBean private TodoRepository todoRepository;

  @Test
  void contextLoads() {}
}
