package View;

import Controller.Controller;

public class RunExampleCommand extends Command {
    private Controller controller;

    public RunExampleCommand(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            this.controller.allSteps();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
