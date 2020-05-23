package com.fyp.paymentservice.paymentservice.Repository;

import com.fyp.paymentservice.paymentservice.Entity.InvestorEholeTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestorEholeTransactionRepository extends JpaRepository<InvestorEholeTransactions, String> {

    public InvestorEholeTransactions findInvestorEholeTransactionsByEholeTransactionId(Long id);

}
