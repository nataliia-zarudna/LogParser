package com.nzarudna.logparser.model;

public class TestUtils {

    public static <T> T mock(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        return clazz.newInstance();
    }
}
