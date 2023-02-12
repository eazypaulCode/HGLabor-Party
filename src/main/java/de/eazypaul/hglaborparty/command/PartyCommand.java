package de.eazypaul.hglaborparty.command;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import de.eazypaul.hglaborparty.PartyPlugin;
import de.eazypaul.hglaborparty.party.Party;
import de.eazypaul.hglaborparty.party.PartyPlayer;
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
                    player.sendMessage(plugin.getPrefix().append(Component.text("Dieser Spieler ist nicht online!").color(NamedTextColor.RED)));
                    break;
                }
                plugin.getPartyManager().invitePlayer(player, target);
                break;
            }
            case "join": {
                if (args.length != 2) return;
                Player target = plugin.getProxyServer().getPlayer(args[1]).orElse(null);
                if (target == null) {
                    player.sendMessage(plugin.getPrefix().append(Component.text("Diese Party existiert nicht!").color(NamedTextColor.RED)));
                    break;
                }
                plugin.getPartyManager().joinParty(player, target);
                break;
            }
            case "list": {
                Party party = plugin.getPartyManager().getPlayersParty(player);
                if (party == null) {
                    player.sendMessage(plugin.getPrefix().append(Component.text("Du bist in keiner Party!").color(NamedTextColor.RED)));
                    return;
                }
                if (party.getMembers().size() == 1) {
                    player.sendMessage(plugin.getPrefix().append(Component.text("Du bist zurzeit alleine in dieser Party.").color(NamedTextColor.GRAY)));
                    return;
                }
                Component allPlayer = Component.empty();
                boolean first = true;
                for (PartyPlayer member : party.getMembers()) {
                    if (first) {
                        allPlayer = allPlayer.append(Component.text(member.getName()).color(NamedTextColor.AQUA));
                        first = false;
                        continue;
                    }
                    allPlayer = allPlayer.append(Component.text(", ").color(NamedTextColor.DARK_GRAY).append(Component.text(member.getName()).color(NamedTextColor.AQUA)));
                }
                player.sendMessage(plugin.getPrefix().append(Component.text("Folgende Spieler sind in dieser Party: ").color(NamedTextColor.GRAY).append(allPlayer)));
            }
        }
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission(permission);
    }
}
