package com.revature.services;

import com.revature.DTO.TweetsDTO;
import com.revature.entities.Tweet;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class TwitterService {

    /*
    Currently, retrieves the tweets from the past 5 days on the passed asset. Returns 10 tweets for now.
     */
    public TweetsDTO searchAssetOnTwitter(String asset) {
        WebClient client;
        client = WebClient.create("https://api.twitter.com/2/tweets/search/recent?");
        String modifiedAsset = asset + " is:verified lang:en";

        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query",modifiedAsset)
                        .queryParam("tweet.fields","created_at")
                        .queryParam("start_time",
                                LocalDateTime.now().minusDays(5).withNano(0) + "Z")
                        .queryParam("max_results",100)
                        .build())
                .header("Authorization",
                        "Bearer AAAAAAAAAAAAAAAAAAAAADGONw" +
                                "EAAAAAr6f8hXiM9o5bmI7w%2BoKEUxY%2FCEI%3Dx9e5LgBNr22yfKKS4SkLEn" +
                                "SAMCPfDEv4PFILkzsRsi0Zy2zElD"
                )
                .retrieve()
                .bodyToMono(TweetsDTO.class)//map results to a RedditPostDTO
                .blockOptional().orElseThrow(RuntimeException::new);
    }

    /*
    Processes a TweetDTO and outputs a list of Strings
     */
    public Collection<String> getAssetPosts(String asset) {

        TweetsDTO tweetsDTO = searchAssetOnTwitter(asset);
        ArrayList<String> tweetList = new ArrayList<>();

        //Iterate
        tweetsDTO.getTweets().stream()
                .map(Tweet::getTweet)
                .filter(str -> str != null && !"".equals(str.trim()))
                .filter(str -> str.length() < 5000)
                .map(str -> bullBearReplace(str))
                .forEach(tweetList::add);

        return tweetList;
    }

    public String bullBearReplace(String tweet) {
        if(tweet.toLowerCase().contains("bullish"))
            tweet = tweet.replace("bullish", "positive");
        if (tweet.toLowerCase().contains("bearish"))
            tweet = tweet.replace("bearish", "negative");

        return tweet;
    }
}
