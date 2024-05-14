package com.mattel.ecomm.coreservices.core.enums;

import java.util.Arrays;
import java.util.stream.StreamSupport;

public enum StringTransformer {
  INSTANCE;
  private static final String EMPTY = "";

  public static String merge(Iterable<String> strings) {
    return StreamSupport.stream(strings.spliterator(), false).reduce(StringTransformer.EMPTY,
        StringTransformer::merge);
  }

  public static String merge(String... strings) {
    return Arrays.asList(strings).stream().reduce(StringTransformer.EMPTY,
        StringTransformer::merge);
  }

  public static String merge(String str1, String str2) {
    return new StringBuilder(str1).append(str2).toString();
  }

  public static String merge(String str1, String str2, String str3) {
    return new StringBuilder(str1).append(str2).append(str3).toString();
  }

  public static String mergePrefixOnCondition(boolean condition, String prefix, String str) {
    return condition && str.indexOf(prefix) == -1 ? StringTransformer.merge(prefix, str) : str;
  }

  public static String mergeSuffixOnCondition(boolean condition, String suffix, String str) {
    return condition && str.indexOf(suffix) == -1 ? StringTransformer.merge(str, suffix) : str;
  }

  public static String mergeWithDelimiter(String delimiter, Iterable<String> strings) {
    return String.join(delimiter, strings);
  }

  public static String mergeWithDelimiter(String delimiter, String... strings) {
    return String.join(delimiter, strings);
  }

  public static String mergeWithDelimiter(String delimiter, String str1, String str2) {
    return new StringBuilder(str1).append(delimiter).append(str2).toString();
  }

  public static String mergeWithDelimiter(String delimiter, String str1, String str2, String str3) {
    return new StringBuilder(str1).append(delimiter).append(str2).append(delimiter).append(str3)
        .toString();
  }

  public static String orElseOnCondition(boolean condition, String str, String other) {
    return condition ? str : other;
  }

  public static String substringOnConditon(boolean condition, int beginIndex, int endIndex,
      String str) {
    return condition && beginIndex >= 0 && endIndex <= str.length()
        ? str.substring(beginIndex, endIndex) : str;
  }

  public static String substringOnConditon(boolean condition, int beginIndex, String str) {
    return condition && beginIndex >= 0 ? str.substring(beginIndex) : str;
  }
}
