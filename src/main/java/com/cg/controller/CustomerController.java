package com.cg.controller;


import com.cg.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private AppUtil appUtil;

    @GetMapping
    public String showListPage(Model model) {
        String username = appUtil.getPrincipalUsername();
        model.addAttribute("username", username);
        return "customer/list";
    }
}
