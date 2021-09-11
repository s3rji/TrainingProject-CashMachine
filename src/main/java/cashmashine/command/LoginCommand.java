package cashmashine.command;

import cashmashine.CashMachine;
import cashmashine.ConsoleHelper;
import cashmashine.exception.InterruptOperationException;

import java.util.ResourceBundle;

public class LoginCommand implements Command {
    private ResourceBundle validCreditCards = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "verifiedCards");
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "login_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));
        boolean isValidSuccess = false;

        while (!isValidSuccess) {
            ConsoleHelper.writeMessage(res.getString("specify.data"));
            String userCard = ConsoleHelper.readString();
            String userPin = ConsoleHelper.readString();

            if (!userCard.matches("\\d{12}") || !userPin.matches("\\d{4}")) {
                ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
                continue;
            }

            if (validCreditCards.containsKey(userCard) && validCreditCards.getString(userCard).equals(userPin)) {
                ConsoleHelper.writeMessage(String.format(res.getString("success.format"), userCard));
                isValidSuccess = true;
            } else {
                ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format"), userCard));
                ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
            }
        }

    }
}
