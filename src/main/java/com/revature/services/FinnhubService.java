package com.revature.services;

import com.revature.dtos.LunarCrushDTO;
import com.revature.entities.Asset;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.repositories.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
public class FinnhubService {

        private AssetRepository assetRepo;

        @Autowired
        public FinnhubService(AssetRepository assetRepo) {
            this.assetRepo = assetRepo;
        }

        //THIS is where we do logic to see if the asset is in the repository/database - otherwise we push the call off to the finnhub service
        public Asset getAsset(String ticker) {
            if (ticker == null || ticker.trim().equals("")) {
                throw new InvalidRequestException("Ticker must not be empty");
            }
            ticker = ticker.toUpperCase();

            try {
                // If ticker is in database
                Asset savedAsset = assetRepo.findAssetByTicker(ticker).get();
                if (savedAsset.getLastTouchedTimestamp().isBefore(LocalDate.now().minusDays(1))) {
                    //OLD - get record from finnhub and update database entry
                    return retrieveAssetFromApi(ticker);
                } else {
                    //STILL VALID - return asset
                    return savedAsset;
                }
            } catch (NoSuchElementException e) {
                return retrieveAssetFromApi(ticker);
            }
        }

        public Asset retrieveAssetFromApi(String ticker) {
            //check database to actually get that asset if it exists
            Asset dbAsset;
            try {
                dbAsset = assetRepo.findAssetByTicker(ticker).get();
            } catch (NoSuchElementException e) {
                dbAsset = null;
            }

            // Search finnhub first (for stock tickers)
            Asset assetToCheck = searchFinnhub(ticker);
            // if we got an Asset back from Finnhub
            if (assetToCheck != null && assetToCheck.getName() != null) {
                if (dbAsset != null && assetToCheck.getName().equals(dbAsset.getName())) {
                    //we are updating a record in the database here
                    dbAsset.setLastTouchedTimestamp(LocalDate.now());
                    assetRepo.save(dbAsset);
                    return dbAsset;
                } else {
                    //we are creating a new record in the database here
                    assetToCheck.setLastTouchedTimestamp(LocalDate.now());
                    assetRepo.save(assetToCheck);
                    return assetToCheck;
                }
            } else {
                // Search lunarcrush next (for crypto currencies)
                assetToCheck = searchLunarCrush(ticker);
                if (assetToCheck != null && assetToCheck.getName() != null) {
                    if (dbAsset != null && assetToCheck.getName().equals(dbAsset.getName())) {
                        //we are updating a record in the database here
                        dbAsset.setLastTouchedTimestamp(LocalDate.now());
                        assetRepo.save(dbAsset);
                        return dbAsset;
                    } else {
                        //we are creating a new record in the database here
                        assetToCheck.setLastTouchedTimestamp(LocalDate.now());
                        assetRepo.save(assetToCheck);
                        return assetToCheck;
                    }
                } else {
                    throw new ResourceNotFoundException("The provided ticker does not correspond to any known stock or crypto currency.");
                }
            }
        }
        
        /**
         * Searches Finnhub to retrieve a stock for a ticker.
         * If the ticker does not exist on the Finnhub API, returns {@code null}
         * @param ticker
         * @return an {@code Asset} corresponding to {@code ticker} or {@code null}
         */
        public Asset searchFinnhub(String ticker) {
            WebClient client;
            client = WebClient.create("https://finnhub.io/api/v1/stock/profile2?token=c1ceppv48v6scqmqtk5g&symbol="+ ticker);
            return client.get()
                    .uri(uriBuilder -> uriBuilder.build())
                    .retrieve()
                    .bodyToMono(Asset.class)//map results to a Asset
                    .blockOptional().orElse(null);
        }
        
        /**
         * Searches LunarCrush to retrieve a cryptocurrency for a ticker.
         * If the ticker does not exist on the LunarCrush API, returns {@code null}
         * @param ticker
         * @return an {@code Asset} corresponding to {@code ticker} or {@code null}
         */
        public Asset searchLunarCrush(String ticker) {
            WebClient client;
            try {
                client = WebClient.create("https://api.lunarcrush.com/v2?data=assets&key=x9aazwqfpgvfd08gtrd2&symbol="+ ticker);
                LunarCrushDTO cryptoAsset = client.get()
                        .uri(uriBuilder -> uriBuilder.build())
                        .retrieve()
                        .bodyToMono(LunarCrushDTO.class) //map results to a LunarCrushDTO
                        .blockOptional().orElse(null);
                return cryptoAsset.getAsset(); // convert the LunarCrush object to an Asset
            } catch (WebClientResponseException e) {
                // LunarCrush will return a 502 if the ticker symbol doesn't exist in their database
                // so we want to catch it and return null here
                return null;
            }
        }


}
