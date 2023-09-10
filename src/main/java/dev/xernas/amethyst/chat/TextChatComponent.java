package dev.xernas.amethyst.chat;

public class TextChatComponent extends ChatComponent{

    private String text;

    public TextChatComponent(String text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
