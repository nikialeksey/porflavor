package com.nikialeksey.porflavor;

public interface Flavor {
    String name();
    boolean hasDimension(String dimension);

    class Fake implements Flavor {

        private final String name;
        private final String dimension;

        public Fake(final String name, final String dimension) {
            this.name = name;
            this.dimension = dimension;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public boolean hasDimension(final String dimension) {
            return this.dimension.equals(dimension);
        }
    }
}
