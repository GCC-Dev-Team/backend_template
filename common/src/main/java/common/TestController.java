package common;

import cn.hutool.core.lang.TypeReference;
import common.utils.cache.CaCheContext;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/base")
public class TestController {
    @Resource
    CaCheContext caCheContext;
    @RequestMapping("/hello")
    String get(){
//        caCheContext.setCacheObject("xiao",new User("小东",12));
//
//
//        User xiao = caCheContext.getCacheObject("xiao", new TypeReference<>() {
//        });
//
//
//        System.out.println(xiao.getName());
        return "你好";
    }
}
