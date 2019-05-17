package com.nikialeksey.porflavor;

import com.android.build.gradle.api.ApplicationVariant;

import java.util.ArrayList;
import java.util.List;

public class FlavorExtension {

    private final List<Field> fields;

    public FlavorExtension() {
        this(new ArrayList<>());
    }

    public FlavorExtension(final List<Field> fields) {
        this.fields = fields;
    }

    public void buildConfigField(
            final String type,
            final String name,
            final String value
    ) {
        fields.add(new Field(type, name, value));
    }

    public void fillIn(final ApplicationVariant variant) {
        for (Field field : fields) {
            field.fillIn(variant);
        }
    }

    private static class Field {
        private final String type;
        private final String name;
        private final String value;


        private Field(String type, String name, String value) {
            this.type = type;
            this.name = name;
            this.value = value;
        }

        public void fillIn(final ApplicationVariant variant) {
            variant.buildConfigField(type, name, value);
        }
    }
}
