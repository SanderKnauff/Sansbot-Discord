package nl.imine.vaccine.exception;

public class UnknownDependencyException extends RuntimeException {

    public UnknownDependencyException(Class parent, Class child) {
        super("No injection candidates of type " + child + " found to inject in " + parent);
    }
}
