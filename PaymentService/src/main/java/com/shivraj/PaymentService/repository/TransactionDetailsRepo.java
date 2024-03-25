package com.shivraj.PaymentService.repository;

import com.shivraj.PaymentService.entity.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailsRepo extends JpaRepository<TransactionDetails,Long> {
}
