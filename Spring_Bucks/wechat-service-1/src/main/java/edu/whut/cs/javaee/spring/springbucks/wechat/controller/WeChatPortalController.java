package edu.whut.cs.javaee.spring.springbucks.wechat.controller;

import edu.whut.cs.javaee.spring.springbucks.wechat.infrastructure.util.MessageTextEntity;
import edu.whut.cs.javaee.spring.springbucks.wechat.infrastructure.util.XmlUtil;
import edu.whut.cs.javaee.spring.springbucks.wechat.infrastructure.validate.IWeiXinValidateService;
import edu.whut.cs.javaee.spring.springbucks.wechat.llm.CustomerSupportAgent;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/portal/{appid}")
public class WeChatPortalController {
    private final Logger logger = LoggerFactory.getLogger(WeChatPortalController.class);
    @Value("${wx.config.originalid}")
    private String originalId;

    @Autowired
    private CustomerSupportAgent agent;

    @Resource
    private IWeiXinValidateService weiXinValidateService;

//    private List<String> messageIDs = new ArrayList<>();
    // 存放OpenAi返回结果数据
    private final Map<String, String> openAiDataMap = new ConcurrentHashMap<>();

    public WeChatPortalController() {

        logger.info("开始 openAiSession");
    }

    /**
     * 处理微信服务器发来的get请求，进行签名的验证
     * http://xfg-studio.natapp1.cc/wx/portal/wx4bd388e42758df34
     * <p>
     * appid     微信端AppID
     * signature 微信端发来的签名
     * timestamp 微信端发来的时间戳
     * nonce     微信端发来的随机字符串
     * echostr   微信端发来的验证字符串
     */
    @GetMapping(produces = "text/plain;charset=utf-8")
    public String validate(@PathVariable String appid,
                           @RequestParam(value = "signature", required = false) String signature,
                           @RequestParam(value = "timestamp", required = false) String timestamp,
                           @RequestParam(value = "nonce", required = false) String nonce,
                           @RequestParam(value = "echostr", required = false) String echostr) {
        try {
            logger.info("微信公众号验签信息{}开始 [{}, {}, {}, {}]", appid, signature, timestamp, nonce, echostr);
            if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
                throw new IllegalArgumentException("请求参数非法，请核实!");
            }
            boolean check = weiXinValidateService.checkSign(signature, timestamp, nonce);
            logger.info("微信公众号验签信息{}完成 check：{}", appid, check);
            if (!check) {
                return null;
            }
            return echostr;
        } catch (Exception e) {
            logger.error("微信公众号验签信息{}失败 [{}, {}, {}, {}]", appid, signature, timestamp, nonce, echostr, e);
            return null;
        }
    }

    /**
     * 此处是处理微信服务器的消息转发的
     */
    @PostMapping(produces = "application/xml; charset=UTF-8")
    public String post(@PathVariable String appid,
                       @RequestBody String requestBody,
                       @RequestParam("signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce,
                       @RequestParam("openid") String openid,
                       @RequestParam(name = "encrypt_type", required = false) String encType,
                       @RequestParam(name = "msg_signature", required = false) String msgSignature) {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            logger.info("接收微信公众号信息请求{}开始 {}", openid, requestBody);
            MessageTextEntity message = XmlUtil.xmlToBean(requestBody, MessageTextEntity.class);
            if (openAiDataMap.get(message.getContent().trim()) == null || "NULL".equals(openAiDataMap.get(message.getContent().trim()))) {
                String agentMessage = "消息处理中，请再回复我一句【" + message.getContent().trim() + "】";
                openAiDataMap.put(message.getContent().trim(), agentMessage);
//            if (!messageIDs.contains(message.getMsgId())) {
//                messageIDs.add(message.getMsgId());
                agentMessage = agent.chat(message.getContent().trim());
//                String agentMessage = "根据您提供的信息，我找到了订单编号为1的预订详情：  - 订单编号：1 - 客户：spring-8090 - 预订项目：   - 名称：capuccino   - 创建时间：2024-01-26 11:52:10   - 更新时间：2024-01-26 11:52:10 - 状态：已取";
                agentMessage = agentMessage.replace('\n', ' ');
//                System.out.println("Agent: " + agentMessage);
                openAiDataMap.put(message.getContent().trim(), agentMessage);
                // 反馈信息[文本]
                MessageTextEntity res = new MessageTextEntity();
                res.setToUserName(openid);
                res.setFromUserName(originalId);
                res.setCreateTime(String.valueOf(System.currentTimeMillis() / 1000L));
                res.setMsgType("text");
                res.setContent(agentMessage);
                String result = XmlUtil.beanToXml(res);
                logger.info("接收微信公众号信息请求{}完成 {}", openid, result);
                logger.info("耗时（s）：" + (System.currentTimeMillis() - currentTimeMillis) / 1000.0);
                return result;
            } else {
                Thread.sleep(3000);
                String agentMessage = openAiDataMap.get(message.getContent().trim());
                // 反馈信息[文本]
                MessageTextEntity res = new MessageTextEntity();
                res.setToUserName(openid);
                res.setFromUserName(originalId);
                res.setCreateTime(String.valueOf(System.currentTimeMillis() / 1000L));
                res.setMsgType("text");
                res.setContent(agentMessage);
                String result = XmlUtil.beanToXml(res);
                logger.info("第二次接收微信公众号信息请求{}完成 {}", openid, result);
                logger.info("耗时（s）：" + (System.currentTimeMillis() - currentTimeMillis) / 1000.0);
                openAiDataMap.remove(message.getContent().trim());
                return result;
            }
        } catch (Exception e) {
            logger.error("接收微信公众号信息请求{}失败 {}", openid, requestBody, e);
            return "";
        }
    }


}
