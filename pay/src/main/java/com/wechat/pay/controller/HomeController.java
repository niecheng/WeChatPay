package com.wechat.pay.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "/test")
public class HomeController {     
	
  @RequestMapping(value= "/t" ,method = RequestMethod.GET)
  public String printWelcome(ModelMap model) {
         model.addAttribute("serverTime", new Date());
         System.out.println("date:"  +  new Date());
        return "home";
    }
}
