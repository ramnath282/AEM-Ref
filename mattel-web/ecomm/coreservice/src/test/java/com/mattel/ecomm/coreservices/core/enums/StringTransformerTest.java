package com.mattel.ecomm.coreservices.core.enums;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

public class StringTransformerTest {

  String[] s = { "a", "b", "c", "d" };

  @Test
  public void test_overloaded_merge() {
    Assert.assertEquals("abcd", StringTransformer.merge(s));
    Assert.assertEquals("ab", StringTransformer.merge(s[0], s[1]));
    Assert.assertEquals("abc", StringTransformer.merge(s[0], s[1], s[2]));
    Assert.assertEquals("abca", StringTransformer.merge(s[0], s[1], s[2], s[0]));
  }

  @Test
  public void test_mergePrefixOnCondition() {
    assertEquals("My_name", StringTransformer.mergePrefixOnCondition(true, "a", "My_name"));
    assertEquals("My_name", StringTransformer.mergePrefixOnCondition(false, "a", "My_name"));
    assertEquals("kMy_name", StringTransformer.mergePrefixOnCondition(true, "k", "My_name"));
    assertEquals("My_name", StringTransformer.mergePrefixOnCondition(false, "k", "My_name"));
  }

  @Test
  public void test_mergeSuffixOnCondition() {
    assertEquals("My_name", StringTransformer.mergeSuffixOnCondition(true, "a", "My_name"));
    assertEquals("My_name", StringTransformer.mergeSuffixOnCondition(false, "a", "My_name"));
    assertEquals("My_namek", StringTransformer.mergeSuffixOnCondition(true, "k", "My_name"));
    assertEquals("My_name", StringTransformer.mergeSuffixOnCondition(false, "k", "My_name"));
  }

  @Test
  public void test_mergeWithDelimiter() {
    assertEquals("a,b,c,d", StringTransformer.mergeWithDelimiter(",", s));
    assertEquals("a,b,c,a", StringTransformer.mergeWithDelimiter(",", s[0], s[1], s[2], s[0]));
    assertEquals("a,b", StringTransformer.mergeWithDelimiter(",", s[0], s[1]));
    assertEquals("a,b,c", StringTransformer.mergeWithDelimiter(",", s[0], s[1], s[2]));
  }

  @Test
  public void test_orElseOnCondition() {
    assertEquals("a", StringTransformer.orElseOnCondition(true, "a", "other"));
    assertEquals("other", StringTransformer.orElseOnCondition(false, "a", "other"));
  }

  @Test
  public void test_substringOnConditon_3args() {
    assertEquals("ringTransform",
        StringTransformer.substringOnConditon(true, 2, "StringTransform"));
    assertEquals("StringTransform",
        StringTransformer.substringOnConditon(false, 2, "StringTransform"));
    assertEquals("StringTransform",
        StringTransformer.substringOnConditon(false, 999, "StringTransform"));
  }

  @Test
  public void test_substringOnConditon_4args() {

    assertEquals("Transform",
        StringTransformer.substringOnConditon(true, 6, 15, "StringTransform"));
    assertEquals("StringTransform",
        StringTransformer.substringOnConditon(true, -1, 15, "StringTransform"));
    assertEquals("StringTransform",
        StringTransformer.substringOnConditon(true, 6, 999, "StringTransform"));
    assertEquals("StringTransform",
        StringTransformer.substringOnConditon(true, -1, 999, "StringTransform"));

    assertEquals("StringTransform",
        StringTransformer.substringOnConditon(false, 6, 15, "StringTransform"));
    assertEquals("StringTransform",
        StringTransformer.substringOnConditon(false, -1, 15, "StringTransform"));
    assertEquals("StringTransform",
        StringTransformer.substringOnConditon(false, 6, 999, "StringTransform"));
    assertEquals("StringTransform",
        StringTransformer.substringOnConditon(false, -1, 999, "StringTransform"));
  }

  @Test(expected = StringIndexOutOfBoundsException.class)
  public void test_substringOnConditon_throwsException() {
    StringTransformer.substringOnConditon(true, 999, "StringTransform");
  }
}