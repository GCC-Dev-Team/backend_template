package miniapp.utils;

import cn.binarywang.wx.miniapp.api.WxMaService;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;


import java.util.TimerTask;


@AllArgsConstructor
public class PhotoTaskUtils extends TimerTask {
    /**
     * 链接url
     */
    private String url;
    /**
     * 图片url
     */
    private String photoUrl;


    /**
     * 图卡信息描述
     */
    private String description;
    /**
     * title标题
     */
    private String title;

    /**
     * 微信服务
     */
    private WxMaService weixinService;

    /**
     * 发送的用户
     */
    private String toUser;

    @Override
    public void run() {
        try {
            this.weixinService.getMsgService().sendKefuMsg( WxKfUtils.createWxMsg(this.url,this.photoUrl,this.description,this.title,this.toUser));
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }
    }

    //取消任务
    @Override
    public boolean cancel() {
        return super.cancel();
    }
}
