package com.comtek_test.state_machine;

import com.comtek_test.states.State;

import java.io.IOException;

public class StateMachine {

    State activeState = null;

    public void activateState(State state) {
        activeState = state;
    }

    public State getActiveState() {
        return activeState;
    }

    public void goToNextState(String data) throws IOException {
        activeState.goToNextState(data);
    }

    public void goToErrorState() {
        activeState.goToErrorState();
    }
}
