package com.nikialeksey.porflavor;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class AndroidVariantsTest {
    @Test
    public void flavorsWithUpperCaseFirstLetters() {
        Assert.assertThat(
            new AndroidVariants(
                Arrays.asList("brand", "version"),
                Arrays.asList(
                    new Flavor.Fake("Brand1", "brand"),
                    new Flavor.Fake("Brand2", "brand"),
                    new Flavor.Fake("Brand3", "brand"),
                    new Flavor.Fake("Store", "version"),
                    new Flavor.Fake("Staging", "version")
                )
            ).asNames(),
            Is.is(
                Arrays.asList(
                    "Brand1Store",
                    "Brand2Store",
                    "Brand3Store",
                    "Brand1Staging",
                    "Brand2Staging",
                    "Brand3Staging"
                )
            )
        );
    }

    @Test
    public void flavorsWithLowerCaseFirstLetters() {
        Assert.assertThat(
            new AndroidVariants(
                Arrays.asList("brand", "version"),
                Arrays.asList(
                    new Flavor.Fake("brand1", "brand"),
                    new Flavor.Fake("brand2", "brand"),
                    new Flavor.Fake("brand3", "brand"),
                    new Flavor.Fake("store", "version"),
                    new Flavor.Fake("staging", "version")
                )
            ).asNames(),
            Is.is(
                Arrays.asList(
                    "brand1Store",
                    "brand2Store",
                    "brand3Store",
                    "brand1Staging",
                    "brand2Staging",
                    "brand3Staging"
                )
            )
        );
    }

    @Test
    public void flavorsWithDifferentCasesFirstLetters() {
        Assert.assertThat(
            new AndroidVariants(
                Arrays.asList("brand", "version"),
                Arrays.asList(
                    new Flavor.Fake("Brand1", "brand"),
                    new Flavor.Fake("brand2", "brand"),
                    new Flavor.Fake("Brand3", "brand"),
                    new Flavor.Fake("Store", "version"),
                    new Flavor.Fake("staging", "version")
                )
            ).asNames(),
            Is.is(
                Arrays.asList(
                    "Brand1Store",
                    "brand2Store",
                    "Brand3Store",
                    "Brand1Staging",
                    "brand2Staging",
                    "Brand3Staging"
                )
            )
        );
    }
}