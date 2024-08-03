package com.cn.example.controller.sys;

import com.cn.example.common.Result;
import com.cn.example.common.ResultUtils;
import com.cn.example.entity.sys.Dict;
import com.cn.example.service.sys.impl.DictServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>系统字典管理 - api</p>
 *
 * @author 咖啡不苦
 * @date 2024-07-21
 * @since 1.0
 */

@RestController
@RequestMapping("/sys/dict")
public class DictController {
    @Autowired
    private DictServiceImpl dictService;

    @PostMapping("add")
    public Result addDict(@RequestBody Dict dict) {
        dictService.addDict(dict);
        return ResultUtils.success();
    }

    @PutMapping("update")
    public Result updateDict(@RequestBody Dict dict) {
        dictService.updateDict(dict);
        return ResultUtils.success();
    }

    @DeleteMapping("delete")
    public Result deleteDict(@RequestParam("ids") String ids) {
        dictService.deleteDict(ids);
        return ResultUtils.success();
    }

    @GetMapping("get")
    public Result getDict(@RequestParam("id") String id) {
        return ResultUtils.success(dictService.get(id));
    }

    @GetMapping("listOfType")
    public Result listDictOfType(@RequestParam("dictType") String dictType) {
        return ResultUtils.success(dictService.listDictOfType(dictType));
    }

    @PostMapping("listPage")
    public Result listPage(@RequestParam("pageNum") Integer pageNum,
                           @RequestParam("pageSize") Integer pageSize,
                           @RequestBody Dict dict) {
        return ResultUtils.success(dictService.listPage(dict, pageNum, pageSize));
    }
}
