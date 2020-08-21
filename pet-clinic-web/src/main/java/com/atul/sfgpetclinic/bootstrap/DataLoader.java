package com.atul.sfgpetclinic.bootstrap;

import com.atul.sfgpetclinic.model.Owner;
import com.atul.sfgpetclinic.model.Pet;
import com.atul.sfgpetclinic.model.PetType;
import com.atul.sfgpetclinic.model.Vet;
import com.atul.sfgpetclinic.services.OwnerService;
import com.atul.sfgpetclinic.services.PetTypeService;
import com.atul.sfgpetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }


    @Override
    public void run(String... args) throws Exception {

        PetType dog=new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat=new PetType();
        dog.setName("cat");
        PetType savedCatPetType = petTypeService.save(cat);

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

        vetService.save(vet1);

        Vet vet2=new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");

        vetService.save(vet2);

        System.out.println("Loaded vets...");
    }
}
