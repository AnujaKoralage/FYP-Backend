package com.fyp.walletservice.walletservice.Service;

import com.fyp.walletservice.walletservice.DTO.InvestorWalletDTO;
import com.fyp.walletservice.walletservice.DTO.InvestorWalletTransactionDTO;
import com.fyp.walletservice.walletservice.Entity.InvestorWalletEntity;
import com.fyp.walletservice.walletservice.Entity.InvestorWalletTransactionEntity;
import com.fyp.walletservice.walletservice.Repository.InvestorWalletRepository;
import com.fyp.walletservice.walletservice.Repository.InvestorWalletTransactionHistory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class InvestorService {
    @Autowired
    InvestorWalletRepository investorWalletRepository;

    @Autowired
    InvestorWalletTransactionHistory investorWalletTransactionHistory;

    @Autowired
    ModelMapper modelMapper;

    public InvestorWalletDTO create(InvestorWalletDTO InvestorWalletDTO) throws Exception {

        InvestorWalletEntity mapEnty = modelMapper.map(InvestorWalletDTO, InvestorWalletEntity.class);
        InvestorWalletEntity InvestorWalletEntity = investorWalletRepository.save(mapEnty);
        if (InvestorWalletEntity == null) {
            throw new Exception();
        }
        InvestorWalletDTO map = modelMapper.map(InvestorWalletEntity, InvestorWalletDTO.class);
        return map;

    }

    public InvestorWalletDTO findByInvestorId(Long id) {
        InvestorWalletEntity byInvestorId = investorWalletRepository.findByInvestorId(id);
        if (byInvestorId == null){
            return null;
        }
        return modelMapper.map(byInvestorId, InvestorWalletDTO.class);
    }

    public InvestorWalletDTO findWalletById(Long userId) throws Exception {
        InvestorWalletEntity byInvestorId = investorWalletRepository.findByInvestorId(userId);
        if (byInvestorId == null) {
            throw new Exception();
        }
        return modelMapper.map(byInvestorId, InvestorWalletDTO.class);
    }

    public InvestorWalletTransactionDTO update(InvestorWalletTransactionDTO transactionDTO, Double balance, Long userId) throws Exception {
        InvestorWalletTransactionEntity entity = new InvestorWalletTransactionEntity();
        entity.setAmount(transactionDTO.getAmount());
        entity.setPaymentTransactionId(transactionDTO.getPaymentTransactionId());

        InvestorWalletEntity InvestorWalletEntity = investorWalletRepository.findByInvestorId(userId);
        InvestorWalletEntity.setCurrentBalance(balance);
        InvestorWalletEntity.setLastUpateedDate(LocalDate.now());

        entity.setInvestorWalletEntity(InvestorWalletEntity);
        InvestorWalletTransactionEntity save = investorWalletTransactionHistory.save(entity);
        if (save == null) {
            throw new Exception();
        }
        return modelMapper.map(save, InvestorWalletTransactionDTO.class);
    }
}
