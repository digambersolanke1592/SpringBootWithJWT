package com.Commercialsite.AppRepository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.Commercialsite.AppModel.ERole;
import com.Commercialsite.AppModel.Role;

@Repository
public interface RoleRepository extends JpaRepository <Role, Integer> {
	Optional<Role> findByName(ERole name);

}
