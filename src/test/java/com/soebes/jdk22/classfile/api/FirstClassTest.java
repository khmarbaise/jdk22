package com.soebes.jdk22.classfile.api;

import org.junit.jupiter.api.Test;

class FirstClassTest {

  @Test
  void name() {

  }

  @Test
  void ignore() {
    String s = "ABC";
    try {
      var _ = Integer.parseInt(s);
    } catch (NumberFormatException _) {
      System.out.println("Bad number: " + s);
    }
  }
}
