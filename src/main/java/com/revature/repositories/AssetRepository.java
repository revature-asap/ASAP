package com.revature.repositories;

import com.revature.entities.Asset;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository containing queries for accessing the asset table of the database
 */
@Repository
public interface AssetRepository extends CrudRepository<Asset, Integer>  {

    Optional<Asset> findAssetByTicker(String ticker);

}
