package com.revature.services;

import com.revature.entities.Asset;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.repositories.AssetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetService {
    private AssetRepository assetRepo;

    @Autowired
    public AssetService(AssetRepository assetRepo) {
        this.assetRepo = assetRepo;
    }

    public Asset getAssetByTicker(String ticker) {
        return assetRepo
                .findAssetByTicker(ticker)
                .orElseThrow(ResourceNotFoundException::new);
    }

    
}
