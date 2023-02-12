package de.eazypaul.hglaborparty;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import de.eazypaul.hglaborparty.listener.DisconnectListener;
import de.eazypaul.hglaborparty.listener.ConnectionListener;
import de.eazypaul.hglaborparty.party.PartyManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * @author eazypaulCode
 * @since 28.01.2023
 */
@Plugin(id = "hglaborparty", name = "HGLaborParties", version = "1.0-SNAPSHOT", url = "https://eazypaul.de/", description = "Party System for HGLabor", authors = {"eazypaulCode"})
public class PartyPlugin {

    @NotNull
    private final ProxyServer proxyServer;

    @NotNull
    private final Logger logger;

    private PartyManager partyManager;

    @Contract(pure = true)
    @Inject
    public PartyPlugin(@NotNull ProxyServer proxyServer, @NotNull Logger logger) {
        this.proxyServer = proxyServer;
        this.logger = logger;
        logger.info("Plugin has been registered successfully!");
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void onProxyInitialization(ProxyInitializeEvent event) {
        this.partyManager = new PartyManager(this);
        proxyServer.getEventManager().register(this, new DisconnectListener(this));
        proxyServer.getEventManager().register(this, new ConnectionListener(this));
    }

    @SuppressWarnings("unused")
    public @NotNull Logger getLogger() {
        return logger;
    }

    public @NotNull ProxyServer getProxyServer() {
        return proxyServer;
    }

    @SuppressWarnings("unused")
    public PartyManager getPartyManager() {
        return partyManager;
    }

    public Component getPrefix() {
        return Component.text("[").color(NamedTextColor.DARK_GRAY)
                .append(Component.text("Party").color(NamedTextColor.AQUA)
                        .append(Component.text("]").color(NamedTextColor.DARK_GRAY)
                                .append(Component.space())));
    }
}
