package org.code.generator;

import cn.hutool.core.lang.Console;
import lombok.SneakyThrows;
import org.code.generator.constant.SystemKey;
import org.code.generator.func.imp.backend.BackendControllerGenerator;
import org.code.generator.func.imp.backend.BackendDaoGenerator;
import org.code.generator.func.imp.backend.BackendEntityGenerator;
import org.code.generator.func.imp.backend.BackendServiceGenerator;
import org.code.generator.func.imp.frontend.FrontMainVueGenerator;
import org.code.generator.func.imp.frontend.FrontendCfgGenerator;
import org.code.generator.func.imp.frontend.FrontendJsGenerator;
import org.code.generator.util.CacheUtil;
import org.code.generator.util.DBUtil;
import org.code.generator.util.TemplateUtil;

import java.io.FileInputStream;
import java.util.Properties;

public class CodeGeneratorApp {

    @SneakyThrows
    public static void main(String[] args) {
        // 初始化缓存
        CacheUtil.init();
        // 读取配置设置值
        initProperties();
        // 初始化数据库
        DBUtil.init(CacheUtil.getInstance().get(SystemKey.TABLE_NAME));
        // 初始化模板
        TemplateUtil.init();

        // 开始生成文件
        new BackendEntityGenerator().generator();
        new BackendDaoGenerator().generator();
        new BackendServiceGenerator().generator();
        new BackendControllerGenerator().generator();
        new FrontendJsGenerator().generator();
        new FrontendCfgGenerator().generator();
        new FrontMainVueGenerator().generator();

        Console.log("-------------------------------------------");
        // print
        Console.log("程序执行完成...");
    }

    /**
     * 加载项目的 application.properties 配置
     *
     * @throws Exception
     */
    private static void initProperties() throws Exception {
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/application.properties");
        properties.load(fileInputStream);
        fileInputStream.close();

        // 获取缓存工具类
        CacheUtil cacheUtil = CacheUtil.getInstance();

        for (SystemKey key : SystemKey.values()) {
            Object obj = properties.get(key.value());
            if (obj == null) {
                throw new RuntimeException("必须要有这个配置：" + key.value());
            }
            cacheUtil.put(key.value(), obj.toString());
        }

    }


}
