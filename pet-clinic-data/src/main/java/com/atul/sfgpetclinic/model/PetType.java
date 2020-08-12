package com.atul.sfgpetclinic.model;

import java.util.Set;

public class PetType extends BaseEntity{
    private Set<Pet> petSet;

    public Set<Pet> getPetSet() {
        return petSet;
    }

    public void setPetSet(Set<Pet> petSet) {
        this.petSet = petSet;
    }
}
