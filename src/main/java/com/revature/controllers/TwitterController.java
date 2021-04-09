package com.revature.controllers;

import com.revature.entities.SentimentCarrier;
import com.revature.services.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * A RestController that handles endpoints for Twitter Sentiment Information
 */
@RestController
@RequestMapping("/twitter")
public class TwitterController {

    private final TwitterService twitterService;

    @Autowired
    public TwitterController(TwitterService twitterService){
        this.twitterService = twitterService;
    }

    /**
     * Retrieves the most up to date sentiment for a provided asset
     * @param asset a String with the asset name + ticker: (APPLE, AAPL)
     * @return the sentiment for that asset
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    SentimentCarrier freshSentiment(@RequestParam String asset){
        return twitterService.updatedSentiment(asset);
    }
}
