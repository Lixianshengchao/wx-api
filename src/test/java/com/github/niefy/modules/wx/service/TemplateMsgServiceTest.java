package com.github.niefy.modules.wx.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.WxMediaImgUploadResult;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import me.chanjar.weixin.mp.util.WxMpConfigStorageHolder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * 程序发送模板消息demo
 */
@SpringBootTest
class TemplateMsgServiceTest {
    @Autowired
    TemplateMsgService templateMsgService;

    @Autowired
    WxMpService wxMpService;



    /**
     * 发送模板消息给用户
     * 添加消息模板指引：https://kf.qq.com/faq/170209E3InyI170209nIF7RJ.html
     * 示例消息模板为：{{first.DATA}} ↵商品名称：{{keyword1.DATA}} ↵购买时间：{{keyword2.DATA}} ↵{{remark.DATA}}
     */
    @Test
    void sendTemplateMsg() {
        String appid = WxMpConfigStorageHolder.get();
        List<WxMpTemplateData> data  = new ArrayList<>();
        data.add(new WxMpTemplateData("first","模板消息测试"));
        data.add(new WxMpTemplateData("keywords1","xxxxx"));
        data.add(new WxMpTemplateData("keywords2","xxxxx"));
        data.add(new WxMpTemplateData("remark","点击查看消息详情"));
        WxMpTemplateMessage wxMpTemplateMessage = WxMpTemplateMessage.builder()
            .templateId("模板ID")
            .url("跳转链接")
            .toUser("用户openid")
            .data(data)
            .build();
        templateMsgService.sendTemplateMsg(wxMpTemplateMessage,appid);
    }

    @Test
    public void  testDownImgFromURL() throws WxErrorException {
        byte[] images = HttpUtil.downloadBytes("http://fc.jxncczx.com/t2/static/O1CN01SpleKe27KNB5jQbEg_!!109342.jpg");


        WxMediaImgUploadResult wxMediaImgUploadResult = wxMpService.getMaterialService().mediaImgUpload(FileUtil.writeBytes(images, "aaaa.jpg"));
        System.out.println(wxMediaImgUploadResult);
    }
}
