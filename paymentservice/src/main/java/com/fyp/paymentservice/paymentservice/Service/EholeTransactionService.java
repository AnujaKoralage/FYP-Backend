package com.fyp.paymentservice.paymentservice.Service;

import com.fyp.paymentservice.paymentservice.Entity.InvestorEholeTransactions;
import com.fyp.paymentservice.paymentservice.Entity.TraderEholeTransactions;
import com.fyp.paymentservice.paymentservice.Repository.InvestorEholeTransactionRepository;
import com.fyp.paymentservice.paymentservice.Repository.TraderEholeTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class EholeTransactionService {

    @Autowired
    InvestorEholeTransactionRepository investorEholeTransactionRepository;

    @Autowired
    TraderEholeTransactionRepository traderEholeTransactionRepository;

    public String saveInvestTransaction(String scope, Double amount, Long eholeId, Long userId) throws Exception {

        if (scope.equals("role_trader")){
            TraderEholeTransactions traderEholeTransactions = new TraderEholeTransactions();
            traderEholeTransactions.setAmount(amount);
            traderEholeTransactions.setCreatedDate(LocalDateTime.now(ZoneId.systemDefault()));
            traderEholeTransactions.setEholeId(eholeId);
            traderEholeTransactions.setTraderId(userId);

            TraderEholeTransactions save = traderEholeTransactionRepository.save(traderEholeTransactions);
            return save.getId();

        } else if (scope.equals("role_investor")) {
            InvestorEholeTransactions investorEholeTransactions = new InvestorEholeTransactions();
            investorEholeTransactions.setAmount(amount);
            investorEholeTransactions.setCreatedDate(LocalDateTime.now(ZoneId.systemDefault()));
            investorEholeTransactions.setEholeId(eholeId);
            investorEholeTransactions.setInvestorId(userId);

            InvestorEholeTransactions save = investorEholeTransactionRepository.save(investorEholeTransactions);
            return save.getId();
        }
        throw new Exception();
    }

}
