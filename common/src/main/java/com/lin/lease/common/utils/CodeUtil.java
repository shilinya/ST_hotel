package com.lin.lease.common.utils;

import java.util.Random;

/**
 * 随机生成短信验证码
 */
public class CodeUtil {
    public static String getRandomCode(Integer length) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }
}
