package org.code.generator.func.imp.frontend;

import cn.hutool.core.lang.Console;
import com.alibaba.fastjson2.JSON;
import freemarker.template.Template;
import org.code.generator.constant.Constant;
import org.code.generator.constant.SystemKey;
import org.code.generator.func.IGenerator;
import org.code.generator.model.entity.TableColumnDefinition;
import org.code.generator.model.entity.VueTableWithFormColumn;
import org.code.generator.util.CacheUtil;
import org.code.generator.util.TemplateUtil;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author riyan6
 * @Description: 生成项目配置文件 cfg.ts
 * @since 2023/6/25 21:55 星期日
 */
public class FrontendCfgGenerator implements IGenerator {

    @Override
    public void generator() {
        try {
            Template template = TemplateUtil.getInstance().configuration().getTemplate("frontend/index_cfg_ts.ftl");
            Map<String, Object> dataModel = new HashMap<>(16);

            // 添加到生成参数中
            addDataModel(dataModel, SystemKey.SERVICE_NAME);
            addDataModel(dataModel, SystemKey.CLASS_CONTROLLER_API_MAPPING);

            // 获取表结构数据
            String jsonStr = CacheUtil.getInstance().get(Constant.TABLE_COLUMN_DEFINITIONS);
            List<TableColumnDefinition> definitions = JSON.parseArray(jsonStr, TableColumnDefinition.class);

            // 生成列配置
            List<VueTableWithFormColumn> columns = definitions.stream()
                    .map(r -> VueTableWithFormColumn.builder()
                            .key(r.field())
                            .name(r.Comment())
                            .build())
                    .collect(Collectors.toList());

            dataModel.put("columns", columns);
            dataModel.put("formItems", columns);

            // 生成代码
            String outputFilePath = CacheUtil.getInstance().get(SystemKey.FILE_SAVE_PATH) + Constant.FRONTEND + "/cfg.ts";
            Writer fileWriter = new FileWriter(outputFilePath);
            template.process(dataModel, fileWriter);
            fileWriter.flush();
            fileWriter.close();

            // print result
            Console.log("cfg.ts", "文件生成完毕");
        } catch (Exception e) {
            Console.error("生成前端页面配置 ts 文件失败，错误信息：", e.getMessage());
        }
    }

}