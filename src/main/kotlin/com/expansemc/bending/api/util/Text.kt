package com.expansemc.bending.api.util

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.command.CommandSender

fun CommandSender.sendMessage(component: BaseComponent) {
    this.spigot().sendMessage(component)
}

fun CommandSender.sendMessage(vararg components: BaseComponent) {
    this.spigot().sendMessage(*components)
}

@JvmOverloads
fun String.translateColor(code: Char = '&'): String = ChatColor.translateAlternateColorCodes(code, this)

fun String.toText(
    color: ChatColor? = null, bold: Boolean? = null, italic: Boolean? = null,
    strikethrough: Boolean? = null, underline: Boolean? = null, obfuscated: Boolean? = null,
    hoverEvent: HoverEvent? = null, clickEvent: ClickEvent? = null, insertion: String? = null
): TextComponent {
    val text = TextComponent(this)
    color?.let { text.color = it }
    bold?.let { text.isBold = it }
    italic?.let { text.isItalic = it }
    strikethrough?.let { text.isStrikethrough = it }
    underline?.let { text.isUnderlined = it }
    obfuscated?.let { text.isObfuscated = it }
    hoverEvent?.let { text.hoverEvent = it }
    clickEvent?.let { text.clickEvent = it }
    insertion?.let { text.insertion = it }
    return text
}

private fun Iterator<BaseComponent>.joinToComponent(
    prefix: BaseComponent? = null,
    separator: BaseComponent? = null,
    postfix: BaseComponent? = null
): BaseComponent {
    if (!this.hasNext()) {
        return TextComponent()
    }

    val first: BaseComponent = this.next()

    if (!this.hasNext()) {
        if (prefix == null && postfix == null) {
            return first
        } else {
            val text = TextComponent()
            prefix?.let {
                text.addExtra(it)
                separator?.let(text::addExtra)
            }
            text.addExtra(first)
            postfix?.let {
                separator?.let(text::addExtra)
                text.addExtra(it)
            }
            return text
        }
    }

    val text = TextComponent()
    prefix?.let {
        text.addExtra(it)
        separator?.let(text::addExtra)
    }
    text.addExtra(first)
    for (component: BaseComponent in this) {
        separator?.let(text::addExtra)
        text.addExtra(component)
    }
    postfix?.let {
        separator?.let(text::addExtra)
        text.addExtra(it)
    }
    return text
}

fun Array<out BaseComponent>.joinToComponent(
    prefix: BaseComponent? = null,
    separator: BaseComponent? = null,
    postfix: BaseComponent? = null
): BaseComponent =
    this.iterator().joinToComponent(prefix, separator, postfix)

fun Iterable<BaseComponent>.joinToComponent(
    prefix: BaseComponent? = null,
    separator: BaseComponent? = null,
    postfix: BaseComponent? = null
): BaseComponent =
    this.iterator().joinToComponent(prefix, separator, postfix)

fun Sequence<BaseComponent>.joinToComponent(
    prefix: BaseComponent? = null,
    separator: BaseComponent? = null,
    postfix: BaseComponent? = null
): BaseComponent =
    this.iterator().joinToComponent(prefix, separator, postfix)