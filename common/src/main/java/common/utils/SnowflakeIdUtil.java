package common.utils;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ID生成器-Snowflake
 * @author I Nhrl
 */
@Component
public class SnowflakeIdUtil {
    static SnowflakeGenerator snowflakeGenerator;
    private static void setSnowflakeGenerator(SnowflakeGenerator snowflakeGenerator){
        SnowflakeIdUtil.snowflakeGenerator = snowflakeGenerator;
    }
    @Autowired
    public void setSnowflakeGeneratorConfig(SnowflakeGenerator snowflakeGenerator){
        setSnowflakeGenerator(snowflakeGenerator);
    }

    /**
     * 获取 snowflakeId
     * @return id
     */
    public static String snowflakeId(){
        return snowflakeGenerator.next().toString();
    }
}
