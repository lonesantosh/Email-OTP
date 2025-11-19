package com.nt.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OTPService {

	@Autowired
	private JavaMailSender mailSender;
	
	Map<String, String> otpStored=new HashMap<String, String>();
	
	public OTPService(JavaMailSender mailSender) {
		this.mailSender=mailSender;
	}
	
	public String generateOTP() {
		return String.valueOf((int) (Math.random() * 900000)+100000);
	}
	
	public void sendOTPtoEmail(String email) {
		String otp=generateOTP();
		otpStored.put(email, otp) ;
		
		SimpleMailMessage message=new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Your OTP code");
		message.setText("Your OTP is: "+otp);
		mailSender.send(message);
	}
	
	public boolean validateOtp(String email,String otp) {
		return otp.equals(otpStored.get(email));
	}
}
