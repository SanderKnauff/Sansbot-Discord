package nl.imine.vaccine.testresources.circularcomplex;

import nl.imine.vaccine.annotation.Component;

public class CircularComplex {

    //Dependency circle A->B->C->E->D->A

    @Component
    public static class CircularPartA {
        public CircularPartA(CircularPartB part) {

        }
    }

    @Component
    private static class CircularPartB {
        public CircularPartB(CircularPartC part) {

        }
    }

    @Component
    private static class CircularPartC {
        public CircularPartC(CircularPartE part) {

        }
    }

    @Component
    private static class CircularPartD {

        public CircularPartD(CircularPartA part) {

        }
    }

    @Component
    private static class CircularPartE {

        public CircularPartE(CircularPartD part) {

        }
    }
}
