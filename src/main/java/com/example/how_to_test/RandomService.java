package com.example.how_to_test;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class RandomService {

  public String createString() {
    byte[] array = new byte[10];
    new Random().nextBytes(array);
    return new String(array, StandardCharsets.UTF_8);
  }
}
