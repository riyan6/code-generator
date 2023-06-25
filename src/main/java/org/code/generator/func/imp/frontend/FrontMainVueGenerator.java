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
 * @Description: Vue主文件生成器
 * @since 2023/6/25 18:14 星期日
 */
public class FrontMainVueGenerator implements IGenerator {

    @Override
    public void generator() {
        try {
            Template template = TemplateUtil.getInstance().configuration().getTemplate("frontend/index_table_page_vue.ftl");
            Map<String, Object> dataModel = new HashMap<>(16);

            // 文件名
            String fileName = CacheUtil.getInstance().get(SystemKey.BASE_VUE_NAME);

            // 生成代码
            String outputFilePath = CacheUtil.getInstance().get(SystemKey.FILE_SAVE_PATH) + Constant.FRONTEND + "/" + fileName + ".vue";
            Writer fileWriter = new FileWriter(outputFilePath);
            template.process(dataModel, fileWriter);
            fileWriter.flush();
            fileWriter.close();

            // print result
            Console.log(fileName + ".vue", "文件生成完毕");
        } catch (Exception e) {
            Console.error("生成前端 vue 主页面文件失败，错误信息：", e.getMessage());
        }
    }

}