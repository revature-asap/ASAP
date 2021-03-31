package com.revature.services;

import com.revature.DTO.TweetsDTO;
import com.revature.entities.Tweet;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TwitterService {

    /*
    Currently, retrieves the tweets from the past 5 days on the passed asset. Returns 10 tweets for now.
     */
    public TweetsDTO searchAssetOnTwitter(final String asset) {
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
                .header("Authorization",
                        "Bearer AAAAAAAAAAAAAAAAAAAAADCONwEAAAAAjMQw2AMLamUZ7qIy69qy5MjLb%2Bk%3DRo0rn9HjFjU09g1Rrz4vqXEEj7PTLa0QkZYEWcJzz8GsbNfP4e"
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
                .map(this::bullBearReplace)
                .forEach(tweetList::add);

        return tweetList;
    }

    public String bullBearReplace(String tweet) {
        //case insensitive searches for bullish and bearish
            tweet = tweet.replaceAll("(?i)bullish","positive");
            tweet = tweet.replaceAll("(?i)bearish", "negative");
        return tweet;
    }
}
