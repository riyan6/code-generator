package org.code.generator.model.entity;

/**
 * 数据库表结构映射对象
 * field    字段名
 * type     字段类型
 * key      字段的索引，PRI 就是主键
 * Comment  列备注
 */
public record TableColumnDefinition(String field, String type, String key, String Comment) {
}
