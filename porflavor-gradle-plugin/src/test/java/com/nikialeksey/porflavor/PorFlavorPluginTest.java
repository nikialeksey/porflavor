package com.nikialeksey.porflavor;

import com.android.build.api.variant.BuildConfigField;
import com.android.build.api.variant.impl.ApplicationVariantBuilderImpl;
import com.android.build.api.variant.impl.ApplicationVariantImpl;
import com.android.build.gradle.internal.VariantManager;
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension;
import com.android.build.gradle.internal.plugins.AppPlugin;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Project;
import org.gradle.api.internal.project.DefaultProject;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.provider.Provider;
import org.gradle.build.event.BuildEventsListenerRegistry;
import org.gradle.internal.service.DefaultServiceRegistry;
import org.gradle.internal.service.scopes.ProjectScopeServices;
import org.gradle.testfixtures.ProjectBuilder;
import org.gradle.tooling.events.OperationCompletionListener;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public final class PorFlavorPluginTest {
    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void pluginApply() throws IOException {
        final File projectRoot = temporaryFolder.newFolder();
        final Project project = ProjectBuilder.builder()
            .withProjectDir(projectRoot)
            .build();
        addFakeService(project);

        project.getPluginManager().apply("com.android.application");
        project.getPluginManager().apply("com.nikialeksey.porflavor");

        final BaseAppModuleExtension androidExtension = (BaseAppModuleExtension) project
            .getExtensions()
            .findByName("android");
        MatcherAssert.assertThat(
            androidExtension,
            IsNull.notNullValue()
        );
        MatcherAssert.assertThat(
            ((ExtensionAware) androidExtension)
                .getExtensions()
                .findByName("porflavor"),
            IsNull.notNullValue()
        );
    }

    @Test
    public void pluginWithTwoDimensions() throws IOException {
        final File projectRoot = temporaryFolder.newFolder();
        final Path manifest = Paths.get(projectRoot.getPath(), "src", "main", "AndroidManifest.xml");
        Files.createDirectories(manifest.getParent());
        Files.createFile(manifest);
        Files.write(
            manifest,
            Arrays.asList(
                "<manifest xmlns:android=\"http://schemas.android.com/apk/res/android\"\n",
                "          package=\"com.nikialeksey.porflavor.test\">\n",
                "    <application android:label=\"Test\"/>\n",
                "</manifest>\n"
            )
        );

        final Project project = ProjectBuilder.builder()
            .withProjectDir(projectRoot)
            .build();
        addFakeService(project);

        project.getPluginManager().apply("com.android.application");
        project.getPluginManager().apply("com.nikialeksey.porflavor");

        final BaseAppModuleExtension androidExtension = (BaseAppModuleExtension) project
            .getExtensions()
            .findByName("android");
        androidExtension.setCompileSdkVersion("31");
        androidExtension.flavorDimensions("brand", "type");
        androidExtension.productFlavors(flavors -> {
            flavors.create(
                "brand1",
                flavor -> {
                    flavor.dimension("brand");
                }
            );
            flavors.create(
                "type1",
                flavor -> {
                    flavor.dimension("type");
                }
            );
        });

        final NamedDomainObjectContainer<FlavorExtension> porflavorExtension = (NamedDomainObjectContainer<FlavorExtension>) ((ExtensionAware) androidExtension)
            .getExtensions()
            .findByName("porflavor");
        porflavorExtension.create("brand1Type1", flavorExtension -> {
            flavorExtension.buildConfigField("String", "TOKEN", "123");
        });

        project.evaluationDependsOn(":");

        final AppPlugin androidPlugin = project.getPlugins().findPlugin(AppPlugin.class);
        final VariantManager<ApplicationVariantBuilderImpl, ApplicationVariantImpl> variantManager = androidPlugin.getVariantManager();
        final Map<String, BuildConfigField<? extends Serializable>> buildConfigFieldMap = variantManager.getMainComponents().get(0).getVariant().getBuildConfigFields().get();

        MatcherAssert.assertThat(
            buildConfigFieldMap.size(),
            IsEqual.equalTo(1)
        );
        MatcherAssert.assertThat(
            buildConfigFieldMap.get("TOKEN").getValue(),
            IsEqual.equalTo("123")
        );
    }

    /**
     * Workaround from https://issuetracker.google.com/issues/193859160
     */
    private static void addFakeService(Project project) {
        try {
            ProjectScopeServices gss =
                (ProjectScopeServices) ((DefaultProject) project).getServices();

            Field state = ProjectScopeServices.class.getSuperclass().getDeclaredField("state");
            state.setAccessible(true);
            AtomicReference<Object> stateValue = (AtomicReference<Object>) state.get(gss);
            Class<?> enumClass = Class.forName(DefaultServiceRegistry.class.getName() + "$State");
            stateValue.set(enumClass.getEnumConstants()[0]);

            // add service and set state so that future mutations are not allowed
            gss.add(BuildEventsListenerRegistry.class, new FakeBuildEventsListenerRegistry());
            stateValue.set(enumClass.getEnumConstants()[1]);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    static class FakeBuildEventsListenerRegistry implements BuildEventsListenerRegistry {
        @Override
        public void onTaskCompletion(Provider<? extends OperationCompletionListener> provider) {}
    }


}