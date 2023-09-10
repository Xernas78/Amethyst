package dev.xernas.amethyst.chat;

import java.util.List;

public class ChatComponent {

    private boolean bold;
    private boolean italic;
    private boolean underlined;
    private String color;
    private List<ChatComponent> extra;

    public ChatComponent setBold(boolean bold) {
        this.bold = bold;
        return this;
    }

    public boolean getBold() {
        return bold;
    }

    public ChatComponent setItalic(boolean italic) {
        this.italic = italic;
        return this;
    }

    public boolean getItalic() {
        return italic;
    }

    public ChatComponent setUnderlined(boolean underlined) {
        this.underlined = underlined;
        return this;
    }

    public boolean getUnderlined() {
        return underlined;
    }

    public ChatComponent setColor(String color) {
        this.color = color;
        return this;
    }

    public String getColor() {
        return color;
    }

    public void addChatComponent(ChatComponent component) {
        extra.add(component);
    }

    public List<ChatComponent> getExtra() {
        return extra;
    }
}
