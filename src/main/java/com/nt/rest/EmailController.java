package com.nt.rest;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nt.service.OTPService;

import jakarta.mail.internet.MimeMessage;

@RestController
@RequestMapping("/mail")
public class EmailController {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private OTPService otpService;

	@GetMapping("/send")
	public String sendMail() {
		try {
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true);
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("lonesantosh12@gmail.com");
			// we can take same email id in both field
			message.setTo("lonesantosh12@gmail.com");
			message.setSubject("Email OTP Verification");
			message.setText("This sent from spring application");
			// single slash not supported used //
			helper.addAttachment("SpringSecurity.png",
					new File("D:\\New Documents\\Java notes doc\\bele\\SpringSecurity.png"));
			mailSender.send(message);
			return "sent successfully";
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GetMapping("/attach")
	public String sendMailWithAttachment() {
		try {
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true);
			SimpleMailMessage message = new SimpleMailMessage();
			helper.setFrom("lonesantosh12@gmail.com");
			helper.setTo("lonesantosh12@gmail.com");// we can take same email id in both field
			helper.setSubject("Java email with attachment from spring application");
			helper.setText("Please find the attached document below.");
			// single slash not supported used //
			helper.addAttachment("SpringSecurity.png",
					new File("D:\\New Documents\\Java notes doc\\bele\\SpringSecurity.png"));
			mailSender.send(msg);
			return "attachment sent successfully";
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	@PostMapping("/sendOTP")//localhost:4041/mail/sendOTP?email=lonesantosh12@gmail.com
	public ResponseEntity<String> sendOtp(@RequestParam String email) {
		
		otpService.sendOTPtoEmail(email);
		 return new ResponseEntity<String>("OTP sent succesfully",HttpStatus.OK);
	}
	
	@PostMapping("/validate")//localhost:4041/mail/validate?email=lonesantosh12@gmail.com&otp=999146
	public ResponseEntity<String> validateEmailOTP(@RequestParam String email,@RequestParam String otp) {
		
		boolean isValid=otpService.validateOtp(email, otp);
		
		if(isValid) {
		return ResponseEntity.ok("OTP validate successfully");	//System.out.println("OTP validate successfully");
		}else
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Invalid OTP");
		}
		
	}
	
}
