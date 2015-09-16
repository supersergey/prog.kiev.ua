package com.lynas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by user on 16.09.2015.
 */
@Controller
public class HomeController{
    @RequestMapping(value = "/")
    public static String home()
    {
        return "test";
    }
}
