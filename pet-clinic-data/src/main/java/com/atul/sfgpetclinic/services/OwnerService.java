package com.atul.sfgpetclinic.services;

import com.atul.sfgpetclinic.model.Owner;


public interface OwnerService extends CrudService<Owner,Long>{
    Owner findByLastName(String lastName);
}
