package com.cn.example.entity.sys;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cn.example.common.DataEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>系统字典 - 实体类</p>
 *
 * @author 咖啡不苦
 * @date 2024-07-21
 * @since 1.0
 */

@Data
@NoArgsConstructor
@TableName(value = "sys_dict")
public class Dict extends DataEntity {
    private String label;           // 标签
    private String value;           // 字典值
    private String dictType;        // 字典类型
    private String description;     // 字典描述
    private Integer sort;           // 字典排序

}
