package team.unnamed.emojis.resourcepack;

import net.kyori.adventure.key.Key;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.central.event.EventListener;
import team.unnamed.creative.central.event.pack.ResourcePackGenerateEvent;
import team.unnamed.creative.font.Font;
import team.unnamed.creative.font.FontProvider;
import team.unnamed.creative.texture.Texture;
import team.unnamed.emojis.Emoji;
import team.unnamed.emojis.object.store.EmojiStore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Responsible for writing resources required to use
 * custom glyphs in Minecraft, it writes the glyph font
 * and the glyph textures
 *
 * @author yusshu (Andre Roldan)
 */
public class EmojisWriter implements EventListener<ResourcePackGenerateEvent> {

    private final EmojiStore registry;

    public EmojisWriter(EmojiStore registry) {
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
    public void on(ResourcePackGenerateEvent event) {
        ResourcePack resourcePack = event.resourcePack();

        Collection<Emoji> emojis = registry.values();
        List<FontProvider> providers = new ArrayList<>(emojis.size());

        for (Emoji emoji : emojis) {

            Key textureKey = Key.key(Key.MINECRAFT_NAMESPACE, "emojis/" + emoji.name());

            // write emoji image
            resourcePack.texture(
                    Texture.builder()
                            .key(textureKey)
                            .data(emoji.data())
                            .build()
            );

            // add a provider for this glyph
            providers.add(
                    FontProvider.bitMap()
                            .height(emoji.height())
                            .ascent(emoji.ascent())
                            .characters(emoji.replacement())
                            .file(textureKey)
                            .build()
            );
        }

        // head emoji provider resources
        //HeadEmojiProvider.installResources(providers, tree);

        // write the default.json font
        resourcePack.font(Font.of(Font.MINECRAFT_DEFAULT, providers));
    }

}
