package nl.imine.vaccine.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class ComponentDependency {

    private static Logger logger = LoggerFactory.getLogger(ComponentDependency.class);

    private final Class type;
    private final Class[] dependencies;
    private final Class[] providedClasses;
    private Object object = null;

    public ComponentDependency(Class type, Class[] dependencies, Class[] providedClasses) {
        this.type = type;
        this.dependencies = dependencies;
        this.providedClasses = providedClasses;
    }

    public boolean isResolved() {
        return object != null;
    }

    public Class getType() {
        return type;
    }

    public Class[] getDependencies() {
        return dependencies;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Class[] getProvidedClasses() {
        return providedClasses;
    }
}
