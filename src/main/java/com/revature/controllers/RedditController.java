package com.revature.controllers;

import com.revature.entities.SentimentCarrier;
import com.revature.services.RedditService;
import com.revature.services.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reddit")
public class RedditController {
    private final RedditService redditService;

    @Autowired
    public RedditController(RedditService redditService){
        this.redditService = redditService;
    }

    //Get (Updating an Asset existing (once every 24 hours) Asset is asset name + ticker (APPLE, AAPL)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    SentimentCarrier freshSentiment(@RequestParam String asset){
        return redditService.updatedSentiment(asset);
    }
}
