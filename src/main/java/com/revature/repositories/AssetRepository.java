package com.revature.repositories;

import java.util.Optional;

import com.revature.entities.Asset;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends JpaRepository<Asset,Integer> {
    
    Optional<Asset> findAssetByTicker(String ticker);

}
