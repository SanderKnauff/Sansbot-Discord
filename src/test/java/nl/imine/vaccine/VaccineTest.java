package nl.imine.vaccine;

import nl.imine.vaccine.exception.CircularDependencyException;
import nl.imine.vaccine.exception.UnknownDependencyException;
import nl.imine.vaccine.model.ComponentDependency;
import nl.imine.vaccine.testresources.property.PropertyTestComponents;
import nl.imine.vaccine.testresources.provider.ProviderComponents;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class VaccineTest {

    private Vaccine vaccine;

    @Before
    public void setUp() {
        vaccine = new Vaccine();
    }

    @Test
    public void testPropertyResolver() {
        Properties properties = new Properties();
        String parentProperty = "parentProperty";
        String childProperty = "childProperty";
        properties.setProperty("parent", parentProperty);
        properties.setProperty("child", childProperty);
        vaccine.inject(properties, "nl.imine.vaccine.testresources.property");

        PropertyTestComponents.PropertyHolderParent propertyHolderParent = (PropertyTestComponents.PropertyHolderParent) vaccine.getInjected(PropertyTestComponents.PropertyHolderParent.class);
        PropertyTestComponents.PropertyHolderChild propertyHolderChild = (PropertyTestComponents.PropertyHolderChild) vaccine.getInjected(PropertyTestComponents.PropertyHolderChild.class);

        assertNotNull(propertyHolderParent);
        assertNotNull(propertyHolderChild);

        assertEquals(propertyHolderChild, propertyHolderParent.getPropertyHolderChild());

        assertEquals(parentProperty, propertyHolderParent.getProperty());
        assertEquals(childProperty, propertyHolderChild.getProperty());
    }

    @Test(expected = UnknownDependencyException.class)
    public void testUnknownDependencies() {
        vaccine.inject(new Properties(), "nl.imine.vaccine.testresources.unkowndependency");
    }

    @Test(expected = CircularDependencyException.class)
    public void testPreventCircularDependenciesSimple() {
        vaccine.inject(new Properties(), "nl.imine.vaccine.testresources.circularsimple");
    }

    @Test(expected = CircularDependencyException.class)
    public void testPreventCircularDependenciesComplex() {
        vaccine.inject(new Properties(), "nl.imine.vaccine.testresources.circularcomplex");
    }

    @Test
    public void testComplexTreeNoDuplicated() {
        vaccine.inject(new Properties(), "nl.imine.vaccine.testresources.complextree");
        Map<Class, Long> occurrences = vaccine.getDependencies().stream().collect(Collectors.groupingBy(ComponentDependency::getType, Collectors.counting()));
        occurrences.forEach((k, v) -> assertEquals(1, (long) v));
    }

    @Test
    public void testProviderAnnotation() {
        vaccine.inject(new Properties(), "nl.imine.vaccine.testresources.provider");
        assertNotNull(((ProviderComponents.ParentB) vaccine.getInjected(ProviderComponents.ParentB.class)).getChildProvided());
    }
}
