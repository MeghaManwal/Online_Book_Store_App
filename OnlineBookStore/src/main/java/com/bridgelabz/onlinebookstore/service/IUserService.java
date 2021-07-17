package com.bridgelabz.onlinebookstore.service;

import java.util.UUID;

import javax.mail.MessagingException;

import com.bridgelabz.onlinebookstore.dto.UserDataDTO;
import com.bridgelabz.onlinebookstore.dto.UserLoginDTO;
import com.bridgelabz.onlinebookstore.model.UserData;

public interface IUserService {

	public UserData createNewUser(UserDataDTO userdto);
	public String userLogin(UserLoginDTO userLoginDto);
	public String sendPasswordResetLink(String emailId) throws MessagingException;
	public String resetPassword(String password, String urlToken);
	public void verifyEmail(String tokenId, UserData userdata);
	
}
