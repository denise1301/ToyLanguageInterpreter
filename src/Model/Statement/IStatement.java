package Model.Statement;

import Model.Containers.IMap;
import Model.ProgramState;
import Model.Type.IType;

public interface IStatement {
    ProgramState execute(ProgramState state) throws Exception;

    IMap<String, IType> typeCheck(IMap<String, IType> typeEnv) throws Exception;

    String toString();
}