package com.atul.sfgpetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("owners")
//As owner is common in the requestMapping in the given below. So, we just take out that.
@Controller
public class OwnerController {

    @RequestMapping({"","/","index","index.html"})
    public String listOwners(){
        return "owners/index";
    }
}