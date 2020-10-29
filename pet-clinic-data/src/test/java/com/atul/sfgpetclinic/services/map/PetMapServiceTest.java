package com.atul.sfgpetclinic.services.map;

import com.atul.sfgpetclinic.model.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PetMapServiceTest {

    private final Long petId=1L;

    private PetMapService petMapService;

    @BeforeEach
    void setUp() {
        petMapService=new PetMapService();
        petMapService.save(Pet.builder().id(petId).build());
    }

    @Test
    void findAll() {
        Set<Pet> pets=petMapService.findAll();
        assertEquals(1,pets.size());
    }

    @Test
    void findById() {
        Pet pet=petMapService.findById(petId);
        assertEquals(1,pet.getId());
    }

    @Test
    void saveNewId() {
        Pet newPet= Pet.builder().id(2L).build();
        Pet pet=petMapService.save(newPet);
        assertEquals(2,pet.getId());
    }

    @Test
    void saveDuplicateId() {
        Pet newPet= Pet.builder().id(1L).build();
        Pet pet=petMapService.save(newPet);
        assertEquals(1,pet.getId());
        assertEquals(1,petMapService.findAll().size());
    }

    @Test
    void saveNoId() {
        Pet newPet= Pet.builder().build();
        Pet pet=petMapService.save(newPet);

        assertNotNull(pet);
        assertNotNull(pet.getId());
        assertEquals(2,petMapService.findAll().size());
    }

    @Test
    void deletePet() {
        petMapService.delete(petMapService.findById(petId));
        assertEquals(0,petMapService.findAll().size());
    }

    @Test
    void deletePetWithNullId() {
        Pet pet= Pet.builder().build();
        petMapService.delete(pet);
        assertEquals(1,petMapService.findAll().size());
    }

    @Test
    void deletePetWithWrongId() {
        Pet pet=Pet.builder().id(5L).build();
        petMapService.save(pet);
        petMapService.deleteById(2L);
        assertEquals(2,petMapService.findAll().size());
    }

    @Test
    void deleteNull() {
        petMapService.delete(null);
        assertEquals(1,petMapService.findAll().size());
    }

    @Test
    void deleteCorrectIdById() {
        petMapService.deleteById(petId);
        assertEquals(0,petMapService.findAll().size());
    }

    @Test
    void deleteIncorrectIdById() {
        petMapService.deleteById(5L);
        assertEquals(1,petMapService.findAll().size());
    }

    @Test
    void deleteNullIdById() {
        petMapService.deleteById(null);
        assertEquals(1,petMapService.findAll().size());
    }
}