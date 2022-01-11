# Kontrol

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
