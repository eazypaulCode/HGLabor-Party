package de.eazypaul.hglaborparty.command;

import com.velocitypowered.api.command.SimpleCommand;
import de.eazypaul.hglaborparty.PartyPlugin;
import net.kyori.adventure.text.Component;

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
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission(permission);
    }

}
