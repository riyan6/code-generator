package ${basePackageName}.${mvcEntityPackageName}.${module};

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

<#if existDateType>
import java.util.Date;
</#if>
<#if existBigDecimalType>
import java.math.BigDecimal;
</#if>

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("${database}.${tableName}")
public class ${classEntityName} {

<#list columns as column>
    <#if column == primaryColumn>
    @TableId
    </#if>
    ${column}
</#list>

}
