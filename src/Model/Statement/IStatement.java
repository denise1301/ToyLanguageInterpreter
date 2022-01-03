package Model.Statement;

import Model.ProgramState;

public interface IStatement {
    ProgramState execute(ProgramState state) throws Exception;

    String toString();
}