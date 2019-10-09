package com.nikialeksey.porflavor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AndroidVariants implements Variants {

    private final List<String> dimensions;
    private final List<Flavor> flavors;

    public AndroidVariants(
        final List<String> dimensions,
        final List<Flavor> flavors
    ) {
        this.dimensions = dimensions;
        this.flavors = flavors;
    }

    @Override
    public List<String> asNames() {
        List<String> variantNames = new ArrayList<>();
        for (String dimension : dimensions) {
            variantNames = addDimension(dimension, variantNames, flavors);
        }

        return variantNames;
    }

    private List<String> addDimension(
        final String dimension,
        final List<String> variants,
        final Collection<Flavor> flavors
    ) {
        final List<String> result = new ArrayList<>();
        if (variants.isEmpty()) {
            for (Flavor flavor : flavors) {
                if (flavor.hasDimension(dimension)) {
                    result.add(flavor.name());
                }
            }
        } else {
            for (Flavor flavor : flavors) {
                if (flavor.hasDimension(dimension)) {
                    for (String variant : variants) {
                        final String name = flavor.name();
                        result.add(variant + (Character.toUpperCase(name.charAt(0)) + name.substring(1)));
                    }
                }
            }
        }
        return result;
    }
}
