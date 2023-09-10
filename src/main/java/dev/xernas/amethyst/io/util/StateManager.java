package dev.xernas.amethyst.io.util;

import dev.xernas.amethyst.utils.AmethystLogger;

public class StateManager {

    private State currentState = State.HANDSHAKE;


    public void changeState(State state) {
        AmethystLogger.info("Changed state: " + currentState.toString() + " --> " + state.toString());
        currentState = state;
    }

    public State getCurrentState() {
        return currentState;
    }

}
