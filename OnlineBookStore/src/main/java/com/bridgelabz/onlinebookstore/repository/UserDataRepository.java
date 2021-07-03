package com.bridgelabz.onlinebookstore.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bridgelabz.onlinebookstore.model.UserData;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, UUID> {
	Optional<UserData> findByEmailID(String emailId);	
}
