package Repository;

import Model.Containers.IList;
import Model.ProgramState;

public interface IRepository {
    IList<ProgramState> getProgramStatesList();
    void setProgramStatesList(IList<ProgramState> newList);
    void addState(ProgramState newState);
    void logPrgStateExec(ProgramState state) throws Exception;
}
