package com.example.how_to_test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

@DataJpaTest
class TodoRepositoryTest {

  @Autowired private TodoRepository todoRepository;

  @Autowired private TestEntityManager testEntityManager;

  @Test
  void testCreateTodo() {
    this.todoRepository.save(new Todo());

    assertThat(this.todoRepository.findAll()).hasSize(1);
  }

  @Test
  void testEmptyDbByDefault() {
    assertThat(todoRepository.findAll()).isEmpty();
  }
}
