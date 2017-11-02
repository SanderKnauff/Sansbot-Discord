package nl.imine.vaccine;

import nl.imine.discord.command.Command;
import nl.imine.vaccine.annotation.Component;
import nl.imine.vaccine.annotation.Property;
import nl.imine.vaccine.exception.CircularDependencyException;
import nl.imine.vaccine.exception.UnknownDependencyException;
import nl.imine.vaccine.model.ComponentDependency;
import nl.imine.vaccine.model.Dependency;
import nl.imine.vaccine.model.PropertyDependency;

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

	private List<ComponentDependency> dependencies = new ArrayList<>();

	private Properties properties;

	public void inject(Properties properties, String basePackage) {
		logger.info("Initializing Injection");
		this.properties = properties;

		List<Class> classes = null;
		try {
			classes = getClassesForPackage(basePackage).stream().filter(c -> c.isAnnotationPresent(Component.class)).collect(Collectors.toList());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Could not load packages");
		}

		logger.info("Found following components:");
		classes.forEach(c -> {
			if (dependencies.stream().noneMatch(d -> d.getType().equals(c))) {
				ComponentDependency requirementsForClass = getRequirementsForClass(c, new ArrayList<>());
				dependencies.add(requirementsForClass);
			}
		});

		dependencies.forEach(d -> logger.error(d.getType().getName()));
	}

	private ComponentDependency getRequirementsForClass(Class c, List<Class> parents) {
		Constructor[] constructors = c.getConstructors();
		if (constructors.length == 1) {
			Constructor constructor = constructors[0];
			Parameter[] parameters = constructor.getParameters();
			Dependency[] constructorParameterTypes = new Dependency[parameters.length];
			for (int i = 0; i < parameters.length; i++) {
				Parameter parameter = parameters[i];
				if (constructor.getParameters()[i].isAnnotationPresent(Property.class)) {
					constructorParameterTypes[i] = resolvePropertyDependency(parameter);
				} else {
					parents.add(c);
					if (parents.contains(parameter.getType())) {
						throw new CircularDependencyException(parents, parameter.getType());
					}
					ComponentDependency childDependency = getRequirementsForClass(parameter.getType(), new ArrayList<>(parents));
					if (childDependency == null) {
						throw new UnknownDependencyException(c, parameter.getType());
					}
					if (dependencies.contains(childDependency)) {
						for (Dependency dependency : dependencies) {
							if (dependency.equals(childDependency)) {
								constructorParameterTypes[i] = dependency;
								break;
							}
						}
					} else {
						dependencies.add(childDependency);
						constructorParameterTypes[i] = childDependency;
					}
				}
			}
			logger.info("\t {} with dependencies: {}", c.getName(), constructorParameterTypes);
			ComponentDependency componentDependency = new ComponentDependency(c, constructorParameterTypes);
			resolveDependencies(componentDependency, null);
			return componentDependency;
		} else {
			return null;
		}
	}

	private PropertyDependency resolvePropertyDependency(Parameter parameter) {
		Property property = parameter.getAnnotation(Property.class);
		PropertyDependency propertyDependency = new PropertyDependency();
		propertyDependency.setProperty(properties.get(property.value()));
		return propertyDependency;
	}

	public void resolveDependencies(ComponentDependency dependency, Class parent) {
		Object injectable = null;
		for (ComponentDependency knownDependency : dependencies) {
			if (dependency.equals(knownDependency)) {
				injectable = knownDependency.getObject();
			}
		}
		if (injectable == null) {
			try {
				//If the Component has no dependencies by itself, we can create it.
				if (dependency.getDependencies().length == 0) {
					injectable = dependency.getType().getConstructors()[0].newInstance();
					invokePostConstruct(injectable);
					//Otherwise list the dependencies and resolve them
				} else {
					for (ComponentDependency child : dependencies) {
						if (!child.isResolved()) {
							resolveDependencies(child, dependency.getType());
						}
					}
					injectable = dependency.getType().getConstructors()[0].newInstance(Arrays.stream(dependency.getDependencies()).map(Dependency::getObject).collect(Collectors.toList()).toArray(new Object[dependency.getDependencies().length]));
					invokePostConstruct(injectable);
				}
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
				logger.error("Failed to create Component '{}' | Reason ({}: {})", dependency.getType().getSimpleName(), e.getClass().getSimpleName(), e.getMessage());
			}
			dependency.setObject(injectable);
		}
	}

	private void invokePostConstruct(Object injectable) throws IllegalAccessException, InvocationTargetException {
		for (Method method : injectable.getClass().getMethods()) {
			if (method.getParameterCount() == 0 && method.isAnnotationPresent(PostConstruct.class)) {
				method.invoke(injectable);
			}
		}
	}

	/**
	 * Attempts to list all the classes in the specified package as determined
	 * by the context class loader
	 *
	 * @param packageName the package name to search
	 * @return a list of classes that exist within that package
	 * @throws ClassNotFoundException if something went wrong
	 */
	private static List<java.lang.Class> getClassesForPackage(String packageName) throws ClassNotFoundException {
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

		ArrayList<java.lang.Class> classes = new ArrayList<java.lang.Class>();
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

//    private List<File> findFilesInUrls(Enumeration<URL> path) {
//        List<File> ret = new ArrayList<>();
//        if (path.isDirectory()) {
//            Arrays.asList(path.listFiles()).forEach(searchPath -> ret.addAll(findFilesInPath(searchPath)));
//        } else {
//            ret.add(path);
//        }
//        return ret;
//    }

	public List<ComponentDependency> getDependencies() {
		return dependencies;
	}

	public Object getInjected(Class type) {
		for (Dependency dependency : dependencies) {
			if (dependency.getObject() != null) {
				if (dependency.getObject().getClass().equals(type)) {
					return dependency.getObject();
				}
			}
		}
		return null;
	}
}
