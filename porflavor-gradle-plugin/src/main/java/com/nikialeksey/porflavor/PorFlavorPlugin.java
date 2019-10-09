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

        final List<String> variantNames = new AndroidVariants(
            app.getFlavorDimensionList(),
            productFlavors(app.getProductFlavors())
        ).asNames();

        final PorFlavorExtension variants = appExtension.getExtensions().create("porflavor", PorFlavorExtension.class);
        final ExtensionAware variantsExtension = (ExtensionAware) variants;

        for (final String variantName : variantNames) {
            variantsExtension.getExtensions().create(variantName, FlavorExtension.class);
        }

        project.afterEvaluate(readyProject -> {
            for (final ApplicationVariant variant : app.getApplicationVariants()) {
                ((FlavorExtension) variantsExtension.getExtensions().getByName(variant.getFlavorName())).fillIn(variant);
            }
        });
    }

    private List<Flavor> productFlavors(final Collection<ProductFlavor> productFlavors) {
        final List<Flavor> result = new ArrayList<>();

        for (final ProductFlavor productFlavor : productFlavors) {
            result.add(new AndroidFlavor(productFlavor));
        }

        return result;
    }
}
