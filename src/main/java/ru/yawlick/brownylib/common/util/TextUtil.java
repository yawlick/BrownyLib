package ru.yawlick.brownylib.common.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

import java.util.ArrayList;
import java.util.Arrays;

public final class TextUtil {
    private static String[][] parseHexes(String input) {
        String regex = "#[A-Fa-f0-9]{6}";
        ArrayList<String> hexList = new ArrayList<>();
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            hexList.add(matcher.group());
        }
        String[] hexes = hexList.toArray(new String[0]);

        String modifiedInput = input.replaceAll(regex, "");
        return new String[][]{hexes, Arrays.stream(modifiedInput.split(";")).filter(s -> !s.isEmpty()).toArray(String[]::new)};
    }

    public static Component formatText(String input) {
        input = input.replace("&", "§");
        String[][] parsedInput = parseHexes(input);
        String[] colors = parsedInput[0];
        String[] text = parsedInput[1];

        if(colors.length == 0) {
            return GsonComponentSerializer.gson().deserialize(String.format("{\"text\":\"%s\"}", input));
        }

        StringBuilder jsonBuilder = new StringBuilder("[");
        for (int i = 0; i < text.length; i++) {
            String c = text[i];
            String color = colors[i % colors.length];

            jsonBuilder.append(String.format("{\"text\":\"%s\",\"color\":\"%s\"},", c, color));
        }
        jsonBuilder.setLength(jsonBuilder.length() - 1);
        jsonBuilder.append("]");

        return GsonComponentSerializer.gson().deserialize(jsonBuilder.toString());
    }
}
