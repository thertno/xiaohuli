package com.thertno.xiaohuli.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * TODO:DOCUMENT ME!
 *
 * @author penglei
 * @date 2018/6/12
 */
@Controller
public class IndexController {

    @GetMapping("/greeting")
    public ModelAndView greeting(
            @RequestParam(name = "name", required = false, defaultValue = "World") String name,
            Model model) {
        model.addAttribute("name", name);
        return new ModelAndView("greeting");
    }
}
