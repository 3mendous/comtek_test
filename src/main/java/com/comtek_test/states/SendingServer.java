package com.comtek_test.states;

import com.comtek_test.state_machine.StateMachine;

import java.io.IOException;
import java.util.Date;

import static com.comtek_test.constants.Constants.*;
import static com.comtek_test.utils.Utils.generateRandomDuration;
import static com.comtek_test.utils.Utils.getDuration;

public class SendingServer extends AbstractState {

    private final int minDuration = getDuration(MIN_DURATION_PROPERTY, FILE_PATH);
    private final int maxDuration = getDuration(MAX_DURATION_PROPERTY, FILE_PATH);

    public SendingServer(StateMachine stateMachine) throws IOException {
        super(stateMachine);
    }

    @Override
    public void goToNextState(String data) {
        System.out.println("Server is sending data");
        //Imitation of data sending
        int duration = generateRandomDuration(minDuration, maxDuration);
        long finishTime = new Date().getTime() + duration;
        while (new Date().getTime() <= finishTime) {
        }
        stateMachine.activateState(new AwaitingServer(stateMachine));
        System.out.println("Data sent successfully");
    }

    @Override
    public void goToErrorState() {
        stateMachine.activateState(new AwaitingServer(stateMachine));
        System.out.println("Error during sending data. Server is back to initial state");
    }

    @Override
    public String getName() {
        return States.SENDING.getName();
    }
}
