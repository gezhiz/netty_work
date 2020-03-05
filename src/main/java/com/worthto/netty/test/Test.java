package com.worthto.netty.test;

import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;

/**
 * @author gezz
 * @description
 * @date 2020/3/2.
 */
public class Test {

    @org.junit.Test
    public void test() {
        System.out.println(NettyRuntime.availableProcessors() * 2);

        System.out.println(SystemPropertyUtil.getInt(
                "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));;
    }

}
