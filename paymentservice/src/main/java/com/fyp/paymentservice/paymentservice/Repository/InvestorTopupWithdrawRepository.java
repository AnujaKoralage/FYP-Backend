package com.fyp.paymentservice.paymentservice.Repository;

import com.fyp.paymentservice.paymentservice.Entity.InvestorTopupWithdrawTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestorTopupWithdrawRepository extends JpaRepository<InvestorTopupWithdrawTransactions, Long> {
    public InvestorTopupWithdrawTransactions findInvestorTopupWithdrawTransactionsById(String id);
}
