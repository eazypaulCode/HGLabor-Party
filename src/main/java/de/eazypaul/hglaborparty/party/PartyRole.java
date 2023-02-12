package de.eazypaul.hglaborparty.party;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

/**
 * Class that represents the different roles a user can have in a party
 *
 * @author eazypaulCode
 * @since 28.01.2023
 */
public enum PartyRole {

    /**
     * Role for a regular member of the party
     */
    MEMBER("Mitglied", NamedTextColor.GRAY),

    /**
     * Role for a moderator of the party
     */
    MOD("Moderator", NamedTextColor.GOLD),

    /**
     * Role for the owner of the party
     */
    OWNER("Besitzer", NamedTextColor.RED);

    /**
     * The name of the role
     */
    private final String name;

    /**
     * The color associated with the role
     */
    private final NamedTextColor color;

    /**
     * Constructor for the PartyRole enum
     *
     * @param name the name of the role
     * @param color the color associated with the role
     */
    PartyRole(String name, NamedTextColor color) {
        this.name = name;
        this.color = color;
    }

    /**
     * Gets the name of the role
     *
     * @return the name of the role
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the color associated with the role
     *
     * @return the color associated with the role
     */
    public NamedTextColor getColor() {
        return color;
    }

    /**
     * Returns a text component representing this party role.
     *
     * @return the text component
     */
    public @NotNull Component getComponent() {
        return Component.text(name).color(color);
    }
}