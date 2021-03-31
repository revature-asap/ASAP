package com.revature.DTO.redditAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO which handles receiving an access token from the Reddit API.
 */
public class RedditAuthTokenDTO {
    @JsonProperty("access_token")
    private String access_token;

    @JsonProperty("token_type")
    private String token_type;

    @JsonProperty("expires_in")
    private int expires_in;

    @JsonProperty("scope")
    private String scope;

    public RedditAuthTokenDTO() {
        super();
    }

    public RedditAuthTokenDTO(final String access_token, final String token_type, final int expires_in, final String scope) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.expires_in = expires_in;
        this.scope = scope;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(final String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(final String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(final int expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
