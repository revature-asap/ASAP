package com.revature.controllers;

import com.revature.entities.Asset;
import com.revature.services.FinnhubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


/**
 * A RestController that handles endpoints dealing with Asset entities
 */
@RestController
@RequestMapping("/asset")
public class AssetController {

    private final FinnhubService finnhubService;

    /**
     * Constructor for the AssetController
     * @param finnhubService the FinnhubService for the Controller
     */
    @Autowired
    public AssetController(FinnhubService finnhubService){
        this.finnhubService = finnhubService;
    }

    //Get (Updating an Asset existing (once every 24 hours)

    /**
     * Retrieves an asset by the ticker
     * @param ticker a String representation for the ticker of a company
     * @return an Asset object that corresponds to the ticker
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    Asset getAsset(@RequestParam String ticker){
        return finnhubService.getAsset(ticker);
    }

}
