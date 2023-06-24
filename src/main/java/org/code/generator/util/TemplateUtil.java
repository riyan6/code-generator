package org.code.generator.util;

import freemarker.template.Configuration;
import org.code.generator.CodeGeneratorApp;

public class TemplateUtil {

    private static TemplateUtil instance = null;
    private Configuration configuration = null;

    private TemplateUtil() {
        this.configuration = new Configuration(Configuration.VERSION_2_3_32);
        // 设置模板文件的加载路径
        this.configuration.setClassForTemplateLoading(CodeGeneratorApp.class, "/templates");
        // 设置默认字符集
        this.configuration.setDefaultEncoding("UTF-8");
    }

    public Configuration configuration() {
        return this.configuration;
    }

    public static void init() {
        if (instance == null) {
            instance = new TemplateUtil();
        }
    }

    public static TemplateUtil getInstance() {
        if (instance == null) {
            throw new RuntimeException("templateUtil not init.");
        }
        return instance;
    }
}
