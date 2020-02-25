package com.micro.bbqentry.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.micro.bbqentry.general.common.ResponseJson;
import com.micro.bbqentry.model.entity.SysDict;
import com.micro.bbqentry.model.param.DictDTO;
import com.micro.bbqentry.model.param.DictVO;
import com.micro.bbqentry.service.ISysDictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据字典-控制器
 *
 * @author jockeys
 * @since 2020/2/3
 */
@Slf4j
@RestController
@RequestMapping("/dicts")
public class DictController {

    @Autowired
    private ISysDictService iSysDictService;

    @GetMapping
    public ResponseJson getDictDetail(String id) {
        SysDict one = iSysDictService.queryById(id);
        return ResponseJson.ok(one);
    }

    @GetMapping("/{type}")
    public ResponseJson getDictsByType(@PathVariable("type") String type) {
        List<DictVO> list = iSysDictService.queryDictsByType(type);
        return ResponseJson.ok(list);
    }
    @PostMapping("/")
    public ResponseJson saveDictDetail(@RequestBody DictDTO param) {
        if(iSysDictService.save(param)){
            return ResponseJson.ok();
        }
        return ResponseJson.error();
    }
    @PutMapping("/")
    public ResponseJson updateDictDetail(@RequestBody DictDTO param) {
        if(iSysDictService.update(param)){
            return ResponseJson.ok();
        }
        return ResponseJson.error();
    }
    @DeleteMapping
    public ResponseJson softDeleteDict(String id) {
        if(iSysDictService.softDelete(id)){
            return ResponseJson.ok();
        }
        return ResponseJson.error();
    }
}
