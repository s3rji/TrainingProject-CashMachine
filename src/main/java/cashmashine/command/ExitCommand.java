package cashmashine.command;

import cashmashine.CashMachine;
import cashmashine.ConsoleHelper;
import cashmashine.exception.InterruptOperationException;

import java.util.ResourceBundle;

class ExitCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "exit_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("exit.question.y.n"));
        String select = ConsoleHelper.readString();

        if (select.equals("y"))
            ConsoleHelper.writeMessage(res.getString("thank.message"));
    }
}
