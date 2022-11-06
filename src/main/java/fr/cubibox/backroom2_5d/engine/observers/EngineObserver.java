package fr.cubibox.backroom2_5d.engine.observers;

import fr.cubibox.backroom2_5d.engine.observers.events.Event;

public interface EngineObserver {
    void addEvent(Event event);
}
