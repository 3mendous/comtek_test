package com.comtek_test.states;

import com.comtek_test.state_machine.StateMachine;

import java.io.IOException;
import java.util.Date;

import static com.comtek_test.constants.Constants.*;
import static com.comtek_test.utils.Utils.generateRandomDuration;
import static com.comtek_test.utils.Utils.getDuration;

public class ProcessingServer extends AbstractState {

    private final int minDuration = getDuration(MIN_DURATION_PROPERTY, FILE_PATH);
    private final int maxDuration = getDuration(MAX_DURATION_PROPERTY, FILE_PATH);

    public ProcessingServer(StateMachine stateMachine) throws IOException {
        super(stateMachine);
    }

    @Override
    public void goToNextState(String data) throws IOException {
        System.out.println("Server is processing data");
        //Imitation of data processing
        int duration = generateRandomDuration(minDuration, maxDuration);
        long currentTime = new Date().getTime();
        long finishTime = currentTime + duration;
        long errorTime = currentTime + generateRandomDuration(minDuration, duration);
        while (new Date().getTime() <= finishTime) {
            //Imitation of processing error in random moment of time
            if (new Date().getTime() <= errorTime) {
                if (data.length() > 30) {
                    goToErrorState();
                    return;
                }
            }
        }
        stateMachine.activateState(new SendingServer(stateMachine));
        System.out.println("Data processed successfully");
    }

    @Override
    public void goToErrorState() {
        stateMachine.activateState(new AwaitingServer(stateMachine));
        System.out.println("Error during processing data. Server is back to initial state");
    }

    @Override
    public String getName() {
        return States.PROCESSING.getName();
    }
}
