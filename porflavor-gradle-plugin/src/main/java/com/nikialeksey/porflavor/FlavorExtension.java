package com.nikialeksey.porflavor;

import com.android.build.gradle.api.ApplicationVariant;

import java.util.ArrayList;
import java.util.List;

public class FlavorExtension {

    private final List<BuildConfigField> fields;
    private final List<ResValue> resValues;

    public FlavorExtension() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    public FlavorExtension(
        final List<BuildConfigField> fields,
        final List<ResValue> resValues
    ) {
        this.fields = fields;
        this.resValues = resValues;
    }

    public void buildConfigField(
            final String type,
            final String name,
            final String value
    ) {
        fields.add(new BuildConfigField(type, name, value));
    }

    public void resValue(
        final String type,
        final String name,
        final String value
    ) {
        resValues.add(new ResValue(type, name, value));
    }

    public void fillIn(final ApplicationVariant variant) {
        for (final BuildConfigField field : fields) {
            field.fillIn(variant);
        }
        for (final ResValue resValue : resValues) {
            resValue.fillIn(variant);
        }
    }
}
