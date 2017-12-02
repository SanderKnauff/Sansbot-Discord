package nl.imine.vaccine;

import nl.imine.vaccine.annotation.Component;
import nl.imine.vaccine.annotation.Property;
import nl.imine.vaccine.annotation.Provided;
import nl.imine.vaccine.exception.CircularDependencyException;
import nl.imine.vaccine.exception.ConstructorStalemateException;
import nl.imine.vaccine.exception.UnknownDependencyException;
import nl.imine.vaccine.model.ComponentDependency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

public class Vaccine {

    private static Logger logger = LoggerFactory.getLogger(Vaccine.class);

    private List<Object> candidates = new ArrayList<>();
    private List<ComponentDependency> dependencies = new ArrayList<>();

    private Properties properties;

    public static void main(String[] args) throws Exception {
        Vaccine vaccine = new Vaccine();
        List<Class> collect = getClassesForPackage("nl.imine.discord").stream().filter(c -> c.isAnnotationPresent(Component.class)).collect(Collectors.toList());
        List<ComponentDependency> dependencies = vaccine.scanClassesForCandidates(collect);

        System.out.println(dependencies);
    }

    /**
     * Attempts to list all the classes in the specified package as determined
     * by the context class loader
     *
     * @param packageName the package name to search
     * @return a list of classes that exist within that package
     * @throws ClassNotFoundException if something went wrong
     */
    private static List<Class> getClassesForPackage(String packageName) throws ClassNotFoundException {
        // This will hold a list of directories matching the packageName. There may be more than one if a package is split over multiple jars/paths
        ArrayList<File> directories = new ArrayList<File>();
        try {
            ClassLoader cld = Thread.currentThread().getContextClassLoader();
            if (cld == null) {
                throw new ClassNotFoundException("Can't get class loader.");
            }
            String path = packageName.replace('.', '/');
            // Ask for all resources for the path
            Enumeration<URL> resources = cld.getResources(path);
            while (resources.hasMoreElements()) {
                directories.add(new File(URLDecoder.decode(resources.nextElement().getPath(), "UTF-8")));
            }
        } catch (NullPointerException x) {
            throw new ClassNotFoundException(packageName + " does not appear to be a valid package (Null pointer exception)");
        } catch (UnsupportedEncodingException encex) {
            throw new ClassNotFoundException(packageName + " does not appear to be a valid package (Unsupported encoding)");
        } catch (IOException ioex) {
            throw new ClassNotFoundException("IOException was thrown when trying to get all resources for " + packageName);
        }

        ArrayList<Class> classes = new ArrayList<java.lang.Class>();
        // For every directory identified capture all the .class files
        for (File directory : directories) {
            if (directory.exists() && directory.isDirectory()) {
                // Get the list of the files contained in the package
                String[] files = directory.list();
                if (files != null) {
                    for (String file : files) {
                        // we are only interested in .class files
                        if (file.endsWith(".class")) {
                            // removes the .class extension
                            try {
                                classes.add(Class.forName(packageName + '.' + file.substring(0, file.length() - 6)));
                            } catch (NoClassDefFoundError e) {
                                // do nothing. this class hasn't been found by the loader, and we don't care.
                            }
                        } else {
                            classes.addAll(getClassesForPackage(packageName + "." + file));
                        }
                    }
                }
            } else {
                throw new ClassNotFoundException(packageName + " (" + directory.getPath() + ") does not appear to be a valid package");
            }
        }
        return classes;
    }

    public void inject(Properties properties, String basePackage) {
        logger.info("Initializing Injection");
        this.properties = properties;

        //Load classes
        List<Class> classes = null;
        try {
            classes = getClassesForPackage(basePackage).stream().filter(c -> c.isAnnotationPresent(Component.class)).collect(Collectors.toList());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not load packages");
        }

        logger.info("Found following components:");

        dependencies = scanClassesForCandidates(classes);

        dependencies.forEach(dependency -> {
            if (dependencies.stream().noneMatch(candidate -> candidate.getClass().equals(dependency.getType()))) {
                resolveDependency(dependency);
            }
        });

        dependencies.forEach(dependency -> logger.error(dependency.getClass().getName()));
    }

    public List<ComponentDependency> scanClassesForCandidates(List<Class> classes) {
        List<ComponentDependency> ret = new ArrayList<>();
        for (Class clazz : classes) {
            ret.add(getInjectionDetails(clazz));
        }
        return ret;
    }

    private ComponentDependency getInjectionDetails(Class clazz) {
        if (clazz.isAnnotationPresent(Component.class)) {
            Constructor[] constructors = clazz.getConstructors();
            if (constructors.length <= 1) {
                List<Class> dependencies = new ArrayList<>();
                for (Parameter parameter : constructors[0].getParameters()) {
                    if (!parameter.isAnnotationPresent(Property.class)) {
                        dependencies.add(parameter.getType());
                    }
                }
                return new ComponentDependency(clazz, dependencies.toArray(new Class[dependencies.size()]), getProvidedComponentsFromClass(clazz));
            }
        }
        throw new ConstructorStalemateException(clazz);
    }

