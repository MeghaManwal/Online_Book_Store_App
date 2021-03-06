package com.bridgelabz.onlinebookstore.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.dto.BookDataDTO;
import com.bridgelabz.onlinebookstore.exception.BookStoreException;
import com.bridgelabz.onlinebookstore.model.BookData;
import com.bridgelabz.onlinebookstore.repository.BookDataRepository;


@Service
public class BookService implements IBookService {

	@Autowired
	private BookDataRepository bookdatarepo;
	
	@Override
	public BookData addNewBook(BookDataDTO bookdatadto) {
		Optional<BookData> searchBookByName = bookdatarepo.findByBookname(bookdatadto.getBookname());
		if(searchBookByName.isPresent()) {
			throw new BookStoreException(BookStoreException.ExceptionTypes.BOOK_ALREADY_PRESENT);
		}else {
		BookData bookdata = new BookData(bookdatadto);
		return bookdatarepo.save(bookdata);
		}
	}

	@Override
	public List<BookData> getAllBooks() {
		List<BookData> booksList = bookdatarepo.findAll().stream()
				           .collect(Collectors.toList());
		return booksList ;
	}

	@Override
	public String getbookscount() {
		long count = bookdatarepo.count();
		String numberOfBooks = "The Total Number of Books Available: "+count;
		return numberOfBooks;
	}

	@Override
	public List<BookData> displaybyHighertoLowerPrice() {
		List<BookData> booksList = bookdatarepo.findAll().stream()
                                           .sorted(Comparator.comparing(bookdata -> bookdata.price))
                                           .collect(Collectors.toList());
		Collections.reverse(booksList);
                return booksList;
	}

	@Override
	public List<BookData> displaybyLowertoHigherPrice() {
		List<BookData> booksList = bookdatarepo.findAll().stream()
                                           .sorted(Comparator.comparing(bookdata -> bookdata.price))
                                           .collect(Collectors.toList());
                return booksList;
	}

	@Override
	public List<BookData> displayBooksbyDateOfArrival() {
		List<BookData> booksList = bookdatarepo.findAll().stream()
                                           .sorted(Comparator.comparing(bookdata -> bookdata.createdAt))
                                           .collect(Collectors.toList());
		Collections.reverse(booksList);
                return booksList;
	}
}

