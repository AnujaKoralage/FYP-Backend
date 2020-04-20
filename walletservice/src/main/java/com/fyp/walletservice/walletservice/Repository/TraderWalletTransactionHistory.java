package com.fyp.walletservice.walletservice.Repository;

import com.fyp.walletservice.walletservice.Entity.TraderWalletTransactionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TraderWalletTransactionHistory extends JpaRepository<TraderWalletTransactionsEntity, Long> {
}
