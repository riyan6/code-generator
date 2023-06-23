package org.code.generator.constant;

public enum SystemKey {

    // mysql
    MYSQL_USER("mysql.user", "mysqlUser"),
    MYSQL_PASSWORD("mysql.password", "mysqlPassword"),
    MYSQL_URL("mysql.url", "mysqlUrl"),
    // system
    DATABASE("database", "database"),
    TABLE_NAME("tableName", "tableName"),
    MODULE("module", "module"),
    BASE_PACKAGE_NAME("base.package.name", "basePackageName"),
    // mvc
    MVC_ENTITY_PACKAGE_NAME("mvc.entity.package.name", "mvcEntityPackageName"),
    MVC_MAPPER_PACKAGE_NAME("mvc.mapper.package.name", "mvcMapperPackageName"),
    MVC_SERVICE_PACKAGE_NAME("mvc.service.package.name", "mvcServicePackageName"),
    MVC_SERVICE_IMPL_PACKAGE_NAME("mvc.service.impl.package.name", "mvcServiceImplPackageName"),
    MVC_CONTROLLER_PACKAGE_NAME("mvc.controller.package.name", "mvcControllerPackageName"),
    ;

    private String key;
    private String value;

    SystemKey(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String key() {
        return key;
    }

    public String value() {
        return value;
    }
}
