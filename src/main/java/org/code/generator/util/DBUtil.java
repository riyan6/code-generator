package org.code.generator.util;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import org.code.generator.constant.Constant;
import org.code.generator.constant.SystemKey;
import org.code.generator.model.entity.TableColumnDefinition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {

    private static DBUtil instance = null;
    private DSLContext context = null;

    public DSLContext context() {
        return this.context;
    }

    private DBUtil() throws SQLException {
        String url = StrUtil.format("{}/{}",
                CacheUtil.getInstance().get(SystemKey.MYSQL_URL.value()),
                CacheUtil.getInstance().get(SystemKey.DATABASE.value())
        );
        Connection connection = DriverManager.getConnection(
                url,
                CacheUtil.getInstance().get(SystemKey.MYSQL_USER.value()),
                CacheUtil.getInstance().get(SystemKey.MYSQL_PASSWORD.value())
        );
        this.context = DSL.using(connection, SQLDialect.MYSQL);
    }

    private DBUtil(String tableName) throws SQLException {
        String url = StrUtil.format("{}/{}",
                CacheUtil.getInstance().get(SystemKey.MYSQL_URL.value()),
                CacheUtil.getInstance().get(SystemKey.DATABASE.value())
        );
        Connection connection = DriverManager.getConnection(
                url,
                CacheUtil.getInstance().get(SystemKey.MYSQL_USER.value()),
                CacheUtil.getInstance().get(SystemKey.MYSQL_PASSWORD.value())
        );
        this.context = DSL.using(connection, SQLDialect.MYSQL);

        // 获取表的列配置 MySQL 查询的表结构信息
        Result<Record> result = this.context.fetch("SHOW FULL COLUMNS FROM " + tableName);

        // 封装结果
        List<TableColumnDefinition> columns = new ArrayList<>();
        for (Record record : result) {
            String field = record.get("Field", String.class);
            String type = record.get("Type", String.class);
            String key = record.get("Key", String.class);
            String Comment = record.get("Comment", String.class);
            columns.add(new TableColumnDefinition(field, type, key, Comment));
        }
        // 存储至缓存
        CacheUtil.getInstance().put(Constant.TABLE_COLUMN_DEFINITIONS, JSON.toJSONString(columns));
    }

    public static void init(String tableName) throws SQLException {
        if (instance == null) {
            instance = new DBUtil(tableName);
        }
    }

    public static void init() throws SQLException {
        if (instance == null) {
            instance = new DBUtil();
        }
    }

    public static DBUtil getInstance() {
        if (instance == null) {
            throw new RuntimeException("DBUtil not init");
        }
        return instance;
    }
}
