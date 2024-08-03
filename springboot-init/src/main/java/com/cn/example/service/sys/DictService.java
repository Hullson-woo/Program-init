package com.cn.example.service.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.example.entity.sys.Dict;

import java.util.List;

public interface DictService extends IService<Dict> {
    void addDict(Dict dict);
    void updateDict(Dict dict);
    void deleteDict(String ids);

    Dict get(String id);
    List<Dict> listDictOfType(String dictType);
    Page<Dict> listPage(Dict dict, Integer pageNum, Integer pageSize);
}
