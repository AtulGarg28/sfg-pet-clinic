package com.atul.sfgpetclinic.controllers;

import com.atul.sfgpetclinic.model.Owner;
import com.atul.sfgpetclinic.model.Pet;
import com.atul.sfgpetclinic.model.PetType;
import com.atul.sfgpetclinic.services.OwnerService;
import com.atul.sfgpetclinic.services.PetService;
import com.atul.sfgpetclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping("/owners/{owner_id}")
public class PetController {

    private static final String CREATE_OR_UPDATE_PET ="pets/createOrUpdatePetForm";
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

    @GetMapping("/pets/new")
    public String addNewPet(Owner owner, Model model){
        Pet pet=new Pet();
        owner.getPets().add(pet);
        model.addAttribute("pet",pet);
        return CREATE_OR_UPDATE_PET;
    }
}
