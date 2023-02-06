package de.eazypaul.hglaborparty.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import de.eazypaul.hglaborparty.PartyPlugin;
import org.jetbrains.annotations.Contract;

/**
 * @author eazypaulCode
 * @since 28.01.2023
 */
public record ConnectionListener(PartyPlugin plugin) {

    @Contract(pure = true)
    @Subscribe
    @SuppressWarnings("unused")
    public void onConnection(ServerConnectedEvent event) {

    }

}
