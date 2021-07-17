package com.bridgelabz.onlinebookstore.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.onlinebookstore.model.CartDetails;
import com.bridgelabz.onlinebookstore.model.UserData;

@Repository
public interface CartDetailsRepository extends JpaRepository<CartDetails, UUID> {

	Optional<CartDetails> findByUserData(UserData userdata);
	//Optional<CartDetails> findByUserId(UUID UserId);
}
