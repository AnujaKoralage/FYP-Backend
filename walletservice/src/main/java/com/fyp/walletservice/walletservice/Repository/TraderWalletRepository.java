package com.fyp.walletservice.walletservice.Repository;

import com.fyp.walletservice.walletservice.Entity.TraderWalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TraderWalletRepository extends JpaRepository<TraderWalletEntity, Long> {

    public TraderWalletEntity findByTraderId(Long id);

}
