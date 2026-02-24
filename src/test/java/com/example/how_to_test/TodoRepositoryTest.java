package com.example.how_to_test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

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
