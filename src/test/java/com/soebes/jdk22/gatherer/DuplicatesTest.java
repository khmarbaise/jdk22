package com.soebes.jdk22.gatherer;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Gatherer;

import static org.assertj.core.api.Assertions.assertThat;

class DuplicatesTest {

  @Test
  void exampleFindDuplicates() {
    var integers = List.of(100, 1, 10, 11, 5, 10, 11, 5, 100, 75, 78, 90);
    var duplicates = findDuplicates(integers);
    System.out.println("duplicates = " + duplicates);
  }

  List<Integer> findDuplicates(List<Integer> givenList) {
    long count = givenList.stream().distinct().count();
    if (count < givenList.size()) {
      return givenList.stream().filter(i -> Collections.frequency(givenList, i) > 1)
          .distinct().toList();
    } else {
      return List.of();
    }
  }

  @Test
  void exampleFindDuplicatesWithGatherer() {
    var integers = List.of(100, 1, 10, 11, 5, 10, 11, 5, 100, 75, 78, 90);
    var resultList = integers.stream().gather(duplicatesWithoutCombiner()).toList();

    assertThat(resultList).containsExactlyInAnyOrder(100, 10, 11, 5);
    System.out.println("resultList = " + resultList);
  }

  Supplier<HashMap<Integer, Integer>> supplier = HashMap::new;
  BiConsumer<HashMap<Integer, Integer>, Integer> accumulator = (state, element) -> {
    var orDefault = state.getOrDefault(element, 0);
    state.put(element, orDefault + 1);
  };
  BinaryOperator<HashMap<Integer, Integer>> combiner = (s1, s2) -> {
    s1.forEach((k, v) -> s2.put(k, v + s2.getOrDefault(k, 0)));
    return s2;
  };
  Function<HashMap<Integer, Integer>, List<Integer>> finisher = (acc) -> {
    var duplicateList = acc.entrySet().stream().filter(e -> e.getValue() >= 2).map(Map.Entry::getKey).toList();
    return List.copyOf(duplicateList);
  };

  @Test
  void exampleFindDuplicatedWithCollectorOf() {
    var integers = List.of(100, 1, 10, 11, 5, 10, 11, 5, 100, 75, 78, 90);

    // <R, A> R collect(Collector<? super T, A, R> collector);
    List<Integer> resultList = integers.stream()
        .collect(
            Collector.of(
                supplier, // A (supplier)
                accumulator,// A,T (accumulator)
                combiner, // A (combiner),
                finisher// A,R (finisher)
            )
        );

    assertThat(resultList).containsExactlyInAnyOrder(100, 10, 11, 5);
    System.out.println("resultList = " + resultList);

  }

  @Test
  void exampleFindDuplicatesWithGathererCombiner() {
    var integers = List.of(100, 1, 10, 11, 5, 10, 11, 5, 100, 75, 78, 90);
    var resultList = integers.parallelStream().gather(duplicates()).toList();

    assertThat(resultList).containsExactlyInAnyOrder(100, 10, 11, 5);
  }

  @Test
  void exampleFindDuplicatesWithGathererCombinerForStrings() {
    var integers = List.of("A", "BB", "A", "C", "BB", "DD", "EE", "F");
    var resultList = integers.parallelStream().gather(duplicates()).toList();

    assertThat(resultList).containsExactlyInAnyOrder("A", "BB");
  }

  // Gatherer<? super T, ?, R> gatherer
  static <T> Gatherer<? super T, ?, T> duplicates() {
    Supplier<HashMap<T, Integer>> initializer = HashMap::new;
    //
    Gatherer.Integrator<HashMap<T, Integer>, T, T> integrator = (state, element, _) -> {
      state.put(element, state.getOrDefault(element, 0) + 1);
      return true;
    };
    //
    BiConsumer<HashMap<T, Integer>, Gatherer.Downstream<? super T>> finisher = (state, downstream) -> {
      state.forEach((k, v) -> {
        if (v >= 2) {
          downstream.push(k);
        }
      });
    };
    //
    BinaryOperator<HashMap<T, Integer>> combiner = (s1, s2) -> {
      s1.forEach((k, v) -> s2.put(k, v + s2.getOrDefault(k, 0)));
      return s2;
    };
    //
    return Gatherer.of(initializer, integrator, combiner, finisher);
  }

  static <T> Gatherer<? super T, ?, T> duplicatesWithoutCombiner() {
    Supplier<HashMap<T, Integer>> initializer = HashMap::new;
    //
    Gatherer.Integrator<HashMap<T, Integer>, T, T> integrator = (state, element, _) -> {
      var orDefault = state.getOrDefault(element, 0);
      state.put(element, orDefault + 1);
      return true;
    };
    //
    BiConsumer<HashMap<T, Integer>, Gatherer.Downstream<? super T>> finisher = (state, downstream) -> {
      state.forEach((k, v) -> {
        if (v >= 2) {
          downstream.push(k);
        }
      });
    };
    //
    return Gatherer.ofSequential(initializer, integrator, finisher);
  }
}
