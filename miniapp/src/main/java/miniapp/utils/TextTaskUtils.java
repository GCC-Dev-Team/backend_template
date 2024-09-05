package miniapp.utils;

import cn.binarywang.wx.miniapp.api.WxMaService;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;


import java.util.TimerTask;

@AllArgsConstructor
public class TextTaskUtils extends TimerTask {

    /**
     * 发送的文本消息
     */
    private String text;

    private WxMaService weixinService;

    /**
     * 发送的用户
     */
    private String toUser;
    @Override
    public void run() {
        try {

            this.weixinService.getMsgService().sendKefuMsg(WxKfUtils.createTextKfMessage(this.toUser,this.text));
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
