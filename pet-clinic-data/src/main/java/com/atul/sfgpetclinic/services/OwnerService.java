package com.atul.sfgpetclinic.services;

import com.atul.sfgpetclinic.model.Owner;

import java.util.List;


public interface OwnerService extends CrudService<Owner,Long>{
    Owner findByLastName(String lastName);
    List<Owner> findAllByLastNameLike(String lastName);
}
