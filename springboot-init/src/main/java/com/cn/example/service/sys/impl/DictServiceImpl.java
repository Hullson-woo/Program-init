package com.cn.example.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.example.common.Constant;
import com.cn.example.common.exception.ServiceException;
import com.cn.example.entity.sys.Dict;
import com.cn.example.mapper.sys.DictMapper;
import com.cn.example.service.sys.DictService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

/**
 * <p>系统字典管理 - 业务层</p>
 *
 * @author 咖啡不苦
 * @date 2024-07-21
 * @since 1.0
 */

@Service
@Slf4j
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
    /**
     * 系统字典管理 - 新增字典
     * @param dict  字典实体
     */
    @Override
    public void addDict(Dict dict) {
        if (dict == null) {
            throw new ServiceException(500, "dict cannot be empty.");
        }
        Assert.isTrue(StringUtils.isNotBlank(dict.getLabel()), "label cannot be null.");
        Assert.isTrue(StringUtils.isNotBlank(dict.getValue()), "value cannot be null.");
        Assert.isTrue(StringUtils.isNotBlank(dict.getDictType()), "dict type cannot be null.");

        LambdaQueryWrapper<Dict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dict::getValue, dict.getValue());
        wrapper.eq(Dict::getDictType, dict.getDictType());
        wrapper.eq(Dict::getDelFlag, Constant.DELETE_FLAG_NOMARL);
        Dict dbDict = baseMapper.selectOne(wrapper);

        if (dbDict != null) {
            throw new ServiceException(500, "sorry, the dict already exists");
        }

        dict.preInsert();
        baseMapper.insert(dict);
    }

    /**
     * 系统字典管理 - 修改字典
     * @param dict  字典实体
     */
    @Override
    public void updateDict(Dict dict) {
        dict.preUpdate();
        baseMapper.updateById(dict);
    }

    /**
     * 系统字典管理 - 删除字典
     * @param ids   字典ID列表
     */
    @Override
    public void deleteDict(String ids) {
        if (StringUtils.isBlank(ids)) {
            throw new ServiceException(500, "the ids cannot be empty");
        }

        List<String> idList = Arrays.asList(ids.split(","));
        baseMapper.deleteDict(idList);
    }

    /**
     * 系统字典管理 - 根据ID获取字典信息
     * @param id    字典ID
     * @return      字典实体
     */
    @Override
    public Dict get(String id) {
        LambdaQueryWrapper<Dict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dict::getId, id);
        wrapper.eq(Dict::getDelFlag, Constant.DELETE_FLAG_NOMARL);

        return baseMapper.selectOne(wrapper);
    }

    /**
     * 系统字典管理 - 根据字典类型获取字典数据列表
     * @param dictType  字典类型
     * @return          字典数据列表
     */
    @Override
    public List<Dict> listDictOfType(String dictType) {
        return baseMapper.listDictOfType(dictType);
    }

    /**
     * 系统字典管理 - 获取字典数据列表（分页）
     * @param dict      字典实体
     * @param pageNum   页码
     * @param pageSize  行数
     * @return          字典数据列表
     */
    @Override
    public Page<Dict> listPage(Dict dict, Integer pageNum, Integer pageSize) {
        log.info("## /sys/dict/listPage ## \t {}", dict);
        if (dict == null) {
            dict = new Dict();
        }
        Page<Dict> page = new Page<>(pageNum, pageSize);

        page.setRecords(baseMapper.listPage(page, dict));
        return page;
    }
}
