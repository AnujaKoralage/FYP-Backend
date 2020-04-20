package com.fyp.walletservice.walletservice.Service;

import com.fyp.walletservice.walletservice.DTO.TraderWalletDTO;
import com.fyp.walletservice.walletservice.Entity.TraderWalletEntity;
import com.fyp.walletservice.walletservice.Repository.TraderWalletRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraderService {

    @Autowired
    TraderWalletRepository traderWalletRepository;

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

}
