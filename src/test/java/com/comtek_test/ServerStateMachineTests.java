package com.comtek_test;

import com.comtek_test.state_machine.StateMachine;
import com.comtek_test.states.AbstractState;
import com.comtek_test.states.AwaitingServer;
import com.comtek_test.states.ProcessingServer;
import com.comtek_test.states.SendingServer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static com.comtek_test.constants.Constants.*;
import static com.comtek_test.states.States.*;
import static com.comtek_test.utils.Utils.generateRandomDuration;
import static com.comtek_test.utils.Utils.getDuration;
import static org.junit.jupiter.api.Assertions.*;

public class ServerStateMachineTests {

    private static String data = "";
    private static StateMachine stateMachine = new StateMachine();

    @Test
    void checkStateMachineInitialization() {
        stateMachine.activateState(new AwaitingServer(stateMachine));
        assertEquals(WAITING.getName(), stateMachine.getActiveState().getName());
    }

    @ParameterizedTest
    @MethodSource
    void checkGoNextTransitions(String stateName, AbstractState state) throws IOException {
        stateMachine.activateState(state);
        stateMachine.goToNextState(data);
        assertEquals(stateName, stateMachine.getActiveState().getName());
    }

    @ParameterizedTest
    @MethodSource
    void checkGoErrorTransitions(String stateName, AbstractState state) {
        stateMachine.activateState(state);
        stateMachine.goToErrorState();
        assertEquals(stateName, stateMachine.getActiveState().getName());
    }

    @Test
    void checkE2ETransitions() throws IOException {
        stateMachine.activateState(new AwaitingServer(stateMachine));
        stateMachine.goToNextState(data);
        stateMachine.goToNextState(data);
        stateMachine.goToNextState(data);
        assertEquals(WAITING.getName(), stateMachine.getActiveState().getName());
    }

    @Test
    void checkError() throws IOException {
        String longString = "very long string more then 30 symbols";
        stateMachine.activateState(new AwaitingServer(stateMachine));
        stateMachine.goToNextState(longString);
        stateMachine.goToNextState(longString);
        assertEquals(WAITING.getName(), stateMachine.getActiveState().getName());
    }

    @Test
    void checkDurationGeneration() throws IOException {
        final int minDuration = getDuration(MIN_DURATION_PROPERTY, FILE_PATH);
        final int maxDuration = getDuration(MAX_DURATION_PROPERTY, FILE_PATH);
        int duration = generateRandomDuration(minDuration, maxDuration);
        assertTrue(minDuration <= duration && duration < maxDuration);
    }

    @ParameterizedTest
    @MethodSource
    void checkExceptions(Exception exception, String property, String filePath) {
        assertThrows(exception.getClass(), () -> getDuration(property, filePath));
    }

    private static Stream<Arguments> checkGoNextTransitions() throws IOException {
        return Stream.of(
                Arguments.of(PROCESSING.getName(), new AwaitingServer(stateMachine)),
                Arguments.of(SENDING.getName(), new ProcessingServer(stateMachine)),
                Arguments.of(WAITING.getName(), new SendingServer(stateMachine))
        );
    }

    private static Stream<Arguments> checkGoErrorTransitions() throws IOException {
        return Stream.of(
                Arguments.of(WAITING.getName(), new AwaitingServer(stateMachine)),
                Arguments.of(WAITING.getName(), new ProcessingServer(stateMachine)),
                Arguments.of(WAITING.getName(), new SendingServer(stateMachine))
        );
    }

    private static Stream<Arguments> checkExceptions() {
        return Stream.of(
                Arguments.of(new IOException(), MIN_DURATION_PROPERTY, FILE_PATH.substring(3)),
                Arguments.of(new NumberFormatException(), MIN_DURATION_PROPERTY.substring(3), FILE_PATH)
        );
    }
}
