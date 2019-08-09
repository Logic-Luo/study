package com.logic.java.loader;

/**
 * @author logic
 * @date 2019-08-08 15:04
 * @since 1.0
 */
public class ClassLoaDerTree {
    public static void main(String[] args) {
        ClassLoader classLoader = ClassLoaDerTree.class.getClassLoader();

        while (classLoader != null) {
            System.out.println(classLoader.toString());
            classLoader = classLoader.getParent();
        }
    }
}
