package com.revature.services;

import com.revature.DTO.FinnhubAssetDTO;
import com.revature.DTO.TweetsDTO;
import com.revature.entities.Asset;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.exceptions.ResourcePersistenceException;
import com.revature.repositories.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Optional;

@Service
public class FinnhubService {

        private AssetRepository assetRepo;

        @Autowired
        public FinnhubService(AssetRepository assetRepo) {
            this.assetRepo = assetRepo;
        }

        //THIS is where we do logic to see if the asset is in the repository/database - otherwise we push the call off to the finnhub service
        public Asset getAsset(String ticker) {
            ticker = ticker.toUpperCase();
            System.out.println("RETURN FROM FINDBYTICKER ON REPO: " + assetRepo.findAssetByTicker(ticker));
            if(assetRepo.findAssetByTicker(ticker).isPresent()){
                Asset assetToCheck = assetRepo.findAssetByTicker(ticker).get();
                //8640000 = a day in milliseconds
                if (assetToCheck.getLastTouchedTimestamp().isBefore(LocalDate.now().minusDays(1))) {
                    //OLD - get record from finnhub and update database entry
                    assetToCheck = searchFinnhub(ticker);
                    assetToCheck.setLastTouchedTimestamp(LocalDate.now());
                    assetRepo.save(assetToCheck);
                    return assetToCheck;
                } else {
                    //STILL VALID - return asset
                    System.out.println("SAVED CALL TO FINNHUB FOR ASSET: " + assetToCheck);
                    return assetToCheck;
                }
            } else {
                Asset assetToCheck = searchFinnhub(ticker);
                if (assetToCheck.getName() != null) {
                    assetToCheck.setLastTouchedTimestamp(LocalDate.now());
                    assetRepo.save(assetToCheck); //!
                    return assetToCheck;
                } else {
                    assetToCheck = searchLunarCrush(ticker);
                    if (assetToCheck.getName() != null) {
                        assetToCheck.setLastTouchedTimestamp(LocalDate.now());
                        assetRepo.save(assetToCheck); //!
                        return assetToCheck;
                    } else {
                        throw new ResourceNotFoundException();
                    }
                }
            }
        }

        /*
        Currently, retrieves the tweets from the past 7 days on the passed asset. Returns 10 tweets for now.
         */
        public Asset searchFinnhub(String ticker) {
            WebClient client;
            client = WebClient.create("https://finnhub.io/api/v1/stock/profile2?symbol="+ ticker + "&token=c1ceppv48v6scqmqtk5g");
            return client.get()
                    .uri(uriBuilder -> uriBuilder.build())
                    .retrieve()
                    .bodyToMono(Asset.class)//map results to a Asset
                    .blockOptional().orElseThrow(RuntimeException::new);
        }

        // Doesn't return an actual Asset right now, may need to parse the return from lunarcrush a bit
        public Asset searchLunarCrush(String ticker) {
            WebClient client;
            client = WebClient.create("https://api.lunarcrush.com/v2?data=assets&key=x9aazwqfpgvfd08gtrd2&symbol="+ ticker);
            return client.get()
                    .uri(uriBuilder -> uriBuilder.build())
                    .retrieve()
                    .bodyToMono(Asset.class)//map results to a Asset
                    .blockOptional().orElseThrow(RuntimeException::new);
        }


}
