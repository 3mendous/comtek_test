package com.comtek_test;

import com.comtek_test.state_machine.StateMachine;
import com.comtek_test.states.AwaitingServer;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ServerStateMachine {

    private static StateMachine stateMachine;
    private static List<String> dataToProcess = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        stateMachine = new StateMachine();
        stateMachine.activateState(new AwaitingServer(stateMachine));
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter your data");
        while (true) {
            String data = console.readLine();
            if (data.equals("exit")) {
                System.out.println("All unprocessed data will be lost");
                return;
            }
            dataToProcess.add(data);
            if (!stateMachine.getActiveState().getName().equals("waiting") || dataToProcess.size() > 1) {
                System.out.println("Sorry, server is busy. It'll process your data later");
            }
            if (stateMachine.getActiveState().getName().equals("waiting")) {
                StateThread stateThread = new StateThread();
                stateThread.start();
            }
        }
    }

    static class StateThread extends Thread {

        @SneakyThrows
        @Override
        public void run() {
            while (!dataToProcess.isEmpty()) {
                String data = dataToProcess.get(0);
                dataToProcess.remove(0);
                stateMachine.goToNextState(data);
                stateMachine.goToNextState(data);
                if (stateMachine.getActiveState().getName().equals("waiting")) {
                    interrupt();
                } else {
                    stateMachine.goToNextState(data);
                }
            }
            System.out.println("Enter your data");
        }
    }
}
