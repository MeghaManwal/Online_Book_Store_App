package com.bridgelabz.onlinebookstore.service;

import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.exception.BookStoreException;
import com.bridgelabz.onlinebookstore.model.BookCartData;
import com.bridgelabz.onlinebookstore.model.BookData;
import com.bridgelabz.onlinebookstore.model.CartDetails;
import com.bridgelabz.onlinebookstore.model.CustomerData;
import com.bridgelabz.onlinebookstore.model.OrderData;
import com.bridgelabz.onlinebookstore.model.UserData;
import com.bridgelabz.onlinebookstore.repository.BookCartRepository;
import com.bridgelabz.onlinebookstore.repository.BookDataRepository;
import com.bridgelabz.onlinebookstore.repository.CartDetailsRepository;
import com.bridgelabz.onlinebookstore.repository.CustomerDataRepository;
import com.bridgelabz.onlinebookstore.repository.OrderDataRepository;
import com.bridgelabz.onlinebookstore.repository.UserDataRepository;
import com.bridgelabz.onlinebookstore.utils.TokenUtils;

@Service
public class OrderService implements IOrderService{

        TokenUtils jwtToken = new TokenUtils();
	
	@Autowired
	UserDataRepository userdatarepository;
	
	@Autowired
	CartDetailsRepository cartrepository;
	
	@Autowired
	BookCartRepository bookcartrepository;
	
	@Autowired
	CustomerDataRepository customerdatarepository;
	
	@Autowired
	OrderDataRepository orderdatarepository;
	
	@Autowired
	BookDataRepository bookdatarepository;
	
	@Override
	public String placeAnOrder(Double totalPrice, UUID customerId, String token) {
		UUID userId = jwtToken.decodeJWT(token);
		UserData findExistingUser = userdatarepository.findById(userId).orElseThrow(() -> new
		        BookStoreException(BookStoreException.ExceptionTypes.USER_NOT_FOUND));
		
		CartDetails cartdetails = cartrepository.findByUserData(findExistingUser).orElseThrow(() -> new
				BookStoreException(BookStoreException.ExceptionTypes.CART_NOT_PRESENT));
		
		
		List<BookCartData> booksInCart = bookcartrepository.findByCartDetailsCartIdAndOrderstatusIsFalse(cartdetails.getCartId());
		
		CustomerData customerdata=customerdatarepository.findByUserId(cartdetails.getUserData().getUserId());
		System.out.println("data"+customerdata);
		
		Integer orderId = (int)Math.floor((Math.random() * 999999) + 100000);
		
		OrderData orderdata = new OrderData(orderId,totalPrice,LocalDate.now(),cartdetails,cartdetails.getUserData().getUserId(),
				                            customerdata, booksInCart);
		orderdata.setCustomer(customerdata);
		OrderData saveorder = orderdatarepository.save(orderdata);
		booksInCart.forEach(cartbooks -> {
			cartbooks.setOrderstatus(true);
			cartbooks.setOrderDetails(orderdata);
			
			bookdatarepository.updatetheValues(cartbooks.getQuantity(),cartbooks.getBookdata().getBookId());
			
		});
		
		booksInCart.forEach(cartbooks -> {
			BookData searchbook = bookdatarepository.findById(cartbooks.getBookdata().getBookId())
					             .orElseThrow(() -> new BookStoreException(BookStoreException.ExceptionTypes.BOOK_NOT_FOUND));
			searchbook.setQuantity(searchbook.getQuantity() - cartbooks.getQuantity());
			bookdatarepository.save(searchbook);
		});
		
		bookcartrepository.updateOrderStatus(cartdetails.getCartId());
		return "Hurray!! Your Order is Confirmed for OrderId #"+saveorder.getOrderId()+" save the OrderId for further Communication";
	}
}
