package com.soebes.jdk22.gatherer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Gatherer;

class FixedGroupTest {

  /**
   * Type parameters:
   * <T> – the type of input elements to the gatherer operation
   * <A> – the potentially mutable state type of the gatherer operation (often hidden as an implementation detail)
   * <R> – the type of output elements from the gatherer operation
   * ..public interface Gatherer<T, A, R>...
   **/
  // PECS Producer extends, Consumer super
  // Ref: https://stackoverflow.com/questions/2723397/what-is-pecs-producer-extends-consumer-super
  //                          +----------------------- The input elements
  //                          !      +---------------- The state
  //                          !      !           +---- Output elements
  //                          !      !           !
  //                          v      v           v
  static private <T> Gatherer<T, List<T>, List<T>> fixedGroup(int windowSize) {
    Supplier<List<T>> initializer = ArrayList::new;
    Gatherer.Integrator<List<T>, T, List<T>> integrator = (state, element, downstream) -> {
      state.add(element);
      if (state.size() == windowSize) {
        downstream.push(List.copyOf(state));
        state.clear();
      }
      return true;
    };

    BiConsumer<List<T>, Gatherer.Downstream<? super List<T>>> finisher = (state, downstream) -> {
      if (!state.isEmpty()) {
        downstream.push(List.copyOf(state));
      }
    };

    return Gatherer.ofSequential(initializer, integrator, finisher);
  }

  @Test
  void name() {
    List<Integer> numbers = List.of(7,1, 2, 7,1, 3, 4, 4, 1);
    System.out.println("numbers = " + numbers);
    var groups = numbers.stream().gather(fixedGroup(3)).toList();
    System.out.println("groups = " + groups);
  }

  /*
    public static<T, A, R> Collector<T, A, R> of(Supplier<A> supplier,
                                                 BiConsumer<A, T> accumulator,
                                                 BinaryOperator<A> combiner,
                                                 Function<A, R> finisher,
                                                 Characteristics... characteristics) {

   */
//  static <T, A, R> Collector<T, A, R> fixedGroupViaCollectorOf(int size) {
//    return Collector.of(
//        ArrayList::new,
//        (state, element) -> {
//          state.add(element);
//          if (state.size() == size) {
//            return List.copyOf(state);
//            state.clear();
//          }
//          return true;
//        },
//        (_, _) -> {
//          throw new UnsupportedOperationException("Cannot be parallelized");
//        }
//    );
//  }

  @Test
  void fixedGroupWithCollectorOf() {
    List<Integer> numbers = List.of(7,1, 2, 7,1, 3, 4, 4, 1);
    System.out.println("numbers = " + numbers);
//    var groups = numbers.stream().collect(fixedGroupViaCollectorOf(3));
//    System.out.println("groups = " + groups);
  }


}
