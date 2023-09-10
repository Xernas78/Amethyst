package dev.xernas.amethyst.chat;

public class TextChatComponent extends ChatComponent{

    private String text;

    public TextChatComponent(String text) {
        this.text = text;
    }

    public TextChatComponent setText(String text) {
        this.text = text;
        return this;
    }

    public String getText() {
        return text;
    }
}
