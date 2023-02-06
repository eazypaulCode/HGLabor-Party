package de.eazypaul.hglaborparty.party;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the privacy level of a {@link Party}.
 *
 * @author eazypaulCode
 * @since 28.01.2023
 */
public enum PartyPrivacy {

    /**
     * Only invited players can join this party.
     */
    PRIVATE("Privat", NamedTextColor.RED),

    /**
     * Everyone can join this party.
     */
    PUBLIC("Öffentlich", NamedTextColor.GREEN),

    /**
     * The party will be shown in the lobby. Also Everyone can join this party.
     */
    FEATURED("Featured", NamedTextColor.DARK_PURPLE);

    /*
     * The display name of this privacy level.
     */
    @NotNull
    private final String name;

    /*
     * The display color of this privacy level.
     */
    @NotNull
    private final NamedTextColor color;

    @Contract(pure = true)
    PartyPrivacy(@NotNull String name, @NotNull NamedTextColor color) {
        this.name = name;
        this.color = color;
    }

    /**
     * Returns the display name of this privacy level.
     *
     * @return the display name
     */
    public @NotNull String getName() {
        return name;
    }

    /**
     * Returns the display color of this privacy level.
     *
     * @return the display color
     */
    public @NotNull NamedTextColor getColor() {
        return color;
    }

    /**
     * Returns a text component representing this privacy level.
     *
     * @return the text component
     */
    public @NotNull Component getComponent() {
        return Component.text(name).color(color);
    }
}