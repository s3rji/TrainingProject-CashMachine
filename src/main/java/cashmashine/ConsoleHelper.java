package cashmashine;

import cashmashine.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

public class ConsoleHelper {
    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));
    private static ResourceBundle res = ResourceBundle.getBundle(CashMachine.class.getPackage().getName() +
            ".resources.common_en");

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws InterruptOperationException {
        String text = null;
        try {
            text = bis.readLine();
            if ("exit".equalsIgnoreCase(text)) {
                throw new InterruptOperationException();
            }
        } catch (IOException ignor) {

        }
        return text;
    }

    public static String askCurrencyCode() throws InterruptOperationException {
        writeMessage(res.getString("choose.currency.code"));
        String currencyCode = readString();
        while (currencyCode.length() != 3) {
            writeMessage(res.getString("invalid.data"));
            currencyCode = readString();
        }
        return currencyCode.toUpperCase();
    }

    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException {
        while (true) {
            writeMessage(String.format(res.getString("choose.denomination.and.count.format"), currencyCode));
            String input = readString();
            String[] result = input.split(" ");

            try {
                if (result.length != 2 || Integer.parseInt(result[0]) < 0 || Integer.parseInt(result[1]) < 0) {
                    writeMessage(res.getString("invalid.data"));
                    continue;
                }

            } catch (NumberFormatException e) {
                writeMessage(res.getString("invalid.data"));
                continue;
            }

            return result;
        }
    }

    public static Operation askOperation() throws InterruptOperationException {
        while (true) {
            writeMessage(res.getString("choose.operation"));
            writeMessage("\t 1 - " + res.getString("operation.INFO"));
            writeMessage("\t 2 - " + res.getString("operation.DEPOSIT"));
            writeMessage("\t 3 - " + res.getString("operation.WITHDRAW"));
            writeMessage("\t 4 - " + res.getString("operation.EXIT"));
            try {
                int choice = Integer.parseInt(readString());

                return Operation.getAllowableOperationByOrdinal(choice);
            } catch (IllegalArgumentException ignored) {
                writeMessage(res.getString("invalid.data"));
            }
        }
    }

    public static void printExitMessage() {
        writeMessage(res.getString("the.end"));
    }
}
