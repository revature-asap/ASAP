package com.revature.controllers;

import com.revature.entities.SentimentCarrier;
import com.revature.services.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/twitter")
public class TwitterController {

    private final TwitterService twitterService;

    @Autowired
    public TwitterController(TwitterService twitterService){
        this.twitterService = twitterService;
    }

    //Get (Updating an Asset existing (once every 24 hours) Asset is asset name + ticker (APPLE, AAPL)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    SentimentCarrier freshSentiment(@RequestParam String asset){
        return twitterService.updatedSentiment(asset);
    }
}
