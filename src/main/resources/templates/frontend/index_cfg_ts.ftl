// 表格分组配置
export const groupData = [
    {
        id: "1",
        key: "all",
        label: "全部",
        defaultGroup: true,
    }
]

// 查询url
export const queryUrl = {
    method: "post",
    servername: "${serverName}",
    path: "${serverName}/${classControllerApiMapping}/page/query",
}
// 新增url
export const addUrl = {
    method: "post",
    servername: "${serverName}",
    path: "${serverName}/${classControllerApiMapping}/add",
}
// 编辑url
export const editUrl = {
    method: "put",
    servername: "${serverName}",
    path: "${serverName}/${classControllerApiMapping}/edit",
}

// 表格列配置
export const columns = [
<#list columns as column>
    {
        label: "${column.name}",
        key: "${column.key}",
        showOverflowTooltip: true
    },
</#list>
]

// 表单列配置
export const formItems = [
<#list formItems as item>
    {
        label: "${item.name}",
        key: "${item.key}",
    },
</#list>
]