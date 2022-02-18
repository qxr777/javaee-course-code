package edu.whut.cs.javaee.spring.springbucks.waiter.util;

import com.alibaba.druid.filter.config.ConfigTools;

/**
 * @author qixin on 2022/2/17.
 * @version 1.0
 */
public class DruidPasswordEncrypt {
    public static void main(String[] args) throws Exception {
        // 需要加密的明文命名
        String password = "123456"; // 【注意：这里要改为你自己的密码】
        // 调用 druid 生成私钥、公钥、密文
        ConfigTools.main(new String[]{password});
    }
}
