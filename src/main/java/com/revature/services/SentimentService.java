package com.revature.services;

import com.revature.entities.SentimentCarrier;
import com.revature.util.sentiment.SentimentCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SentimentService {

    private TwitterService twitterService;
    private RedditService redditService;
    private SentimentCalculator sentimentCalculator;

    @Autowired
    public SentimentService(TwitterService twitterService, SentimentCalculator sentimentCalculator,
                            RedditService redditService){
        this.twitterService = twitterService;
        this.redditService = redditService;
        this.sentimentCalculator = sentimentCalculator;
    }

    public SentimentCarrier updatedSentiment(String asset) {
        ArrayList<String> compiledPosts = new ArrayList<>();

        compiledPosts.addAll(twitterService.getAssetPosts(asset));
        compiledPosts.addAll(redditService.getAssetPosts(asset));

        return sentimentCalculator.apiArrayProcessor(compiledPosts);
    }
}
