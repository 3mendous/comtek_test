package com.comtek_test.states;

import com.comtek_test.state_machine.StateMachine;

import java.io.IOException;

abstract public class AbstractState implements State {

    protected StateMachine stateMachine;

    public AbstractState(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }

    public void goToNextState(String data) throws IOException {
    }

    public void goToErrorState() {
    }

    abstract public String getName();

}
