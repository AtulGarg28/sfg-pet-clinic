package com.atul.sfgpetclinic.bootstrap;

import com.atul.sfgpetclinic.model.*;
import com.atul.sfgpetclinic.services.OwnerService;
import com.atul.sfgpetclinic.services.PetTypeService;
import com.atul.sfgpetclinic.services.SpecialityService;
import com.atul.sfgpetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialityService specialityService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
    }


    @Override
    public void run(String... args) throws Exception {

        int count=petTypeService.findAll().size();

        if (count==0) {
            loadData();
        }
    }

    private void loadData() {
        PetType dog=new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat=new PetType();
        dog.setName("cat");
        PetType savedCatPetType = petTypeService.save(cat);

        Speciality radiology=new Speciality();
        radiology.setDescription("radiology");
        Speciality savedRadiology=specialityService.save(radiology);

        Speciality surgery=new Speciality();
        radiology.setDescription("surgery");
        Speciality savedSurgery=specialityService.save(surgery);

        Speciality dentistry=new Speciality();
        radiology.setDescription("dentistry");
        Speciality savedDentistry=specialityService.save(dentistry);

        Owner owner1=new Owner();
        owner1.setFirstName("Michael");
        owner1.setLastName("Weston");
        owner1.setAddress("321 sdfds");
        owner1.setCity("Miami");
        owner1.setTelephone("321456789");

        Pet mikesPet = new Pet();
        mikesPet.setPetType(savedDogPetType);
        mikesPet.setOwner(owner1);
        mikesPet.setBirthDate(LocalDate.now());
        mikesPet.setName("Rosco");

        owner1.getPets().add(mikesPet);
        ownerService.save(owner1);

        Owner owner2=new Owner();
        owner2.setFirstName("Fiona");
        owner2.setLastName("Glenanne");
        owner2.setAddress("321 sdfds");
        owner2.setCity("Miami");
        owner2.setTelephone("321456789");

        Pet fionasCat = new Pet();
        fionasCat.setName("Fiona");
        fionasCat.setOwner(owner2);
        fionasCat.setBirthDate(LocalDate.now());
        fionasCat.setPetType(savedCatPetType);

        owner2.getPets().add(fionasCat);
        ownerService.save(owner2);

        System.out.println("Loaded owners...");

        Vet vet1=new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");
        vet1.getSpecialities().add(savedRadiology);

        vetService.save(vet1);

        Vet vet2=new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");
        vet2.getSpecialities().add(savedSurgery);

        vetService.save(vet2);

        System.out.println("Loaded vets...");
    }
}
