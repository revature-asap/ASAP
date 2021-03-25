package com.revature.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SentimentCarrier {

    private Map<String, Double> sentimentAverage;
    private Map<String, Integer> sentimentTotals;

    public SentimentCarrier() {
        super();
        sentimentAverage = new HashMap<>();
        sentimentTotals = new HashMap<>();
    }

    public SentimentCarrier(Map<String, Double> sentimentAverage, Map<String, Integer> sentimentTotals) {
        this.sentimentAverage = sentimentAverage;
        this.sentimentTotals = sentimentTotals;
    }

    public Map<String, Double> getSentimentAverage() {
        return sentimentAverage;
    }

    public void setSentimentAverage(Map<String, Double> sentimentAverage) {
        this.sentimentAverage = sentimentAverage;
    }

    public Map<String, Integer> getSentimentTotals() {
        return sentimentTotals;
    }

    public void setSentimentTotals(Map<String, Integer> sentimentTotals) {
        this.sentimentTotals = sentimentTotals;
    }

    public void clear(){
        sentimentTotals.clear();
        sentimentAverage.clear();
    }

    @Override
    public String toString() {
        return "SentimentScore{" +
                "sentimentAverage=" + sentimentAverage +
                ", sentimentTotals=" + sentimentTotals +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SentimentCarrier that = (SentimentCarrier) o;
        return Objects.equals(sentimentAverage, that.sentimentAverage) && Objects.equals(sentimentTotals, that.sentimentTotals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sentimentAverage, sentimentTotals);
    }
}
