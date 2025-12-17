package com.karastift.erzaehler.story

const val mockStoryJson = """
{
  "characters": [
    { "id": "ninja", "name": "ninja" },
    { "id": "businessman", "name": "businessman" }
  ],
  "script": [
    { "type": "enter", "id": "ninja" },
    { "type": "enter", "id": "businessman" },
    { "type": "dialog", "speaker": "ninja", "text": "Hello." },
    { "type": "dialog", "speaker": "businessman", "text": "Oh, hi!" },
    { "type": "dialog", "speaker": "ninja", "text": "Nice evening, isn't it?" },
    { "type": "exit", "id": "businessman" },
    { "type": "dialog", "speaker": "ninja", "text": "Wait, where did you go?" },
    { "type": "enter", "id": "businessman" },
    { "type": "dialog", "speaker": "businessman", "text": "Hallo ich bin wieder da" }
  ]
}
"""