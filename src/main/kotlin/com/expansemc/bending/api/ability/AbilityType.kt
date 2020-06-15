package com.expansemc.bending.api.ability

import com.expansemc.bending.api.element.Element
import com.expansemc.bending.api.registry.*
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.TextComponent
import ninja.leaping.configurate.ConfigurationNode
import org.bukkit.NamespacedKey

/**
 * A type of ability.
 */
interface AbilityType : NamedCatalogType {

    /**
     * Gets the [Element] this [AbilityType] is associated with.
     *
     * @return The associated element
     */
    val element: Element

    /**
     * Gets all possible ways of executing the ability.
     *
     * @return All registered ability execution types
     */
    val executionTypes: Collection<AbilityExecutionType>

    /**
     * Gets instructions on how to execute the ability.
     *
     * @return The instructions, if available
     */
    val instructions: String?

    val instructionsComponent: BaseComponent?

    /**
     * Gets the description of what the ability does.
     *
     * @return The description, if available
     */
    val description: String?

    val descriptionComponent: BaseComponent?

    fun show(): TextComponent

    /**
     * Loads an ability configuration from the given [ConfigurationNode].
     *
     * @param node The node used for loading
     * @return The ability configuration, if available
     */
    fun load(node: ConfigurationNode): Ability?

    interface Builder {

        /**
         * Sets the namespaced key to be used for [AbilityType.key].
         *
         * @param key The namespaced key
         * @return This builder, for chaining
         */
        fun key(key: NamespacedKey): Builder

        /**
         * Sets the human readable name to be used for [AbilityType.name].
         *
         * @param name The human readable name
         * @return This builder, for chaining
         */
        fun name(name: String): Builder

        /**
         * Sets the associated element.
         *
         * @param element The associated element
         * @return This builder, for chaining
         */
        fun element(element: Element): Builder

        fun executionTypes(executionTypes: Collection<AbilityExecutionType>): Builder

        fun executionTypes(vararg executionTypes: AbilityExecutionType): Builder

        fun instructions(instructions: String): Builder

        fun description(description: String): Builder

        fun loader(loader: (ConfigurationNode) -> Ability?): Builder

        /**
         * Builds a new [AbilityType], provided that the [key], [name],
         * [element], [executionTypes], and [loader] are set.
         *
         * @return The generated ability type
         */
        fun build(): AbilityType
    }

    companion object {
        /**
         * Creates a new [Builder] to build an [AbilityType].
         *
         * @return The builder
         */
        @JvmStatic
        fun builder(): Builder = FactoryRegistry.instance.create(Builder::class.java)

        /**
         * Attempts to retrieve the [AbilityType] based on
         * the namespaced key given.
         *
         * @param key The namespaced key
         * @return The found ability type, if available
         */
        @JvmStatic
        operator fun get(key: NamespacedKey): AbilityType? = CatalogRegistry.instance[key]

        /**
         * Gets a collection of all available [AbilityType]s.
         *
         * @return A collection of all known ability types
         */
        @JvmStatic
        val all: Collection<AbilityType>
            get() = CatalogRegistry.instance.getAllOf()
    }
}