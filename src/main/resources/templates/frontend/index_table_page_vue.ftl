<template>
    <mom-table-page
        ref="tableRef"
        :menuId="menuId"
        :group-data="groupData"
        :query-url="queryUrl"
        :add-url="addUrl"
        :edit-url="editUrl"
        :columns="columns"
        :form-items="formItems"
        :singleSelect="false"
        :is-page="true"
        :left-search="true"
        :show-header="true"
        :show-table-checkbox="true"
        :header-border="true"
        :init-request="true"
        button-layout="add,del"
        button-main="add"
        operate="edit"
    >
    </mom-table-page>

</template>


<script setup lang="ts">
    import {groupData, queryUrl, addUrl, editUrl, columns, formItems} from './cfg'
    import {useMenuId} from "@/hooks/useMenuId";
    import {ref} from 'vue';

    const {menuId} = useMenuId();
    const tableRef = ref(null);

</script>

