package com.atul.sfgpetclinic.controllers;

import com.atul.sfgpetclinic.model.Owner;
import com.atul.sfgpetclinic.model.Pet;
import com.atul.sfgpetclinic.model.PetType;
import com.atul.sfgpetclinic.services.OwnerService;
import com.atul.sfgpetclinic.services.PetService;
import com.atul.sfgpetclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public String initAddNewPet(Owner owner, Model model){
        Pet pet=new Pet();
        owner.getPets().add(pet);
        System.out.println(owner.getFirstName());

        // To set owner for that pet.
        pet.setOwner(owner);
        model.addAttribute("pet",pet);
        return CREATE_OR_UPDATE_PET;
    }

    @PostMapping("/pets/new")
    public String processAddNewPet(Owner owner,Pet pet, BindingResult result, ModelMap modelMap){
        if (StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(),true)!=null ){
            result.rejectValue("name", "duplicate", "already exists");
        }
        owner.getPets().add(pet);
        if (result.hasErrors()){
            modelMap.put("pet",pet);
            return CREATE_OR_UPDATE_PET;
        } else {
            petService.save(pet);
            return "redirect:/owners/"+owner.getId();
        }
    }

    @GetMapping("/pets/{pet_id}/edit")
    public String initUpdatePet(@PathVariable Long pet_id, Model model){
        model.addAttribute("pet",petService.findById(pet_id));
        return CREATE_OR_UPDATE_PET;
    }

    @PostMapping("/pets/{pet_id}/edit")
    public String processUpdatePet(@Valid Pet pet,Owner owner,BindingResult result,Model model){
        if (result.hasErrors()){
            pet.setOwner(owner);
            model.addAttribute("pet",pet);
            return CREATE_OR_UPDATE_PET;
        }
        else {
            owner.getPets().add(pet);
            System.out.println(pet.getName());
            petService.save(pet);
            return "redirect:/owners/"+owner.getId();
        }
    }
}
