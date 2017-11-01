package nl.imine.vaccine.model;

public interface Dependency {

    Object getObject();
    boolean isResolved();
}
