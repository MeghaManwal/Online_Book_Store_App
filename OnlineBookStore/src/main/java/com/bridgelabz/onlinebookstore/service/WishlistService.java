package com.bridgelabz.onlinebookstore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.dto.WishlistDataDTO;
import com.bridgelabz.onlinebookstore.exception.BookStoreException;
import com.bridgelabz.onlinebookstore.model.BookCartData;
import com.bridgelabz.onlinebookstore.model.BookData;
import com.bridgelabz.onlinebookstore.model.BookWishlistData;
import com.bridgelabz.onlinebookstore.model.UserData;
import com.bridgelabz.onlinebookstore.model.WishlistDetails;
import com.bridgelabz.onlinebookstore.repository.BookDataRepository;
import com.bridgelabz.onlinebookstore.repository.BookWishlistRepository;
import com.bridgelabz.onlinebookstore.repository.UserDataRepository;
import com.bridgelabz.onlinebookstore.repository.WishlistDetailsRepository;
import com.bridgelabz.onlinebookstore.utils.TokenUtils;

@Service
public class WishlistService implements IWishlistService{

        TokenUtils jwtToken = new TokenUtils();
	
	@Autowired
	UserDataRepository userdatarepository;
	
	@Autowired
	WishlistDetailsRepository wishlistrepository;
	
	@Autowired
	BookWishlistRepository bookwishlistrepository;
	
	@Autowired
	BookDataRepository bookdatarepository;
	
	@Override
	public String addBookToWishlist(WishlistDataDTO wishlistdto, String token) {
		if(token == null) {
			throw new BookStoreException(BookStoreException.ExceptionTypes.TOKEN_NOT_FOUND);
		}else {
		UUID userId = jwtToken.decodeJWT(token);
		UserData findExistingUser = userdatarepository.findById(userId).orElseThrow(() -> new
		                 BookStoreException(BookStoreException.ExceptionTypes.USER_NOT_FOUND));
		
		WishlistDetails wishlistdetails = wishlistrepository.findByUserData(findExistingUser).orElseThrow(() -> new
				BookStoreException(BookStoreException.ExceptionTypes.WISHLIST_NOT_PRESENT));
		
		BookData bookById = bookdatarepository.findById(wishlistdto.getBookId())
	            .orElseThrow(() -> new
	              BookStoreException(BookStoreException.ExceptionTypes.BOOK_NOT_FOUND));
		
		List<BookWishlistData> wishlistdatalist = new ArrayList<>();
		BookWishlistData data = new BookWishlistData(wishlistdto);
		
		wishlistdatalist.add(data);
		wishlistdetails.getWishlistdata().add(data);
		wishlistdetails.setWishlistdata(wishlistdatalist);
		wishlistrepository.save(wishlistdetails);
		
		data.setWhishlistDetails(wishlistdetails);
		data.setBookdata(bookById);
		BookWishlistData savebooksTocart = bookwishlistrepository.save(data);
		
		return "Book added successfully";
		}
	}

	@Override
	public List<BookWishlistData> displayBooksFromWishlist(String token) {
		UUID userId = jwtToken.decodeJWT(token);
		 Optional<UserData> findExistingUser = userdatarepository.findById(userId);
		 
		 if(!findExistingUser.isPresent()) {
			 throw new BookStoreException(BookStoreException.ExceptionTypes.USER_NOT_FOUND);
		 }
		 return bookwishlistrepository.findAll()
					.stream()
					.map(bookwishlistdetails-> new BookWishlistData(bookwishlistdetails))
					.collect(Collectors.toList());
	}

	@Override
	public String deleteBookFromWishlist(UUID bookId, String token) {
		UUID userId = jwtToken.decodeJWT(token);
		 Optional<UserData> findExistingUser = userdatarepository.findById(userId);
		 
		 if(!findExistingUser.isPresent()) {
			 throw new BookStoreException(BookStoreException.ExceptionTypes.USER_NOT_FOUND);
		 }
		 Optional<BookWishlistData> findbookById = bookwishlistrepository.findByBookdataBookId(bookId);
		 if (!findbookById.isPresent()){
	            throw new BookStoreException(BookStoreException.ExceptionTypes.BOOK_NOT_FOUND);
	         }
		 
		 
		 BookData searchbookbyId = bookdatarepository.findById(bookId)
                 .orElseThrow(()-> new BookStoreException(BookStoreException.ExceptionTypes.BOOK_NOT_FOUND));

                 bookdatarepository.save(searchbookbyId);
                 bookwishlistrepository.deleteById(findbookById.get().getWishlistdataId());
                 return "Book REMOVED Successfully";
	}

	@Override
	public boolean setUserId(UserData userdata) {
		WishlistDetails wishlistdetails = new WishlistDetails();
		wishlistdetails.setUserData(userdata);;
		
		wishlistrepository.save(wishlistdetails);
		return true;
	}

	
}
