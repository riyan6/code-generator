package org.code.generator.func.imp;

import cn.hutool.core.util.StrUtil;
import freemarker.template.Template;
import org.code.generator.constant.Key;
import org.code.generator.constant.SystemKey;
import org.code.generator.func.IGenerator;
import org.code.generator.model.entity.TableColumnDefinition;
import org.code.generator.util.CacheUtil;
import org.code.generator.util.DBUtil;
import org.code.generator.util.TemplateUtil;
import org.jooq.Record;
import org.jooq.Result;

import java.io.FileWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BackendEntityGenerator implements IGenerator {

    @Override
    public boolean generator() {

        try {
            List<TableColumnDefinition> columnDefinitions = getColumnDefinition();

            Template template = TemplateUtil.getInstance().configuration().getTemplate("backend/entity.ftl");
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put(SystemKey.BASE_PACKAGE_NAME.value(), CacheUtil.getInstance().get(SystemKey.BASE_PACKAGE_NAME.value()));
            dataModel.put(SystemKey.MVC_ENTITY_PACKAGE_NAME.value(), CacheUtil.getInstance().get(SystemKey.MVC_ENTITY_PACKAGE_NAME.value()));
            dataModel.put(SystemKey.MODULE.value(), CacheUtil.getInstance().get(SystemKey.MODULE.value()));
            dataModel.put(SystemKey.DATABASE.value(), CacheUtil.getInstance().get(SystemKey.DATABASE.value()));
            dataModel.put(SystemKey.TABLE_NAME.value(), CacheUtil.getInstance().get(SystemKey.TABLE_NAME.value()));
            // 实体类 类名
            String entityClassName = toJavaClassName(CacheUtil.getInstance().get(SystemKey.TABLE_NAME.value()));
            dataModel.put(Key.ENTITY_CLASS_NAME, entityClassName);

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

            String outputFilePath = CacheUtil.getInstance().get(Key.FILE_SAVE_PATH) + entityClassName + ".java";
            Writer fileWriter = new FileWriter(outputFilePath);
            template.process(dataModel, fileWriter);
            fileWriter.flush();
            fileWriter.close();

            // 存储数据到缓存共其他层使用
            CacheUtil.getInstance().put(Key.ENTITY_CLASS_NAME, entityClassName);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 将 数据库表名 转成 符合Java规范的类名
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
     * 获取表的列配置
     *
     * @return
     * @throws SQLException
     */
    private List<TableColumnDefinition> getColumnDefinition() throws SQLException {
        String tableName = CacheUtil.getInstance().get(SystemKey.TABLE_NAME.value());
        Result<Record> result = DBUtil.getInstance().context().fetch("DESC " + tableName);

        // 封装结果
        List<TableColumnDefinition> columns = new ArrayList<>();
        for (Record record : result) {
            String field = record.get("Field", String.class);
            String type = record.get("Type", String.class);
            String key = record.get("Key", String.class);
            columns.add(new TableColumnDefinition(field, type, key));
        }
        return columns;
    }

    /**
     * 将 SQL 类型转成 Java类型
     *
     * @param sqlType
     * @return
     */
    private String toJavaType(String sqlType) {
        // 处理 sqlType，去除括号内的内容
        int bracketIndex = sqlType.indexOf('(');
        if (bracketIndex != -1) {
            sqlType = sqlType.substring(0, bracketIndex);
        }

        return switch (sqlType) {
            case "varchar", "char", "text" -> "String";
            case "int", "tinyint" -> "Integer";
            case "bigint" -> "Long";
            case "float" -> "Float";
            case "double" -> "Double";
            case "decimal" -> "BigDecimal";
            case "boolean" -> "Boolean";
            case "date", "datetime", "timestamp" -> "Date";
            default -> throw new RuntimeException("不支持的 SQL 字段类型: " + sqlType);
        };
    }


}
