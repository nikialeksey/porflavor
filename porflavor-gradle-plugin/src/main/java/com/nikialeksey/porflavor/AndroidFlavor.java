package com.nikialeksey.porflavor;

import com.android.build.gradle.internal.dsl.ProductFlavor;

public class AndroidFlavor implements Flavor {

    private final ProductFlavor productFlavor;

    public AndroidFlavor(final ProductFlavor productFlavor) {
        this.productFlavor = productFlavor;
    }

    @Override
    public String name() {
        return this.productFlavor.getName();
    }

    @Override
    public boolean hasDimension(final String dimension) {
        return this.productFlavor.getDimension().equals(dimension);
    }
}
