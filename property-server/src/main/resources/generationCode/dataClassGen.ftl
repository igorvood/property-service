<#assign conn = default_connection()/>
<#assign par = template_param()/>
<#assign util = util_methods()/>
<#assign multiMaps = par['multiMaps']/>
<#assign map = par['map']/>
<#assign multiList = par['multiList']/>

<#function get_list_tables>
    <#local sql>
        select aat.owner,
        aat.table_name,
        ac.constraint_name pk_name
        from ALL_ALL_TABLES aat
        join ALL_TAB_COMMENTS tab_com on (tab_com.owner, tab_com.table_name) = ((aat.owner, aat.table_name)) and
        tab_com.comments like '%@Editable@%'
        join ALL_CONSTRAINTS ac
        on (ac.owner, ac.table_name, ac.constraint_type) = ((aat.owner, aat.table_name, 'P'))
    </#local>
    <#local tab = conn.query(sql)/>
    <#return tab/>
</#function>

<#function get_list_columns owner table pk>
<#local sql>
    select tc.owner,
    tc.table_name,
    tc.column_name,
    tc.data_type,
    tc.column_id,
    nvl(cc.comments,'NO COMMENTS'),
    decode(acc.constraint_name, null, 0, 1) pk,
    decode(tc.nullable, 'N', ' ', '?') nullable
    from ALL_TAB_COLS tc
    left join ALL_CONS_COLUMNS acc
    on (acc.owner, acc.table_name, acc.column_name, acc.constraint_name) =
    ((tc.owner, tc.table_name, tc.column_name, :1))
    join ALL_COL_COMMENTS cc on (cc.owner, cc.table_name, cc.column_name) = ((tc.owner, tc.table_name, tc.column_name))
    where tc.owner = :2
    and tc.table_name = :3
    and tc.virtual_column = 'NO'
    order by tc.column_name
</#local>
    <#local tab = conn.query(sql,[pk, owner, table])/>
    <#return tab/>
</#function>

<#function get_annotation is_pk>
    <#if is_pk == 1>
        <#return 'Pk'/>
    <#else>
        <#return 'Column'/>
        <#local tab = 'Column'/>
    </#if>
</#function>

<#assign tables = get_list_tables()/>
<#macro generateClass tab>
    <#list tab as t>
        package ru.vood.property.server.generation.dto

import ru.vood.property.server.annotation.Column
import ru.vood.property.server.annotation.Pk
import ru.vood.property.server.annotation.Table
import javax.annotation.Generated
/**
* Генерированный класс для доступа к таблице
*/
@Generated("tjc plugin")
@Table( owner="${t.OWNER}", name = "${t.TABLE_NAME}")
data class ${util.toCamelCase(t.TABLE_NAME)} (
<#assign cols = get_list_columns(t.OWNER, t.TABLE_NAME, t.PK_NAME)/>
<#assign fields>
<#list cols as c>

<#--    /* ${c.COMMENTS} */-->
    @${get_annotation(c.PK)}(name = "${c.COLUMN_NAME}", colId=${c.COLUMN_ID})
    val ${util.toCamelCaseFirstLetterLower(c.COLUMN_NAME)}: ${util.sqlToJavaTypeMapping(c.DATA_TYPE)}${c.NULLABLE},
</#list>
</#assign>${fields?remove_ending(",
")}
)
===================separator class======================
</#list>
</#macro>
<@generateClass tables/>
<#flush>