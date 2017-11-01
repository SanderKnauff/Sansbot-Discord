package nl.imine.vaccine.testresources.property;

import nl.imine.vaccine.annotation.Component;
import nl.imine.vaccine.annotation.Property;

public class PropertyTestComponents {

    @Component
    public static class PropertyHolderParent {
        private final PropertyHolderChild propertyHolderChild;
        private final String property;

        public PropertyHolderParent(PropertyHolderChild propertyHolderChild, @Property("parent") String property) {
            this.propertyHolderChild = propertyHolderChild;
            this.property = property;
        }

        public PropertyHolderChild getPropertyHolderChild() {
            return propertyHolderChild;
        }

        public String getProperty() {
            return property;
        }
    }

    @Component
    public static class PropertyHolderChild {
        private final String property;

        public PropertyHolderChild(@Property("child") String property) {
            this.property = property;
        }

        public String getProperty() {
            return property;
        }
    }
}
