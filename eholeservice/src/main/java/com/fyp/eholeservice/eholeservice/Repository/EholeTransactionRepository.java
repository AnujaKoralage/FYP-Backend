package com.fyp.eholeservice.eholeservice.Repository;

import com.fyp.eholeservice.eholeservice.Entity.EholeTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EholeTransactionRepository extends JpaRepository<EholeTransactionEntity, Long> {
}
