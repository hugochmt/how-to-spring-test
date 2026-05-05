package com.example.how_to_test;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {TodoService.class, RandomService.class})
class TodoServiceSpringJunitConfigTest {

  @MockitoBean private TodoRepository todoRepository;

  @Test
  void contextLoads() {}
}
