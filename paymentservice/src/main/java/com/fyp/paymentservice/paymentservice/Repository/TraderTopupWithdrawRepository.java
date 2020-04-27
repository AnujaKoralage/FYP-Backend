package com.fyp.paymentservice.paymentservice.Repository;

import com.fyp.paymentservice.paymentservice.Entity.TraderTopupWithdrawTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraderTopupWithdrawRepository extends JpaRepository<TraderTopupWithdrawTransactions, Long> {
    public TraderTopupWithdrawTransactions getTraderTopupWithdrawTransactionsById(String id);
}
