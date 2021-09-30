package com.ewyboy.enderpay.common.economy;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class Account {

    private static HashMap<UUID, Account> accountMap = new HashMap<>();
    private static File location;
    private boolean changed;

    private UUID uuid;
    private long balance;
    private long lastLogin;

    private Account(UUID uuid) {
        this.uuid = uuid;
        this.balance = 1000;
        this.changed = true;
    }

    public static Account get(Player player) {
        return get(player.getUUID());
    }

    @Nullable
    public static Account get(UUID uuid) {
        Account account = accountMap.get(uuid);

        if (account != null) return account;

        if (location == null) return null;

        location.mkdir();

        account = new Account(uuid);
        accountMap.put(uuid, account);

        File file = account.getFile();
        if (!file.exists()) return account;

        try {
            account.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return account;
    }

    private File getFile() {
        return new File(location, uuid + ".json");
    }

    private void read() throws IOException {
        read(getFile());
    }

    public void readIfChanged() throws IOException {
        if (changed) read();
    }

    private void read(File file) throws IOException {
        changed = false;

        JsonParser jsonParser = new JsonParser();

        try {
            Object obj = jsonParser.parse(new FileReader(file));

            JsonObject jsonObject = (JsonObject) obj;

            balance = jsonObject.get("balance").getAsLong();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void write() throws IOException {
        write(getFile());
    }

    public void writeIfChanged() throws IOException {
        if (changed) write();
    }

    private void write(File file) throws IOException {
        JsonObject obj = new JsonObject();

        obj.addProperty("balance", balance);

        try(FileWriter writer = new FileWriter(location)) {
            String str = obj.toString();
            writer.write(str);
        }

        changed = false;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long value) {
        balance = value;
        changed = true;
    }

    public void addBalance(long value) {
        setBalance(balance + value);
    }

    public void update() {

    }
}
