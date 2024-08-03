package com.cn.example.entity.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cn.example.common.DataEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>系统角色 - 实体类</p>
 *
 * @author 咖啡不苦
 * @date 2024-07-27
 * @since 1.0
 */

@Data
@NoArgsConstructor
@TableName(value = "sys_role")
public class Role extends DataEntity {

    private String roleName;
    private String code;
    private String roleType;
    private Integer sort;
    private String description;

    @TableField(exist = false)
    private List<User> userList;
}
