package com.bridgelabz.onlinebookstore.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.dto.WishlistDataDTO;
import com.bridgelabz.onlinebookstore.model.BookCartData;
import com.bridgelabz.onlinebookstore.model.BookWishlistData;
import com.bridgelabz.onlinebookstore.service.IWishlistService;

@RestController
@RequestMapping("/wishlist")
@ComponentScan
@EnableAutoConfiguration
public class WishlistDataController {
	
	@Autowired
	private IWishlistService wishlistservice;
	
	@PostMapping("/add")
	public ResponseEntity<ResponseDTO> addBooksToWishlist(@RequestBody WishlistDataDTO wishlistdto,
			                                      @RequestHeader(value="token", required=false)String token){
		String msg = wishlistservice.addBookToWishlist(wishlistdto, token);
		ResponseDTO respdto = new ResponseDTO("Adding New book to Wishlist", msg);
		return new ResponseEntity<ResponseDTO>(respdto, HttpStatus.OK);
		
	}
	
	@GetMapping("/view")
	public ResponseEntity<List<BookWishlistData>> displayAllBooksInWishlist(@RequestHeader(value = "token") String Token) {
		List<BookWishlistData> booksList=wishlistservice.displayBooksFromWishlist(Token);
		return ResponseEntity.status(HttpStatus.OK).body(booksList);
	}
	
	@DeleteMapping("/wishlist/{id}")
	public ResponseEntity removeBookFromWishlist(@PathVariable UUID id,
            @RequestHeader(value = "token") String Token) {
		String msg = wishlistservice.deleteBookFromWishlist(id, Token);
		ResponseDTO respdto = new ResponseDTO("Removing book from Wishlist", msg);
		return new ResponseEntity<ResponseDTO>(respdto, HttpStatus.OK);
	}

}
