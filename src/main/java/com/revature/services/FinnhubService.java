package com.revature.services;

import com.revature.dtos.LunarCrushDTO;
import com.revature.entities.Asset;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.repositories.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;

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
            // If ticker is in database
            if(assetRepo.findAssetByTicker(ticker).isPresent()){
                Asset assetToCheck = assetRepo.findAssetByTicker(ticker).get();
                // If data is old and needs to be refreshed
                if (assetToCheck.getLastTouchedTimestamp().isBefore(LocalDate.now().minusDays(1))) {
                    //OLD - get record from finnhub and update database entry
                    return retrieveAssetFromApi(ticker);
                } else {
                    //STILL VALID - return asset
                    System.out.println("SAVED CALL TO FINNHUB FOR ASSET: " + assetToCheck);
                    return assetToCheck;
                }
            } else {
                // If ticker is not in database
                return retrieveAssetFromApi(ticker);
            }
        }

        public Asset retrieveAssetFromApi(String ticker) {
            // Search finnhub first (for stock tickers)
            Asset assetToCheck = searchFinnhub(ticker);
            if (assetToCheck.getName() != null) {
                assetToCheck.setLastTouchedTimestamp(LocalDate.now());
                assetRepo.save(assetToCheck); //!
                return assetToCheck;
            } else {
                // Search lunarcrush next (for crypto currencies)
                assetToCheck = searchLunarCrush(ticker);
                if (assetToCheck.getName() != null) {
                    assetToCheck.setLastTouchedTimestamp(LocalDate.now());
                    assetRepo.save(assetToCheck); //!
                    return assetToCheck;
                } else {
                    throw new ResourceNotFoundException("The provided ticker does not correspond to any known stock or crypto currency.");
                }
            }
        }
        
        public Asset searchFinnhub(String ticker) {
            WebClient client;
            client = WebClient.create("https://finnhub.io/api/v1/stock/profile2?token=c1ceppv48v6scqmqtk5g&symbol="+ ticker);
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
            LunarCrushDTO cryptoAsset = client.get()
                    .uri(uriBuilder -> uriBuilder.build())
                    .retrieve()
                    .bodyToMono(LunarCrushDTO.class) //map results to a LunarCrushDTO
                    .blockOptional().orElseThrow(RuntimeException::new);
            return cryptoAsset.convertToAsset(); // convert the LunarCrush object to an Asset
        }


}
