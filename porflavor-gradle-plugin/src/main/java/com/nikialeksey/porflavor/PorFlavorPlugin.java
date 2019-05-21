package com.nikialeksey.porflavor;

import com.android.build.gradle.AppExtension;
import com.android.build.gradle.api.ApplicationVariant;
import com.android.build.gradle.internal.dsl.ProductFlavor;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionAware;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PorFlavorPlugin implements Plugin<Project> {
    @Override
    public void apply(final Project project) {
        final AppExtension app = project.getExtensions().getByType(AppExtension.class);
        final ExtensionAware appExtension = (ExtensionAware) app;

        final PorFlavorExtension variants = appExtension.getExtensions().create("porflavor", PorFlavorExtension.class);
        final ExtensionAware variantsExtension = (ExtensionAware) variants;

        List<String> variantNames = new ArrayList<>();
        for (String dimension : app.getFlavorDimensionList()) {
            variantNames = addDimension(dimension, variantNames, app.getProductFlavors());
        }

        for (final String variantName : variantNames) {
            variantsExtension.getExtensions().create(variantName, FlavorExtension.class);
        }

        project.afterEvaluate(readyProject -> {
            for (ApplicationVariant variant : app.getApplicationVariants()) {
                ((FlavorExtension) variantsExtension.getExtensions().getByName(variant.getFlavorName())).fillIn(variant);
            }
        });
    }

    private List<String> addDimension(
        final String dimension,
        final List<String> variants,
        final Collection<ProductFlavor> flavors
    ) {
        final List<String> result = new ArrayList<>();
        if (variants.isEmpty()) {
            for (ProductFlavor flavor : flavors) {
                if (flavor.getDimension().equals(dimension)) {
                    result.add(flavor.getName());
                }
            }
        } else {
            for (ProductFlavor flavor : flavors) {
                if (flavor.getDimension().equals(dimension)) {
                    for (String variant : variants) {
                        result.add(variant + flavor.getName());
                    }
                }
            }
        }
        return result;
    }
}
