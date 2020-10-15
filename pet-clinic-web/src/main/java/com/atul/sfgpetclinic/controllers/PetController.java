package com.atul.sfgpetclinic.controllers;

import com.atul.sfgpetclinic.model.Owner;
import com.atul.sfgpetclinic.model.PetType;
import com.atul.sfgpetclinic.services.OwnerService;
import com.atul.sfgpetclinic.services.PetService;
import com.atul.sfgpetclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("/owners/{owner_id}")
public class PetController {
    private final OwnerService ownerService;
    private final PetService petService;
    private final PetTypeService petTypeService;

    public PetController(OwnerService ownerService, PetService petService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.petService = petService;
        this.petTypeService = petTypeService;
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable Long owner_id){
        return ownerService.findById(owner_id);
    }

    @ModelAttribute("types")
    public Collection<PetType> populatePetTypes(){
        return petTypeService.findAll();
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder binder){
        binder.setDisallowedFields("id");
    }
}
