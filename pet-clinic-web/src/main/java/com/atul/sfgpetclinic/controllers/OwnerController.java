package com.atul.sfgpetclinic.controllers;

import com.atul.sfgpetclinic.model.Owner;
import com.atul.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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

    @RequestMapping("/find")
    public String findOwners(Model model){
        model.addAttribute("owner", Owner.builder().build());
        return "owners/findOwners";
    }

    @GetMapping
    public String listFindedOwnersByLastName(Owner owner, BindingResult result,Model model,@RequestParam("lastName") String lastName){
        owner.setLastName(lastName);
        if (owner.getLastName() == null) {
            owner.setLastName(""); // empty string signifies broadest possible search
        }

        List<Owner> ownerList=ownerService.findAllByLastNameLike("%"+owner.getLastName()+"%");

        if (ownerList.isEmpty()){
            result.rejectValue("lastName","notFound","not found");
            return "owners/findOwners";
        }
        else if (ownerList.size()==1){
            //if 1 owner found
            owner=ownerList.get(0);
            return "redirect:/owners/"+owner.getId();
        }
        else {
            // if multiple owners found
            model.addAttribute("selections",ownerList);
            return "owners/ownersList";
        }
    }

    @GetMapping("/{owner_id}")
    public ModelAndView showOwnerRecord(@PathVariable("owner_id") Long owner_id){
        ModelAndView mav=new ModelAndView("owners/ownerDetails");
        mav.addObject(ownerService.findById(owner_id));
        return mav;
    }
}