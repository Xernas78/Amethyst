package dev.xernas.amethyst.io.util;

import dev.xernas.amethyst.io.AmethystServer;

import java.util.logging.Logger;

public class StateManager {

    private State currentState = State.HANDSHAKE;


    public void changeState(State state) {
        currentState = state;
    }

    public State getCurrentState() {
        return currentState;
    }

}
