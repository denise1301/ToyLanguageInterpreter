package Controller;

import Model.ProgramState;

public interface IController {
    void allSteps() throws Exception;
    void add(ProgramState state);
}
