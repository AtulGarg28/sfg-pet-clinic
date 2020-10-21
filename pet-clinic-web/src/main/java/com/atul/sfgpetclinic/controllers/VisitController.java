package com.atul.sfgpetclinic.controllers;

import com.atul.sfgpetclinic.model.Pet;
import com.atul.sfgpetclinic.model.Visit;
import com.atul.sfgpetclinic.services.PetService;
import com.atul.sfgpetclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RequestMapping("/owners/{owner_id}")
@Controller
public class VisitController {
    private final VisitService visitService;
    private final PetService petService;

    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    /**
     * Called before each and every @RequestMapping annotated method.
     * 2 goals:
     * - Make sure we always have fresh data
     * - Since we do not use the session scope, make sure that Pet object always has an id
     * (Even though id is not part of the form fields)
     *
     * @param pet_id
     * @return Pet
     */

    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable Long pet_id, Map<String,Object> objectMap){
        Pet pet=petService.findById(pet_id);
        objectMap.put("pet",pet);
        Visit visit=new Visit();
        pet.getVisits().add(visit);
        visit.setPet(pet);
        return visit;
    }

    @GetMapping("/pets/{pet_id}/visits/new")
    public String initAddVisit(){
        return "pets/createOrUpdateVisitForm";
    }

    @PostMapping("/pets/{pet_id}/visits/new")
    public String processAddVisit(@Valid Visit visit, BindingResult result){
        if (result.hasErrors()){
            return "pets/createOrUpdateVisitForm";
        }else {
            visitService.save(visit);
            return "redirect:/owners/{owner_id}";
        }
    }
}
