package com.codetaylor.mc.dropt.modules.dropt.compat.crafttweaker;

import com.codetaylor.mc.athenaeum.util.StringHelper;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ExportDocumentation {

  public static void main(String[] args) {

    ExportDocumentation export = new ExportDocumentation();
    export.run();
  }

  private void run() {

    Class[] classes = new Class[]{
        ZenDropt.class,
        ZenRuleList.class,
        ZenRule.class,
        ZenHarvester.class,
        ZenDrop.class
    };

    for (int i = 0; i < classes.length; i++) {
      StringBuilder out = new StringBuilder();

      ZenDocClass zenClass = (ZenDocClass) classes[i].getDeclaredAnnotation(ZenDocClass.class);

      if (zenClass == null) {
        continue;
      }

      if (i > 0) {
        out.append("\n");
      }

      // --- Header

      String[] h3 = zenClass.value().split("\\.");
      String zenClassName = h3[h3.length - 1];
      out.append("### Class\n");
      out.append("\n");

      // --- Import

      out.append("```java").append("\n");
      out.append("import ").append(zenClass.value()).append("\n");
      out.append("```").append("\n");
      out.append("\n");

      // --- Class Description

      out.append("<div class=\"zen-description zen-class-description\">\n");
      String[] description = zenClass.description();

      if (description.length > 0) {

        for (String line : description) {
          out.append(this.parse(line)).append("<br/>\n");
        }
        out.append("\n");
      }
      out.append("</div>\n");

      // --- Methods

      out.append("#### Methods\n");
      out.append("\n");

      Method[] methods = classes[i].getDeclaredMethods();
      List<MethodAnnotationPair> methodList = this.getSortedMethodList(methods);

      // Add static methods to new list.
      List<MethodAnnotationPair> staticMethodList = methodList.stream()
          .filter(pair -> Modifier.isStatic(pair.method.getModifiers()))
          .collect(Collectors.toList());

      // Remove static methods from main list.
      methodList = methodList.stream()
          .filter(pair -> !Modifier.isStatic(pair.method.getModifiers()))
          .collect(Collectors.toList());

      // --- Static Methods

      if (!staticMethodList.isEmpty()) {
        this.writeMethodList(out, staticMethodList);
      }

      // --- Methods

      if (!methodList.isEmpty()) {
        this.writeMethodList(out, methodList);
      }

      // --- Output

      try {
        Files.write(Paths.get("docs/zs/" + zenClassName.toLowerCase() + ".md"), out.toString().getBytes());

      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

  private void writeMethodList(StringBuilder out, List<MethodAnnotationPair> staticMethodList) {

    for (int j = 0; j < staticMethodList.size(); j++) {

      if (j > 0) {
        out.append("\n");
      }

      this.writeMethod(out, staticMethodList.get(j).method, staticMethodList.get(j).annotation);
    }
  }

  private void writeMethod(StringBuilder out, Method method, ZenDocMethod annotation) {

    String methodName = method.getName();
    Class<?> returnType = method.getReturnType();
    String returnTypeString = this.getSimpleTypeString(returnType);

    out.append("```java").append("\n");

    if (Modifier.isStatic(method.getModifiers())) {
      out.append("static ");
    }

    // Method return type and name
    out.append(returnTypeString).append(" ").append(methodName).append("(");

    Class[] types = method.getParameterTypes();
    String[] names = annotation.args();

    if (types.length != names.length) {
      throw new IllegalStateException("Wrong number of parameter names found for method: " + methodName);
    }

    for (int k = 0; k < types.length; k++) {
      String typeString = this.getSimpleTypeString(types[k]);
      String nameString = names[k];
      out.append(typeString).append(" ").append(nameString);

      if (k < types.length - 1) {
        out.append(", ");
      }
    }

    out.append(");\n");

    out.append("```").append("\n");

    out.append("<div class=\"zen-description zen-method-description\">\n");

    String[] description = annotation.description();

    if (description.length > 0) {

      for (String line : description) {
        out.append(this.parse(line));
      }
    }
    out.append("</div>\n");
  }

  private String parse(String line) {

    if (line.startsWith("@see")) {
      String[] links = line.substring(4).trim().split(" ");

      StringBuilder sb = new StringBuilder("<br/>For more information, see:\n<ul>");

      for (String link : links) {
        sb.append("<li>");
        sb.append("<a href=\"").append(link).append("\">").append(link).append("</a>");
        sb.append("</li>");
      }
      sb.append("</ul>");

      return sb.toString();
    }

    return line + "<br/>\n";
  }

  private List<MethodAnnotationPair> getSortedMethodList(Method[] methods) {

    List<MethodAnnotationPair> methodList = new ArrayList<>();

    for (int j = 0; j < methods.length; j++) {
      ZenDocMethod annotation = methods[j].getDeclaredAnnotation(ZenDocMethod.class);

      if (annotation != null) {
        methodList.add(new MethodAnnotationPair(methods[j], annotation));
      }
    }

    methodList.sort(Comparator.comparingInt(o -> o.annotation.order()));
    return methodList;
  }

  private String getSimpleTypeString(Class type) {

    String result = type.getSimpleName();

    if (result.startsWith("Zen")) {
      result = result.substring(3);

    } else if (result.startsWith("String")) {
      result = StringHelper.lowercaseFirstLetter(result);
    }
    return result;
  }

  private static class MethodAnnotationPair {

    public final Method method;
    public final ZenDocMethod annotation;

    private MethodAnnotationPair(Method method, ZenDocMethod annotation) {

      this.method = method;
      this.annotation = annotation;
    }
  }

}
