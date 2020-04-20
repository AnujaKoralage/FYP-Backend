package com.fyp.walletservice.walletservice.Repository;

import com.fyp.walletservice.walletservice.Entity.InvestorWalletTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestorWalletTransactionHistory extends JpaRepository<InvestorWalletTransactionEntity, Long> {
}
