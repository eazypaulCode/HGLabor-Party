package de.eazypaul.hglaborparty.party;

import com.velocitypowered.api.proxy.Player;
import de.eazypaul.hglaborparty.PartyPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

import javax.inject.Named;
import javax.naming.Name;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Class to manage a collection of {@link Party} objects.
 *
 * @author eazypaulCode
 * @since 28.01.2023
 */
public class PartyManager {

    private final PartyPlugin plugin;
    private final List<Party> parties;

    /**
     * Creates an instance of PartyManager.
     *
     * @param plugin the PartyPlugin instance
     */
    public PartyManager(@NotNull PartyPlugin plugin) {
        this.plugin = plugin;
        this.parties = new ArrayList<>();
    }

    public Party createParty(Player player) {
        if (getPlayersParty(player) != null) {
            if (getPlayersParty(player).getOwner().getUuid().equals(player.getUniqueId())) {
                player.sendMessage(plugin.getPrefix().append(Component.text("Du besitzt bereits eine Party!").color(NamedTextColor.RED)));
                return null;
            }
            player.sendMessage(plugin.getPrefix().append(Component.text("Du bist bereits in einer Party!").color(NamedTextColor.RED)));
            return null;
        }
        Party party = new Party(UUID.randomUUID(), player, 20);
        this.parties.add(party);
        player.sendMessage(plugin.getPrefix().append(Component.text("Deine Party wurde erfolgreich erstellt!").color(NamedTextColor.GREEN)));
        return party;
    }

    public void leaveParty(Player player) {
        Party party = getPlayersParty(player);
        if (party == null) {
            player.sendMessage(plugin.getPrefix().append(Component.text("Du bist in keiner Party!").color(NamedTextColor.RED)));
            return;
        }
        if (party.getOwner().getUuid().equals(player.getUniqueId())) {
            player.sendMessage(plugin.getPrefix().append(Component.text("Du bist der Besitzer dieser Party! Verwende /party disband.").color(NamedTextColor.RED)));
            return;
        }
        PartyPlayer partyPlayer = party.getMemberByUUID(player.getUniqueId());
        if (partyPlayer == null) {
            player.sendMessage(plugin.getPrefix().append(Component.text("Es ist ein Fehler aufgetreten!").color(NamedTextColor.RED)));
            return;
        }
        party.removePlayer(partyPlayer);
        player.sendMessage(plugin.getPrefix().append(Component.text("Du hast die Party verlassen!").color(NamedTextColor.GRAY)));
        for (PartyPlayer member : party.getMembers()) {
            if (!member.isOnline()) continue;
            Optional<Player> memberPlayer = plugin.getProxyServer().getPlayer(member.getUuid());
            if (memberPlayer.isEmpty()) continue;
            memberPlayer.get().sendMessage(plugin.getPrefix().append(Component.text(player.getUsername()).color(NamedTextColor.AQUA).append(Component.text(" hat die Party verlassen!").color(NamedTextColor.GRAY))));
        }
    }

    public void invitePlayer(Player player, Player target) {
        Party party = getPlayersParty(player);
        if (party.getMemberByUUID(player.getUniqueId()).getRole() == PartyRole.MEMBER) {
            player.sendMessage(plugin.getPrefix().append(Component.text("Du bist dazu nicht berechtigt.").color(NamedTextColor.RED)));
            return;
        }
        if (party.getPrivacy() != PartyPrivacy.PRIVATE) {
            player.sendMessage(plugin.getPrefix().append(Component.text("Diese Party ist öffentlich.").color(NamedTextColor.RED)));
            return;
        }
        if (party.getInvitedPlayers().contains(target)) {
            player.sendMessage(plugin.getPrefix().append(Component.text("Du hast diesen Spieler bereits eingeladen.").color(NamedTextColor.RED)));
            return;
        }
        party.createInvitation(target);
        player.sendMessage(plugin.getPrefix().append(Component.text("Du hast ").color(NamedTextColor.GRAY).append(Component.text(target.getUsername()).color(NamedTextColor.AQUA).append(Component.text(" zur Party eingeladen!").color(NamedTextColor.GRAY)))));
        target.sendMessage(plugin.getPrefix().append(Component.text("Du wurdest zur Party von").color(NamedTextColor.GRAY).append(Component.text(player.getUsername()).color(NamedTextColor.AQUA).append(Component.text(" eingeladen!").color(NamedTextColor.GRAY)))));
    }

    public void joinParty(Player player, Player target) {
        Party old = getPlayersParty(player);
        if (old != null) {
            player.sendMessage(plugin.getPrefix().append(Component.text("Du bist bereits in einer Party!").color(NamedTextColor.RED)));
            return;
        }
        Party party = getPlayersParty(target);
        if (party == null) {
            player.sendMessage(plugin.getPrefix().append(Component.text("Dieser Spieler hat keine Party!").color(NamedTextColor.RED)));
            return;
        }
        if (!party.getOwner().getUuid().equals(target.getUniqueId())) {
            player.sendMessage(plugin.getPrefix().append(Component.text("Dieser Spieler hat keine Party!").color(NamedTextColor.RED)));
            return;
        }
        if (party.getMembers().stream().anyMatch(member -> member.getUuid().equals(player.getUniqueId()))) {
            player.sendMessage(plugin.getPrefix().append(Component.text("Du bist bereit in dieser Party!").color(NamedTextColor.RED)));
            return;
        }
        if (party.getPrivacy() == PartyPrivacy.PRIVATE && !party.getInvitedPlayers().contains(player)) {
            player.sendMessage(plugin.getPrefix().append(Component.text("Du darfst dieser Party nicht beitreten!").color(NamedTextColor.RED)));
            return;
        }
        player.sendMessage(plugin.getPrefix().append(Component.text("Du bist der Party beigetreten!").color(NamedTextColor.GRAY)));
        for (PartyPlayer member : party.getMembers()) {
            if (!member.isOnline()) continue;
            Optional<Player> memberPlayer = plugin.getProxyServer().getPlayer(member.getUuid());
            if (memberPlayer.isEmpty()) continue;
            memberPlayer.get().sendMessage(plugin.getPrefix().append(Component.text(player.getUsername()).color(NamedTextColor.AQUA).append(Component.text(" ist der Party beigetreten!").color(NamedTextColor.GRAY))));
        }
        party.addPlayer(new PartyPlayer(player.getUniqueId(), player.getUsername(), PartyRole.MEMBER));
    }

    /**
     * Returns a {@link Party} object with the specified UUID.
     *
     * @param uuid the UUID of the desired party
     * @return the party with the specified UUID, or null if it does not exist
     */
    public Party getPartyByUUID(@NotNull UUID uuid) {
        return parties.stream().filter(party -> party.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    /**
     * Returns the {@link Party} object that the specified player is a member of.
     *
     * @param player the player to search for
     * @return the party that the player is a member of, or null if they are not in a party
     */
    public Party getPlayersParty(@NotNull Player player) {
        return parties.stream().filter(party -> party.getMembers().stream().map(PartyPlayer::getUuid).anyMatch(uuid -> uuid.equals(player.getUniqueId()))).findFirst().orElse(null);
    }
}