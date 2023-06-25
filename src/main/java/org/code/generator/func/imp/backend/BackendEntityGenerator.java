package org.code.generator.func.imp.backend;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import freemarker.template.Template;
import org.code.generator.constant.Constant;
import org.code.generator.constant.SystemKey;
import org.code.generator.func.IGenerator;
import org.code.generator.model.entity.TableColumnDefinition;
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
 * @Description: entity 代码生成
 * @since 2023/6/22 20:35 星期四
 */
public class BackendEntityGenerator implements IGenerator {

    @Override
    public void generator() {

        try {
            List<TableColumnDefinition> columnDefinitions = JSON.parseArray(
                    CacheUtil.getInstance().get(Constant.TABLE_COLUMN_DEFINITIONS),
                    TableColumnDefinition.class
            );

            Template template = TemplateUtil.getInstance().configuration().getTemplate("backend/entity.ftl");
            Map<String, Object> dataModel = new HashMap<>(16);

            // 存储列配置
            String jsonStr = JSON.toJSONString(columnDefinitions);
            CacheUtil.getInstance().put(Constant.TABLE_COLUMN_DEFINITIONS, jsonStr);

            addDataModel(dataModel, SystemKey.BASE_PACKAGE_NAME);
            addDataModel(dataModel, SystemKey.MVC_ENTITY_PACKAGE_NAME);
            addDataModel(dataModel, SystemKey.MODULE);
            addDataModel(dataModel, SystemKey.DATABASE);
            addDataModel(dataModel, SystemKey.TABLE_NAME);
            addDataModel(dataModel, SystemKey.CLASS_ENTITY_NAME);

            // 实体类 类名
            String entityClassName = CacheUtil.getInstance().get(SystemKey.CLASS_ENTITY_NAME.value());

            // 设置初始值避免程序异常
            dataModel.put("primaryColumn", "_");
            dataModel.put("existDateType", false);
            dataModel.put("existBigDecimalType", false);

            // 构建实体类动态列
            List<String> columns = columnDefinitions.stream().map(definition -> {
                        // 获取Java类型
                        String javaType = toJavaType(definition.type());
                        //构建每一行
                        String row = new StringBuffer("private ")
                                .append(javaType)
                                .append(" ")
                                .append(definition.field())
                                .append(";")
                                .toString();

                        // 指定主键列
                        if (StrUtil.isNotBlank(definition.key()) && definition.key().equals("PRI")) {
                            dataModel.put("primaryColumn", row);
                        }
                        // 如果有Date类型
                        if ("Date".equals(javaType)) {
                            dataModel.put("existDateType", true);
                        }
                        // 如果有BigDecimal类型
                        if ("BigDecimal".equals(javaType)) {
                            dataModel.put("existBigDecimalType", true);
                        }

                        return row;
                    })
                    .collect(Collectors.toList());
            dataModel.put("columns", columns);

            String outputFilePath = CacheUtil.getInstance().get(SystemKey.FILE_SAVE_PATH.value()) + Constant.BACKEDN + "/" + entityClassName + ".java";
            Writer fileWriter = new FileWriter(outputFilePath);
            template.process(dataModel, fileWriter);
            fileWriter.flush();
            fileWriter.close();

            // print result
            Console.log(entityClassName + ".java", "文件生成完毕");
        } catch (Exception e) {
            Console.error("生成Entity代码失败，错误信息：", e.getMessage());
        }
    }

    /**
     * 将 数据库表名 转成 符合Java规范的类名
     *
     * @param tableName
     * @return
     */
    private String toJavaClassName(String tableName) {
        String[] words = tableName.toLowerCase().split("_");
        StringBuilder classNameBuilder = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                classNameBuilder.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1));
            }
        }

        return classNameBuilder.toString();
    }

    /**
     * 将 SQL 类型转成 Java类型
     *
     * @param sqlType
     * @return
     */
    private String toJavaType(String sqlType) {
        // 处理 sqlType，去除括号内的内容 比如 varchar(32) 转为 varchar
        int bracketIndex = sqlType.indexOf('(');
        if (bracketIndex != -1) {
            sqlType = sqlType.substring(0, bracketIndex);
        }
        // 处理 unsigned 关键字
        boolean isUnsigned = sqlType.endsWith(" unsigned");
        if (isUnsigned) {
            sqlType = sqlType.replace(" unsigned", "");
        }
        // 根据 MySQL 的数据类型转为对应的 Java 数据类型
        return switch (sqlType) {
            case "varchar", "char", "text" -> "String";
            case "int", "tinyint" -> "Integer";
            case "bigint" -> isUnsigned ? "BigInteger" : "Long";
            case "float" -> "Float";
            case "double" -> "Double";
            case "decimal" -> "BigDecimal";
            case "boolean" -> "Boolean";
            case "date", "datetime", "timestamp" -> "Date";
            default -> throw new RuntimeException("不支持的 SQL 字段类型: " + sqlType);
        };
    }


}
