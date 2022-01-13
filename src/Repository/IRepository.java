package Repository;

import Model.Containers.IList;
import Model.ProgramState;

import java.util.List;

public interface IRepository {
    List<ProgramState> getProgramStatesList();
    void setProgramStatesList(IList<ProgramState> newList);
    void addState(ProgramState newState);
    void logPrgStateExec(ProgramState state) throws Exception;

    ProgramState getProgramStateById(int id);
}
