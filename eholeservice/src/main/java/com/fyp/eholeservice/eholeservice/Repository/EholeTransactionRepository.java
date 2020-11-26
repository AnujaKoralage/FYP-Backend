package com.fyp.eholeservice.eholeservice.Repository;

import com.fyp.eholeservice.eholeservice.Entity.EholeEntity;
import com.fyp.eholeservice.eholeservice.Entity.EholeTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EholeTransactionRepository extends JpaRepository<EholeTransactionEntity, Long> {

    public List<EholeTransactionEntity> findEholeTransactionEntitiesByEholeEntity(EholeEntity eholeEntity);

    public List<EholeTransactionEntity> findAllByUserId(Long id);

}
