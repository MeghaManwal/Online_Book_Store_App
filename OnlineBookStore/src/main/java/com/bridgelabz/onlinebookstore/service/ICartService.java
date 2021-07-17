package com.bridgelabz.onlinebookstore.service;

import java.util.List;
import java.util.UUID;

import com.bridgelabz.onlinebookstore.dto.CartDataDTO;
import com.bridgelabz.onlinebookstore.dto.UpdateCartDTO;
import com.bridgelabz.onlinebookstore.model.BookCartData;
import com.bridgelabz.onlinebookstore.model.UserData;

public interface ICartService {
	
	public String addBookToCart(CartDataDTO cartdto, String token);
	public boolean setUserId(UserData userdata);
	public List<BookCartData> displayBooksFromCart(String token);
	public String deleteBookFromCart(UUID bookId, String token);
	public String updateCartData(UpdateCartDTO updatedto, String token);
	
}
