package com.soebes.jdk22.gatherer.sealed;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Gatherer;

class KeepOnlyTest {

  sealed interface Element permits CustomElement, KnownElement { }
  sealed interface KnownElement extends Element { }
  non-sealed interface CustomElement extends Element { }
  sealed interface HtmlElement extends KnownElement { }
  sealed interface InternalElement extends KnownElement { }

  record Text(String value) implements InternalElement { }
  record HtmlLiteral(String value) implements InternalElement { }

  record Div(String value) implements HtmlElement { }
  record Paragraph(String value) implements HtmlElement { }
  record Span(String value) implements HtmlElement { }
  record Image(String value) implements HtmlElement { }
  record Anchor(String value) implements HtmlElement { }

  record FirstCustom(String value) implements CustomElement { }


  private static final List<Element> ELEMENT_LIST = List.of(
      new FirstCustom("FirstCustom"),
      new Div("Div1"),
      new Paragraph("P1"),
      new Span("Span1"),
      new Image("Image1"),
      new Anchor("https://google.de"),
      new Div("Div2"),
      new HtmlLiteral("Literal1"),
      new Text("Text1")
  );

  static <T> Gatherer<T, ?, T> keepOnly(Class<? extends Element> clazz) {
    Gatherer.Integrator<Void, T, T> integrator =
        (_, element, downstream) -> {
          if (clazz.isInstance(element)) {
            downstream.push(element);
          }
          return true;
        };
    return Gatherer.ofSequential(integrator);
  }
  static <T> Gatherer<T, ?, T> keepOnlyFirst(Class<? extends Element> clazz) {
    Gatherer.Integrator<Void, T, T> integrator =
        (_, element, downstream) -> {
          if (clazz.isInstance(element)) {
            downstream.push(element);
            return false;
          }
          return true;
        };
    return Gatherer.ofSequential(integrator);
  }

  @Test
  void filterWithGathererKeepOnly() {
    var result = ELEMENT_LIST.stream().gather(keepOnly(Div.class)).toList();
    System.out.println("result = " + result);
  }
  @Test
  void filterWithGathererKeepOnlyFirst() {
    var result = ELEMENT_LIST.stream().gather(keepOnlyFirst(Div.class)).toList();
    System.out.println("result = " + result);
  }
  @Test
  void filterWithStaticHelper() {
    var result = ELEMENT_LIST.stream().flatMap(HelpClass.keepOnly(Div.class)).toList();
    System.out.println("result = " + result);
  }
  @Test
  void filterWithMapMulti() {
    var result = ELEMENT_LIST.stream().mapMulti((Element v1, Consumer<Element> v2) -> {
      System.out.println("-".repeat(20));
      System.out.println("v1 = " + v1);
      System.out.println("v2 = " + v2);
      if (v1 instanceof Div) {
        v2.accept(v1);
      }
    }).toList();
    System.out.println("result = " + result);
  }

}
