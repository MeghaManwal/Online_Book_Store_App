package com.bridgelabz.onlinebookstore.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.onlinebookstore.model.BookData;

@Repository
public interface BookDataRepository extends JpaRepository<BookData, UUID> {

	Optional<BookData> findByBookname(String bookname);
}
