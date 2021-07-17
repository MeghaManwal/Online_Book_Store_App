package com.bridgelabz.onlinebookstore.service;

import java.util.UUID;

public interface IOrderService {

	public String placeAnOrder(Double totalPrice,UUID customerId, String token);
	
}
