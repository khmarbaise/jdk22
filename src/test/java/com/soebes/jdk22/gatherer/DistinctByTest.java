package com.soebes.jdk22.gatherer;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

class DistinctByTest {

  @Test
  void first() {
//    var result = Stream.of("foo", "bar", "baz", "quux")
//        .distinctBy(String::length)      // Hypothetical
//        .toList();
  }

  @Test
  void exampleDistinctWithNumbers() {
    var integers = List.of(1, 10, 11, 10, 11);
    var result = integers.stream().distinct().toList();
    System.out.println("result = " + result);
  }

  @Test
  void second() {
    var result = Stream.of("foo", "bar", "baz", "quux")
        .distinct()
        .toList();
    System.out.println("result = " + result);
  }
}
