package com.bridgelabz.onlinebookstore.service;

import java.util.Optional;
import java.util.UUID;

import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.dto.UserDataDTO;
import com.bridgelabz.onlinebookstore.dto.UserLoginDTO;
import com.bridgelabz.onlinebookstore.exception.BookStoreException;
import com.bridgelabz.onlinebookstore.exception.UserDataException;
import com.bridgelabz.onlinebookstore.model.UserData;
import com.bridgelabz.onlinebookstore.repository.UserDataRepository;
import com.bridgelabz.onlinebookstore.utils.EmailService;
import com.bridgelabz.onlinebookstore.utils.TokenUtils;

@Service
public class UserService implements IUserService{

	@Autowired
	private UserDataRepository userdatarepo;
	
	@Autowired
        private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	TokenUtils jwtToken = new TokenUtils();
	
	@Autowired
        private EmailService emailService;
	
	@Autowired
	ICartService cartservice;
	
	@Autowired
	IWishlistService wishlistservice;

	@Override
	public UserData createNewUser(UserDataDTO userdto) {
		Optional<UserData> checkEmailId = userdatarepo.findByEmailID(userdto.getEmailId());
		if(checkEmailId.isPresent()) {
			throw new BookStoreException(BookStoreException.ExceptionTypes.USER_ALREADY_PRESENT);
		}else {
			String password = bCryptPasswordEncoder.encode(userdto.getPassword());	
			userdto.setPassword(password);
		    UserData userdata = new UserData(userdto.getFullName(),
		    		                         userdto.getPhoneNumber(),
		    		                         userdto.getEmailId(),
		    		                         password);
		    UserData savedata = userdatarepo.save(userdata);
		    String tokenId = jwtToken.generateVerificationToken(userdata.getUserId());
		    String requestUrl ="http://localhost:8080/user/verify/email/"+tokenId;
		    try {
	            emailService.sendMail(requestUrl,"the verification link is ", userdto.getEmailId());
	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
		    return savedata;
	        }
	}

	@Override
	public void verifyEmail(String tokenId, UserData userdata) {
		UUID token = jwtToken.decodeJWT(tokenId);
		Optional<UserData> userId= userdatarepo.findById(token);
		if(!userId.isPresent()) {
			throw new BookStoreException(BookStoreException.ExceptionTypes.USER_NOT_FOUND); 
		}
		userId.get().isVerified=cartservice.setUserId(userdata);
		userId.get().isVerified=wishlistservice.setUserId(userdata);
		userdatarepo.save(userId.get());
	}

	@Override
	public String userLogin(UserLoginDTO userLoginDto) {
		  Optional<UserData> userEmail = userdatarepo.findByEmailID(userLoginDto.getEmailId());
		  if (!userEmail.isPresent()) {
	            throw new UserDataException(UserDataException.ExceptionTypes.EMAIL_NOT_FOUND);
	      }
		  if(userEmail.get().isVerified){
	            boolean password = bCryptPasswordEncoder.matches(userLoginDto.password, userEmail.get().password);
	            if (!password) {
	                throw new UserDataException(UserDataException.ExceptionTypes.PASSWORD_NOT_FOUND);
	            }
	            String tokenString = jwtToken.generateLoginToken(userEmail.get());
	            System.out.println("Login Token:"+tokenString);
	            return tokenString;
	        }
	        throw  new UserDataException(UserDataException.ExceptionTypes.EMAIL_INVALID);
	}
	
	@Override
        public String sendPasswordResetLink(String email) throws MessagingException {
                  UserData userdata = userdatarepo.findByEmailID(email)
        		            .orElseThrow(() -> new UserDataException(UserDataException.ExceptionTypes.EMAIL_NOT_FOUND));
                  String token = jwtToken.generateVerificationtoken(userdata);
                  String urlToken = "Link provided to RESET your password \n"
                                    +"http://localhost:8080/user/reset/password/" 
        		           +token;
                  emailService.sendMail(urlToken, "To RESET Password", userdata.emailID);
                  return "The link to RESET Password is sent";
    }

	@Override
	public String resetPassword(String password, String urlToken) {
		UUID userId = jwtToken.decodeJWT(urlToken);
		UserData userdata = userdatarepo.findById(userId)
				             .orElseThrow(() -> new UserDataException(UserDataException.ExceptionTypes.USER_NOT_FOUND));
		String encodePassword = bCryptPasswordEncoder.encode(password);
		userdata.password=encodePassword;
		userdatarepo.save(userdata);
                return "Password is Successfully Reset";
	}
}
	 
	

