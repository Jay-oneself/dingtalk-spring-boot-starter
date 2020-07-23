/*
 * Copyright(c) 2015-2019, AnswerAIL
 * ShenZhen Answer.AI.L Technology Co., Ltd.
 * All rights reserved.
 *
 * <a>https://github.com/AnswerAIL/</a>
 *
 */
package com.jaemon.dingtalk;


import com.alibaba.fastjson.JSON;
import com.jaemon.dingtalk.config.HttpClient;
import com.jaemon.dingtalk.entity.DingTalkProperties;
import com.jaemon.dingtalk.entity.Message;
import com.jaemon.dingtalk.entity.RequestHeader;
import com.jaemon.dingtalk.exception.DingTalkException;
import com.jaemon.dingtalk.support.CustomMessage;
import com.jaemon.dingtalk.support.Notice;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static com.jaemon.dingtalk.entity.Message.MSG_TEXT;

/**
 * 钉钉机器人消息推送工具类
 *
 * @author Jaemon@answer_ljm@163.com
 * @version 1.0
 */
public class DingTalkRobot {

    @Autowired
    private HttpClient httpClient;
    @Autowired
    private Notice notice;
    @Autowired
    private CustomMessage customMessage;

    private DingTalkProperties dingTalkProperties;

    public DingTalkRobot(DingTalkProperties dingTalkProperties) {
        this.dingTalkProperties = dingTalkProperties;
    }

    /**
     * 发送预警消息到钉钉
     *
     * @param keyword 关键词(方便定位日志)
     * @param content 消息内容
     * @return 响应报文
     * */
    public String send(String keyword, String content) {
        String text = customMessage.message(dingTalkProperties, keyword, content);
        Message message = Message.builder()
                .msgtype(MSG_TEXT)
                .text(new Message.Text(text)).build();

        return send(keyword, message);
    }


    /**
     * 发送预警消息到钉钉-消息指定艾特人电话信息
     *
     * @param keyword 关键词(方便定位日志)
     * @param content 消息内容
     * @param phones 艾特人电话集
     * @return 响应报文
     * */
    public String send(String keyword, String content, List<String> phones) {
        String text = customMessage.message(dingTalkProperties, keyword, content);
        Message message = Message.builder()
                .msgtype(MSG_TEXT)
                .text(new Message.Text(text))
                .at(new Message.At(phones)).build();

        return send(keyword, message);
    }


    /**
     * 发送预警消息到钉钉-艾特所有人
     *
     * @param keyword 关键词(方便定位日志)
     * @param content 消息内容
     * @return 响应报文
     * */
    public String sendAll(String keyword, String content) {
        String text = customMessage.message(dingTalkProperties, keyword, content);
        Message message = Message.builder()
                .msgtype(MSG_TEXT)
                .text(new Message.Text(text))
                .at(new Message.At(true)).build();

        return send(keyword, message);
    }


    private String send(String keyword, Message message) {
        try {
            String tokenId = dingTalkProperties.getTokenId();
            String webhook = MessageFormat.format("{0}={1}", dingTalkProperties.getRobotUrl(), tokenId);

            RequestHeader headers = new RequestHeader();
            RequestHeader.Pairs pairs = new RequestHeader.Pairs("Content-Type", "application/json; charset=utf-8");
            ArrayList<RequestHeader.Pairs> list = new ArrayList<>();
            list.add(pairs);
            headers.setData(list);

            return httpClient.doPost(webhook, headers, JSON.toJSONString(message), HttpClient.HC_JSON);
        } catch (DingTalkException e) {
            notice.callback(dingTalkProperties, keyword, message, e);
        }
        return null;
    }

}