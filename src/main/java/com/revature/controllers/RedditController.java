package com.revature.controllers;

import com.revature.entities.SentimentCarrier;
import com.revature.services.RedditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * A RestController that handles endpoints for Reddit Sentiment
 */
@RestController
@RequestMapping("/reddit")
public class RedditController {
    private final RedditService redditService;

    @Autowired
    public RedditController(RedditService redditService){
        this.redditService = redditService;
    }

    /**
     * Retrieves the most up to date sentiment for a provided asset
     * @param asset a String with the asset name + ticker: (APPLE, AAPL)
     * @return the sentiment for that asset
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    SentimentCarrier freshSentiment(@RequestParam String asset){
        return redditService.updatedSentiment(asset);
    }
}
