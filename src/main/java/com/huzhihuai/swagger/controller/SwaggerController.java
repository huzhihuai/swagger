/**
 * Copyright(C) 2020 Hangzhou Fugle Technology Co., Ltd. All rights reserved.
 */
package com.huzhihuai.swagger.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @since 2020年08月28日 11:12 上午
 * @version $Id$
 * @author HuZhihuai
 */
@RestController
public class SwaggerController {

    @GetMapping("test")
    public Result test(String param1){
        return new Result();
    }

    @PostMapping("test")
    public String testPost(){
        return "testPost";
    }
    @GetMapping("test2")
    public String test2(){
        return "test";
    }

    @PostMapping("test2")
    public String testPost2(){
        return "testPost";
    }
}
