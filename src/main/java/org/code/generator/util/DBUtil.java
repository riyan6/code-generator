package org.code.generator.util;

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

    private DBUtil(String database) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://xxx.xxx.xxx.xxx:xxx/" + database, "", "");
        this.context = DSL.using(connection, SQLDialect.MYSQL);
    }

    public static void init() throws SQLException {
        if (instance == null) {
            instance = new DBUtil(CacheUtil.getInstance().get(SystemKey.DATABASE.value()));
        }
    }

    public static DBUtil getInstance() {
        if (instance == null) {
            throw new RuntimeException("DBUtil not init");
        }
        return instance;
    }
}
