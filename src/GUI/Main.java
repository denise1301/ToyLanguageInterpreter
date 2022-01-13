package GUI;

import Controller.Controller;
import Model.Containers.*;
import Model.Expression.*;
import Model.ProgramState;
import Model.Statement.*;
import Model.Type.*;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.IRepository;
import Repository.Repository;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.*;

public class Main extends Application {
    private final IStatement ex1 =
            new CompStatement(
                    new VarDeclStatement("v", new IntType()),
                    new CompStatement(
                            new AssignStatement("v", new ValueExpression(new IntValue(2))),
                            new PrintStatement(new VarExpression("v"))));
    private final IStatement ex2 =
            new CompStatement(
                    new VarDeclStatement("a", new IntType()),
                    new CompStatement(
                            new VarDeclStatement("b", new IntType()),
                            new CompStatement(
                                    new AssignStatement("a", new ArithmeticExpression(
                                            new ValueExpression(new IntValue(2)),
                                            new ArithmeticExpression(
                                                    new ValueExpression(new IntValue(3)),
                                                    new ValueExpression(new IntValue(5)),
                                                    ArithmeticOperation.MULTIPLY),
                                            ArithmeticOperation.PLUS)),
                                    new CompStatement(
                                            new AssignStatement("b", new ArithmeticExpression(
                                                    new VarExpression("a"),
                                                    new ValueExpression(new IntValue(1)),
                                                    ArithmeticOperation.PLUS)),
                                            new PrintStatement(new VarExpression("b"))))));
    private final IStatement ex3 =
            new CompStatement(
                    new VarDeclStatement("a", new BoolType()),
                    new CompStatement(
                            new VarDeclStatement("v", new IntType()),
                            new CompStatement(
                                    new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                    new CompStatement(
                                            new IfStatement(
                                                    new VarExpression("a"),
                                                    new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                                    new AssignStatement("v", new ValueExpression(new IntValue(3)))),
                                            new PrintStatement(new VarExpression("v"))))));
    private final IStatement ex4 =
            new CompStatement(
                    new VarDeclStatement("varf", new StringType()),
                    new CompStatement(
                            new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))),
                            new CompStatement(
                                    new OpenRFileStatement(new VarExpression("varf")),
                                    new CompStatement(
                                            new VarDeclStatement("varc", new IntType()),
                                            new CompStatement(
                                                    new ReadRFileStatement(new VarExpression("varf"), "varc"),
                                                    new CompStatement(
                                                            new PrintStatement(new VarExpression("varc")),
                                                            new CompStatement(
                                                                    new ReadRFileStatement(new VarExpression("varf"), "varc"),
                                                                    new CompStatement(
                                                                            new PrintStatement(new VarExpression("varc")),
                                                                            new CloseRFileStatement(new VarExpression("varf"))))))))));

    private final IStatement ex5 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
            new CompStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                    new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                            new CompStatement(new HeapAllocationStatement("a", new VarExpression("v")),
                                    new CompStatement(new PrintStatement(new VarExpression("v")), new PrintStatement(new VarExpression("a")))))));

    private final IStatement ex6 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
            new CompStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                    new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                            new CompStatement(new HeapAllocationStatement("a", new VarExpression("v")),
                                    new CompStatement(new PrintStatement(new HeapReadingExpression(new VarExpression("v"))),
                                            new PrintStatement(new ArithmeticExpression(
                                                    new ValueExpression(new IntValue(5)),
                                                    new HeapReadingExpression(new HeapReadingExpression(new VarExpression("a"))),
                                                    ArithmeticOperation.PLUS)))))));

    private final IStatement ex7 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
            new CompStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                    new CompStatement(new PrintStatement(new HeapReadingExpression(new VarExpression("v"))), new CompStatement(
                            new HeapWritingStatement("v", new ValueExpression(new IntValue(30))),
                            new PrintStatement(new ArithmeticExpression(
                                    new ValueExpression(new IntValue(5)),
                                    new HeapReadingExpression(new VarExpression("v")),
                                    ArithmeticOperation.PLUS))))));

    private final IStatement ex8 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
            new CompStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                    new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                            new CompStatement(new HeapAllocationStatement("a", new VarExpression("v")),
                                    new CompStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                            new PrintStatement(new HeapReadingExpression(new HeapReadingExpression(new VarExpression("a")))))))));

    private final IStatement ex9 = new CompStatement(new VarDeclStatement("v", new IntType()),
            new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(4))),
                    new CompStatement(new WhileStatement(new RelationalExpression(new VarExpression("v"),
                            new ValueExpression(new IntValue(0)), ">"),
                            new CompStatement(new PrintStatement(new VarExpression("v")),
                                    new AssignStatement("v", new ArithmeticExpression(new VarExpression("v"), new ValueExpression(new IntValue(1)), ArithmeticOperation.MINUS)))),
                            new PrintStatement(new VarExpression("v")))));

    private final IStatement ex10 = new CompStatement(new VarDeclStatement("v", new IntType()),
            new CompStatement(new VarDeclStatement("a", new RefType(new IntType())),
                    new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(10))),
                            new CompStatement(new HeapAllocationStatement("a", new ValueExpression(new IntValue(22))),
                                    new CompStatement(new ForkStatement(new CompStatement(new HeapWritingStatement("a", new ValueExpression(new IntValue(30))),
                                            new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                    new CompStatement(new PrintStatement(new VarExpression("v")), new PrintStatement(new HeapReadingExpression(new VarExpression("a"))))))),
                                            new CompStatement(new PrintStatement(new VarExpression("v")), new PrintStatement(new HeapReadingExpression(new VarExpression("a")))))))));


    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("Choose an example");
            ListView<String> statements = new ListView<>();
            statements.getItems().add("1. " + ex1);
            statements.getItems().add("2. " + ex2);
            statements.getItems().add("3. " + ex3);
            statements.getItems().add("4. " + ex4);
            statements.getItems().add("5. " + ex5);
            statements.getItems().add("6. " + ex6);
            statements.getItems().add("7. " + ex7);
            statements.getItems().add("8. " + ex8);
            statements.getItems().add("9. " + ex9);
            statements.getItems().add("10. " + ex10);
            statements.setPrefSize(800, 600);

            Button selectStatementButton = new Button("Run example");
            selectStatementButton.setOnAction(e -> {
                try {
                    showSecondWindow(primaryStage, statements.getSelectionModel().getSelectedIndex() + 1);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            GridPane pane = new GridPane();
            pane.setAlignment(Pos.TOP_CENTER);
            pane.add(statements, 1, 0);
            pane.add(selectStatementButton, 1, 1);
            GridPane.setHalignment(selectStatementButton, HPos.CENTER);
            GridPane.setValignment(selectStatementButton, VPos.CENTER);

            Scene mainScene = new Scene(pane, 800, 600);
            primaryStage.setScene(mainScene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showSecondWindow(Stage primaryStage, int index) {
        IStatement statement;
        switch (index) {
            case 0:
                System.out.println("Invalid index");
            case 1:
                statement = ex1;
                break;
            case 2:
                statement = ex2;
                break;
            case 3:
                statement = ex3;
                break;
            case 4:
                statement = ex4;
                break;
            case 5:
                statement = ex5;
                break;
            case 6:
                statement = ex6;
                break;
            case 7:
                statement = ex7;
                break;
            case 8:
                statement = ex8;
                break;
            case 9:
                statement = ex9;
                break;
            case 10:
                statement = ex10;
                break;
            default:
                statement = null;
                Alert alert = new Alert(Alert.AlertType.ERROR, "There was no program selected!", ButtonType.OK);
                alert.showAndWait();
        }

        // type checking
        IMap<String, IType> check = new MyMap<>();
        try {
            statement.typeCheck(check);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }


        ProgramState state = new ProgramState(statement);
        IRepository repository = new Repository(state, "logFile" + index + "" + ".out");
        repository.addState(state);
        Controller controller = new Controller(repository);
        GridPane windowGrid = new GridPane();
        windowGrid.setPadding(new Insets(10, 10, 10, 10));

        // PROGRAM STATES COUNT
        TextField programStatesCount = new TextField(repository.getProgramStatesList().size() + ""); // the number of PrgStates as a Text Field
        programStatesCount.setEditable(false);
        Label countLabel = new Label("Program States count");
        countLabel.setFont(new Font("Verdana", 18));
        countLabel.setStyle("-fx-font-weight: bold");
        VBox count = new VBox();
        count.getChildren().addAll(countLabel, programStatesCount);
        count.setSpacing(5);
        count.setAlignment(Pos.CENTER);
        windowGrid.add(count, 0,2);

        // HEAP TABLE
        Label heapLabel = new Label("Heap Table");
        heapLabel.setFont(new Font("Verdana", 18));
        heapLabel.setStyle("-fx-font-weight: bold");
        VBox heap = new VBox();
        TableView<Map.Entry<String,String>> heapTable = new TableView<>();
        heapTable.setEditable(true);
        TableColumn<Map.Entry<String,String>,String> addressCol = new TableColumn<>("Address");
        TableColumn<Map.Entry<String,String>,String> valueCol = new TableColumn<>("Value");
        addressCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        valueCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue()));
        heapTable.getColumns().addAll(addressCol, valueCol);
        heap.getChildren().addAll(heapLabel, heapTable);
        heap.setSpacing(5);
        heap.setAlignment(Pos.CENTER);
        windowGrid.add(heap, 1, 1);

        // OUTPUT
        Label outputLabel = new Label("Output");
        outputLabel.setFont(new Font("Verdana", 18));
        outputLabel.setStyle("-fx-font-weight: bold");
        VBox out = new VBox();
        ListView<String> outList = new ListView<>();
        out.getChildren().addAll(outputLabel, outList);
        out.setSpacing(5);
        out.setAlignment(Pos.CENTER);
        windowGrid.add(out, 2, 1);

        // FILE TABLE
        Label fileTableLabel = new Label("File Table");
        fileTableLabel.setFont(new Font("Verdana", 18));
        fileTableLabel.setStyle("-fx-font-weight: bold");
        VBox fileTable = new VBox();
        ListView fileTableList = new ListView();
        fileTable.getChildren().addAll(fileTableLabel, fileTableList);
        fileTable.setSpacing(5);
        fileTable.setAlignment(Pos.CENTER);
        windowGrid.add(fileTable, 0, 1);

        // SYMBOL TABLE
        Label symbolTableLabel = new Label("Symbol Table");
        symbolTableLabel.setFont(new Font("Verdana", 18));
        symbolTableLabel.setStyle("-fx-font-weight: bold");
        TableView<Map.Entry<String,String>> symTable = new TableView<>();
        symTable.setEditable(true);
        TableColumn<Map.Entry<String, String>, String> varName = new TableColumn<>("Variable Name");
        TableColumn<Map.Entry<String, String>, String> varValue = new TableColumn<>("Variable Value");
        varName.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        varValue.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue()));
        symTable.getColumns().addAll(varName, varValue);
        VBox sTable = new VBox();
        sTable.getChildren().addAll(symbolTableLabel, symTable);
        sTable.setSpacing(5);
        sTable.setAlignment(Pos.CENTER);
        windowGrid.add(sTable, 1, 0);

        // EXECUTION STACK
        Label stackLabel = new Label("Execution Stack");
        stackLabel.setFont(new Font("Verdana", 18));
        stackLabel.setStyle("-fx-font-weight: bold");
        ListView<String> exeStackList = new ListView<>();
        VBox exeStack = new VBox();
        exeStack.getChildren().addAll(stackLabel, exeStackList);
        exeStack.setSpacing(5);
        exeStack.setAlignment(Pos.CENTER);
        windowGrid.add(exeStack, 0, 0);

        // PROGRAM STATES IDENTIFIERS
        Label identifiersLabel = new Label("Program States Identifiers");
        identifiersLabel.setFont(new Font("Verdana", 18));
        identifiersLabel.setStyle("-fx-font-weight: bold");
        ListView<ProgramState> prgStateIdentifiersList = new ListView<>();
        prgStateIdentifiersList.getItems().add(state);
        prgStateIdentifiersList.setCellFactory(TextFieldListCell.forListView(new StringConverter<>() {
            @Override
            public String toString(ProgramState programState) {
                return Integer.toString(programState.getIdThread());
            }

            @Override
            public ProgramState fromString(String s) {
                return null;
            }
        }));
        VBox prgStateIdentifiers = new VBox();
        prgStateIdentifiers.getChildren().addAll(identifiersLabel, prgStateIdentifiersList);
        prgStateIdentifiers.setSpacing(5);
        prgStateIdentifiers.setAlignment(Pos.CENTER);
        windowGrid.add(prgStateIdentifiers, 2, 0);

        prgStateIdentifiersList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        prgStateIdentifiersList.getSelectionModel().selectedItemProperty().addListener((a, b, stateNew)-> {
            if (stateNew != null)
                update(stateNew, symTable, exeStackList);
        });

        Button oneStep = new Button("Run One Step");
        oneStep.setAlignment(Pos.CENTER);

        oneStep.setOnAction(e-> {
            try {
                List<ProgramState> states = controller.getRepository().getProgramStatesList();
                controller.oneStepForAllPrograms(states);
                //System.out.println(state);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            exeStackList.getItems().clear();
            Stack<IStatement> executionStackNew = state.getExecutionStack().cloneStack();
            while(!executionStackNew.isEmpty()) {
                exeStackList.getItems().add(executionStackNew.pop().toString());
            }

            outList.getItems().clear();
            IList<IValue> outTemp = state.getOut();
            for(int i = 0; i < outTemp.size(); i++)
                outList.getItems().add(outTemp.get(i).toString());

            prgStateIdentifiersList.getItems().clear();
            for(ProgramState p : repository.getProgramStatesList())
                prgStateIdentifiersList.getItems().add(p);

            symTable.getItems().clear();
            IMap<String, IValue> symbolTableTemp= state.getSymbolTable();
            List<Map.Entry<String,String>> symbolTableList = new ArrayList<>();
            for(Map.Entry<String, IValue> entry : symbolTableTemp.getAll().entrySet()){
                Map.Entry<String, String> el = new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue().toString());
                symbolTableList.add(el);
            }
            symTable.setItems(FXCollections.observableList(symbolTableList));
            symTable.refresh();

            heapTable.getItems().clear();
            IHeap<IValue> heapTemp = state.getHeap();
            List<Map.Entry<String, String>> heapTableList = new ArrayList<>();
            for(Map.Entry<Integer, IValue> elem : heapTemp.getContent().entrySet()){
                Map.Entry<String, String> el = new AbstractMap.SimpleEntry<>(elem.getKey().toString(), elem.getValue().toString());
                heapTableList.add(el);
            }
            heapTable.setItems(FXCollections.observableList(heapTableList));
            heapTable.refresh();

            programStatesCount.setText(repository.getProgramStatesList().size() + "");
        });

        if (repository.getProgramStatesList().isEmpty()) {
            controller.shutDownExecutor();
        }

        Button exit = new Button("Exit");
        exit.setAlignment(Pos.CENTER);
        windowGrid.add(oneStep, 2, 2);

        Scene newScene = new Scene(windowGrid, 800, 800);
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.setTitle("Example " + index);
        primaryStage.hide();
        newStage.show();
    }

    void update(ProgramState state, TableView symbolTable, ListView executionStack){

        symbolTable.getItems().clear();
        executionStack.getItems().clear();

        executionStack.getItems().clear();
        Stack<IStatement> newStack = state.getExecutionStack().cloneStack();
        while (!newStack.isEmpty()) {
            executionStack.getItems().add(newStack.pop().toString());
        }
        symbolTable.getItems().clear();
        IMap<String, IValue> symbolTableNew = state.getSymbolTable();
        List<Map.Entry<String,String>> symbolTableList = new ArrayList<>();
        for(Map.Entry<String, IValue> elem: symbolTableNew.getAll().entrySet()){
            Map.Entry<String, String> el = new AbstractMap.SimpleEntry<>(elem.getKey(), elem.getValue().toString());
            symbolTableList.add(el);
        }
        symbolTable.setItems(FXCollections.observableList(symbolTableList));
        symbolTable.refresh();
    }

    public static void main(String[] args) {
        launch();
    }
}
