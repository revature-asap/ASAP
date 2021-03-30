package com.revature.services;

import com.revature.DTO.SentimentDTO;
import com.revature.entities.SentimentCarrier;
import com.revature.util.sentiment.SentimentCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SentimentService {

    private TwitterService twitterService;
    private RedditService redditService;
    //TODO: REDDIT SERVICE GOES HERE;
    private SentimentCalculator sentimentCalculator;

    @Autowired
    public SentimentService(TwitterService twitterService, SentimentCalculator sentimentCalculator,
                            RedditService redditService){
        this.twitterService = twitterService;
        this.redditService = redditService;
        this.sentimentCalculator = sentimentCalculator;
    }

    public SentimentDTO updatedSentiment(String asset) {
        SentimentDTO sentimentDTO = new SentimentDTO();
        sentimentDTO.setTwitterScores(sentimentCalculator.apiArrayProcessor
                ((ArrayList<String>) twitterService.getAssetPosts(asset)));
        sentimentDTO.setRedditScores(sentimentCalculator.apiArrayProcessor
                ((ArrayList<String>) redditService.getAssetPosts(asset)));

        return sentimentDTO;
    }
}
