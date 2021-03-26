package com.revature.controllers;

import com.revature.entities.SentimentCarrier;
import com.revature.services.SentimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sentiment")
public class SentimentController {

    private final SentimentService sentimentService;

    @Autowired
    public SentimentController(SentimentService sentimentService){
        this.sentimentService = sentimentService;
    }

    //Get (Updating an Asset existing (once every 24 hours)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    SentimentCarrier freshSentiment(@RequestParam String asset){
        return sentimentService.updatedSentiment(asset);
    }
}
