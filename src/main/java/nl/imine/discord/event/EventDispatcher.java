package nl.imine.discord.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventDispatcher {

    private Logger logger = LoggerFactory.getLogger(EventDispatcher.class);

    private final Set<Listener> listeners;

    public EventDispatcher() {
        this.listeners = new HashSet<>();
    }

    public void registerListener(Listener listener) {
        listeners.add(listener);
    }

    public void callEvent(Event event) {
        for (Listener listener : listeners) {
            for (Method method : listener.getClass().getMethods()) {
                if (method.isAnnotationPresent(EventHandler.class) && method.getParameterCount() == 1) {
                    if (method.getParameters()[0].getType().equals(event.getClass())) {
                        try {
                            method.invoke(listener, event);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            logger.warn("Error dispatching event to {}. Reason: ({}: {})", listener.getClass().getSimpleName(), e.getClass().getSimpleName(), e.getMessage());
                        }
                    }

                }
            }
        }
    }
}
