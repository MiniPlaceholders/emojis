package team.unnamed.emojis.resourcepack;

import team.unnamed.emojis.Emoji;
import team.unnamed.emojis.EmojiRegistry;
import team.unnamed.emojis.io.AssetWriter;
import team.unnamed.emojis.io.Streams;
import team.unnamed.emojis.io.TreeOutputStream;

import java.io.IOException;
import java.util.Iterator;

/**
 * Responsible for writing resources for the emojis
 * plugin.
 * @author yusshu (Andre Roldan)
 */
public class EmojiAssetWriter implements AssetWriter {

    private final EmojiRegistry registry;

    public EmojiAssetWriter(EmojiRegistry registry) {
        this.registry = registry;
    }

    /**
     * Transfers the resource pack information to the
     * given {@code output}
     *
     * <strong>Note that this method won't close the
     * given {@code output}</strong>
     */
    @Override
    public void write(TreeOutputStream output) throws IOException {

        // write the font json file
        output.useEntry("assets/minecraft/font/default.json");
        Streams.writeUTF(output, createFontJson());
        output.closeEntry();

        // write the emojis images
        for (Emoji emoji : registry.values()) {
            output.useEntry("assets/minecraft/textures/emojis/" + emoji.getName() + ".png");
            emoji.getData().write(output);
            output.closeEntry();
        }
    }

    private String createFontJson() {
        StringBuilder builder = new StringBuilder("{ \"providers\": [ ");

        Iterator<Emoji> iterator = registry.values().iterator();
        while (iterator.hasNext()) {
            Emoji emoji = iterator.next();
            builder
                    .append("{\"ascent\":").append(emoji.getAscent())
                    .append(",\"chars\":[\"").append(emoji.getCharacter())
                    .append("\"],\"file\":\"emojis/").append(emoji.getName()).append(".png")
                    .append("\",\"height\":").append(emoji.getHeight())
                    .append(",\"type\":\"bitmap\"}");
            if (iterator.hasNext()) {
                builder.append(',');
            }
        }

        return builder.append("]}").toString();
    }

}