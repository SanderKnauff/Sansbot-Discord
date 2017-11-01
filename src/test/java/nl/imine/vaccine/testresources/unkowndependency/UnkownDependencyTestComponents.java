package nl.imine.vaccine.testresources.unkowndependency;

import nl.imine.vaccine.annotation.Component;

public class UnkownDependencyTestComponents {

    @Component
    public static class KnownComponent {
        public KnownComponent(UnknownComponent unknownComponent) {

        }
    }

    private static class UnknownComponent {
    }
}
