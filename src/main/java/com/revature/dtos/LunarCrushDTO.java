package com.revature.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.revature.entities.Asset;

import java.math.BigDecimal;
import java.util.Map;

public class LunarCrushDTO {

    private String name;

    private String cryptoTicker;

    private BigDecimal marketCap;

    private BigDecimal maxSupply;

    @SuppressWarnings("unchecked")
    @JsonProperty("data")
    private void unpackData(Object[] realDataArray) {
        Map<String,Object> coinDataMap = (Map<String, Object>) realDataArray[0];
        this.marketCap = new BigDecimal(coinDataMap.get("market_cap").toString());
        this.name = (String) coinDataMap.get("name");
        this.maxSupply = new BigDecimal(coinDataMap.get("max_supply").toString());
        this.cryptoTicker = (String) coinDataMap.get("symbol");
    }

    private String logo = "https://images-platform.99static.com//M34iQO78yv3AOYIv80EOKPfr-DI=/0x0:2048x2048/fit-in/500x500/projects-files/85/8572/857264/3063604a-66b9-47b9-96c2-756309e6d1ca.jpg";

    private String  industry = "Crypto";

    private String webUrl = "https://www.coindesk.com/";

    public LunarCrushDTO() {
        super();
    }

    public Asset getAsset() {
        Asset returnAsset = new Asset();
        returnAsset.setFinnhubIndustry(industry);
        returnAsset.setLogo(logo);
        returnAsset.setName(name);
        returnAsset.setTicker(cryptoTicker);
        returnAsset.setMarketCapitalization(marketCap);
        returnAsset.setShareOutstanding(maxSupply);
        returnAsset.setWeburl(webUrl);
        return returnAsset;
    }
}
