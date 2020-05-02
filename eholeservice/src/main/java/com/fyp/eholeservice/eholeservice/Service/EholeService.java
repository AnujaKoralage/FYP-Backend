package com.fyp.eholeservice.eholeservice.Service;

import com.fyp.eholeservice.eholeservice.DTO.EholeDTO;
import com.fyp.eholeservice.eholeservice.Entity.EholeEntity;
import com.fyp.eholeservice.eholeservice.Enums.EholeAmountType;
import com.fyp.eholeservice.eholeservice.Enums.EholeStatusType;
import com.fyp.eholeservice.eholeservice.Enums.EholeType;
import com.fyp.eholeservice.eholeservice.Repository.EholeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class EholeService {

    @Autowired
    EholeRepository eholeRepository;

    public void createEhole(EholeDTO eholeDTO, Long traderId) throws Exception {

        EholeEntity eholeEntity = new EholeEntity();
        eholeEntity.setEholeType(EholeType.findByValue(eholeDTO.getEholeType()));
        eholeEntity.setTraderId(traderId);
//        eholeEntity.setCompletionDate(LocalDateTime.now(ZoneId.systemDefault()));
        eholeEntity.setCreatedDate(LocalDateTime.now(ZoneId.systemDefault()));
        eholeEntity.setEholeAmountType(EholeAmountType.UNFILLED);
        eholeEntity.setEholeStatusType(EholeStatusType.ACTIVE);
        eholeEntity.setTotalAmount(eholeDTO.getAmount());
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
            eholeEntitiesById.setEholeStatusType(EholeStatusType.CANCELED);
        } else if (eholeStatusType.equals(EholeStatusType.FINISHED)) {
            //money dispatch
            eholeEntitiesById.setEholeStatusType(EholeStatusType.FINISHED);

        }
        eholeEntitiesById.setUpdatedDate(LocalDateTime.now(ZoneId.systemDefault()));
        EholeEntity updatedEntity = eholeRepository.save(eholeEntitiesById);
        return updatedEntity.getEholeStatusType().equals(eholeStatusType);
    }

}
