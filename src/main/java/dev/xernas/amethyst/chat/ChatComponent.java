package dev.xernas.amethyst.chat;

import java.util.List;

public class ChatComponent {

    private boolean bold;
    private boolean italic;
    private boolean underlined;
    private String color;
    private List<ChatComponent> extra;

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean getBold() {
        return bold;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public boolean getItalic() {
        return italic;
    }

    public void setUnderlined(boolean underlined) {
        this.underlined = underlined;
    }

    public boolean getUnderlined() {
        return underlined;
    }

    public void setColor(String color) {
        this.color = color;
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
