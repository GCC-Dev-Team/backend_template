package common;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/base")
public class TestController {
    @RequestMapping("/hello")
    String get(){
        return "你好";
    }
}
