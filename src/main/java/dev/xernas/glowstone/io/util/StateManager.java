package dev.xernas.glowstone.io.util;

public class StateManager {

    private State currentState = State.HANDSHAKE;


    public void changeState(State state) {
        currentState = state;
    }

    public State getCurrentState() {
        return currentState;
    }

}
