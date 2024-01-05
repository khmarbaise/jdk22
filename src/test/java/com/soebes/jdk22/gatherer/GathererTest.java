package com.soebes.jdk22.gatherer;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Gatherer;

class GathererTest {

  static <T> Gatherer<T, ?, T> duplicates() {
    Gatherer.Integrator.Greedy<Map<T, Integer>, T, T> integrator =
        (state, element, downstream) -> {
          Integer occurrences =
              state.compute(element, (_, v) -> v == null ? 1 : v + 1);
          if (occurrences == 2) {
            return downstream.push(element);
          } else {
            return true;
          }
        };
    return Gatherer.ofSequential(
        HashMap::new,
        Gatherer.Integrator.ofGreedy(integrator)
    );
  }
  @Test
  void name() {
    List<Integer> numbers = List.of(7,1, 2, 7,1, 3, 4, 4, 1);
    List<Integer> unique = numbers.stream().distinct().toList();
    List<Integer> duplicates = numbers.stream().gather(duplicates()).toList();
    System.out.println(duplicates); // [1, 4]
    System.out.println("unique = " + unique);
    System.out.println("numbers = " + numbers);
  }
}
