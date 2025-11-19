package com.nt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class UserData {

		@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long userId;
	    private String userName;
	    private String email;
	    private String password;
	    private String otp;
	    private boolean verified;
}
