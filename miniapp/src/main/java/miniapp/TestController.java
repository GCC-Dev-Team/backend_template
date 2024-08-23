//package miniapp;
//
//import cn.binarywang.wx.miniapp.api.WxMaService;
//import cn.binarywang.wx.miniapp.bean.scheme.WxMaGenerateSchemeRequest;
//import lombok.RequiredArgsConstructor;
//import me.chanjar.weixin.common.error.WxErrorException;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/basess")
//@RequiredArgsConstructor
//public class TestController {
//    private final WxMaService wxMaService;
//    @RequestMapping("/hello")
//    String get() throws WxErrorException {
//
//        WxMaGenerateSchemeRequest.JumpWxa.JumpWxaBuilder jumpWxaBuilder = WxMaGenerateSchemeRequest.JumpWxa.newBuilder();
//
//        jumpWxaBuilder.path("pages/PDFProject/DocumentLibraryPage/index");
//        jumpWxaBuilder.query("Order=123456");
//
//        WxMaGenerateSchemeRequest wxMaGenerateSchemeRequest = WxMaGenerateSchemeRequest.newBuilder().build();
//
//        wxMaGenerateSchemeRequest.setJumpWxa(jumpWxaBuilder.build());
//
//
//
//        String generate = wxMaService.getWxMaSchemeService().generate(wxMaGenerateSchemeRequest);
//
//        return generate;
//    }
//}
