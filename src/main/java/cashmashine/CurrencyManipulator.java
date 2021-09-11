package cashmashine;

import cashmashine.exception.NotEnoughMoneyException;

import java.util.*;

public class CurrencyManipulator {
    private String currencyCode;
    private Map<Integer, Integer> denominations = new HashMap<>();

    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void addAmount(int denomination, int count) {
        denominations.merge(denomination, count, Integer::sum);
    }

    public int getTotalAmount() {
        return denominations.entrySet().stream().
                mapToInt(key-> key.getKey() * key.getValue()).sum();
    }

    public boolean hasMoney() {
        return !denominations.isEmpty();
    }

    public boolean isAmountAvailable(int expectedAmount) {
        return expectedAmount <= getTotalAmount();
    }

    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {
        Map<Integer, Integer> result = new HashMap<>();

        Set<Integer> banknoteTypes = new TreeSet<>(Comparator.reverseOrder());
        banknoteTypes.addAll(denominations.keySet());

        for (Integer banknote : banknoteTypes) {
            int qtyBanknotes = denominations.get(banknote);

            while (qtyBanknotes > 0) {
                if ((expectedAmount - banknote) >= 0) {
                    expectedAmount -= banknote;
                    qtyBanknotes--;
                } else
                    break;
            }

            if (denominations.get(banknote) > qtyBanknotes) {
                result.put(banknote, denominations.get(banknote) - qtyBanknotes);
            }

            if (qtyBanknotes == 0) {
                denominations.remove(banknote);
            } else {
                denominations.put(banknote, qtyBanknotes);
            }
        }

        if (expectedAmount > 0) throw new NotEnoughMoneyException();

        return result;
    }
}
