package org.orglot.gosloto.admin.ui.utils;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NumbersHelper {

  public static final Set<Class<?>> BOOLEAN_VALUES = Stream
      .of(
          boolean.class,
          Boolean.class
      )
      .collect(Collectors.toSet());

  public static final Set<Class<?>> FLOATING_NUMBERS = Stream
      .of(
          float.class,
          double.class,
          Float.class,
          Double.class
      )
      .collect(Collectors.toSet());

  public static boolean checkFieldType(Class<?> fieldType, Class<?> object, Class<?> primitive){
    return object.equals(fieldType) || primitive.equals(fieldType);

  }

}
