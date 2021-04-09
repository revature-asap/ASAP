package com.revature.services;

import com.revature.dtos.redditAPI.RedditAuthTokenDTO;
import com.revature.dtos.redditAPI.RedditResultsDTO;
import com.revature.entities.SentimentCarrier;
import com.revature.entities.redditAPI.RedditChildren;
import com.revature.entities.redditAPI.RedditThreadPost;
import com.revature.exceptions.InvalidRequestException;
import com.revature.util.sentiment.SentimentCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


/**
 * This class handles sending and receiving data from the reddit API.
 */

@Service
public class RedditService {

    //list of subreddit to search for an asset.
    private final String[] subreddits = {"/r/stocks","/r/wallstreetbets","/r/investing"};

    //used in headers when calling the api.
    private final String user_agent = "mytestofapi by testingapiforrevatur";

    private final WebClient client;

    private final SentimentCalculator sentimentCalculator;

    //authorization token needed in order to use the Reddit API.
    private String auth_token;

    @Autowired
    public RedditService(SentimentCalculator sentimentCalculator) {
        //base url for making calls to the api.
        final String base_url = "https://oauth.reddit.com";
        this.client = WebClient.builder()
                        .baseUrl(base_url)
                        //change the default buffer size.
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024))
                        .build();
        this.sentimentCalculator = sentimentCalculator;
    }

    /**
     * Make a call to the reddit API to get an authorization token.
     */
    public void setAUthToken() {
        
        //public key for reddit api
        final String reddit_public = (System.getProperty("reddit_public") != null) ? System.getProperty("reddit_public") : System.getenv("reddit_public");

        //private key for reddit api
        final String reddit_private = (System.getProperty("reddit_private") != null) ? System.getProperty("reddit_private") : System.getenv("reddit_private");

        //url for getting the authorization token.
        final String auth_url = "https://www.reddit.com/api/v1/access_token";

        //use this to set values in the form-encodedurl
        final MultiValueMap<String, String> encoded_form = new LinkedMultiValueMap<>();
        encoded_form.add("grant_type","password");
        encoded_form.add("username", (System.getProperty("reddit_username") != null) ? System.getProperty("reddit_username") : System.getenv("reddit_username"));
        encoded_form.add("password", (System.getProperty("reddit_password") != null) ? System.getProperty("reddit_password") : System.getenv("reddit_password"));

        final WebClient webClient1 = WebClient.create(auth_url);
        final RedditAuthTokenDTO results = webClient1.post()
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                .header("User-agent",user_agent)
                                .headers(httpHeaders -> httpHeaders.setBasicAuth(reddit_public,reddit_private))//set basic authorization on the post
                                .body(BodyInserters.fromFormData(encoded_form)) //insert the encodedurl values into the body
                                .retrieve()
                                .bodyToMono(RedditAuthTokenDTO.class)//map results to a RedditAuthTokenDTO
                                .blockOptional().orElseThrow(RuntimeException::new); //block until the results come back from the API.

        auth_token = results.getAccess_token();
    }


    /**
     * Method to return an ArrayList of threads from a reddit search for an asset.
     * @param asset asset ticker to search reddit for.
     * @return arraylist of strings where each string is a thread from reddit.
     */
    public Collection<String> getAssetPosts(final String asset) {
        setAUthToken();
        final ArrayList<String> assets_list = new ArrayList<>();
        Arrays.stream(subreddits)
                .map(subreddit -> getArrayFromDTO(searchAssetOnSubbreddit(subreddit,asset,"top")))
                .forEach(assets_list::addAll);
        return assets_list;
    }


    /**
     * MEthod to search a subreddit for a particular asset.
     * @param subreddit the subreddit to search.
     * @param asset the asset to search for.
     * @param sort how to sort results. options are: relevance, hot, top, new, comments.
     * @return RedditResultsDTO which holds the entire result of the search. it's best to then parse this in a dedicate method to get the values you want.
     */
    public RedditResultsDTO searchAssetOnSubbreddit(final String subreddit, final String asset, final String sort) {
        final int limit = 25; //the number of results to limit to. we can hard code in a value or add it as a method parameter.
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(subreddit + "/search") //take the base url and add this stuff to the end of it.
                        .queryParam("q",asset)
                        .queryParam("sort",sort)
                        .queryParam("limit",limit)
                        .queryParam("restrict_sr",1)  //limit search to specific subreddit.
                        .queryParam("raw_json","1")  //tell reddit not to convert characters '<','>',and'&'.
                        .build())
                .header("User", user_agent)
                .header("Authorization", "bearer " + auth_token)
                .retrieve()
                .bodyToMono(RedditResultsDTO.class)//map results to a RedditResultsDTO
                .blockOptional().orElseThrow(RuntimeException::new);
                    //the exception thrown should be changed by production to be a more relevant exception.
                    //possible change it to not thrown an exception here.
    }

    /**
     * Method which takes in a RedditResultsDTO and returns an array list containing strings of the individual posts from reddit.
     * @param dto DTO which contains the object returned from a call to the Reddit API
     * @return ArrayList of strings. each string is the body of a post on Reddit.
     */
    public Collection<String> getArrayFromDTO(final RedditResultsDTO dto) {
        //arraylist to hold the body of every reddit post inside the dto.
        final ArrayList<String> body_array = new ArrayList<>();
        dto.getData().getChildren().stream()
                .map(RedditChildren::getData)
                .map(RedditThreadPost::getSelftext) //get the actual reddit post
                .filter(str -> str != null && !"".equals(str.trim()))  //filter out any null or empty strings.
                .filter(str -> str.length() < 5000)  //filter out any post longer than 5000 bytes
                .forEach(body_array::add);
        return body_array;
    }

    /**
     * Get Sentiment analysis for an asset on reddit.
     * @param asset asset ticker to search for
     * @return the SentimentCarrier which holds the results of sentiment analysis on the reddit search.
     */
    public SentimentCarrier updatedSentiment(final String asset) {
        if(asset == null || asset.trim().equals("")) {
            throw new InvalidRequestException("asset cannot be null or empty.");
        }
        return sentimentCalculator.apiArrayProcessor((ArrayList<String>) getAssetPosts(asset));
    }
}
