package com.zac.flycloud.restfulcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping(value = "/404",method = RequestMethod.GET)
    public String notFound() {
        return "404";
    }

    @RequestMapping(value = "/500",method = RequestMethod.GET)
    public String serverErr() {
        return "500";
    }

    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register() {
        return "register";
    }
}
