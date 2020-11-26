package com.fyp.paymentservice.paymentservice.Repository;

import com.fyp.paymentservice.paymentservice.Entity.TraderTopupWithdrawTransactions;
import com.fyp.paymentservice.paymentservice.Enum.PaymentStatusTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TraderTopupWithdrawRepository extends JpaRepository<TraderTopupWithdrawTransactions, Long> {
    public TraderTopupWithdrawTransactions getTraderTopupWithdrawTransactionsById(String id);

    public List<TraderTopupWithdrawTransactions> findAllByTraderIdAndStatusType(Long id, PaymentStatusTypes value);
}
