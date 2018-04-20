package cn.zhou.utils;

import cn.zhou.exception.CommonException;

public class Assert {
    public static void notNull(String str) {
        if (StringUtil.isEmpty(str)) {
            throw new CommonException("");
        }
    }
}
