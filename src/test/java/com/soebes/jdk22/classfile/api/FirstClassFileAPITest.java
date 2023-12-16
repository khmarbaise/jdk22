package com.soebes.jdk22.classfile.api;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.classfile.AccessFlags;
import java.lang.classfile.ClassElement;
import java.lang.classfile.ClassFile;
import java.lang.classfile.ClassFileVersion;
import java.lang.classfile.ClassModel;
import java.lang.classfile.CustomAttribute;
import java.lang.classfile.FieldModel;
import java.lang.classfile.Interfaces;
import java.lang.classfile.MethodModel;
import java.lang.classfile.Superclass;
import java.lang.classfile.attribute.CompilationIDAttribute;
import java.lang.classfile.attribute.DeprecatedAttribute;
import java.lang.classfile.attribute.EnclosingMethodAttribute;
import java.lang.classfile.attribute.InnerClassesAttribute;
import java.lang.classfile.attribute.ModuleAttribute;
import java.lang.classfile.attribute.ModuleHashesAttribute;
import java.lang.classfile.attribute.ModuleMainClassAttribute;
import java.lang.classfile.attribute.ModulePackagesAttribute;
import java.lang.classfile.attribute.ModuleResolutionAttribute;
import java.lang.classfile.attribute.ModuleTargetAttribute;
import java.lang.classfile.attribute.NestHostAttribute;
import java.lang.classfile.attribute.NestMembersAttribute;
import java.lang.classfile.attribute.PermittedSubclassesAttribute;
import java.lang.classfile.attribute.RecordAttribute;
import java.lang.classfile.attribute.RuntimeInvisibleAnnotationsAttribute;
import java.lang.classfile.attribute.RuntimeInvisibleTypeAnnotationsAttribute;
import java.lang.classfile.attribute.RuntimeVisibleAnnotationsAttribute;
import java.lang.classfile.attribute.RuntimeVisibleTypeAnnotationsAttribute;
import java.lang.classfile.attribute.SignatureAttribute;
import java.lang.classfile.attribute.SourceDebugExtensionAttribute;
import java.lang.classfile.attribute.SourceFileAttribute;
import java.lang.classfile.attribute.SourceIDAttribute;
import java.lang.classfile.attribute.SyntheticAttribute;
import java.lang.classfile.attribute.UnknownAttribute;
import java.nio.file.Path;

class FirstClassFileAPITest {

  @Test
  void xxx() throws IOException {
    ClassModel cm = ClassFile.of().parse(Path.of("target/classes/com/soebes/jdk22/FirstClass.class"));
    for (FieldModel fm : cm.fields())
      System.out.printf("Field %s%n", fm.fieldName().stringValue());
    for (MethodModel mm : cm.methods())
      System.out.printf("Method %s%n", mm.methodName().stringValue());
  }

  @Test
  void name() throws IOException {
    ClassModel cm = ClassFile.of().parse(Path.of("target/classes/com/soebes/jdk22/FirstClass.class"));
    for (ClassElement ce : cm) {
      switch (ce) {
        case MethodModel mm -> System.out.printf("MethodModel %s%n", mm.methodName().stringValue());
        case FieldModel fm -> System.out.printf("FieldModel %s%n", fm.fieldName().stringValue());
        case AccessFlags af -> System.out.printf("AccessFlags %s%n", af.flags());
        case ClassFileVersion cfv -> System.out.printf("ClassFileVersion %s %s%n", cfv.majorVersion(), cfv.minorVersion());
        case CustomAttribute ca -> System.out.printf("CustomAttribute %s %s%n", ca.attributeName(), ca.attributeMapper());
        case Interfaces i -> System.out.printf("Interfaces %s%n", i.interfaces());
        case Superclass sc -> System.out.printf("Superclass %s%n", sc.superclassEntry());
        case CompilationIDAttribute cIDA -> System.out.printf("CompilationIDAttribute %s%n", cIDA.compilationId());
        case DeprecatedAttribute deprecatedAttribute -> {
        }
        case EnclosingMethodAttribute enclosingMethodAttribute -> {
        }
        case InnerClassesAttribute innerClassesAttribute -> {
        }
        case ModuleAttribute moduleAttribute -> {
        }
        case ModuleHashesAttribute moduleHashesAttribute -> {
        }
        case ModuleMainClassAttribute moduleMainClassAttribute -> {
        }
        case ModulePackagesAttribute modulePackagesAttribute -> {
        }
        case ModuleResolutionAttribute moduleResolutionAttribute -> {
        }
        case ModuleTargetAttribute moduleTargetAttribute -> {
        }
        case NestHostAttribute nestHostAttribute -> {
        }
        case NestMembersAttribute nestMembersAttribute -> {
        }
        case PermittedSubclassesAttribute permittedSubclassesAttribute -> {
        }
        case RecordAttribute recordAttribute -> {
        }
        case RuntimeInvisibleAnnotationsAttribute runtimeInvisibleAnnotationsAttribute -> {
        }
        case RuntimeInvisibleTypeAnnotationsAttribute runtimeInvisibleTypeAnnotationsAttribute -> {
        }
        case RuntimeVisibleAnnotationsAttribute runtimeVisibleAnnotationsAttribute -> {
        }
        case RuntimeVisibleTypeAnnotationsAttribute runtimeVisibleTypeAnnotationsAttribute -> {
        }
        case SignatureAttribute signatureAttribute -> {
        }
        case SourceDebugExtensionAttribute sourceDebugExtensionAttribute -> {
        }
        case SourceFileAttribute sourceFileAttribute -> {
        }
        case SourceIDAttribute sourceIDAttribute -> {
        }
        case SyntheticAttribute syntheticAttribute -> {
        }
        case UnknownAttribute unknownAttribute -> {
        }
      }
    }
  }

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
