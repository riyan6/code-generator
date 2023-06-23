package org.code.generator;

import lombok.SneakyThrows;
import org.code.generator.constant.KeyConstant;
import org.code.generator.constant.SystemKey;
import org.code.generator.func.IGenerator;
import org.code.generator.func.imp.BackendEntityGenerator;
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
        DBUtil.init();
        // 初始化模板
        TemplateUtil.init();

        // 生成文件的路径
        CacheUtil.getInstance().put(KeyConstant.FILE_SAVE_PATH, "C:\\Users\\cao\\Desktop\\code\\");

        // 开始构建 实体类
        IGenerator entityGenerator = new BackendEntityGenerator();
        entityGenerator.generator();
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
