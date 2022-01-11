package de.theaninova.kontrol.controls.schema

val KONTROL_TEST_ITEMS: List<KontrolSection> = listOf(
    KontrolSection(
        title = "Sound Enhancement",
        description = "Enhances the sound of any pair of headphones, speakers, or other artificial sound sources.",
        items = listOf(
            KontrolSlider("Volume"),
            KontrolSlider("Bass"),
            KontrolSlider("Treble"),
            KontrolSlider("Enhancement")
        )
    ),
    KontrolSection(
        title = "Image Enhancement",
        items = listOf(
            KontrolCheckbox("Test Checkbox")
        )
    )
)