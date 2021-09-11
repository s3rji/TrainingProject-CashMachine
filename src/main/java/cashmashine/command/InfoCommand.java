package cashmashine.command;

import cashmashine.CashMachine;
import cashmashine.ConsoleHelper;
import cashmashine.CurrencyManipulator;
import cashmashine.CurrencyManipulatorFactory;

import java.util.Collection;
import java.util.ResourceBundle;

class InfoCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "info_en");

    @Override
    public void execute() {
        ConsoleHelper.writeMessage(res.getString("before"));

        Collection<CurrencyManipulator> manipulators = CurrencyManipulatorFactory.getAllCurrencyManipulators();

        boolean isNoMoney = true;
        for (CurrencyManipulator cm : manipulators) {
            if (cm.hasMoney()) {
                ConsoleHelper.writeMessage(cm.getCurrencyCode() + " - " + cm.getTotalAmount());
                isNoMoney = false;
            }
        }

        if (isNoMoney) ConsoleHelper.writeMessage(res.getString("no.money"));
    }
}
