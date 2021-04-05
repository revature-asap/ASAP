package com.revature.repositories;

import com.revature.entities.Asset;
import com.revature.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssetRepository extends CrudRepository<Asset, Integer>  {

    Optional<Asset> findAssetByTicker(String ticker);

}
