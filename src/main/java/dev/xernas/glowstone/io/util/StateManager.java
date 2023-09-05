package dev.xernas.glowstone.io.util;

import dev.xernas.glowstone.utils.GlowstoneLogger;

public class StateManager {

    private State currentState = State.HANDSHAKE;


    public void changeState(State state) {
        GlowstoneLogger.info("Changed state: " + currentState.toString() + " --> " + state.toString());
        currentState = state;
    }

    public State getCurrentState() {
        return currentState;
    }

}
