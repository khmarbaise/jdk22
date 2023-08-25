package com.soebes.jdk22;

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
}
