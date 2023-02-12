package de.eazypaul.hglaborparty.command;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import de.eazypaul.hglaborparty.PartyPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author eazypaulCode
 * @since 28.01.2023
 */
public class PartyCommand implements SimpleCommand {

    public static final String name = "party";
    public static final String permission = "parties.command.party";
    private final PartyPlugin plugin;

    public PartyCommand(PartyPlugin plugin) {
        this.plugin = plugin;
        this.plugin.getProxyServer().getCommandManager().register(name, new PartyCommand(plugin));
    }

    @Override
    public void execute(Invocation invocation) {
        plugin.getProxyServer().sendMessage(Component.text(name).append(Component.space().append(Component.text("executed!"))));
        String[] args = invocation.arguments();
        if (!(invocation.source() instanceof Player player)) return;
        if (args.length == 0) return;

        switch (args[0]) {
            case "create":
                plugin.getPartyManager().createParty(player);
                break;
            case "leave":
                plugin.getPartyManager().leaveParty(player);
                break;
            case "invite": {
                if (args.length != 2) return;
                Player target = plugin.getProxyServer().getPlayer(args[1]).orElse(null);
                if (target == null) {
                    player.sendMessage(plugin.getPrefix().append(Component.text("Dieser Spieler ist nicht online!").color(NamedTextColor.GRAY)));
                    break;
                }
                plugin.getPartyManager().invitePlayer(player, target);
                break;
            }
            case "join": {
                if (args.length != 2) return;
                Player target = plugin.getProxyServer().getPlayer(args[1]).orElse(null);
                if (target == null) {
                    player.sendMessage(plugin.getPrefix().append(Component.text("Diese Party existiert nicht!").color(NamedTextColor.GRAY)));
                    break;
                }
                plugin.getPartyManager().joinParty(player, target);
                break;
            }
        }
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission(permission);
    }
}