    private Class[] getProvidedComponentsFromClass(Class clazz) {
        return Arrays.stream(clazz.getMethods())
                .filter(method -> method.getParameterCount() == 0)
                .filter(method -> method.isAnnotationPresent(Provided.class))
                .map(Method::getReturnType)
                .toArray(Class[]::new);
    }

    private void resolveDependency(ComponentDependency dependency) {
        dependency.setObject(createOrGetCandidateInstance(dependency.getType(), new ArrayList<>()));
    }

    private Object createOrGetCandidateInstance(Class candidate, List<Class> parents) {
        Optional<Object> instance = candidates.stream()
                .filter(createdInstance -> candidate.equals(createdInstance.getClass()))
                .findFirst();

        if (instance.isPresent()) {
            return instance.get();
        } else {
            if(!parents.contains(candidate)) {
                try {
                    Constructor[] constructors = candidate.getConstructors();
                    if (constructors.length == 1) {
                        Constructor constructor = constructors[0];
                        Parameter[] parameters = constructor.getParameters();
                        Object newInstance = null;
                        if (parameters.length == 0) {
                            newInstance = constructor.newInstance();
                        } else {
                            Object[] constructorParameterTypes = new Object[parameters.length];
                            for (int i = 0; i < parameters.length; i++) {
                                Parameter parameter = parameters[i];
                                if (constructor.getParameters()[i].isAnnotationPresent(Property.class)) {
                                    constructorParameterTypes[i] = resolvePropertyDependency(parameter);
                                } else {
                                    Class parameterType = parameter.getType();
                                    if(parameterType.isAnnotationPresent(Component.class)) {
                                        parents.add(candidate);
                                        constructorParameterTypes[i] = createOrGetCandidateInstance(parameterType, parents);
                                    } else {
                                        Class provider = getProvider(parameterType);
                                        if(provider != null) {
                                            parents.add(candidate);
                                            Object providerInstance = createOrGetCandidateInstance(provider, parents);
                                            constructorParameterTypes[i] = searchAndCreateProviderInstance(providerInstance, parameterType);
                                        } else {
                                            throw new UnknownDependencyException(candidate, parameterType);
                                        }
                                    }
                                }
                            }
                            newInstance = constructor.newInstance(constructorParameterTypes);
                        }
                        candidates.add(newInstance);
                        afterCreation(newInstance);
                        return newInstance;
                    }
                    throw new ConstructorStalemateException(candidate);
                } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                    logger.error("Could not create Instance for {}. Reason: ({}: {})",
                            candidate.getClass().getName(),
                            e.getClass().getSimpleName(),
                            e.getMessage());
                    return null;
                }
            } else {
                throw new CircularDependencyException(parents, candidate);
            }
        }
    }

    private Object searchAndCreateProviderInstance(Object providerInstance, Class requestedType) {
        return Arrays.stream(providerInstance.getClass().getMethods())
                .filter(method -> method.getParameterCount() == 0)
                .filter(method -> method.isAnnotationPresent(Provided.class))
                .filter(method -> method.getReturnType().equals(requestedType))
                .map(method -> {
                    try {
                        return method.invoke(providerInstance);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        logger.error("Could not provide Instance for {}. Reason: ({}: {})",
                                requestedType.getClass().getName(),
                                e.getClass().getSimpleName(),
                                e.getMessage());                    }
                    return null;
                })
                .findFirst().orElse(null);
    }

    private String resolvePropertyDependency(Parameter parameter) {
        Property property = parameter.getAnnotation(Property.class);
        return (String) properties.get(property.value());
    }

    private void afterCreation(Object injectable) {
        for (Method method : injectable.getClass().getMethods()) {
            if (method.getParameterCount() == 0 && method.isAnnotationPresent(PostConstruct.class)) {
                try {
                    method.invoke(injectable);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    logger.error("Could not run @PostConstruct for {}. Reason: ({}: {})",
                            injectable.getClass().getName(),
                            e.getClass().getSimpleName(),
                            e.getMessage());
                }
            }
        }
    }

    public Class getProvider(Class type) {
        for(ComponentDependency dependency : dependencies) {
            for (Class providerClass : dependency.getProvidedClasses()) {
                if(providerClass.equals(type)) {
                    return dependency.getType();
                }
            }
        }
        return null;
    }

    public Object getCandidates() {
        return candidates;
    }

    public Object getInjected(Class type) {
        for (Object candidate : candidates) {
            if (candidate.getClass().equals(type)) {
                return candidate;
            }
        }
        return null;
    }
}
