package com.nikialeksey.porflavor;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.IOException;

public final class PorFlavorPluginTest {
    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void simplePluginTest() throws IOException {
        final Project project = ProjectBuilder.builder()
            .withProjectDir(temporaryFolder.newFolder())
            .build();

    }
}