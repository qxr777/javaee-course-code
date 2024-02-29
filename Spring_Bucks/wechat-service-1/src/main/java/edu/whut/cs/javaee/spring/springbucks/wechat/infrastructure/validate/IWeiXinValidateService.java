package edu.whut.cs.javaee.spring.springbucks.wechat.infrastructure.validate;

/**
 * @author 小傅哥，微信：fustack
 * @description 微信公众号验签服务
 * @github https://github.com/fuzhengwei
 * @Copyright 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
public interface IWeiXinValidateService {

    boolean checkSign(String signature, String timestamp, String nonce);

}
