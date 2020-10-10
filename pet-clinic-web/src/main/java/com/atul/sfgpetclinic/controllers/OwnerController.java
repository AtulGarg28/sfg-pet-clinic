package com.atul.sfgpetclinic.controllers;

import com.atul.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("owners")
//As owner is common in the requestMapping in the given below. So, we just take out that.

@Controller
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setAllowedFields("id");
    }

    @RequestMapping({"","/","index","index.html"})
    public String listOwners(Model model){
        model.addAttribute("owners", ownerService.findAll());
        return "owners/index";
    }

    @RequestMapping("/find")
    public String findOwners(){
        return "notImplemented";
    }

    @GetMapping("/{owner_id}")
    public ModelAndView showOwnerRecord(@PathVariable("owner_id") Long owner_id){
        ModelAndView mav=new ModelAndView("owners/ownerDetails");
        mav.addObject(ownerService.findById(owner_id));
        return mav;
    }
}