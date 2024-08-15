import cn.hutool.core.lang.TypeReference;
import common.CommonApplication;
import common.User;
import common.utils.cache.CaffeineUtil;
import common.utils.cache.RedisCache;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest(classes = CommonApplication.class)
class TestDemoApplicationTests {
    @Resource
    RedisCache redisCache;
    @Resource
    CaffeineUtil caffeineUtil;
    @Test
    void contextLoads() {

        User xiaodonng = new User("xiaodonng", 12);


        caffeineUtil.setCacheObject("caffeineTest",xiaodonng);

        User caffeineTest = caffeineUtil.getCacheObject("caffeineTest", new TypeReference<User>() {
        });

        System.out.println("我是caffe"+caffeineTest.getName());


        redisCache.setCacheObject("RedisTest",xiaodonng);

        User redisTest = redisCache.getCacheObject("RedisTest", new TypeReference<User>() {
        });

        System.out.println("我是redis"+redisTest.getName());

    }
}
