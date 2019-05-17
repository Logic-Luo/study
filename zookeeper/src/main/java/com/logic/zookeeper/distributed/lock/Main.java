package com.logic.zookeeper.distributed.lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 分布式锁主函数
 *
 * @author logic
 * @date 2019/5/17 9:16 PM
 * @since 1.0
 */
public class Main {
    public static void main(String[] args) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

    }
}
