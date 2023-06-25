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
 * @Description: Controller 代码生成
 * @since 2023/6/25 19:13 星期日
 */
public class BackendControllerGenerator implements IGenerator {

    @Override
    public void generator() {
        try {
            Template template = TemplateUtil.getInstance().configuration().getTemplate("backend/controller.ftl");
            Map<String, Object> dataModel = new HashMap<>(16);

            // 持久层 类名
            String controllerClassName = CacheUtil.getInstance().get(SystemKey.CLASS_CONTROLLER_NAME);

            // 添加到生成参数中
            addDataModel(dataModel, SystemKey.BASE_PACKAGE_NAME);
            addDataModel(dataModel, SystemKey.MVC_CONTROLLER_PACKAGE_NAME);
            addDataModel(dataModel, SystemKey.MVC_MAPPER_PACKAGE_NAME);
            addDataModel(dataModel, SystemKey.MVC_ENTITY_PACKAGE_NAME);
            addDataModel(dataModel, SystemKey.MVC_SERVICE_PACKAGE_NAME);
            addDataModel(dataModel, SystemKey.MODULE);
            addDataModel(dataModel, SystemKey.CLASS_ENTITY_NAME);
            addDataModel(dataModel, SystemKey.CLASS_SERVICE_NAME);
            addDataModel(dataModel, SystemKey.CLASS_CONTROLLER_NAME);
            addDataModel(dataModel, SystemKey.CLASS_CONTROLLER_API_TAGS);
            addDataModel(dataModel, SystemKey.CLASS_CONTROLLER_API_MAPPING);

            // 生成代码
            String outputFilePath = CacheUtil.getInstance().get(SystemKey.FILE_SAVE_PATH) + Constant.BACKEDN + "/" + controllerClassName + ".java";
            Writer fileWriter = new FileWriter(outputFilePath);
            template.process(dataModel, fileWriter);
            fileWriter.flush();
            fileWriter.close();

            // print result
            Console.log(controllerClassName + ".java", "文件生成完毕");
        } catch (Exception e) {
            Console.error("生成Controller代码失败，错误信息：", e.getMessage());
        }
    }

}