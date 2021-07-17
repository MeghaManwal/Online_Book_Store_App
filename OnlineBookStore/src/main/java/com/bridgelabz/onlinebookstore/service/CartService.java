package com.bridgelabz.onlinebookstore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.onlinebookstore.dto.CartDataDTO;
import com.bridgelabz.onlinebookstore.dto.UpdateCartDTO;
import com.bridgelabz.onlinebookstore.exception.BookStoreException;
import com.bridgelabz.onlinebookstore.model.BookData;
import com.bridgelabz.onlinebookstore.model.BookCartData;
import com.bridgelabz.onlinebookstore.model.CartDetails;
import com.bridgelabz.onlinebookstore.model.UserData;
import com.bridgelabz.onlinebookstore.repository.BookDataRepository;
import com.bridgelabz.onlinebookstore.repository.BookCartRepository;
import com.bridgelabz.onlinebookstore.repository.CartDetailsRepository;
import com.bridgelabz.onlinebookstore.repository.UserDataRepository;
import com.bridgelabz.onlinebookstore.utils.TokenUtils;


@Service
public class CartService implements ICartService {

	TokenUtils jwtToken = new TokenUtils();
	
	@Autowired
	UserDataRepository userdatarepository;
	
	@Autowired
	CartDetailsRepository cartrepository;
	
	@Autowired
	BookCartRepository bookcartrepository;
	
	@Autowired
	BookDataRepository bookdatarepository;
	
	
	@Override
	public String addBookToCart(CartDataDTO cartdto, String token) {
		UUID userId = jwtToken.decodeJWT(token);
		UserData findExistingUser = userdatarepository.findById(userId).orElseThrow(() -> new
		        BookStoreException(BookStoreException.ExceptionTypes.USER_NOT_FOUND));
		
		CartDetails cartdetails = cartrepository.findByUserData(findExistingUser).orElseThrow(() -> new
				BookStoreException(BookStoreException.ExceptionTypes.CART_NOT_PRESENT));
		
		BookData bookById = bookdatarepository.findById(cartdto.getBookId())
				            .orElseThrow(() -> new
				              BookStoreException(BookStoreException.ExceptionTypes.BOOK_NOT_FOUND));
		
		List<BookCartData> cartlist = new ArrayList<>();
		BookCartData cartdata = new BookCartData(cartdto);
		
		cartlist.add(cartdata);
		cartdetails.getCartdata().add(cartdata);
		cartdetails.setCartdata(cartlist);
		cartrepository.save(cartdetails);
		
		cartdata.setCartDetails(cartdetails);
		cartdata.setBookdata(bookById);
		BookCartData savebooksTocart = bookcartrepository.save(cartdata);
		
		return "Book added successfully";
	}


	@Override
	public List<BookCartData> displayBooksFromCart(String token) {
		 UUID userId = jwtToken.decodeJWT(token);
		 Optional<UserData> findExistingUser = userdatarepository.findById(userId);
		 
		 if(!findExistingUser.isPresent()) {
			 throw new BookStoreException(BookStoreException.ExceptionTypes.USER_NOT_FOUND);
		 }
		
		return bookcartrepository.findAll()
				.stream()
				.map(bookCartData -> new BookCartData(bookCartData))
				.collect(Collectors.toList());
	}


	@Override
	public String deleteBookFromCart(UUID bookId, String token) {
		UUID userId = jwtToken.decodeJWT(token);
		 Optional<UserData> findExistingUser = userdatarepository.findById(userId);
		 
		 if(!findExistingUser.isPresent()) {
			 throw new BookStoreException(BookStoreException.ExceptionTypes.USER_NOT_FOUND);
		 }
		 
		 Optional<BookCartData> findbookById =bookcartrepository.findByBookdataBookId(bookId);
		 if (!findbookById.isPresent()){
	            throw new BookStoreException(BookStoreException.ExceptionTypes.BOOK_NOT_FOUND);
	      }
		 
		 BookData searchbookbyId = bookdatarepository.findById(bookId)
				                   .orElseThrow(()-> new BookStoreException(BookStoreException.ExceptionTypes.BOOK_NOT_FOUND));

		 bookdatarepository.save(searchbookbyId);
		 bookcartrepository.deleteById(findbookById.get().getCartdataId());
		return "Book REMOVED Successfully";
	}


	@Override
	public String updateCartData(UpdateCartDTO updatedto, String token) {
		UUID userId = jwtToken.decodeJWT(token);
     
		Optional<UserData> findExistingUser = userdatarepository.findById(userId);
     
                if(!findExistingUser.isPresent()){
                       throw new BookStoreException(BookStoreException.ExceptionTypes.USER_NOT_FOUND);
                }
                 
		 BookCartData cartdata = bookcartrepository.findByBookdataBookId(updatedto.getBookId())
				             .orElseThrow(() -> new BookStoreException(BookStoreException.ExceptionTypes.BOOK_NOT_FOUND));
		
		 System.out.println(cartdata);
		 
		 cartdata.setQuantity(updatedto.getQuantity());
		 cartdata.setTotalprice(updatedto.getTotalprice());
	         bookcartrepository.save(cartdata);
	     
		 return "Changes save Successfully";
	}

	@Override
	public boolean setUserId(UserData userdata) {
		CartDetails cartdetails = new CartDetails();
		cartdetails.setUserData(userdata);;
		
		cartrepository.save(cartdetails);
		return true;
	}
	
}
