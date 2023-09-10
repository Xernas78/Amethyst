package dev.xernas.amethyst.chat;

import com.google.gson.*;

import java.util.ArrayList;
import java.util.List;

public class ChatComponentSerializer {

    public static JsonElement serialize(ChatComponent component) {
        JsonObject jsonObject = new JsonObject();
        if (component instanceof TextChatComponent) {
            jsonObject.addProperty("text", ((TextChatComponent) component).getText());
        }
        jsonObject.addProperty("bold", component.getBold());
        jsonObject.addProperty("italic", component.getItalic());
        jsonObject.addProperty("underlined", component.getUnderlined());
        if (component.getColor() != null) {
            jsonObject.addProperty("color", component.getColor().toLowerCase());
        }
        if (component.getExtra() != null) {
            JsonArray array = new JsonArray();
            for (ChatComponent c : component.getExtra()) {
                array.add(serialize(c));
            }
            jsonObject.add("extra", array);
        }
        return jsonObject;
    }

    public static ChatComponent deserialize(JsonObject object) {
        ChatComponent component;
        if (object.has("text")) {
            component = new TextChatComponent(object.get("text").getAsString());
        } else {
            throw new RuntimeException("Unhandled component");
        }

        if (object.has("bold")) {
            component.setBold(object.get("bold").getAsBoolean());
        }
        if (object.has("italic")) {
            component.setItalic(object.get("italic").getAsBoolean());
        }
        if (object.has("underlined")) {
            component.setUnderlined(object.get("underlined").getAsBoolean());
        }
        if (object.has("color")) {
            component.setColor(object.get("color").getAsString());
        }

        if (object.has("extra")) {
            for (ChatComponent c : deserialize(object.getAsJsonArray("extra"))) {
                component.addChatComponent(c);
            }
        }
        return component;
    }

    private static List<ChatComponent> deserialize(JsonArray array) {
        List<ChatComponent> componentArray = new ArrayList<>();
        for(int i = 0; i < array.size(); i++) {
            componentArray.add(deserialize(array.get(i).getAsJsonObject()));
        }
        return componentArray;
    }

}
