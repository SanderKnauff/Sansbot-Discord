package nl.imine.vaccine.testresources.provider;

import nl.imine.vaccine.annotation.Component;
import nl.imine.vaccine.annotation.Provided;

public class ProviderComponents {

    @Component
    public static class ParentA {
        public ParentA(ChildInjected childInjected) {

        }
    }

    @Component
    public static class ParentB {
        private final ChildProvided childProvided;

        public ParentB(ChildProvided childProvided) {
            this.childProvided = childProvided;
        }

        public ChildProvided getChildProvided() {
            return childProvided;
        }
    }

    @Component
    public static class ChildInjected {
        @Provided
        public ChildProvided childProvided() {
            return new ChildProvided();
        }
    }

    public static class ChildProvided {
        private ChildProvided() {

        }
    }
}
