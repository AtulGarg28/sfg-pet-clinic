package com.atul.sfgpetclinic.services.springdatajpa;

import com.atul.sfgpetclinic.model.Owner;
import com.atul.sfgpetclinic.repositories.OwnerRepository;
import com.atul.sfgpetclinic.repositories.PetRepository;
import com.atul.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    @Mock
    OwnerRepository ownerRepository;

    @Mock
    PetRepository petRepository;

    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerSDJpaService ownerSDJpaService;

    public static final String LAST_NAME = "Smith";

    Owner returnOwner;

    @BeforeEach
    void setUp() {
        returnOwner=Owner.builder().id(1L).lastName(LAST_NAME).build();
    }

    @Test
    void findByLastName() {
//        Owner returnOwner=Owner.builder().id(1L).lastName(LAST_NAME).build();
        when(ownerRepository.findByLastName(any())).thenReturn(returnOwner);

        Owner owner=ownerSDJpaService.findByLastName(LAST_NAME);

        assertEquals(LAST_NAME,owner.getLastName());

        verify(ownerRepository).findByLastName(any());
    }

    @Test
    void findAll() {
        Set<Owner> returnOwnerSet=new HashSet<>();
        returnOwnerSet.add(Owner.builder().id(1L).build());
        returnOwnerSet.add(Owner.builder().id(2L).build());

        when(ownerRepository.findAll()).thenReturn(returnOwnerSet);

        Set<Owner> ownerSet=ownerSDJpaService.findAll();

        assertNotNull(ownerSet);
        assertEquals(2,ownerSet.size());
    }

    @Test
    void findById() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(returnOwner));
        Owner owner=ownerSDJpaService.findById(1L);
        assertNotNull(owner);
//        System.out.println(owner);
        assertEquals(1L,owner.getId());
    }

    @Test
    void findByIdNotNull() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());
        Owner owner=ownerSDJpaService.findById(1L);
        assertNull(owner);
    }

    @Test
    void save() {
        Owner ownerToSave=Owner.builder().id(1L).build();
        when(ownerRepository.save(any())).thenReturn(returnOwner);
        Owner owner=ownerSDJpaService.save(ownerToSave);
        assertNotNull(owner);
        verify(ownerRepository).save(any());
    }

    @Test
    void delete() {
        ownerSDJpaService.delete(returnOwner);
        verify(ownerRepository,times(1)).delete(any());
    }

    @Test
    void deleteById() {
        ownerSDJpaService.deleteById(returnOwner.getId());
        verify(ownerRepository,times(1)).deleteById(anyLong());
    }
}