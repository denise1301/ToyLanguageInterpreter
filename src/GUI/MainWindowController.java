package GUI;

import Controller.Controller;
import Model.Containers.*;
import Model.ProgramState;
import Model.Statement.IStatement;

import Model.Value.IValue;
import Model.Value.StringValue;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.net.URL;
import java.security.KeyStore;
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
        Map<Integer, IValue> heapTable = currentProgramState.getHeap().getContent();
        Map<Integer, Integer> heapTableCast = new HashMap<>();
        for (Map.Entry<Integer, IValue> entry : heapTable.entrySet()) {
            heapTableCast.put(entry.getKey(), Integer.getInteger(entry.getValue().toString()));
        }
        List<Map.Entry<Integer, Integer>> heapTableList = new ArrayList<>(heapTableCast.entrySet());
        heapTableView.setItems(FXCollections.observableList(heapTableList));
        heapTableView.refresh();
    }

    private void populateFileTable(ProgramState currentProgramState) {
        Map<StringValue, BufferedReader> fileTable = currentProgramState.getFileTable().getAll();
        Map<Integer, String> fileTableMap = new HashMap<>();
        Integer index = 1;
        for (Map.Entry<StringValue, BufferedReader> entry : fileTable.entrySet()) {
            fileTableMap.put(index, entry.getKey().toString() + entry.getValue().toString());
            index++;
        }
        List<Map.Entry<Integer, String>> fileTableList = new ArrayList<>(fileTableMap.entrySet());
        fileTableView.setItems(FXCollections.observableList(fileTableList));
        fileTableView.refresh();
    }

    private void populateOutput(ProgramState currentProgramState) {
        List<IValue> output = currentProgramState.getOut().getAll();
        List<Integer> outputToInteger = new ArrayList<>();
        for (IValue value : output) {
            outputToInteger.add(Integer.getInteger(value.toString()));
        }
        outputListView.setItems(FXCollections.observableList(outputToInteger));
        outputListView.refresh();
    }

    private void populateSymbolTable(ProgramState currentProgramState) {
         Map<String, IValue> symbolTable = currentProgramState.getSymbolTable().getAll();
         Map<String, Integer> symbolTableMap = new HashMap<>();
         for (Map.Entry<String, IValue> entry : symbolTable.entrySet()) {
             symbolTableMap.put(entry.getKey(), Integer.getInteger(entry.getValue().toString()));
         }
         List<Map.Entry<String, Integer>> symbolTableList = new ArrayList<>(symbolTableMap.entrySet());
         symbolTableView.setItems(FXCollections.observableList(symbolTableList));
         symbolTableView.refresh();
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
        if (programStateListView.getSelectionModel().getSelectedIndex() != -1) {
            int currentId = programStateListView.getSelectionModel().getSelectedItem();
            return controller.getRepository().getProgramStateById(currentId);
        }
        return null;
    }

    private List<Integer> getProgramStateIds(List<ProgramState> programStateList) {
        return programStateList.stream().map(ProgramState::getIdThread).collect(Collectors.toList());
    }

    private void populateProgramStateIdentifiers() {
        List<ProgramState> programStates = controller.getRepository().getProgramStatesList().getAll();
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
        controller.oneStepForAllPrograms(controller.getRepository().getProgramStatesList().getAll());
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
        programStateListView.setOnMouseClicked(mouseEvent -> changeProgramState(getCurrentProgramState()));
        executeOneStepButton.setOnAction(actionEvent -> {
            try {
                oneStep();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
