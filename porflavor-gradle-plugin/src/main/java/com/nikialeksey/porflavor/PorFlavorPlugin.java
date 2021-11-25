package com.nikialeksey.porflavor;

import com.android.build.gradle.api.BaseVariant;
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionAware;

@SuppressWarnings("allfinal")
public class PorFlavorPlugin implements Plugin<Project> {
    @Override
    public void apply(final Project project) {
        final BaseAppModuleExtension app = project.getExtensions().getByType(BaseAppModuleExtension.class);
        final ExtensionAware appExtension = (ExtensionAware) app;

        final NamedDomainObjectContainer<FlavorExtension> variantsContainer = project.container(FlavorExtension.class);
        appExtension.getExtensions().add("porflavor", variantsContainer);

        app.getApplicationVariants().whenObjectAdded((final BaseVariant variant) -> {
            final FlavorExtension flavorExtension = variantsContainer.findByName(variant.getFlavorName());
            if (flavorExtension != null) {
                flavorExtension.fillIn(variant);
            }
        });
    }
}
