package dev.xernas.amethyst.io.util;

import dev.xernas.amethyst.io.AmethystServer;

import java.util.logging.Logger;

public class StateManager {

    private static final Logger logger = AmethystServer.getLogger();

    private State currentState = State.HANDSHAKE;


    public void changeState(State state) {
        logger.info("Changed state: " + currentState.toString() + " --> " + state.toString());
        currentState = state;
    }

    public State getCurrentState() {
        return currentState;
    }

}
