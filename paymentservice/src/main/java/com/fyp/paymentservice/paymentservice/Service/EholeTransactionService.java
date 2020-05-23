package com.fyp.paymentservice.paymentservice.Service;

import com.fyp.paymentservice.paymentservice.DTO.PaybackTransactionDTO;
import com.fyp.paymentservice.paymentservice.Entity.InvestorEholeTransactions;
import com.fyp.paymentservice.paymentservice.Entity.TraderEholeTransactions;
import com.fyp.paymentservice.paymentservice.Enum.UserType;
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

    public String saveInvestTransaction(String scope, Double amount, Long eholeId, Long userId, Long eholeTransactionId) throws Exception {

        if (scope.equals("role_trader")){
            TraderEholeTransactions traderEholeTransactions = new TraderEholeTransactions();
            traderEholeTransactions.setAmount(amount);
            traderEholeTransactions.setCreatedDate(LocalDateTime.now(ZoneId.systemDefault()));
            traderEholeTransactions.setEholeId(eholeId);
            traderEholeTransactions.setTraderId(userId);
            traderEholeTransactions.setEholeTransactionId(eholeTransactionId);

            TraderEholeTransactions save = traderEholeTransactionRepository.save(traderEholeTransactions);
            return save.getId();

        } else if (scope.equals("role_investor")) {
            InvestorEholeTransactions investorEholeTransactions = new InvestorEholeTransactions();
            investorEholeTransactions.setAmount(amount);
            investorEholeTransactions.setCreatedDate(LocalDateTime.now(ZoneId.systemDefault()));
            investorEholeTransactions.setEholeId(eholeId);
            investorEholeTransactions.setInvestorId(userId);
            investorEholeTransactions.setEholeTransactionId(eholeTransactionId);

            InvestorEholeTransactions save = investorEholeTransactionRepository.save(investorEholeTransactions);
            return save.getId();
        }
        throw new Exception();
    }

    public String paybackTransactions(PaybackTransactionDTO paybackTransactionDTO) {
//        for (PaybackTransactionDTO paybackTransactionDTO:
//              paybackTransactionDTOS) {
            if (paybackTransactionDTO.getUserType().equals(UserType.TRADER)) {
                TraderEholeTransactions traderEholeTransactionsByEholeTransactionId = traderEholeTransactionRepository.findTraderEholeTransactionsByEholeTransactionId(paybackTransactionDTO.getId());
                if (traderEholeTransactionsByEholeTransactionId.getEholeTransactionId() != paybackTransactionDTO.getId()) {
                    System.out.println("SERVICE // // TRANSACTION ID MISMATCH");
                    return null;
                }
                TraderEholeTransactions newTraderEholeTransactions = new TraderEholeTransactions();

                newTraderEholeTransactions.setAmount(traderEholeTransactionsByEholeTransactionId.getAmount());
                newTraderEholeTransactions.setCreatedDate(LocalDateTime.now(ZoneId.systemDefault()));
                newTraderEholeTransactions.setEholeId(traderEholeTransactionsByEholeTransactionId.getEholeId());
                newTraderEholeTransactions.setTraderId(traderEholeTransactionsByEholeTransactionId.getTraderId());
                newTraderEholeTransactions.setEholeTransactionId(paybackTransactionDTO.getNewTransactionId());
                TraderEholeTransactions save = traderEholeTransactionRepository.save(newTraderEholeTransactions);
                if (save != null) {
                    return save.getId();
                }
            } else if (paybackTransactionDTO.getUserType().equals(UserType.INVESTOR)) {
                InvestorEholeTransactions investorEholeTransactionsByEholeTransactionId = investorEholeTransactionRepository.findInvestorEholeTransactionsByEholeTransactionId(paybackTransactionDTO.getId());
                if (investorEholeTransactionsByEholeTransactionId.getEholeTransactionId() != paybackTransactionDTO.getId()) {
                    System.out.println("SERVICE // // TRANSACTION ID MISMATCH");
                    return null;
                }
                InvestorEholeTransactions newInvestorEholeTransactions = new InvestorEholeTransactions();

                newInvestorEholeTransactions.setEholeTransactionId(paybackTransactionDTO.getNewTransactionId());
                newInvestorEholeTransactions.setInvestorId(investorEholeTransactionsByEholeTransactionId.getInvestorId());
                newInvestorEholeTransactions.setAmount(paybackTransactionDTO.getAmount());
                newInvestorEholeTransactions.setCreatedDate(LocalDateTime.now(ZoneId.systemDefault()));
                newInvestorEholeTransactions.setEholeId(investorEholeTransactionsByEholeTransactionId.getEholeId());
                InvestorEholeTransactions save = investorEholeTransactionRepository.save(newInvestorEholeTransactions);
                if (save != null) {
                    return save.getId();
                }
            }
//        }
        return null;
    }

    public boolean isTransactionExists(Long eholeTransactionId, UserType userType) {
        if (userType.equals(UserType.INVESTOR)) {
            InvestorEholeTransactions investorEholeTransactionsByEholeTransactionId = investorEholeTransactionRepository.findInvestorEholeTransactionsByEholeTransactionId(eholeTransactionId);
            if (investorEholeTransactionsByEholeTransactionId != null) {
                return true;
            }
        } else if (userType.equals(UserType.TRADER)) {
            TraderEholeTransactions traderEholeTransactionsByEholeTransactionId = traderEholeTransactionRepository.findTraderEholeTransactionsByEholeTransactionId(eholeTransactionId);
            if (traderEholeTransactionsByEholeTransactionId != null) {
                return true;
            }
        }
        return false;
    }

}
