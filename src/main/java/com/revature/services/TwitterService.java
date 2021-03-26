package com.revature.services;

import com.revature.DTO.TweetDTO;
import com.revature.entities.Tweet;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
public class TwitterService {

    /*
    Currently, retrieves the tweets from the past 7 days on the passed asset. Returns 10 tweets for now.
     */
    public TweetDTO searchAssetOnTwitter(String asset) {

        WebClient client;
        client = WebClient.create("https://api.twitter.com/2/tweets/search/recent?");

        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query",asset)
                        .queryParam("tweet.fields","created_at")
                        .build())
                .header("Authorization",
                        "Bearer AAAAAAAAAAAAAAAAAAAAADGONwEAAAAAr6f8hXiM9o5bmI7w%2BoKEUxY%2FCEI%3Dx9e5LgBNr22yfKKS4SkLEnSAMCPfDEv4PFILkzsRsi0Zy2zElD"
                )
                .retrieve()
                .bodyToMono(TweetDTO.class)//map results to a RedditPostDTO
                .blockOptional().orElseThrow(RuntimeException::new);


    }




    /*
    Processes a TweetDTO and outputs a list of Strings
     */
    public Collection<String> getAssetPosts(String asset) {

        TweetDTO tweetDTO = searchAssetOnTwitter(asset);
        ArrayList<String> tweetList = new ArrayList<>();

        //Iterate
        tweetDTO.getTweets().stream()
                .map(Tweet::getTweet)
                .filter(str -> str != null && !"".equals(str.trim()))
                .filter(str -> str.length() < 5000)
                .forEach(tweetList::add);


        return tweetList;

    }
}
