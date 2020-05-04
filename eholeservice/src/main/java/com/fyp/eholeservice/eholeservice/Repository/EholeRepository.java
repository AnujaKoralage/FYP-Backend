package com.fyp.eholeservice.eholeservice.Repository;

import com.fyp.eholeservice.eholeservice.Entity.EholeEntity;
import com.fyp.eholeservice.eholeservice.Enums.EholeStatusType;
import com.fyp.eholeservice.eholeservice.Enums.EholeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EholeRepository extends JpaRepository<EholeEntity, Long> {

    public List<EholeEntity> findEholeEntitiesByEholeStatusType(EholeStatusType eholeStatusType);

    public int countEholeEntitiesByEholeStatusType(EholeStatusType eholeStatusType);

    public EholeEntity findEholeEntitiesById(Long id);

    public Long findEholeEntitiesByTraderId(Long id);

}
