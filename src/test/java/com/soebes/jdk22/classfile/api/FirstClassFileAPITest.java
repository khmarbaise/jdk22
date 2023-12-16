package com.soebes.jdk22.classfile.api;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.classfile.ClassFile;
import java.lang.classfile.ClassModel;
import java.nio.file.Path;

class FirstClassFileAPITest {

  @Test
  void analyseAFinalClass() throws IOException {
    ClassModel cm = ClassFile.of().parse(Path.of("target/classes/com/soebes/jdk22/FirstClass.class"));

    System.out.println("cm.isModuleInfo() = " + cm.isModuleInfo());
    System.out.printf("cm.majorVersion(): %d(%d) %n", cm.majorVersion(), cm.minorVersion());
    cm.fields().forEach(s -> {
      var classDesc = s.fieldTypeSymbol();
      System.out.println("classDesc = " + classDesc);
      System.out.printf("fieldName: %s (Class:%s)%n", s.fieldName(), s.getClass().getName());
      s.attributes().forEach(at -> System.out.printf("attributeName: %s (Class:%s)%n", at.attributeName(), at.getClass().getName()));
      s.elementList().forEach(fe -> System.out.println("fe = " + fe.getClass().getName()));
    });
    cm.methods().forEach(s -> {
      System.out.printf("methodName: %s%n", s.methodName());
      s.attributes().forEach(at -> System.out.println("at = " + at.attributeName()));
    });
    cm.interfaces().forEach(s -> System.out.printf("interfaces: %s%n", s.name()));
    cm.superclass().ifPresent(s -> System.out.printf("superclass: %s%n", s.name()));
  }
}
