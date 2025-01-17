package team.unnamed.emojis.hook.ezchat;

import me.fixeddev.ezchat.event.AsyncEzChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import team.unnamed.emojis.object.store.EmojiStore;
import team.unnamed.emojis.format.Permissions;
import team.unnamed.emojis.format.processor.MessageProcessor;

public class EzChatListener implements Listener {

    private final EmojiStore registry;
    private final MessageProcessor<Component, Component> messageProcessor;

    public EzChatListener(Plugin plugin, EmojiStore registry) {
        this.registry = registry;
        this.messageProcessor = MessageProcessor.component(plugin);
    }

    @EventHandler
    public void onChat(AsyncEzChatEvent event) {
        event.setMessage(
                messageProcessor.process(
                        event.getMessage(),
                        registry,
                        Permissions.permissionTest(event.getPlayer())
                )
        );
    }

}
