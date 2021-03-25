package com.revature.web.intercom.redditapi;

import com.revature.DTO.RedditAuthTokenDTO;
import com.revature.DTO.RedditPostDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.util.MultiValueMap;


/**
 * This class handles sending and receiving data from the reddit API.
 */

@Component
public class RedditAPI {

    //base url for making calls to the api.
    private final String base_url = "https://oauth.reddit.com";

    //used in headers when calling the api.
    private final String user_agent = "mytestofapi by testingapiforrevatur";

    private final WebClient client;

    private String auth_token;

    public RedditAPI() {
        this.client = WebClient.create();
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
        encoded_form.add("username","testingapiforrevatur"); //username and password here need to be enviromental vars for production code
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


    public RedditPostDTO searchAssetOnSubbreddit(final String subreddit, final String asset,final String sort) {
        final int limit = 5;
        final WebClient webClient = WebClient.create(base_url + "/r/" + subreddit + "/search");
        final RedditPostDTO results = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q",asset)
                        .queryParam("sort",sort)
                        .queryParam("limit",limit)
                        .queryParam("restrict_sr",1)
                        .build())
                .header("User-agent", user_agent)
                .header("Authorization", "bearer " + auth_token)
                .retrieve()
                .bodyToMono(RedditPostDTO.class)//map results to a RedditAuthTOkenDTO
                .blockOptional().orElseThrow(RuntimeException::new); //block until the results come back from the API.
        return results;
    }

}
