package de.eazypaul.hglaborparty.party;

import com.velocitypowered.api.proxy.Player;
import de.eazypaul.hglaborparty.PartyPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
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