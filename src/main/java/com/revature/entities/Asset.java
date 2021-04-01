package com.revature.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;


@Entity
@Table(name="assets")
public class Asset {

    @Id
    @Column(name = "asset_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int assetId;

//    @Column
//    private String phone;

    @Column(nullable = false, name = "asset_name")
    @JsonProperty("name")
    private String name;

    @Column(nullable = false)
    private String ticker;

    @Column(name = "asset_image_url")
    private String logo;

    @Column(name = "market_cap")
    private BigDecimal marketCapitalization;

    @Column(name = "share_outstanding")
    private BigDecimal shareOutstanding;

    @Column(nullable = false, name = "industry_category")
    private String finnhubIndustry;

    @Column(name = "website_url")
    private String weburl;

    @Column(nullable = false, name = "last_touched_timestamp")
    private LocalDate lastTouchedTimestamp;

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public BigDecimal getMarketCapitalization() {
        return marketCapitalization;
    }

    public void setMarketCapitalization(BigDecimal marketCapitalization) {
        this.marketCapitalization = marketCapitalization;
    }

    public BigDecimal getShareOutstanding() {
        return shareOutstanding;
    }

    public void setShareOutstanding(BigDecimal shareOutstanding) {
        this.shareOutstanding = shareOutstanding;
    }

    public String getFinnhubIndustry() {
        return finnhubIndustry;
    }

    public void setFinnhubIndustry(String finnhubIndustry) {
        this.finnhubIndustry = finnhubIndustry;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public LocalDate getLastTouchedTimestamp() {
        return lastTouchedTimestamp;
    }

    public void setLastTouchedTimestamp(LocalDate lastTouchedTimestamp) {
        this.lastTouchedTimestamp = lastTouchedTimestamp;
    }

    @Override
    public String toString() {
        return "Asset{" +
                "assetId=" + assetId +
//                ", exchange='" + exchange + '\'' +
//                ", phone='" + phone + '\'' +
                ", assetName='" + name + '\'' +
                ", ticker='" + ticker + '\'' +
                ", logo='" + logo + '\'' +
                ", marketCapitalization=" + marketCapitalization +
                ", shareOutstanding=" + shareOutstanding +
                ", finnhubIndustry='" + finnhubIndustry + '\'' +
                ", weburl='" + weburl + '\'' +
                ", lastTouchedTimestamp=" + lastTouchedTimestamp +
                '}';
    }
}
