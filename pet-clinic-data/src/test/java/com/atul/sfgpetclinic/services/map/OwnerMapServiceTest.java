package com.atul.sfgpetclinic.services.map;

import com.atul.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    OwnerMapService ownerMapService;
    final Long ownerId=1L;
    final String lastName="Garg";

    @BeforeEach
    void setUp() {
        ownerMapService=new OwnerMapService(new PetTypeMapService(),new PetMapService());
        ownerMapService.save(Owner.builder().id(ownerId).lastName(lastName).build());
    }

    @Test
    void findAll() {
        Set<Owner> owners=ownerMapService.findAll();
        assertEquals(1,owners.size());
    }

    @Test
    void findById() {
        Owner owner=ownerMapService.findById(ownerId);
        assertEquals(ownerId,owner.getId());
    }

    @Test
    void saveExistingId() {
        Long id=2L;
        Owner owner2=Owner.builder().id(id).build();
        Owner savedOwner=ownerMapService.save(owner2);
        assertEquals(id,savedOwner.getId());
    }

    @Test
    void saveNoId() {
        Owner savedOwner=ownerMapService.save(Owner.builder().build());
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void delete() {
        ownerMapService.delete(ownerMapService.findById(ownerId));
        assertEquals(0,ownerMapService.findAll().size());
    }

    @Test
    void deleteById() {
        ownerMapService.deleteById(ownerId);
        assertEquals(0,ownerMapService.findAll().size());
    }

    @Test
    void findByLastName() {
        Owner savedOwner=ownerMapService.findByLastName(lastName);
        assertNotNull(savedOwner);
        assertEquals(ownerId,savedOwner.getId());
    }

    @Test
    void findByLastNameNotNull() {
        Owner savedOwner=ownerMapService.findByLastName("at");
        assertNull(savedOwner);
    }

}