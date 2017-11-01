package nl.imine.vaccine.exception;

import java.util.List;

public class CircularDependencyException extends RuntimeException {

    private final List<Class> parents;
    private final Class child;

    public CircularDependencyException(List<Class> parents, Class child) {
        this.parents = parents;
        this.child = child;
    }

    @Override
    public String getMessage() {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("Circular dependency detected while injecting Components: (");
        for (Class clazz : parents) {
            errorMessage.append(clazz.getName()).append(" -> ");
        }
        errorMessage.append(child.getName()).append(")");
        return errorMessage.toString();
    }
}
