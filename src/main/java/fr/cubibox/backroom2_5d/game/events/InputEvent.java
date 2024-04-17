package fr.cubibox.backroom2_5d.game.events;

import fr.cubibox.backroom2_5d.engine.observers.Event;

public class InputEvent implements Event {
    public final String keyName;

    public InputEvent(String keyName) {
        this.keyName = keyName;
    }

    @Override
    public void execute() {
        System.out.println("Key : " + keyName);
    }
}
