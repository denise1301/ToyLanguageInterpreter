package Repository;

import Model.Containers.IList;
import Model.Containers.MyList;
import Model.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Repository implements IRepository {
    private IList<ProgramState> states;
    private Path logFilePath;
    private PrintWriter logFile;

    public Repository(ProgramState programState, String fileName) {
        states = new MyList<ProgramState>();
        states.add(programState);
        logFilePath = Paths.get(fileName).toAbsolutePath();
    }

    @Override
    public IList<ProgramState> getProgramStatesList() {
        return this.states;
    }

    @Override
    public void setProgramStatesList(IList<ProgramState> newList) {
        states = newList;
    }

    @Override
    public void addState(ProgramState newState) {
        states.add(newState);
    }


    @Override
    public void logPrgStateExec(ProgramState state) throws Exception {
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath.toString(), true)));
        logFile.println(state.toString());
        logFile.flush();
        logFile.close();
    }
}
