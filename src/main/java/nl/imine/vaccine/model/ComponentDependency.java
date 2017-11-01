package nl.imine.vaccine.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class ComponentDependency implements Dependency {

    private static Logger logger = LoggerFactory.getLogger(ComponentDependency.class);

    private final Class type;
    private final Dependency[] dependencies;
    private Object object = null;

    public ComponentDependency(Class type, Dependency[] dependencies) {
        this.type = type;
        this.dependencies = dependencies;
    }

    public boolean isResolved() {
        return dependencies.length == 0 || Arrays.stream(dependencies).allMatch(Dependency::isResolved);
    }

    public Class getType() {
        return type;
    }

    public Dependency[] getDependencies() {
        return dependencies;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComponentDependency that = (ComponentDependency) o;

        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }
}
