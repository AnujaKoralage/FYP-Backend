package com.fyp.paymentservice.paymentservice.Repository;

import com.fyp.paymentservice.paymentservice.Entity.TraderEholeTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraderEholeTransactionRepository extends JpaRepository<TraderEholeTransactions, String> {
}
