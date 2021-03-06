package com.bridgelabz.onlinebookstore.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.bridgelabz.onlinebookstore.dto.BookDataDTO;

import lombok.Data;

@Entity
@Table(name="books_data")
public @Data class BookData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(type="uuid-char")
	private UUID bookId;
	
	private String bookname;
	private String author;
	public double ratings;
	public double price;
	public int quantity;
	public LocalDateTime createdAt = LocalDateTime.now();
	
	public BookData() {}
	
	public BookData(BookData bookdata) {}
	
	@OneToMany( mappedBy = "bookdata")
	public List<BookCartData> bookCartData;
	
	public BookData(UUID bookId, BookDataDTO bookdatadto) {
		this.bookId = bookId;
		this.bookname = bookdatadto.getBookname();
		this.author = bookdatadto.getAuthor();
		this.ratings = bookdatadto.getRatings();
		this.price = bookdatadto.getPrice();
		this.quantity = bookdatadto.getQuantity();
	}
	
	public BookData(BookDataDTO bookdatadto) {
		this.bookname = bookdatadto.getBookname();
		this.author = bookdatadto.getAuthor();
		this.ratings = bookdatadto.getRatings();
		this.price = bookdatadto.getPrice();
		this.quantity = bookdatadto.getQuantity();
	}

	public BookData(String author, String bookname, double price, int quantity, double ratings) {
		this.author = author;
		this.bookname = bookname;
		this.price = price;
		this.quantity = quantity;
		this.ratings = ratings;
			
	}
	
	public BookData(UUID bookId) {
		this.bookId = bookId;
	}

	public void setAdded(boolean b) {
		
		
	}
	
}
