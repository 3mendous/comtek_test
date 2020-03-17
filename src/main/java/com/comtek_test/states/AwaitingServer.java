package com.comtek_test.states;

import com.comtek_test.state_machine.StateMachine;

import java.io.IOException;

public class AwaitingServer extends AbstractState {

    public AwaitingServer(StateMachine stateMachine) {
        super(stateMachine);
    }

    @Override
    public void goToNextState(String data) throws IOException {
        stateMachine.activateState(new ProcessingServer(stateMachine));
    }

    @Override
    public void goToErrorState() {
        stateMachine.activateState(new AwaitingServer(stateMachine));
        System.out.println("Error during reading input. Server is back to initial state");
    }

    @Override
    public String getName() {
        return States.WAITING.getName();
    }
}
