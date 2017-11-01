package nl.imine.vaccine.testresources.circularsimple;

import nl.imine.vaccine.annotation.Component;

public class CircularComponents {

    @Component
    public static class CircularHalf {
        public CircularHalf(CircularOtherHalf otherHalf) {

        }
    }

    @Component
    private static class CircularOtherHalf {
        public CircularOtherHalf(CircularHalf half) {

        }
    }
}
