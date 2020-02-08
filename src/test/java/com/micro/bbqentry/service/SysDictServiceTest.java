package com.micro.bbqentry.service;

import com.micro.bbqentry.model.entity.SysDict;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysDictServiceTest {

    @Autowired
    private ISysDictService iSysDictService;

    @Test
    public void queryById() {
       SysDict dict= iSysDictService.queryById("1");
        assertEquals("sex",dict.getType());
    }

    @Test
    public void queryDictsByType() {
        assertEquals(3,iSysDictService.queryDictsByType("sex").size());
    }
}