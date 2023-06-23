package org.code.generator.constant;

public enum SystemKey {

    // mysql
    MYSQL_USER("mysqlUser"),
    MYSQL_PASSWORD("mysqlPassword"),
    MYSQL_URL("mysqlUrl"),
    // system
    DATABASE("database"),
    TABLE_NAME("tableName"),
    MODULE("module"),
    BASE_PACKAGE_NAME("basePackageName"),
    // mvc
    MVC_ENTITY_PACKAGE_NAME("mvcEntityPackageName"),
    MVC_MAPPER_PACKAGE_NAME("mvcMapperPackageName"),
    MVC_SERVICE_PACKAGE_NAME("mvcServicePackageName"),
    MVC_SERVICE_IMPL_PACKAGE_NAME("mvcServiceImplPackageName"),
    MVC_CONTROLLER_PACKAGE_NAME("mvcControllerPackageName"),
    ;

    private String value;

    SystemKey(String value) {
        this.value = value;
    }


    public String value() {
        return value;
    }
}
