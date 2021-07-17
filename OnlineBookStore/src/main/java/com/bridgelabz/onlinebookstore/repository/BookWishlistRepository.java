package com.bridgelabz.onlinebookstore.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.onlinebookstore.model.BookWishlistData;

@Repository
public interface BookWishlistRepository extends JpaRepository<BookWishlistData, UUID> {

	Optional<BookWishlistData> findByBookdataBookId(UUID bookId);
	
}
