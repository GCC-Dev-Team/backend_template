import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.scheme.WxMaGenerateSchemeRequest;

import jakarta.annotation.Resource;
import me.chanjar.weixin.common.error.WxErrorException;
import miniapp.MiniAppApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = MiniAppApplication.class)
class DemoTests {

    @Resource
    private  WxMaService wxMaService;
    @Test
    void contextLoads() throws WxErrorException {


        WxMaGenerateSchemeRequest.JumpWxa.JumpWxaBuilder jumpWxaBuilder = WxMaGenerateSchemeRequest.JumpWxa.newBuilder();

        jumpWxaBuilder.path("pages/PDFProject/DocumentLibraryPage/index");
        jumpWxaBuilder.query("Order=123456");

        WxMaGenerateSchemeRequest wxMaGenerateSchemeRequest = WxMaGenerateSchemeRequest.newBuilder().build();

        wxMaGenerateSchemeRequest.setJumpWxa(jumpWxaBuilder.build());



        String generate = wxMaService.getWxMaSchemeService().generate(wxMaGenerateSchemeRequest);

        System.out.println(generate);

    }
}
