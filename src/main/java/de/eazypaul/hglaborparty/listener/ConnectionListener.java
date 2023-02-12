package de.eazypaul.hglaborparty.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import de.eazypaul.hglaborparty.PartyPlugin;
import de.eazypaul.hglaborparty.party.Party;
import de.eazypaul.hglaborparty.party.PartyPlayer;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;

import java.util.Optional;

/**
 * @author eazypaulCode
 * @since 28.01.2023
 */
public record ConnectionListener(PartyPlugin plugin) {

    @Contract(pure = true)
    @Subscribe
    @SuppressWarnings("unused")
    public void onConnection(ServerConnectedEvent event) {
        Party party = plugin.getPartyManager().getPlayersParty(event.getPlayer());
        if (party == null) return;
        if (event.getServer().getServerInfo().getName().toLowerCase().startsWith("lobby")) return;
        if (party.getOwner().getUuid().equals(event.getPlayer().getUniqueId())) {
            for (PartyPlayer member : party.getMembers()) {
                if (!member.isOnline()) continue;
                Optional<Player> memberPlayer = plugin.getProxyServer().getPlayer(member.getUuid());
                if (memberPlayer.isEmpty()) continue;
                memberPlayer.get().createConnectionRequest(event.getServer()).connect().whenCompleteAsync((result, throwable) -> {
                    if (throwable != null || !result.isSuccessful()) {
                        memberPlayer.get().sendMessage(Component.text("Du konntest nicht verbunden werden!"));
                    }
                });
            }
        }
    }

}
