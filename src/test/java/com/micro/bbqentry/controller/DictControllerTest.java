package com.micro.bbqentry.controller;

import com.micro.bbqentry.general.utils.RedisUtils;
import com.micro.bbqentry.model.entity.SysDict;
import com.micro.bbqentry.repository.SysDictMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DictControllerTest {

    @Autowired
    private RedisUtils redisUtility;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SysDictMapper sysDictMapper;

    /**
     * 构造数据，初始化通过Mockito实现
     */
    @Before
    public void initRegister(){
        SysDict dict = new SysDict();
        dict.setId("0");
        dict.setCode("cs-key");
        dict.setValue("cs-value");
        dict.setType("cs-dict");
        dict.setDelFlag(0);
        Mockito.when(sysDictMapper.queryById(any())).thenReturn(dict);
    }
    @Test
    public void getDictDetail() throws Exception {


        //构造请求
        RequestBuilder request = get("/dicts")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "0");
        //执行
        this.mockMvc.perform(request).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.data.type").value("cs-dict"))
                .andExpect(jsonPath("$.data.code").value("cs-key"))
        ;


    }

    @Test
    public void getDictsByType() {

    }

    @Test
    public void saveDictDetail() {
    }

    @Test
    public void updateDictDetail() {
    }

    @Test
    public void softDeleteDict() {
    }
}