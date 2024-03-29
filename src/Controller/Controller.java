package Controller;

import Model.Containers.IList;
import Model.Containers.MyList;
import Model.ProgramState;
import Model.Value.IValue;
import Model.Value.RefValue;
import Repository.IRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller implements IController {
    private final IRepository repository;
    private ExecutorService executor;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public void oneStepForAllPrograms(List<ProgramState> states) throws Exception {
        //print for each program state
        states.forEach(state -> {
            try {
                this.repository.logPrgStateExec(state);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //prepare the list of callables
        List<Callable<ProgramState>> callList = states
                .stream()
                .map((ProgramState state) -> (Callable<ProgramState>)(state::oneStep))
                .collect(Collectors.toList());

        //execute all callables => newly created program states (threads)
        List<ProgramState> newStates = executor.invokeAll(callList)
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull).toList();

        //add the new threads to the list of existing threads
        states.addAll(newStates);

        //print for each program state again
        states.forEach(state -> {
            try {
                this.repository.logPrgStateExec(state);
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
        });

        //save current program states in the repository
        IList<ProgramState> copyStates = new MyList<ProgramState>(states);
        this.repository.setProgramStatesList(copyStates);
    }

    @Override
    public void allSteps() throws Exception {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> states = removeCompletedProgram(repository.getProgramStatesList().getAll());
        while (states.size() > 0) {
            oneStepForAllPrograms(states);
            states = removeCompletedProgram(repository.getProgramStatesList().getAll());
        }
        executor.shutdownNow();
        IList<ProgramState> copyStates = new MyList<ProgramState>(states);
        repository.setProgramStatesList(copyStates);
    }

    public List<ProgramState> removeCompletedProgram(List<ProgramState> inPrgList) {
        return inPrgList
                .stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    @Override
    public void add(ProgramState state) {
        this.repository.addState(state);
    }
}
