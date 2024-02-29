package com.soebes.jdk22.gatherer.sealed;

import java.util.function.Function;
import java.util.stream.Stream;

final class HelpClass {

  static <E, T> Function<E, Stream<T>> keepOnly(Class<T> type) {
    return e -> type.isInstance(e) ? Stream.of(type.cast(e)) : Stream.empty();
  }

}
