package com.bjy.javademo.controller;
import com.bjy.javademo.httpaspect.HttpFrequency;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author bjy
 */
@Controller
@RequestMapping("/api")
public class TestController {

    /**
     * 2000毫秒可以请求一次
     */
    @HttpFrequency(key = "/api/getAccess",frequency = 1,time = 2000)
    @RequestMapping(value = "/getAccess", method = RequestMethod.GET)
    public void findAlarmPage() {

    }

}
