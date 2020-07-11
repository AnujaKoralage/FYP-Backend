package com.fyp.eholeservice.eholeservice.Service;

import com.fyp.eholeservice.eholeservice.DTO.EhDTO;
import com.fyp.eholeservice.eholeservice.DTO.EholeDTO;
import com.fyp.eholeservice.eholeservice.DTO.PaybackTransactionDTO;
import com.fyp.eholeservice.eholeservice.Entity.EholeEntity;
import com.fyp.eholeservice.eholeservice.Entity.EholeTransactionEntity;
import com.fyp.eholeservice.eholeservice.Enums.*;
import com.fyp.eholeservice.eholeservice.Repository.EholeRepository;
import com.fyp.eholeservice.eholeservice.Repository.EholeTransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class EholeService {

    @Autowired
    EholeRepository eholeRepository;

    @Autowired
    EholeTransactionRepository eholeTransactionRepository;

    @Autowired
    ModelMapper modelMapper;

    public Long createEhole(EholeDTO eholeDTO, Long traderId) throws Exception {

        EholeEntity eholeEntity = new EholeEntity();
        eholeEntity.setEholeType(EholeType.findByValue(eholeDTO.getEholeType()));
        eholeEntity.setTraderId(traderId);
//        eholeEntity.setCompletionDate(LocalDateTime.now(ZoneId.systemDefault()));
        eholeEntity.setCreatedDate(LocalDateTime.now(ZoneId.systemDefault()));
        eholeEntity.setEholeAmountType(EholeAmountType.UNFILLED);
        eholeEntity.setEholeStatusType(EholeStatusType.ACTIVE);
        eholeEntity.setProfitMargin(eholeDTO.getProfit());
        eholeEntity.setTotalAmount(eholeDTO.getCompletedAmount());
        eholeEntity.setCompletedAmount(0);
        eholeEntity.setUpdatedDate(LocalDateTime.now(ZoneId.systemDefault()));
        if (eholeEntity.getEholeType().equals(EholeType.ONEHOUREHOLE)) {
            eholeEntity.setCompletionDate(LocalDateTime.now(ZoneId.systemDefault()).plusHours(1));
        } else if (eholeEntity.getEholeType().equals(EholeType.ONEDAYEHOLE)) {
            eholeEntity.setCompletionDate(LocalDateTime.now(ZoneId.systemDefault()).plusDays(1));
        } else if (eholeEntity.getEholeType().equals(EholeType.ONEWEEKEHOLE)) {
            eholeEntity.setCompletionDate(LocalDateTime.now(ZoneId.systemDefault()).plusDays(7));
        } else {
            throw new Exception();
        }
        EholeEntity savedEhole = eholeRepository.save(eholeEntity);
        if (savedEhole == null) {
            throw new Exception();
        }
        return savedEhole.getId();

    }

    public List<EholeEntity> getAllEholesByStatus(EholeStatusType eholeStatusType) {
        return eholeRepository.findEholeEntitiesByEholeStatusType(eholeStatusType);
    }

    public int getEholeCountBtStatus(EholeStatusType eholeStatusType) {
        return eholeRepository.countEholeEntitiesByEholeStatusType(eholeStatusType);
    }

    public boolean updateEholeStatus(Long eholeId, EholeStatusType eholeStatusType) throws Exception {
        EholeEntity eholeEntitiesById = eholeRepository.findEholeEntitiesById(eholeId);
        if (eholeEntitiesById == null) {
            throw new Exception("Ehole connot found");
        }
        double requiredAmount = eholeEntitiesById.getTotalAmount() * 0.75;
        if (eholeStatusType.equals(EholeStatusType.TRADING)) {
            if (requiredAmount > eholeEntitiesById.getCompletedAmount()) {
                eholeEntitiesById.setEholeStatusType(EholeStatusType.CANCELED);
            } else {
                eholeEntitiesById.setEholeStatusType(eholeStatusType);
                eholeEntitiesById.setEholeAmountType(EholeAmountType.FILLED);
            }
        } else if (eholeStatusType.equals(EholeStatusType.CANCELED)) {
            if (eholeEntitiesById.getCompletedAmount() < requiredAmount && eholeEntitiesById.getEholeStatusType().equals(EholeStatusType.ACTIVE)) {
                eholeEntitiesById.setEholeStatusType(EholeStatusType.CANCELED);
            } else {
                throw new Exception();
            }

        } else if (eholeStatusType.equals(EholeStatusType.FINISHED)) {
            //money dispatch
            eholeEntitiesById.setEholeStatusType(EholeStatusType.FINISHED);

        }
        eholeEntitiesById.setUpdatedDate(LocalDateTime.now(ZoneId.systemDefault()));
        EholeEntity updatedEntity = eholeRepository.save(eholeEntitiesById);
        return updatedEntity.getEholeStatusType().equals(eholeStatusType);
    }

    public boolean isEholeCreaterId(Long eholeId, Long traderId) throws Exception {

        EholeEntity eholeEntitiesByTraderId = eholeRepository.findEholeEntitiesById(eholeId);
        if (eholeEntitiesByTraderId == null) {
            throw new Exception();
        }
        if (eholeEntitiesByTraderId.getTraderId() == traderId) {
            return true;
        }
        return false;
    }

    public EholeTransactionEntity investEhole(Long eholeId, double amount, Long investorId, UserType userType) throws Exception {

        EholeEntity eholeEntitiesById = eholeRepository.findEholeEntitiesById(eholeId);
        if (eholeEntitiesById.getEholeStatusType().equals(EholeStatusType.ACTIVE) && eholeEntitiesById.getEholeAmountType().equals(EholeAmountType.UNFILLED)) {
            double requiredAmountToComplete = eholeEntitiesById.getTotalAmount() - eholeEntitiesById.getCompletedAmount();
            if (requiredAmountToComplete < 0) {
                return null;
            }
            eholeEntitiesById.setCompletedAmount(eholeEntitiesById.getCompletedAmount() + amount);
            double initialRequreidAmount = eholeEntitiesById.getTotalAmount() * 0.75;
            if (initialRequreidAmount < eholeEntitiesById.getCompletedAmount()) {
                eholeEntitiesById.setEholeAmountType(EholeAmountType.FILLED);
            }
            if (eholeEntitiesById.getCompletedAmount() == eholeEntitiesById.getTotalAmount()) {
                eholeEntitiesById.setEholeStatusType(EholeStatusType.TRADING);
            }

//            List<EholeTransactionEntity> eholeTransactions = new ArrayList<>();
//            eholeTransactions.add(transaction);
//            eholeEntitiesById.setEholeTransactions(eholeTransactions);
            EholeEntity save = eholeRepository.save(eholeEntitiesById);
            if (save != null) {
                EholeTransactionEntity transaction = new EholeTransactionEntity();
                transaction.setAmount(amount);
                transaction.setTransactionType(TransactionType.INVEST);
                transaction.setUserId(investorId);
                transaction.setUserType(userType);
                transaction.setEholeEntity(save);
                EholeTransactionEntity savedTransaction = eholeTransactionRepository.save(transaction);
                if (savedTransaction == null) {
                    throw new  Exception("Unable to save transaction");
                }
                return savedTransaction;
            }
            throw new Exception();

        } else {
            return null;
        }
    }

    public boolean isEholeExists(Long eholeId){
        EholeEntity eholeEntitiesById = eholeRepository.findEholeEntitiesById(eholeId);
        if (eholeEntitiesById != null)
            return true;
        return false;
    }

    public ArrayList<PaybackTransactionDTO> paybackTransactions(Long eholeId, TransactionType transactionType) {
        EholeEntity eholeEntitiesById = eholeRepository.findEholeEntitiesById(eholeId);
        List<EholeTransactionEntity> eholeTransactionEntitiesByEholeEntity = eholeTransactionRepository.findEholeTransactionEntitiesByEholeEntity(eholeEntitiesById);
        ArrayList<PaybackTransactionDTO> transactions = new ArrayList<>();
        for (EholeTransactionEntity entity :
                eholeTransactionEntitiesByEholeEntity) {
            PaybackTransactionDTO map = modelMapper.map(entity, PaybackTransactionDTO.class);

            EholeTransactionEntity newTransaction = new EholeTransactionEntity();
            newTransaction.setEholeEntity(entity.getEholeEntity());
            newTransaction.setUserType(entity.getUserType());
            newTransaction.setUserId(entity.getUserId());
            newTransaction.setTransactionType(transactionType);
            newTransaction.setAmount(entity.getAmount());

            EholeTransactionEntity save = eholeTransactionRepository.save(newTransaction);
            map.setNewTransactionId(save.getId());
            transactions.add(map);

        }
        return transactions;

    }

    public EholeDTO getEholeById(long id) {
        EholeEntity eholeEntitiesById = eholeRepository.findEholeEntitiesById(id);
        EholeDTO eholeDTO = new EholeDTO();
        eholeDTO.setCompletedAmount(eholeEntitiesById.getCompletedAmount());
        eholeDTO.setEholeType(eholeEntitiesById.getEholeType().getValue());
        eholeDTO.setProfit(eholeEntitiesById.getProfitMargin());
        return eholeDTO;
    }

    public List<EhDTO> getEholeByStatus(EholeStatusType eholeStatusType) {
        List<EholeEntity> eholeEntitiesByEholeStatusType = eholeRepository.findEholeEntitiesByEholeStatusType(eholeStatusType);
        List<EhDTO> ehDTOS = new ArrayList<>();
        for (EholeEntity eholeEntity:
             eholeEntitiesByEholeStatusType) {
            ehDTOS.add(modelMapper.map(eholeEntity, EhDTO.class));
        }
        return ehDTOS;
    }

    public boolean checkEholeAuth(long eholeId, long userId) {
        EholeEntity eholeEntitiesById = eholeRepository.findEholeEntitiesById(eholeId);
        if (eholeEntitiesById.getTraderId() == userId) {
            return true;
        } else {
            return false;
        }
    }

    public boolean endEhole(long id, long ownerId) {
        EholeEntity eholeEntitiesById = eholeRepository.findEholeEntitiesById(id);
        if (eholeEntitiesById == null || ownerId != eholeEntitiesById.getTraderId()) {
            return false;
        }
        eholeEntitiesById.setEholeStatusType(EholeStatusType.FINISHED);
        eholeRepository.save(eholeEntitiesById);
        return true;
    }

}
