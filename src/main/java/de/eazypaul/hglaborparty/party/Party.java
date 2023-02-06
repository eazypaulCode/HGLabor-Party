package de.eazypaul.hglaborparty.party;

import com.velocitypowered.api.proxy.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Represents a Minecraft party.
 *
 * @author eazypaulCode
 * @since 28.01.2023
 */
public class Party {

    /**
     * The unique identifier of the party.
     */
    @NotNull
    private final UUID uuid;

    /**
     * The members of the party.
     */
    @NotNull
    private final List<PartyPlayer> members;

    /**
     * The players that have been invited to the party.
     */
    @NotNull
    private final List<Player> invited;

    /**
     * The privacy setting of the party.
     */
    @NotNull
    private PartyPrivacy privacy;

    /**
     * The maximum number of players allowed in the party.
     */
    private int maxPlayers;

    /**
     * Creates a new party.
     *
     * @param uuid       The unique identifier of the party.
     * @param owner      The owner of the party.
     * @param maxPlayers The maximum number of players allowed in the party.
     */
    public Party(@NotNull UUID uuid, @NotNull Player owner, int maxPlayers) {
        this.uuid = uuid;
        this.members = new ArrayList<>();
        this.members.add(new PartyPlayer(owner.getUniqueId(), owner.getUsername(), PartyRole.OWNER));
        this.invited = new ArrayList<>();
        this.privacy = PartyPrivacy.PRIVATE;
        this.maxPlayers = maxPlayers;
    }

    /**
     * Gets the unique identifier of the party.
     *
     * @return The unique identifier of the party.
     */
    @NotNull
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Gets the owner of the party.
     *
     * @return The owner of the party.
     */
    @Nullable("If the Party has no owner")
    public PartyPlayer getOwner() {
        return getMembers().stream().filter(member -> member.getRole() == PartyRole.OWNER).findFirst().orElse(null);
    }

    /**
     * Gets the members of the party.
     *
     * @return The members of the party.
     */
    @NotNull
    @Unmodifiable
    public List<PartyPlayer> getMembers() {
        return Collections.unmodifiableList(members);
    }

    /**
     * Gets the players that have been invited to the party.
     *
     * @return The players that have been invited to the party.
     */
    @NotNull
    @Unmodifiable
    public List<Player> getInvitedPlayers() {
        return Collections.unmodifiableList(invited);
    }


    /**
     * Adds a player to the list of invited players
     * @param player The player to be invited to the party
     */
    protected void createInvitation(Player player) {
        if (this.invited.contains(player)) return;
        this.invited.add(player);
    }

    /**
     * Removes a player from the list of invited players
     * @param player The player to be removed from the list of invited players
     */
    protected void removeInvitation(Player player) {
        if (!this.invited.contains(player)) return;
        this.invited.remove(player);
    }

    /**
     * Adds a player to the list of party members
     * @param player The player to be added to the list of party members
     */
    protected void addPlayer(PartyPlayer player) {
        if (this.members.contains(player)) return;
        this.members.add(player);
    }

    /**
     * Removes a player from the list of party members
     * @param player The player to be removed from the list of party members
     */
    protected void removePlayer(PartyPlayer player) {
        if (!this.members.contains(player)) return;
        this.members.remove(player);
    }

    /**
     * Gets the privacy status of the party
     * @return The privacy status of the party (public or private)
     */
    public PartyPrivacy getPrivacy() {
        return privacy;
    }

    /**
     * Sets the privacy status of the party
     * @param privacy The privacy status to be set (public or private)
     */
    protected void setPrivacy(PartyPrivacy privacy) {
        this.privacy = privacy;
    }

    /**
     * Gets the maximum number of players that can join the party
     * @return The maximum number of players that can join the party
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * Sets the maximum number of players that can join the party
     * @param maxPlayers The maximum number of players to set
     */
    protected void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
}
