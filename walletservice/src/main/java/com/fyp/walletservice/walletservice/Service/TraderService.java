package com.fyp.walletservice.walletservice.Service;

import com.fyp.walletservice.walletservice.DTO.TraderWalletDTO;
import com.fyp.walletservice.walletservice.DTO.TraderWalletTransactionDTO;
import com.fyp.walletservice.walletservice.Entity.TraderWalletEntity;
import com.fyp.walletservice.walletservice.Entity.TraderWalletTransactionsEntity;
import com.fyp.walletservice.walletservice.Repository.TraderWalletRepository;
import com.fyp.walletservice.walletservice.Repository.TraderWalletTransactionHistory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TraderService {

    @Autowired
    TraderWalletRepository traderWalletRepository;

    @Autowired
    TraderWalletTransactionHistory traderWalletTransactionHistory;

    @Autowired
    ModelMapper modelMapper;

    public TraderWalletDTO create(TraderWalletDTO traderWalletDTO) throws Exception {

        TraderWalletEntity mapEnty = modelMapper.map(traderWalletDTO, TraderWalletEntity.class);
        TraderWalletEntity traderWalletEntity = traderWalletRepository.save(mapEnty);
        if (traderWalletEntity == null) {
            throw new Exception();
        }
        TraderWalletDTO map = modelMapper.map(traderWalletEntity, TraderWalletDTO.class);
        return map;

    }

    public TraderWalletDTO findByTraderId(Long id) {
        TraderWalletEntity byTraderId = traderWalletRepository.findByTraderId(id);
        if (byTraderId == null){
            return null;
        }
        return modelMapper.map(byTraderId, TraderWalletDTO.class);
    }

    public TraderWalletDTO findWalletById(Long userId) throws Exception {
        TraderWalletEntity byTraderId = traderWalletRepository.findByTraderId(userId);
        if (byTraderId == null) {
            throw new Exception();
        }
        return modelMapper.map(byTraderId, TraderWalletDTO.class);
    }

    public TraderWalletTransactionDTO update(TraderWalletTransactionDTO transactionDTO, Double balance, Long userId) throws Exception {
        TraderWalletTransactionsEntity entity = new TraderWalletTransactionsEntity();
        entity.setAmount(transactionDTO.getAmount());
        entity.setPaymentTransactionId(transactionDTO.getPaymentTransactionId());

        TraderWalletEntity traderWalletEntity = traderWalletRepository.findByTraderId(userId);
        traderWalletEntity.setCurrentBalance(balance);
        traderWalletEntity.setLastUpateedDate(LocalDate.now());

        entity.setTraderWalletEntity(traderWalletEntity);
        TraderWalletTransactionsEntity save = traderWalletTransactionHistory.save(entity);
        if (save == null) {
            throw new Exception();
        }
        return modelMapper.map(save, TraderWalletTransactionDTO.class);
    }

}
