package com.bridgelabz.onlinebookstore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.dto.CustomerDataDTO;
import com.bridgelabz.onlinebookstore.dto.UpdateCustomerDTO;
import com.bridgelabz.onlinebookstore.exception.BookStoreException;
import com.bridgelabz.onlinebookstore.model.BookCartData;
import com.bridgelabz.onlinebookstore.model.CustomerData;
import com.bridgelabz.onlinebookstore.model.UserData;
import com.bridgelabz.onlinebookstore.repository.CustomerDataRepository;
import com.bridgelabz.onlinebookstore.repository.UserDataRepository;
import com.bridgelabz.onlinebookstore.utils.TokenUtils;

@Service
public class CustomerService implements ICustomerService{

        TokenUtils jwtToken = new TokenUtils();
	
	@Autowired
	UserDataRepository userdatarepository;
	
	@Autowired
	CustomerDataRepository customerdatarepository;
	
	@Override
	public CustomerData addNewCustomerDetails(CustomerDataDTO customerdto, String token) {
		 UUID userId = jwtToken.decodeJWT(token);
		 Optional<UserData> findExistingUser = userdatarepository.findById(userId);
		 
		 if(!findExistingUser.isPresent()) {
			 throw new BookStoreException(BookStoreException.ExceptionTypes.USER_NOT_FOUND);
		 }
		 
		 List<CustomerData> customerdatalist = new ArrayList<>();
		 CustomerData customerdata = new CustomerData(customerdto);
		 customerdata.setUserId(userId);
		 customerdatalist.add(customerdata);
		 
		 CustomerData data = customerdatarepository.save(customerdata); 
		 return data;
	}

	@Override
	public List<CustomerData> displayCustomerDetails(String token) {
		UUID userId = jwtToken.decodeJWT(token);
		 Optional<UserData> findExistingUser = userdatarepository.findById(userId);
		 
		 if(!findExistingUser.isPresent()) {
			 throw new BookStoreException(BookStoreException.ExceptionTypes.USER_NOT_FOUND);
		 }
		return customerdatarepository.findAll()
				.stream()
				.collect(Collectors.toList());
	}

	@Override
	public String updateCustomerData(UpdateCustomerDTO updatedto, String token) {
		UUID userId = jwtToken.decodeJWT(token);
	     
		Optional<UserData> findExistingUser = userdatarepository.findById(userId);
     
                if(!findExistingUser.isPresent()){
                        throw new BookStoreException(BookStoreException.ExceptionTypes.USER_NOT_FOUND);
                }
         
                CustomerData customerdata = customerdatarepository.findByCustomerId(updatedto.getCustomerdataId())
	             .orElseThrow(() -> new BookStoreException(BookStoreException.ExceptionTypes.CUSTOMER_NOT_FOUND));
         
                customerdata.setAddress(updatedto.getAddress());
                customerdata.setCity(updatedto.getCity());
                customerdata.setLandmark(updatedto.getLandmark());
                customerdata.setState(updatedto.getState());
                customerdata.setPincode(updatedto.getPincode());
                customerdatarepository.save(customerdata);
         
		return "Customer Data Updated Successfully";
	}

}
