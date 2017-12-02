package nl.imine.vaccine.exception;

public class ConstructorStalemateException extends RuntimeException {

    public ConstructorStalemateException(Class component) {
        super("Component could no be created. Class " + component.getName() + " contains multiple constructors");
    }
}
