package com.ewyboy.enderpay.api;

import com.ewyboy.enderpay.api.exception.InsufficientFundsException;
import com.ewyboy.enderpay.api.exception.NoSuchAccountException;
import com.ewyboy.enderpay.common.economy.Account;

import java.util.UUID;

public class EnderPayment {

    public static long getBalance(UUID uuid) throws NoSuchAccountException {
        Account account = Account.get(uuid);

        if (account == null) throw new NoSuchAccountException();

        return account.getBalance();
    }

    public static void addToBalance(UUID uuid, long amount) throws NoSuchAccountException {
        Account account = Account.get(uuid);

        if (account == null) throw new NoSuchAccountException();

        account.addBalance(amount);
    }

    public static void removeFromBalance(UUID uuid, long amount) throws InsufficientFundsException, NoSuchAccountException {
        Account account = Account.get(uuid);

        if (account == null) throw new NoSuchAccountException();
        if (account.getBalance() < amount) throw new InsufficientFundsException();

        account.addBalance(-amount);
    }

}
