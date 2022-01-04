package View;

import Controller.Controller;
import Model.Containers.IList;
import Model.Containers.IMap;
import Model.Containers.MyList;
import Model.Containers.MyMap;
import Model.Expression.*;
import Model.ProgramState;
import Model.Statement.*;
import Model.Type.*;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.Repository;

import java.util.List;

public class Interpreter {
    public static void main(String[] args) throws Exception {
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

        try {
            IList<IStatement> statements = new MyList<IStatement>();
            statements.add(ex1);
            statements.add(ex2);
            statements.add(ex3);
            statements.add(ex4);
            statements.add(ex5);
            statements.add(ex6);
            statements.add(ex7);
            statements.add(ex8);
            statements.add(ex9);
            statements.add(ex10);
            for (int i = 0; i < statements.size(); i++) {
                IMap<String, IType> typeEnv = new MyMap<>();
                statements.get(i).typeCheck(typeEnv);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        System.out.println("All type checks have passed successfully!");


        ProgramState programState1 = new ProgramState(ex1);
        ProgramState programState2 = new ProgramState(ex2);
        ProgramState programState3 = new ProgramState(ex3);
        ProgramState programState4 = new ProgramState(ex4);
        ProgramState programState5 = new ProgramState(ex5);
        ProgramState programState6 = new ProgramState(ex6);
        ProgramState programState7 = new ProgramState(ex7);
        ProgramState programState8 = new ProgramState(ex8);
        ProgramState programState9 = new ProgramState(ex9);
        ProgramState programState10 = new ProgramState(ex10);

        Repository repository1 = new Repository(programState1, "logFile1.out");
        Controller controller1 = new Controller(repository1);
        Repository repository2 = new Repository(programState2, "logFile2.out");
        Controller controller2 = new Controller(repository2);
        Repository repository3 = new Repository(programState3, "logFile3.out");
        Controller controller3 = new Controller(repository3);
        Repository repository4 = new Repository(programState4, "logFile4.out");
        Controller controller4 = new Controller(repository4);
        Repository repository5 = new Repository(programState5, "logFile5.out");
        Controller controller5 = new Controller(repository5);
        Repository repository6 = new Repository(programState6, "logFile6.out");
        Controller controller6 = new Controller(repository6);
        Repository repository7 = new Repository(programState7, "logFile7.out");
        Controller controller7 = new Controller(repository7);
        Repository repository8 = new Repository(programState8, "logFile8.out");
        Controller controller8 = new Controller(repository8);
        Repository repository9 = new Repository(programState9, "logFile9.out");
        Controller controller9 = new Controller(repository9);
        Repository repository10 = new Repository(programState10, "logFile10.out");
        Controller controller10 = new Controller(repository10);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExampleCommand("1", ex1.toString(), controller1));
        menu.addCommand(new RunExampleCommand("2", ex2.toString(), controller2));
        menu.addCommand(new RunExampleCommand("3", ex3.toString(), controller3));
        menu.addCommand(new RunExampleCommand("4", ex4.toString(), controller4));
        menu.addCommand(new RunExampleCommand("5", ex5.toString(), controller5));
        menu.addCommand(new RunExampleCommand("6", ex6.toString(), controller6));
        menu.addCommand(new RunExampleCommand("7", ex7.toString(), controller7));
        menu.addCommand(new RunExampleCommand("8", ex8.toString(), controller8));
        menu.addCommand(new RunExampleCommand("9", ex9.toString(), controller9));
        menu.addCommand(new RunExampleCommand("10", ex10.toString(), controller10));

        menu.show();
    }
}
