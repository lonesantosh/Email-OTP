package com.nt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nt.model.UserData;

@Repository
public interface UserRepository extends JpaRepository<UserData, Long>{

	UserData findByEmail(String email); 
}
