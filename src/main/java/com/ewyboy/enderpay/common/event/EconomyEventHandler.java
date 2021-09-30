package com.ewyboy.enderpay.common.event;

import com.ewyboy.enderpay.common.economy.Account;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class EconomyEventHandler {

    private static long lastTickEvent = 0;


    public void test(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof Player && !event.getEntity().level.isClientSide) {
            Account account = Account.get((Player) event.getEntity());
            account.update();
            long balance = account.getBalance();

        }
    }

    public void test(PlayerEvent.SaveToFile event) {

    }

}