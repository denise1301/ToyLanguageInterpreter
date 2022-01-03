package View;

import Model.Containers.IMap;
import Model.Containers.MyMap;

import java.util.Scanner;

public class TextMenu {
    private IMap<String, Command> commands;

    public TextMenu() {
        commands = new MyMap<>();
    }
    public void addCommand(Command c) throws Exception {
        commands.add(c.getKey(), c);
    }

    private void printMenu() {
        for(Command command : commands.values()) {
            String line = String.format("%4s : %s", command.getKey(), command.getDescription());
            System.out.println(line);
        }
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            printMenu();
            System.out.print("Input the option: ");
            String key = scanner.nextLine();
            Command com = commands.get(key);
            if (com == null) {
                System.out.println("Invalid option!");
                continue;
            }
            com.execute();
        }
    }
}
