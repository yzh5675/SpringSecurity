package com.cs.comment0719.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Configuration
public class ErrorController {

    @ExceptionHandler(Throwable.class)
    public String exception(final Throwable throwable, final Model model){
        System.out.println("==Throwable=="+throwable);
        String errorMessage = (throwable!=null?throwable.getMessage():"Unkown error");
        model.addAttribute("errorMessage",errorMessage);
       return "error";
    }
}
