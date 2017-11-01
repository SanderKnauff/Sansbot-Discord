package nl.imine.vaccine.model;

public class PropertyDependency implements Dependency {

   private Object property = null;

    public PropertyDependency() {
    }

    @Override
    public Object getObject() {
        return property;
    }

    @Override
    public boolean isResolved() {
        return property != null;
    }

    public Object getProperty() {
        return property;
    }

    public void setProperty(Object property) {
        this.property = property;
    }
}
