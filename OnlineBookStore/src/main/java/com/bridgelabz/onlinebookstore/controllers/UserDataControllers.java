package com.bridgelabz.onlinebookstore.controllers;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.dto.UserDataDTO;
import com.bridgelabz.onlinebookstore.dto.UserLoginDTO;
import com.bridgelabz.onlinebookstore.model.UserData;
import com.bridgelabz.onlinebookstore.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserDataControllers {
	
	@Autowired
	private IUserService userservice;
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDTO> addUserData(@Valid @RequestBody UserDataDTO userdto) {
		UserData userdata=userservice.createNewUser(userdto);
		ResponseDTO respdto = new ResponseDTO("Created New User Successfully", userdata);
		return new ResponseEntity<ResponseDTO>(respdto, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/verify/email/{tokenId}")
	public ResponseEntity verifyEmail(@PathVariable String tokenId) {
		userservice.verifyEmail(tokenId);
		return new ResponseEntity("Email is successfully verified", HttpStatus.OK);	
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/login")
    public ResponseEntity userlogin(@Valid @RequestBody UserLoginDTO userLoginDTO, HttpServletResponse httpServletResponse) {
        String userLogin = userservice.userLogin(userLoginDTO);
        httpServletResponse.setHeader("Authorization", userLogin);
        return new ResponseEntity("LOGIN SUCCESSFUL", HttpStatus.OK);
    }
	
	@PostMapping("/reset/link")
	public ResponseEntity<ResponseDTO> sendResetLink(@RequestParam(value = "emailID") String emailID) throws MessagingException {
		String link = userservice.sendPasswordResetLink(emailID);
		ResponseDTO respdto = new ResponseDTO("Reset Link Sent successfully", link);
		return new ResponseEntity<ResponseDTO>(respdto, HttpStatus.OK);
	}
	
	@PostMapping("/reset/password")
	public ResponseEntity<ResponseDTO> setNewPassword(@RequestParam(value = "password") String password,
			                                          @RequestParam(value = "token") String urltoken) {
		String setpassword = userservice.resetPassword(password, urltoken);
		ResponseDTO respdto = new ResponseDTO("New Password has been set successfully", setpassword);
		return new ResponseEntity<ResponseDTO>(respdto, HttpStatus.OK);	
	}

}
