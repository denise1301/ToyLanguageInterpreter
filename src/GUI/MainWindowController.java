package GUI;

import Controller.Controller;
import Model.Containers.*;
import Model.ProgramState;
import Model.Statement.IStatement;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class MainWindowController implements Initializable {
    private Controller controller;

    @FXML
    private TableView<Map.Entry<Integer, Integer>> heapTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, Integer> heapAddressColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, Integer>, Integer> heapValueColumn;

    @FXML
    private TableView<Map.Entry<Integer, String>> fileTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, String>, Integer> fileIdentifierColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, String>, String> fileNameColumn;

    @FXML
    private TableView<Map.Entry<String, Integer>> symbolTableView;

    @FXML
    private TableColumn<Map.Entry<String, Integer>, String> symbolTableVariableColumn;

    @FXML
    private TableColumn<Map.Entry<String, Integer>, Integer> symbolTableValueColumn;

    @FXML
    private ListView<Integer> outputListView;

    @FXML
    private ListView<Integer> programStateListView;

    @FXML
    private ListView<String> executionStackListView;

    @FXML
    private TextField numberOfProgramStatesTextField;

    @FXML
    private Button executeOneStepButton;

    private void populateHeapTable(ProgramState currentProgramState) {

    }

    private void populateFileTable(ProgramState currentProgramState) {

    }

    private void populateOutput(ProgramState currentProgramState) {
        List<Integer> output = (List<Integer>) currentProgramState.getOut();
        outputListView.setItems(FXCollections.observableList(output));
        outputListView.refresh();
    }

    private void populateSymbolTable(ProgramState currentProgramState) {

    }

    private void populateExecutionStack(ProgramState currentProgramState) {
        IStack<IStatement> executionStack = currentProgramState.getExecutionStack();

        List<String> executionStackList = new ArrayList<>();
        for(IStatement s : executionStack.getAll()){
            executionStackList.add(s.toString());
        }
        executionStackListView.setItems(FXCollections.observableList(executionStackList));
        executionStackListView.refresh();
    }

    private void changeProgramState(ProgramState currentState) {
        if (currentState != null) {
            populateExecutionStack(currentState);
            populateSymbolTable(currentState);
            populateOutput(currentState);
            populateFileTable(currentState);
            populateHeapTable(currentState);
        }
    }

    public void setController(Controller controller){
        this.controller = controller;
        populateProgramStateIdentifiers();
    }

    private ProgramState getCurrentProgramState(){
        if(programStateListView.getSelectionModel().getSelectedIndex() != -1) {
            int currentId = programStateListView.getSelectionModel().getSelectedItem();
            return controller.getRepository().getProgramStateById(currentId);
        }
        return null;
    }

    private List<Integer> getProgramStateIds(List<ProgramState> programStateList) {
        return programStateList.stream().map(ProgramState::getIdThread).collect(Collectors.toList());
    }

    private void populateProgramStateIdentifiers() {
        List<ProgramState> programStates = (List<ProgramState>) controller.getRepository().getProgramStatesList();
        programStateListView.setItems(FXCollections.observableList(getProgramStateIds(programStates)));
        numberOfProgramStatesTextField.setText("" + programStates.size());
    }

    private void oneStep() throws Exception {
        if (controller == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The program was not selected", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        boolean programStateLeft = getCurrentProgramState().getExecutionStack().isEmpty();
        if (programStateLeft) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing left to execute", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        controller.oneStepForAllPrograms((List<ProgramState>) controller.getRepository().getProgramStatesList());
        changeProgramState(getCurrentProgramState());
        populateProgramStateIdentifiers();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        heapAddressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        heapValueColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getValue()).asObject());
        fileIdentifierColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        fileNameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue() + ""));
        symbolTableVariableColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey() + ""));
        symbolTableValueColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getValue()).asObject());
        programStateListView.setOnMouseClicked(mouseEvent -> { changeProgramState(getCurrentProgramState()); });
        executeOneStepButton.setOnAction(actionEvent -> {
            try {
                oneStep();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
