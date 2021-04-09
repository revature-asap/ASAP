package com.revature.services;

import com.revature.entities.Asset;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.repositories.AssetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Service that for data validation on Asset entities
 */
@Service
public class AssetService {
    private AssetRepository assetRepo;

    /**
     * Constructor for the AssetService
     * @param assetRepo the Repository for the Asset Entities
     */
    @Autowired
    public AssetService(AssetRepository assetRepo) {
        this.assetRepo = assetRepo;
    }

    /**
     * Retrieves an Asset by Ticker
     * @param ticker the String representation of the ticker for a company
     * @return the Asset that corresponds to the provided ticker
     */
    public Asset getAssetByTicker(String ticker) {
        return assetRepo
                .findAssetByTicker(ticker)
                .orElseThrow(ResourceNotFoundException::new);
    }

    
}
