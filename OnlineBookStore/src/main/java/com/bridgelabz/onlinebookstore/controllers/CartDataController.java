package com.bridgelabz.onlinebookstore.controllers;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.onlinebookstore.dto.CartDataDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.dto.UpdateCartDTO;
import com.bridgelabz.onlinebookstore.model.BookCartData;
import com.bridgelabz.onlinebookstore.service.ICartService;

@RestController
@RequestMapping("/cart")
@ComponentScan
@EnableAutoConfiguration
public class CartDataController {
	
	@Autowired
	private ICartService cartservice;
	
	@PostMapping("/add")
	public ResponseEntity<ResponseDTO> addBooksToCart(@RequestBody CartDataDTO cartdto,
			                                          @RequestHeader(value="token", required=false)String Token){
		String msg = cartservice.addBookToCart(cartdto, Token);
		ResponseDTO respdto = new ResponseDTO("Adding New book in Cart", msg);
		return new ResponseEntity<ResponseDTO>(respdto, HttpStatus.OK);
		
	}

	@GetMapping("/view")
	public ResponseEntity<List<BookCartData>> displayAllBooksInCart(@RequestHeader(value = "token") String Token) {
		List<BookCartData> booksList=cartservice.displayBooksFromCart(Token);
		return ResponseEntity.status(HttpStatus.OK).body(booksList);
	}
	
	@DeleteMapping("/cart/{bookId}")
	public ResponseEntity removeBookFromCart(@PathVariable UUID bookId,
                                             @RequestHeader(value = "token") String Token) {
		String msg = cartservice.deleteBookFromCart(bookId, Token);
		ResponseDTO respdto = new ResponseDTO("Removing book from Cart", msg);
		return new ResponseEntity<ResponseDTO>(respdto, HttpStatus.OK);
	}
	
	@PutMapping("/update")
        public ResponseEntity updateBookQuantity(@ Valid @RequestBody UpdateCartDTO updateCartdto,
                                             @RequestHeader(value = "token") String Token) {
        String msg = cartservice.updateCartData(updateCartdto, Token);
        ResponseDTO respdto = new ResponseDTO("Updating book details in the Cart", msg);
		return new ResponseEntity<ResponseDTO>(respdto, HttpStatus.OK);
    }
}
