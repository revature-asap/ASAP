package com.revature.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.revature.entities.Asset;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.repositories.AssetRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AssetServiceTest {

    Asset minAsset, fullAsset;
    List<Asset> assetList;
    
    @Mock
    AssetRepository assetRepo;
    
    @InjectMocks
    AssetService assetService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        minAsset = new Asset();
        minAsset.setAssetId(1);
        minAsset.setName("min asset");
        minAsset.setTicker("MNAS");
        minAsset.setFinnhubIndustry("Fake");
        minAsset.setLastTouchedTimestamp(LocalDate.now());

    }

    @Test
    public void testGetAssetByTicker_withValidTicker() {
        when(assetRepo.findAssetByTicker(minAsset.getTicker())).thenReturn(Optional.of(minAsset));

        Asset asset = assetService.getAssetByTicker(minAsset.getTicker());

        assertEquals(minAsset, asset);
        verify(assetRepo, times(1)).findAssetByTicker(minAsset.getTicker());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetAssetByTicker_withInvalidTicker() {
        when(assetRepo.findAssetByTicker(minAsset.getTicker())).thenReturn(Optional.empty());

        assetService.getAssetByTicker(minAsset.getTicker());
    }
}
