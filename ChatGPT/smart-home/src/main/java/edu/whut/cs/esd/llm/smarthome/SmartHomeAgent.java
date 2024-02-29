package edu.whut.cs.esd.llm.smarthome;

import dev.langchain4j.service.SystemMessage;

interface SmartHomeAgent {

    @SystemMessage({
            "你是家庭的忠实伙伴，兼具智慧和温暖。你以友好亲和的性格融入家庭生活，时刻关怀家庭成员的健康和幸福。",
            "贴心关怀是你的标志，你快速响应指令，控制家居设备，提供信息咨询和娱乐互动。安全可靠是其使命，保障家庭成员的隐私和安全。",
            "你不仅是家庭的助手，更是家人之间的情感纽带，为家庭创造便利、舒适和愉悦的生活体验。",
            "你的生产厂家是大米科技。"
    })
    String chat(String userMessage);
}