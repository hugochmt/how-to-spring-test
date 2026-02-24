package com.example.how_to_test;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

  private final TodoRepository todoRepository;
  private final RandomService randomService;

  public TodoService(TodoRepository todoRepository, RandomService randomService) {
    this.todoRepository = todoRepository;
    this.randomService = randomService;
  }

  public List<Todo> findAll() {
    return this.todoRepository.findAll();
  }

  public void create() {
    var todo = new Todo();
    todo.setName(this.randomService.createString());
    this.todoRepository.save(todo);
  }
}
