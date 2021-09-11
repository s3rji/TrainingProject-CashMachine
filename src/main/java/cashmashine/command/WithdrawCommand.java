package cashmashine.command;

import cashmashine.CashMachine;
import cashmashine.ConsoleHelper;
import cashmashine.CurrencyManipulator;
import cashmashine.CurrencyManipulatorFactory;
import cashmashine.exception.InterruptOperationException;
import cashmashine.exception.NotEnoughMoneyException;

import java.util.Comparator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

class WithdrawCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "withdraw_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));

        String currencyCode = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.
                getManipulatorByCurrencyCode(currencyCode);

        while (true) {
            ConsoleHelper.writeMessage(res.getString("specify.amount"));
            int expectedAmount;
            try {
                expectedAmount = Integer.parseInt(ConsoleHelper.readString());
            } catch (NumberFormatException e) {
                ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                continue;
            }

            if (currencyManipulator.isAmountAvailable(expectedAmount)) {
                try {
                    Map<Integer, Integer> map = new TreeMap<>(Comparator.reverseOrder());
                    map.putAll(currencyManipulator.withdrawAmount(expectedAmount));
                    map.forEach((key, value) -> System.out.println("\t" + key + " - " + value));

                    ConsoleHelper.writeMessage(String.format(res.getString("success.format"), expectedAmount, currencyCode));
                    break;
                } catch (NotEnoughMoneyException e) {
                    ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
                }

            } else {
                ConsoleHelper.writeMessage(res.getString("not.enough.money"));
            }
        }

    }
}
