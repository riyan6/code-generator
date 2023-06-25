package org.code.generator.model.entity;

import lombok.*;

/**
 * @Description: 前端列配置生成对象
 * @author riyan6
 * @since 2023/6/25 21:50 星期日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VueTableWithFormColumn {

    private String name;
    private String key;

}