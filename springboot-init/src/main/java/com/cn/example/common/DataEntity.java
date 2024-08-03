package com.cn.example.common;

import com.cn.example.utils.GenerateId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>通用实体类</p>
 *
 * @author 咖啡不苦
 * @date 2024-07-21
 * @since 1.0
 */

@Data
@NoArgsConstructor
public class DataEntity implements Serializable {
    private static final long seriaVersionUID = 1L;

    private String id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    private String createBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;
    private String updateBy;
    private String delFlag;
    private String remarks;

    public DataEntity(String id) {
        this();
        this.id = id;
    }

    public void preInsert() {
        if (StringUtils.isBlank(this.getId())) {
            this.setId(GenerateId.uuidGen());
        }
        if (StringUtils.isBlank(this.getDelFlag())) {
            this.setDelFlag(Constant.DELETE_FLAG_NOMARL);
        }
        // todo 获取登录用户
        this.setCreateDate(new Date());
        this.setUpdateDate(new Date());
    }

    public void preUpdate() {
        // todo 获取登录用户
        this.setUpdateDate(new Date());
    }

    /**
     * 重写equals方法
     * @param o 传入对象
     * @return  对象是否相等
     */
    @Override
    public boolean equals(Object o) {
        if (null == o) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!getClass().equals(o.getClass())) {
            return false;
        }
        DataEntity that = (DataEntity) o;
        return null != this.getId() && this.getId().equals(that.getId());
    }

    /**
     * 重写hashcode
     * @return 返回当前对象的hashcode值
     */
    @Override
    public int hashCode() {
        if (this.id != null) {
            return this.id.hashCode();
        } else {
            return super.hashCode();
        }
    }

}
