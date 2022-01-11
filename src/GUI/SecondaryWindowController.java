package GUI;

import Controller.Controller;
import Model.Expression.*;
import Model.ProgramState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.IRepository;
import Repository.Repository;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SecondaryWindowController implements Initializable {
    private List<IStatement> programStatements;
    private MainWindowController mainWindowController;

    @FXML
    private ListView<String> programListView;

    @FXML
    private Button executeButton;

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    private void buildProgramStatements() {

        IStatement ex1 =
                new CompStatement(
                        new VarDeclStatement("v", new IntType()),
                        new CompStatement(
                                new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                new PrintStatement(new VarExpression("v"))));
        IStatement ex2 =
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
        IStatement ex3 =
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
        IStatement ex4 =
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

        IStatement ex5 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(new HeapAllocationStatement("a", new VarExpression("v")),
                                        new CompStatement(new PrintStatement(new VarExpression("v")), new PrintStatement(new VarExpression("a")))))));

        IStatement ex6 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(new HeapAllocationStatement("a", new VarExpression("v")),
                                        new CompStatement(new PrintStatement(new HeapReadingExpression(new VarExpression("v"))),
                                                new PrintStatement(new ArithmeticExpression(
                                                        new ValueExpression(new IntValue(5)),
                                                        new HeapReadingExpression(new HeapReadingExpression(new VarExpression("a"))),
                                                        ArithmeticOperation.PLUS)))))));

        IStatement ex7 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new PrintStatement(new HeapReadingExpression(new VarExpression("v"))), new CompStatement(
                                new HeapWritingStatement("v", new ValueExpression(new IntValue(30))),
                                new PrintStatement(new ArithmeticExpression(
                                        new ValueExpression(new IntValue(5)),
                                        new HeapReadingExpression(new VarExpression("v")),
                                        ArithmeticOperation.PLUS))))));

        IStatement ex8 = new CompStatement(new VarDeclStatement("v", new RefType(new IntType())),
                new CompStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompStatement(new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompStatement(new HeapAllocationStatement("a", new VarExpression("v")),
                                        new CompStatement(new HeapAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new HeapReadingExpression(new HeapReadingExpression(new VarExpression("a")))))))));

        IStatement ex9 = new CompStatement(new VarDeclStatement("v", new IntType()),
                new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(4))),
                        new CompStatement(new WhileStatement(new RelationalExpression(new VarExpression("v"),
                                new ValueExpression(new IntValue(0)), ">"),
                                new CompStatement(new PrintStatement(new VarExpression("v")),
                                        new AssignStatement("v", new ArithmeticExpression(new VarExpression("v"), new ValueExpression(new IntValue(1)), ArithmeticOperation.MINUS)))),
                                new PrintStatement(new VarExpression("v")))));

        IStatement ex10 = new CompStatement(new VarDeclStatement("v", new IntType()),
                new CompStatement(new VarDeclStatement("a", new RefType(new IntType())),
                        new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompStatement(new HeapAllocationStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompStatement(new ForkStatement(new CompStatement(new HeapWritingStatement("a", new ValueExpression(new IntValue(30))),
                                                new CompStatement(new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                        new CompStatement(new PrintStatement(new VarExpression("v")), new PrintStatement(new HeapReadingExpression(new VarExpression("a"))))))),
                                                new CompStatement(new PrintStatement(new VarExpression("v")), new PrintStatement(new HeapReadingExpression(new VarExpression("a")))))))));


        programStatements = new ArrayList<>(Arrays.asList(ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9, ex10));}

    private List<String> getStringRepresentations(){
        return programStatements.stream().map(IStatement::toString).collect(Collectors.toList());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        buildProgramStatements();
        programListView.setItems(FXCollections.observableArrayList(getStringRepresentations()));
        executeButton.setOnAction(actionEvent -> {
            int index = programListView.getSelectionModel().getSelectedIndex();
            if (index < 0)
                return;
            ProgramState initialProgramState = new ProgramState(programStatements.get(index));
            IRepository repository = new Repository(initialProgramState,"log" + index + ".txt");
            Controller controller = new Controller(repository);
            mainWindowController.setController(controller);
        });
    }
}
