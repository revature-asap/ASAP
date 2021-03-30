package com.revature.DTO;

import com.revature.entities.SentimentCarrier;

import java.util.Objects;

public class SentimentDTO {

    private SentimentCarrier redditScores;
    private SentimentCarrier twitterScores;

    public SentimentDTO() {
    }

    public SentimentDTO(SentimentCarrier redditScores, SentimentCarrier twitterScores) {
        this.redditScores = redditScores;
        this.twitterScores = twitterScores;
    }

    public SentimentCarrier getRedditScores() {
        return redditScores;
    }

    public void setRedditScores(SentimentCarrier redditScores) {
        this.redditScores = redditScores;
    }

    public SentimentCarrier getTwitterScores() {
        return twitterScores;
    }

    public void setTwitterScores(SentimentCarrier twitterScores) {
        this.twitterScores = twitterScores;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SentimentDTO that = (SentimentDTO) o;
        return Objects.equals(redditScores, that.redditScores) && Objects.equals(twitterScores, that.twitterScores);
    }

    @Override
    public int hashCode() {
        return Objects.hash(redditScores, twitterScores);
    }

    @Override
    public String toString() {
        return "SentimentDTO{" +
                "redditScores=" + redditScores +
                ", twitterScores=" + twitterScores +
                '}';
    }
}
