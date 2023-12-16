package com.soebes.jdk22;

import java.math.BigInteger;

import static java.lang.System.out;

public class FirstClass {

  private final String name;
  private final String sureName;

  public FirstClass(String name, String sureName) {
    this.name = name;
    this.sureName = sureName;
  }

  public String getName() {
    return name;
  }

  public String getSureName() {
    return sureName;
  }

  public void x() {
    out.println("x");
    var bigInteger = BigInteger.valueOf(200L);
    out.println("bigInteger = " + bigInteger);
  }
}
