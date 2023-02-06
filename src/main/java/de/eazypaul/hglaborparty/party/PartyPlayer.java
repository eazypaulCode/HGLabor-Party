package de.eazypaul.hglaborparty.party;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

/**
 * Class representing a player in a party.
 *
 * @author eazypaulCode
 * @since 28.01.2023
 */
public class PartyPlayer {

    /**
     * The UUID of the Minecraft Player.
     */
    private final @NotNull UUID uuid;

    /**
     * The name of the Minecraft Player.
     */
    private final @NotNull String name;

    /**
     * If the player is online or not.
     */
    private boolean online;

    /**
     * The Role Enum of the Minecraft Player.
     */
    private @NotNull PartyRole role;

    /**
     * Constructs a new instance of the PartyPlayer class.
     *
     * @param uuid The UUID of the Minecraft player.
     * @param name The name of the Minecraft player.
     * @param role The role of the player in the party.
     */
    public PartyPlayer(@NotNull UUID uuid, @NotNull String name, @NotNull PartyRole role) {
        this.uuid = uuid;
        this.name = name;
        this.online = true;
        this.role = role;
    }

    /**
     * Gets the UUID of the Minecraft player.
     *
     * @return The UUID of the Minecraft player.
     */
    public @NotNull UUID getUuid() {
        return uuid;
    }

    /**
     * Gets the name of the Minecraft player.
     *
     * @return The name of the Minecraft player.
     */
    public @NotNull String getName() {
        return name;
    }

    /**
     * Gets a value indicating whether the player is online or not.
     *
     * @return True if the player is online, false otherwise.
     */
    public boolean isOnline() {
        return online;
    }

    /**
     * Gets the role of the player in the party.
     *
     * @return The role of the player in the party.
     */
    public @NotNull PartyRole getRole() {
        return role;
    }

    /**
     * Sets the value indicating whether the player is online or not.
     *
     * @param online The value indicating whether the player is online or not.
     */
    protected void setOnline(boolean online) {
        this.online = online;
    }

    /**
     * Sets the role of the player in the party.
     * Please note you cannot have more than one Owner for each party.
     *
     * @param role The role of the player in the party.
     */
    protected void setRole(@NotNull PartyRole role) {
        this.role = role;
    }

    /**
     * Indicates whether the specified object is equal to this PartyPlayer instance.
     *
     * @param obj The object to compare to this PartyPlayer instance.
     * @return True if the specified object is not null and equal to this PartyPlayer instance, false otherwise.
     */
    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(Object obj) {
        if (Objects.isNull(obj)) return false;
        if (!(obj instanceof PartyPlayer player)) return false;
        return player.getUuid().equals(getUuid());
    }
}