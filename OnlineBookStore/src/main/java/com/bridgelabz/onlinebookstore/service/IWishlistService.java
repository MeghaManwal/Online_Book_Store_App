package com.bridgelabz.onlinebookstore.service;

import java.util.List;
import java.util.UUID;

import com.bridgelabz.onlinebookstore.dto.WishlistDataDTO;
import com.bridgelabz.onlinebookstore.model.BookWishlistData;
import com.bridgelabz.onlinebookstore.model.UserData;

public interface IWishlistService {

	public String addBookToWishlist(WishlistDataDTO wishlistdto, String token);
	public boolean setUserId(UserData userdata);
	public List<BookWishlistData> displayBooksFromWishlist(String token);
	public String deleteBookFromWishlist(UUID bookId, String token);
	
}
