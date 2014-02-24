package com.kesho.datamart.ui.util;

import javax.inject.Named;
import java.util.*;

//TODO: this implementation should be replaced with something more appropriate
/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/18/13
 * Time: 9:25 PM
 * To change this template use File | Settings | File Templates.
 */
@Named
public class EventBus {
    private Map<Event, List<SystemEventListener>> listeners = new HashMap<>();

    public void registerListener(SystemEventListener listener, Event ...types) {
        for (Event event:types) {
            registerListener(event, listener);
        }
    }

    public void registerListener(Event type, SystemEventListener listener) {
        List<SystemEventListener> eventListeners = listeners.get(type);
        if(eventListeners == null) {
            eventListeners = new ArrayList<>();
            listeners.put(type, eventListeners);
        }

        eventListeners.add(listener);
    }

    public void fireEvent(Event type) {
        System.out.println("Fire " + type);
        List<SystemEventListener> eventListeners = listeners.get(type);
        if(eventListeners != null) {
            for (SystemEventListener listener:eventListeners) {
                listener.handle();
            }
        }
    }
}
