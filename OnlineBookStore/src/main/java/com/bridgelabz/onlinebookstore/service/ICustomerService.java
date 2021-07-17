package com.bridgelabz.onlinebookstore.service;

import java.util.List;

import com.bridgelabz.onlinebookstore.dto.CustomerDataDTO;
import com.bridgelabz.onlinebookstore.dto.UpdateCustomerDTO;
import com.bridgelabz.onlinebookstore.model.CustomerData;

public interface ICustomerService {

	public CustomerData addNewCustomerDetails(CustomerDataDTO customerdto, String token);
	public List<CustomerData> displayCustomerDetails(String token);
	public String updateCustomerData(UpdateCustomerDTO updatedto, String token);
}
