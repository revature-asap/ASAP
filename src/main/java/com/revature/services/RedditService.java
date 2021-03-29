package com.revature.services;

import com.revature.DTO.redditAPI.RedditAuthTokenDTO;
import com.revature.DTO.redditAPI.RedditResultsDTO;
import com.revature.DTO.redditAPI.RedditThreadDTO;
import com.revature.entities.redditAPI.RedditChildren;
import com.revature.entities.redditAPI.RedditThreadPost;
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

    //authorization token needed in order to use the Reddit API.
    private String auth_token;

    public RedditService() {
        //base url for making calls to the api.
        final String base_url = "https://oauth.reddit.com";
        this.client = WebClient.builder()
                        .baseUrl(base_url)
                        //change the default buffer size.
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024))
                        .build();
    }


    /**
     * Make a call to the reddit API to get an authorization token.
     */
    public void setAUthToken() {
        //public username for reddit app
        final String username = System.getenv("reddit_public");        //"kpgtqXkTJsCWsQ";
        //private key for reddit app. this and username need to be environmental vars for production code.
        final String pass = System.getenv("reddit_private");   //"2NOsdIoiOMykyMlAQnLm8nxIinRP4A";
        //url for getting the authorization token.
        final String auth_url = "https://www.reddit.com/api/v1/access_token";
        //use this to set values in the form-encodedurl
        final MultiValueMap<String, String> encoded_form = new LinkedMultiValueMap<>();
        encoded_form.add("grant_type","password");
        encoded_form.add("username",System.getenv("reddit_username")); //"testingapiforrevatur"); //username and password here need to be environmental vars for production code
        encoded_form.add("password",System.getenv("reddit_password")); //"Password!2");

        final WebClient webClient1 = WebClient.create(auth_url);
        final RedditAuthTokenDTO results = webClient1.post()
                                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                .header("User-agent",user_agent)
                                .headers(httpHeaders -> httpHeaders.setBasicAuth(username,pass))//set basic authorization on the post
                                .body(BodyInserters.fromFormData(encoded_form)) //insert the encodedurl values into the body
                                .retrieve()
                                .bodyToMono(RedditAuthTokenDTO.class)//map results to a RedditAuthTOkenDTO
                                .blockOptional().orElseThrow(RuntimeException::new); //block until the results come back from the API.

        auth_token = results.getAccess_token();
    }


    /**
     * Method to return an ArrayList of threads from a reddit search for an asset.
     * @param asset
     * @return
     */
    public Collection<String> getAssetPosts(final String asset) {
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
        System.out.println("auth_token: " + auth_token);
        final int limit = 25; //the number of results to limit to. we can hard code in a value or add it as a method parameter.
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(subreddit + "/search") //take the base url and add this stuff to the end of it.
                        .queryParam("q",asset)
                        .queryParam("sort",sort)
                        .queryParam("limit",limit)
                        .queryParam("restrict_sr",1)
                        .queryParam("raw_json","1")  //tell reddit not convert characters '<','>',and'&'
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
    public ArrayList<String> getArrayFromDTO(final RedditResultsDTO dto) {
        //arraylist to hold the body of every reddit post inside the dto.
        final ArrayList<String> body_array = new ArrayList<>();
        dto.getData().getChildren().stream()
                .map(RedditChildren::getData)
                .map(RedditThreadPost::getSelftext)
                .filter(str -> str != null && !"".equals(str.trim()))
                .filter(str -> str.length() < 5000)
                .forEach(body_array::add);
        return body_array;
    }



}