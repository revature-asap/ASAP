package com.revature.controllers;

import com.revature.entities.Asset;
import com.revature.entities.SentimentCarrier;
import com.revature.services.FinnhubService;
import com.revature.services.SentimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/asset")
public class AssetController {

    private final FinnhubService finnhubService;

    @Autowired
    public AssetController(FinnhubService finnhubService){
        this.finnhubService = finnhubService;
    }

    //Get (Updating an Asset existing (once every 24 hours)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    Asset getAsset(@RequestParam String ticker){
        return finnhubService.getAsset(ticker);
    }

}
