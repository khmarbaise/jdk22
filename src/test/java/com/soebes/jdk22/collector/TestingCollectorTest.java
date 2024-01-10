package com.soebes.jdk22.collector;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

class TestingCollectorTest {

  static <T> TestingCollector<T> partitioningBySize(int size) {
    return new TestingCollector<T>(size);
  }
  @Test
  void name() {
    var result = IntStream.range(0, 10).boxed().collect(partitioningBySize(3));
    System.out.println("result = " + result);
  }

}
