package org.code.generator.func.imp;

import cn.hutool.core.lang.Console;
import freemarker.template.Template;
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
 * @Description: dao 代码生成
 * @since 2023/6/24 15:28 星期六
 */
public class BackendDaoGenerator implements IGenerator {

    @Override
    public void generator() {
        try {
            Template template = TemplateUtil.getInstance().configuration().getTemplate("backend/dao.ftl");
            Map<String, Object> dataModel = new HashMap<>(16);

            // 持久层 类名
            String daoClassName = CacheUtil.getInstance().get(SystemKey.CLASS_DAO_NAME);

            // 添加到生成参数中
            addDataModel(dataModel, SystemKey.BASE_PACKAGE_NAME);
            addDataModel(dataModel, SystemKey.MVC_MAPPER_PACKAGE_NAME);
            addDataModel(dataModel, SystemKey.MODULE);
            addDataModel(dataModel, SystemKey.MVC_ENTITY_PACKAGE_NAME);
            addDataModel(dataModel, SystemKey.CLASS_ENTITY_NAME);
            addDataModel(dataModel, SystemKey.CLASS_DAO_NAME);

            // 生成代码
            String outputFilePath = CacheUtil.getInstance().get(SystemKey.FILE_SAVE_PATH) + daoClassName + ".java";
            Writer fileWriter = new FileWriter(outputFilePath);
            template.process(dataModel, fileWriter);
            fileWriter.flush();
            fileWriter.close();

        } catch (Exception e) {
            Console.error("生成持久层类代码失败，错误信息：", e.getMessage());
        }
    }

    private void addDataModel(Map<String, Object> dataModel, SystemKey key) {
        dataModel.put(key.value(), CacheUtil.getInstance().get(key));
    }

}