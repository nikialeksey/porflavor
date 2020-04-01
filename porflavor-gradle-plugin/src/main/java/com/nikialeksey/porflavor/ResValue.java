package com.nikialeksey.porflavor;

import com.android.build.gradle.api.ApplicationVariant;

public class ResValue {
    private final String type;
    private final String name;
    private final String value;

    public ResValue(
        final String type,
        final String name,
        final String value
    ) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public void fillIn(final ApplicationVariant variant) {
        variant.resValue(type, name, value);
    }
}