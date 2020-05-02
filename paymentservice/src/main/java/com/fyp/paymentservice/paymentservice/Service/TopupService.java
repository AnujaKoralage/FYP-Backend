package com.fyp.paymentservice.paymentservice.Service;

import com.fyp.paymentservice.paymentservice.DTO.PayDTO;
import com.fyp.paymentservice.paymentservice.Entity.InvestorTopupWithdrawTransactions;
import com.fyp.paymentservice.paymentservice.Entity.TraderTopupWithdrawTransactions;
import com.fyp.paymentservice.paymentservice.Enum.PaymentStatusTypes;
import com.fyp.paymentservice.paymentservice.Enum.TransactionTypes;
import com.fyp.paymentservice.paymentservice.Repository.InvestorTopupWithdrawRepository;
import com.fyp.paymentservice.paymentservice.Repository.TraderTopupWithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TopupService {

    @Autowired
    TraderTopupWithdrawRepository traderTopupWithdrawRepository;

    @Autowired
    InvestorTopupWithdrawRepository investorTopupWithdrawRepository;

    public String saveTransaction(PayDTO payDTO, String id, int userType, TransactionTypes transactionType) throws Exception {
        if (id == null) {
            throw new Exception();
        }
        if (userType == 1) {
            TraderTopupWithdrawTransactions traderTopupWithdrawTransaction = new TraderTopupWithdrawTransactions();
            traderTopupWithdrawTransaction.setAmount(payDTO.getPrice());
            traderTopupWithdrawTransaction.setCreatedDate(LocalDate.now());
            traderTopupWithdrawTransaction.setStatusType(PaymentStatusTypes.PROCESSING);
            traderTopupWithdrawTransaction.setTraderId(Long.valueOf(id));
            traderTopupWithdrawTransaction.setTransactionType(transactionType);

            TraderTopupWithdrawTransactions save = traderTopupWithdrawRepository.save(traderTopupWithdrawTransaction);
            if (save == null) {
                throw new Exception();
            }
            return save.getId();
        } else {
            InvestorTopupWithdrawTransactions investorTopupWithdrawTransaction = new InvestorTopupWithdrawTransactions();
            investorTopupWithdrawTransaction.setAmount(payDTO.getPrice());
            investorTopupWithdrawTransaction.setCreatedDate(LocalDate.now());
            investorTopupWithdrawTransaction.setStatusType(PaymentStatusTypes.PROCESSING);
            investorTopupWithdrawTransaction.setInvestorId(Long.valueOf(id));
            investorTopupWithdrawTransaction.setTransactionType(transactionType);

            InvestorTopupWithdrawTransactions save = investorTopupWithdrawRepository.save(investorTopupWithdrawTransaction);
            if (save == null) {
                throw new Exception();
            }
            return save.getId();
        }

    }

    public Long updateTransaction(PaymentStatusTypes paymentStatus, String paypalId, String transactionId, String userId) throws Exception {
        TraderTopupWithdrawTransactions traderTopupWithdrawTransactionsById = traderTopupWithdrawRepository.getTraderTopupWithdrawTransactionsById(transactionId);
        if (traderTopupWithdrawTransactionsById != null) {
            if (!userId.equals(traderTopupWithdrawTransactionsById.getTraderId().toString())) {
                throw new Exception("conflict User id");
            }
            traderTopupWithdrawTransactionsById.setPaypalPaymentId(paypalId);
            traderTopupWithdrawTransactionsById.setStatusType(paymentStatus);
            TraderTopupWithdrawTransactions save = traderTopupWithdrawRepository.save(traderTopupWithdrawTransactionsById);
            if (save == null) {
                throw new Exception("Save problem");
            }
            return save.getTraderId();
        } else {
            InvestorTopupWithdrawTransactions investorTopupWithdrawTransactionsById = investorTopupWithdrawRepository.findInvestorTopupWithdrawTransactionsById(transactionId);
            if (investorTopupWithdrawTransactionsById == null) {
                throw new Exception("transactionId Not exists");
            }
            if (userId.equals(investorTopupWithdrawTransactionsById.getId().toString())) {
                throw new Exception("conflict User id");
            }
            investorTopupWithdrawTransactionsById.setPaypalPaymentId(paypalId);
            investorTopupWithdrawTransactionsById.setStatusType(paymentStatus);
            InvestorTopupWithdrawTransactions save = investorTopupWithdrawRepository.save(investorTopupWithdrawTransactionsById);
            if (save == null) {
                throw new Exception("Save problem");
            }
            return save.getInvestorId();
        }

    }

}
