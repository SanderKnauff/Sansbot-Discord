package nl.imine.vaccine.testresources.complextree;

import nl.imine.vaccine.annotation.Component;

public class ComplexTreeComponents {

    @Component
    public static class ParentA {
        public ParentA(ChildA childA, ChildB childB) {
        }
    }

    @Component
    public static class ParentB {
        public ParentB(ChildC childC) {
        }
    }

    @Component
    public static class ChildA {
        public ChildA(SharedChild sharedChild) {
        }
    }

    @Component
    public static class ChildB {
        public ChildB(SharedChild sharedChild) {
        }
    }

    @Component
    public static class ChildC {
        public ChildC(SharedChild sharedChild) {
        }
    }

    @Component
    public static class SharedChild {

    }
}
