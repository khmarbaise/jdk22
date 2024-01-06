package com.soebes.jdk22.gatherer;

import org.junit.jupiter.api.Test;

import java.util.List;

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

//  @Test
//  void second() {
//    var result = Stream.of("foo", "bar", "baz", "quux")
//        .gather(distinctBy(String::length))
//        .toList();
//    System.out.println("result = " + result);
//  }

  /**
   * Type parameters:
   * <T> – the type of input elements to the gatherer operation
   * <A> – the potentially mutable state type of the gatherer operation (often hidden as an implementation detail)
   * <R> – the type of output elements from the gatherer operation
   * ..public interface Gatherer<T, A, R>...
   *                             ^  ^  ^
   *                             !  !  !
   *                             !  !  +--- Result Type
   *                             !  +------ The State Type
   *                             +--------- Input Type
   **/
//// PECS Producer extends, Consumer super
//  // Ref: https://stackoverflow.com/questions/2723397/what-is-pecs-producer-extends-consumer-super
//  // The given type "Void" defines the type (A) for the "state" which is replaced by "_" because it's not used as all!
//  private static <T, A> Gatherer<? extends T, A, ? super T> distinctBy(Function<? super T, ? extends T> classifier) {
//    Gatherer.Integrator<? extends T, A, ? super T> integrator = (state, element, downstream) -> {
//      if (isNull(state)) {
//        return true;
//      }
//
//      T classifierAppliedState = classifier.apply(state);
//      A classifierAppliedElements = classifier.apply(element);
//      if (classifierAppliedState.equals(classifierAppliedElements)) {
//        downstream.push(element);
//      }
//      return true;
//    };
//    return Gatherer.ofSequential(integrator);
//  }
}
