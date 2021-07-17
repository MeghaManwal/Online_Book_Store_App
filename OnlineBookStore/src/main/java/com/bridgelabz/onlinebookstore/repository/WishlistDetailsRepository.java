package com.bridgelabz.onlinebookstore.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.onlinebookstore.model.UserData;
import com.bridgelabz.onlinebookstore.model.WishlistDetails;

@Repository
public interface WishlistDetailsRepository extends JpaRepository<WishlistDetails, UUID> {

	Optional<WishlistDetails> findByUserData(UserData userdata);
	//Optional<WishlistDetails> findByUserId(UUID userId);
	
}
