package com.bridgelabz.onlinebookstore.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.onlinebookstore.model.OrderData;

@Repository
public interface OrderDataRepository extends JpaRepository<OrderData, UUID> {

	List<OrderData> findOderDataByUserId(UUID userId);
        Optional<OrderData> findByOrderId(Integer orderId);
}
