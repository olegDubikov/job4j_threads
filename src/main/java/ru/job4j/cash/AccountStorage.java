package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized Account add(Account account) {
        return accounts.putIfAbsent(account.id(), account);
    }

    public synchronized boolean update(Account account) {
        boolean result = false;
        if (accounts.containsKey(account.id())) {
            accounts.put(account.id(), account);
            result = true;
        }
        return result;
    }

    public synchronized void delete(int id) {
        accounts.remove(id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        if (amount > 0) {
            Account fromAccount = accounts.get(fromId);
            Account toAccount = accounts.get(toId);
            if (fromAccount != null && toAccount != null && fromAccount.amount() >= amount) {
                Account fromAccountNew = new Account(fromId, fromAccount.amount() - amount);
                Account toAccountNew = new Account(toId, toAccount.amount() + amount);
                accounts.put(fromId, fromAccountNew);
                accounts.put(toId, toAccountNew);
                result = true;
            }
        }
        return result;
    }
}
