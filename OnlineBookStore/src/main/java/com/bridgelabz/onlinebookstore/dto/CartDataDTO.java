package com.bridgelabz.onlinebookstore.dto;

import javax.validation.constraints.Min;

import lombok.Data;

public @Data class CartDataDTO {
	
	@Min(value = 0)
	private int quantity;
	
	@Min(value = 0)
	private double totalPrice;

}
