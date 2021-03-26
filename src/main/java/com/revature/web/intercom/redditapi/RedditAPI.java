package com.revature.web.intercom.redditapi;

import com.revature.DTO.RedditAuthTokenDTO;
import com.revature.DTO.RedditPostDTO;
import com.revature.DTO.RedditThreadDTO;
import com.revature.entities.RedditAPI.RedditChildren;
import com.revature.entities.RedditAPI.RedditPost;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * This class handles sending and receiving data from the reddit API.
 */

@Component
public class RedditAPI {

    //used in headers when calling the api.
    private final String user_agent = "mytestofapi by testingapiforrevatur";

    private final WebClient client;

    private String auth_token;

    public RedditAPI() {
        //base url for making calls to the api.
        final String base_url = "https://oauth.reddit.com";
        this.client = WebClient.create(base_url);
    }


    /**
     * Make a call to the reddit API to get an authorization token.
     */

    public void setAUthToken() {
        //public username for reddit app
        final String username = "kpgtqXkTJsCWsQ";
        //private key for reddit app. this and username need to be environmental vars for production code.
        final String pass = "2NOsdIoiOMykyMlAQnLm8nxIinRP4A";
        //url for getting the authorization token.
        final String auth_url = "https://www.reddit.com/api/v1/access_token";
        //use this to set values in the form-encodedurl
        final MultiValueMap<String, String> encoded_form = new LinkedMultiValueMap<>();
        encoded_form.add("grant_type","password");
        encoded_form.add("username","testingapiforrevatur"); //username and password here need to be environmental vars for production code
        encoded_form.add("password","Password!2");

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
     * MEthod to search a subreddit for a particular asset.
     * @param subreddit the subreddit to search.
     * @param asset the asset to search for.
     * @param sort how to sort results. options are: relevance, hot, top, new, comments.
     * @return RedditPostDTO which holds the entire result of the search. it's best to then parse this in a dedicate method to get the values you want.
     */
    public RedditPostDTO searchAssetOnSubbreddit(final String subreddit, final String asset,final String sort) {
        final int limit = 5; //the number of results to limit to. we can hard code in a value or add it as a method parameter.
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/r/" + subreddit + "/search") //take the base url and add this stuff to the end of it.
                        .queryParam("q",asset)
                        .queryParam("sort",sort)
                        .queryParam("limit",limit)
                        .queryParam("restrict_sr",1)
                        .queryParam("raw_json","1")  //tell reddit not convert characters '<','>',and'&'
                        .build())
                .header("User-agent", user_agent)
                .header("Authorization", "bearer " + auth_token)
                .retrieve()
                .bodyToMono(RedditPostDTO.class)//map results to a RedditPostDTO
                .blockOptional().orElseThrow(RuntimeException::new);
                    //the exception thrown should be changed by production to be a more relevant exception.
                    //possible change it to not thrown an exception here.
    }

    /**
     * Method which takes in a RedditPostDTO and returns an array list containing strings of the individual posts from reddit.
     * @param dto DTO which contains the object returned from a call to the Reddit API
     * @return ArrayList of strings. each string is the body of a post on Reddit.
     */
    public ArrayList<String> getArrayFromDTO(final RedditPostDTO dto) {
        //arraylist to hold the body of every reddit post inside the dto.
        final ArrayList<String> body_array = new ArrayList<>();
        Arrays.stream(dto.getData().getChildren().toArray(new RedditChildren[0]))
                .map(RedditChildren::getData)
                .map(RedditPost::getSelftext)
                .forEach(body_array::add);
        return body_array;
    }


    //Method currently not working. mapping the json objects form api calls into java is not working correctly.
    public RedditThreadDTO getCommentsOfThread(final String subreddit,final String thread) {
        final int limit = 5; //the number of results to limit to. we can hard code in a value or add it as a method parameter.
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/r/" + subreddit + "/comments/" + thread) //take the base url and add this stuff to the end of it.
                        .build())
                .header("User-agent", user_agent)
                .header("Authorization", "bearer " + auth_token)
                .retrieve()
                .bodyToMono(RedditThreadDTO.class)//map results to a RedditPostDTO
                .blockOptional().orElseThrow(RuntimeException::new);
    }


}
