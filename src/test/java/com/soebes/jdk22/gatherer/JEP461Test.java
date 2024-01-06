package com.soebes.jdk22.gatherer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Stream;

class JEP461Test {

  record DistinctByLength(String str) {

    @Override public boolean equals(Object obj) {
      return obj instanceof DistinctByLength(String other)
             && str.length() == other.length();
    }

    @Override public int hashCode() {
      return str == null ? 0 : Integer.hashCode(str.length());
    }

  }

  @Test
  void example_one() {
    var result = Stream.of("foo", "bar", "baz", "quux")
        .map(DistinctByLength::new)
        .distinct()
        .map(DistinctByLength::str)
        .toList();

    System.out.println("result = " + result);
  }

//  private static<T> Collector<T, ArrayList<T>, ArrayList<T>> window(int size){
//    return Collector.of(
//        () -> new ArrayList<List<T>>(),
//        (groups, element) -> {
//          if (groups.isEmpty() || groups.getLast().size() == 3) {
//            var current = new ArrayList<Integer>();
//            current.add(element);
//            groups.addLast(current);
//          } else {
//            groups.getLast().add(element);
//          }
//        },
//        (_, _) -> {
//          throw new UnsupportedOperationException("Cannot be parallelized");
//        }
//    );
//  }


    @Test
  void example_window() {
    var result = Stream.iterate(0, i -> i + 1)
        .limit(3 * 2)
        .collect(Collector.of(
            () -> new ArrayList<List<Integer>>(),
            (groups, element) -> {
              if (groups.isEmpty() || groups.getLast().size() == 3) {
                var current = new ArrayList<Integer>();
                current.add(element);
                groups.addLast(current);
              } else {
                groups.getLast().add(element);
              }
            },
            (_, _) -> {
              throw new UnsupportedOperationException("Cannot be parallelized");
            }
        ));

    System.out.println("result = " + result);
// result ==> [[0, 1, 2], [3, 4, 5]]
  }
}
