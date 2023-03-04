package edu.whut.cs.jee.spring.data.mysqldemo;

import com.alibaba.druid.filter.config.ConfigTools;

/**
 * @author qixin on 2023/3/4.
 * @version 1.0
 */
public class DruidPasswordTest {
    public static void main(String[] args) throws Exception {
        // 生成公钥和私钥
        String[] keyPair = ConfigTools.genKeyPair(512);
        // 公钥
        String publicKey = keyPair[0];
        // 私钥
        String privateKey = keyPair[1];
        // 数据库密码
        String password = "123456";
        // 使用公钥加密密码，得到密文
        String encryptPassword = ConfigTools.encrypt(publicKey, password);

        System.out.println("privateKey:" + publicKey);
        System.out.println("publicKey:" + privateKey);
//        System.out.println("publicKey:" + publicKey);
//        System.out.println("privateKey:" + privateKey);
        System.out.println("password:" + password);
        System.out.println("encryptPassword:" + encryptPassword);

    }
}
