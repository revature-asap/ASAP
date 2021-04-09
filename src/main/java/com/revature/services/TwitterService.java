package com.revature.services;

import com.revature.dtos.TweetsDTO;
import com.revature.entities.SentimentCarrier;
import com.revature.entities.Tweet;
import com.revature.exceptions.InvalidRequestException;
import com.revature.util.sentiment.SentimentCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class TwitterService {

    private final SentimentCalculator sentimentCalculator;

    @Autowired
    public TwitterService(SentimentCalculator sentimentCalculator) {
        this.sentimentCalculator = sentimentCalculator;
    }

    /**
     * Retrieves the tweets from the past 5 days on the provided asset. Returns 10 tweets.
     * @param asset the Asset to search for
     * @return a DTO for Tweets
     */
    public TweetsDTO searchAssetOnTwitter(final String asset) {
        if (System.getProperty("twitter_bearer_token") == null)
            System.setProperty("twitter_bearer_token", System.getenv("twitter_bearer_token"));
        final WebClient client = WebClient.create("https://api.twitter.com/2/tweets/search/recent?");
        final String modifiedAsset = asset + " is:verified lang:en";

        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query",modifiedAsset)
                        .queryParam("tweet.fields","created_at")
                        .queryParam("start_time",
                                LocalDateTime.now().minusDays(5).withNano(0) + "Z")
                        .queryParam("max_results",100)
                        .build())
                        .header("Authorization","Bearer " + System.getProperty("twitter_bearer_token") )
                .retrieve()
                .bodyToMono(TweetsDTO.class)//map results to a RedditPostDTO
                .blockOptional().orElseThrow(RuntimeException::new);
    }

    /*
    Processes a TweetDTO and outputs a list of Strings
     */

    /**
     * Processes a TweetDTO and provides a list of Strings from Tweets
     * @param asset the asset to get Posts for
     * @return a Collection of Strings
     */
    public Collection<String> getAssetPosts(final String asset) {

        final TweetsDTO tweetsDTO = searchAssetOnTwitter(asset);
        final ArrayList<String> tweetList = new ArrayList<>();

        //Iterate
        tweetsDTO.getTweets().stream()
                .map(Tweet::getTweet)
                .filter(str -> str != null && !"".equals(str.trim()))
                .filter(str -> str.length() < 5000)
                .map(this::bullBearReplace)
                .forEach(tweetList::add);

        return tweetList;
    }

    /**
     * Replaces bullish and bearish with positive and negative respectively.
     * This is needed to ensure that we can display sentiment on the front end easier
     * @param tweet String representation of tweet
     * @return a tweet String but with bullish and bearish replaced
     */
    public String bullBearReplace(final String tweet) {
        //case insensitive searches for bullish and bearish
            return tweet.replaceAll("(?i)bullish","positive")
                    .replaceAll("(?i)bearish", "negative");
    }

    /**
     * Retrieves the most recent sentiment for an asset
     * @param asset the asset to return sentiment for
     * @return a SentimentCarrier object
     */
    public SentimentCarrier updatedSentiment(String asset) {
        if(asset == null || asset.trim().equals("")) {
            throw new InvalidRequestException("asset cannot be null or empty.");
        }
        return sentimentCalculator.apiArrayProcessor((ArrayList<String>) getAssetPosts(asset));
    }
}
