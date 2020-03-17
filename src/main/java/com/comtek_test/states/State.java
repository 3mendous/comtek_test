package com.comtek_test.states;

import java.io.IOException;

public interface State {

    void goToNextState(String data) throws IOException;

    void goToErrorState();

    String getName();
}
