package org.code.generator.func.imp.backend;

import cn.hutool.core.lang.Console;
import freemarker.template.Template;
import org.code.generator.constant.Constant;
import org.code.generator.constant.SystemKey;
import org.code.generator.func.IGenerator;
import org.code.generator.util.CacheUtil;
import org.code.generator.util.TemplateUtil;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author riyan6
 * @Description: service 代码生成
 * @since 2023/6/24 15:54 星期六
 */
public class BackendServiceGenerator implements IGenerator {

    @Override
    public void generator() {
        // 文件存储路径
        String serviceFilePath = new StringBuilder(CacheUtil.getInstance().get(SystemKey.FILE_SAVE_PATH))
                .append(Constant.BACKEDN)
                .append("/")
                .append(CacheUtil.getInstance().get(SystemKey.CLASS_SERVICE_NAME))
                .append(".java")
                .toString();
        String serviceImplFilePath = new StringBuilder(CacheUtil.getInstance().get(SystemKey.FILE_SAVE_PATH))
                .append(Constant.BACKEDN)
                .append("/")
                .append(CacheUtil.getInstance().get(SystemKey.CLASS_SERVICE_IMPL_NAME))
                .append(".java")
                .toString();

        try (Writer serviceFileWriter = new FileWriter(serviceFilePath); Writer serviceImplFileWriter = new FileWriter(serviceImplFilePath)) {
            Template serviceTemplate = TemplateUtil.getInstance().configuration().getTemplate("backend/service.ftl");
            Template serviceImplTemplate = TemplateUtil.getInstance().configuration().getTemplate("backend/serviceImpl.ftl");
            Map<String, Object> dataModel = new HashMap<>(16);

            // 添加到生成参数中
            addDataModel(dataModel, SystemKey.BASE_PACKAGE_NAME);
            addDataModel(dataModel, SystemKey.MVC_SERVICE_PACKAGE_NAME);
            addDataModel(dataModel, SystemKey.MVC_SERVICE_IMPL_PACKAGE_NAME);
            addDataModel(dataModel, SystemKey.MVC_ENTITY_PACKAGE_NAME);
            addDataModel(dataModel, SystemKey.MVC_MAPPER_PACKAGE_NAME);
            addDataModel(dataModel, SystemKey.MODULE);
            addDataModel(dataModel, SystemKey.CLASS_ENTITY_NAME);
            addDataModel(dataModel, SystemKey.CLASS_SERVICE_NAME);
            addDataModel(dataModel, SystemKey.CLASS_SERVICE_IMPL_NAME);
            addDataModel(dataModel, SystemKey.CLASS_DAO_NAME);

            // 生成代码
            serviceTemplate.process(dataModel, serviceFileWriter);
            serviceImplTemplate.process(dataModel, serviceImplFileWriter);

            // print result
            Console.log(CacheUtil.getInstance().get(SystemKey.CLASS_SERVICE_NAME) + ".java", "文件生成完毕");
            Console.log(CacheUtil.getInstance().get(SystemKey.CLASS_SERVICE_IMPL_NAME) + ".java", "文件生成完毕");
        } catch (Exception e) {
            Console.error("生成Service代码失败，错误信息：", e.getMessage());
        }
    }

}