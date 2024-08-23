package miniapp.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.scheme.WxMaGenerateSchemeRequest;
import cn.binarywang.wx.miniapp.bean.urllink.GenerateUrlLinkRequest;
import common.request.handler.WebResponse;
import common.utils.SnowflakeIdUtil;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx")
@RequiredArgsConstructor
public class WxController {
    private final WxMaService wxMaService;
    @GetMapping("/user")
    WebResponse<String> getString() throws WxErrorException {
//        WxMaGenerateSchemeRequest.JumpWxa.JumpWxaBuilder jumpWxaBuilder = WxMaGenerateSchemeRequest.JumpWxa.newBuilder();
//
//        jumpWxaBuilder.path("pages/PDFProject/DocumentLibraryPage/index");
//        jumpWxaBuilder.query("Order=123456");
//
//        WxMaGenerateSchemeRequest wxMaGenerateSchemeRequest = WxMaGenerateSchemeRequest.newBuilder().build();
//
//        wxMaGenerateSchemeRequest.setJumpWxa(jumpWxaBuilder.build());


        GenerateUrlLinkRequest generateUrlLinkRequest = new GenerateUrlLinkRequest();
        generateUrlLinkRequest.setPath("pages/PDFProject/DocumentLibraryPage/index");
        generateUrlLinkRequest.setQuery("order="+ SnowflakeIdUtil.snowflakeId());
        generateUrlLinkRequest.setEnvVersion("trial");


        String generate = wxMaService.getLinkService().generateUrlLink(generateUrlLinkRequest);

        return WebResponse.success(generate);

    }
}
