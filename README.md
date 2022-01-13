# Kontrol

<p align="start">
    <a href="https://play.google.com/store/apps/details?id=de.theaninova.kontrol">
        <img src="https://cdn.rawgit.com/steverichey/google-play-badge-svg/master/img/en_get.svg" width="250px">
    </a>
</p>

This is a relatively simple Android App, where you can configure a bunch of dummy settings pages using a JSON import.

```json
[
    {
        "title": "Page One",
        "description": "This is the description of Page One.",
        "items": [
            {"name": "A Slider", "icon": "VolumeUp", "type": "slider"},
            {"name": "A Checkbox", "icon": "Equalizer", "type": "checkbox"}
        ]
    },
    {
        "title": "Page Two",
        "description": "This is the description of Page Two.",
        "items": [
            {"name": "A Slider", "icon": "VolumeUp", "type": "slider"},
            {"name": "A Switch", "icon": "Equalizer", "type": "switch"}
        ]
    }
]
```

You can look into the Kotlin classes for more info about the JSON schema.

## iOS

No. Sorry. That's a complete rewrite unfortunately, a system I am completely unfamiliar with, and one that requires having a Mac just to compile an app, and horrendous fees for publishing it. So no.
