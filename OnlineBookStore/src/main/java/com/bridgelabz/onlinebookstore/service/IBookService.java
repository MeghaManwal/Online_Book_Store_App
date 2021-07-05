package com.bridgelabz.onlinebookstore.service;

import java.util.List;

import com.bridgelabz.onlinebookstore.dto.BookDataDTO;
import com.bridgelabz.onlinebookstore.model.BookData;

public interface IBookService {
	
	public BookData addNewBook(BookDataDTO bookdatadto);
	public List<BookData> getAllBooks(); 
	public String getbookscount();
	public List<BookData> displaybyHighertoLowerPrice();
	public List<BookData> displaybyLowertoHigherPrice();
	public List<BookData> displayBooksbyDateOfArrival();
	
}
