package miniapp.utils;


import cn.binarywang.wx.miniapp.bean.WxMaKefuMessage;
import cn.binarywang.wx.miniapp.builder.LinkMessageBuilder;
import cn.binarywang.wx.miniapp.builder.TextMessageBuilder;

public class WxKfUtils {


    /**
     * 创建图文信息(客服消息)
     * @param url url
     * @param photoUrl 照片url
     * @param description 描述
     * @param title 标题
     * @param toUser 发送的openid(用户的openid)
     * @return
     */
    public static WxMaKefuMessage createWxMsg(String url, String photoUrl, String description, String title, String toUser){
//
        LinkMessageBuilder linkMessageBuilder = WxMaKefuMessage.newLinkBuilder().title(title).description(description).url(url)
                .toUser(toUser)
                .thumbUrl(photoUrl);

        return linkMessageBuilder.build();
    }


    /**
     * 创建文字消息
     * @param toUser 用户的openid
     * @param text 文本内容
     * @return
     */
    public static WxMaKefuMessage createTextKfMessage(String toUser,String text){
        TextMessageBuilder textMessageBuilder = WxMaKefuMessage.newTextBuilder();

        textMessageBuilder.content(text);
        textMessageBuilder.toUser(toUser);

        return textMessageBuilder.build();

    }
}
