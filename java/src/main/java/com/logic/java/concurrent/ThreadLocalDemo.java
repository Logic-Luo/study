package com.logic.java.concurrent;


import java.text.SimpleDateFormat;

/**
 * @author logic
 * @date 2019/6/17 9:24 AM
 * @since 1.0
 */
public class ThreadLocalDemo {
    static final ThreadLocal<SimpleDateFormat> tl = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

}
