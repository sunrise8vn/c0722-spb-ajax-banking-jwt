package com.cg.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/histories")
public class HistoryController {

    @GetMapping
    public String showTransferHistoryPage() {
        return "history/transfer";
    }
}
