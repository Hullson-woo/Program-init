package com.cn.example.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cn.example.entity.sys.Dict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DictMapper extends BaseMapper<Dict> {
    void deleteDict(@Param("ids") List<String> ids);

    List<Dict> listDictOfType(@Param("dictType") String dictType);
    List<Dict> listPage(Page<Dict> page, @Param("dict") Dict dict);
}
