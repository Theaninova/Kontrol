package de.theaninova.kontrol.controls.schema

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KontrolSection(
    val title: String,
    val items: List<KontrolItem> = listOf(),
    val canBeDisabled: Boolean = true,
    val description: String? = null,
)

@Serializable
sealed class KontrolItem {
    abstract val name: String
    abstract val description: String
    abstract val icon: KontrolIcons?
}

@Serializable
@SerialName("slider")
data class KontrolSlider(
    override val name: String,
    val default: Float = 0F,
    val min: Float = 0F,
    val max: Float = 1F,
    override val icon: KontrolIcons? = null,
    override val description: String = "No description provided."
) : KontrolItem()

/*@Serializable
@SerialName("toggle")
data class KontrolToggle(
    override val name: String,
    val default: Boolean = false,
    override val icon: KontrolIcons? = null,
    override val description: String = "No description provided."
) : KontrolItem()*/

@Serializable
@SerialName("checkbox")
data class KontrolCheckbox(
    override val name: String,
    val default: Boolean = false,
    override val icon: KontrolIcons? = null,
    override val description: String = "No description provided.",
) : KontrolItem()

@Serializable
@SerialName("separator")
data class KontrolSeparator(
    override val name: String,
    override val icon: KontrolIcons? = null,
    override val description: String = "No description provided.",
) : KontrolItem()
