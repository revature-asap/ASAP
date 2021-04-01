package com.revature.services;

import com.revature.DTO.TweetsDTO;
import com.revature.entities.SentimentCarrier;
import com.revature.entities.Tweet;
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
                        .header("Authorization",System.getenv("TWITTER_BEARER_TOKEN") )
                .retrieve()
                .bodyToMono(TweetsDTO.class)//map results to a RedditPostDTO
                .blockOptional().orElseThrow(RuntimeException::new);
    }

    /*
    Processes a TweetDTO and outputs a list of Strings
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

    public String bullBearReplace(final String tweet) {
        //case insensitive searches for bullish and bearish
            return tweet.replaceAll("(?i)bullish","positive")
                    .replaceAll("(?i)bearish", "negative");
    }

    public SentimentCarrier updatedSentiment(String asset) {
        return sentimentCalculator.apiArrayProcessor((ArrayList<String>) getAssetPosts(asset));
    }
}
