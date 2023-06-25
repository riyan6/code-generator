package org.code.generator.func.imp.frontend;

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
 * @Description: 前端 js 引导文件生成
 * @since 2023/6/25 20:34 星期日
 */
public class FrontendJsGenerator implements IGenerator {

    @Override
    public void generator() {
        try {
            Template template = TemplateUtil.getInstance().configuration().getTemplate("frontend/index_js.ftl");
            Map<String, Object> dataModel = new HashMap<>(16);

            // 文件名
            String fileName = CacheUtil.getInstance().get(SystemKey.BASE_VUE_NAME);

            // 添加到生成参数中
            addDataModel(dataModel, SystemKey.BASE_VUE_NAME);
            // 生成代码
            String outputFilePath = CacheUtil.getInstance().get(SystemKey.FILE_SAVE_PATH) + Constant.FRONTEND + "/" + fileName + ".js";
            Writer fileWriter = new FileWriter(outputFilePath);
            template.process(dataModel, fileWriter);
            fileWriter.flush();
            fileWriter.close();

            // print result
            Console.log(fileName + ".js", "文件生成完毕");
        } catch (Exception e) {
            Console.error("生成前端 js 文件失败，错误信息：", e.getMessage());
        }
    }

}