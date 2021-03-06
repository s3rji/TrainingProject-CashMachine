package cashmashine;

import cashmashine.command.CommandExecutor;
import cashmashine.exception.InterruptOperationException;

import java.util.Locale;


public class CashMachine {
    public static final String RESOURCE_PATH = CashMachine.class.getPackage().getName() + ".resources.";

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);

        try {
            CommandExecutor.execute(Operation.LOGIN);

            Operation operation;
            do {
                operation = ConsoleHelper.askOperation();
                CommandExecutor.execute(operation);

            } while (operation != Operation.EXIT);

        } catch (InterruptOperationException ignored) {
            ConsoleHelper.printExitMessage();
        }
    }
}
