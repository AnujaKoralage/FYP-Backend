package com.fyp.paymentservice.paymentservice.Repository;

import com.fyp.paymentservice.paymentservice.Entity.InvestorTopupWithdrawTransactions;
import com.fyp.paymentservice.paymentservice.Enum.PaymentStatusTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestorTopupWithdrawRepository extends JpaRepository<InvestorTopupWithdrawTransactions, Long> {
    public InvestorTopupWithdrawTransactions findInvestorTopupWithdrawTransactionsById(String id);

    public List<InvestorTopupWithdrawTransactions> findAllByInvestorIdAndStatusType(Long investorId, PaymentStatusTypes success);
}
