package org.code.generator.util;

import cn.hutool.core.util.StrUtil;
import org.code.generator.constant.SystemKey;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
