package ua.geckuss.loginmessage;

import java.util.List;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl.api.*;
import dev.isxander.yacl.gui.controllers.TickBoxController;
import dev.isxander.yacl.gui.controllers.string.StringController;
import dev.isxander.yacl.config.ConfigEntry;
import dev.isxander.yacl.config.ConfigInstance;
import dev.isxander.yacl.config.GsonConfigInstance;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.nio.file.Path;

public class LoginMessageConfig {
    public static final ConfigInstance<LoginMessageConfig> INSTANCE = new GsonConfigInstance<>(LoginMessageConfig.class,
            Path.of("./config/loginmessage.json"), new GsonBuilder().setPrettyPrinting());
    @ConfigEntry public List<String> serversList = List.of("mc.digit.fi");
    @ConfigEntry public List<String> commandsList = List.of();
    @ConfigEntry public boolean isEnabledInSingleplayer = false;

    public static Screen getModConfigScreenFactory(Screen parent) {
        return YetAnotherConfigLib.create(LoginMessageConfig.INSTANCE, (defaults, config, builder) -> builder
                        .title(Text.of("Login Message Config"))
                        .category(ConfigCategory.createBuilder()
                                .name(Text.of("Login Message"))
                                .option(ListOption.createBuilder(String.class)
                                        .name(Text.of("Servers List"))
                                        .tooltip(Text.of("List of servers on which the mod is enabled"))
                                        .controller(StringController::new)
                                        .binding(
                                                defaults.serversList,
                                                () -> config.serversList,
                                                (value) -> config.serversList = value
                                        )
                                        .initial("")
                                        .build())
                                .option(ListOption.createBuilder(String.class)
                                        .name(Text.of("Messages List"))
                                        .tooltip(Text.of("List of the messages the mod should send at the start of " +
                                                "the session. May contain commands, those should start with a /."))
                                        .controller(StringController::new)
                                        .binding(
                                                defaults.commandsList,
                                                () -> config.commandsList,
                                                (value) -> config.commandsList = value
                                        )
                                        .initial("")
                                        .build())
                                .option(Option.createBuilder(Boolean.class)
                                        .name(Text.of("Enabled in singleplayer?"))
                                        .tooltip(Text.of("Should the mod send the messages, specified in the messages" +
                                                " list, at the start of the singleplayer session?"))
                                        .controller(TickBoxController::new)
                                        .binding(defaults.isEnabledInSingleplayer,
                                                () -> config.isEnabledInSingleplayer,
                                                (value) -> config.isEnabledInSingleplayer = value)
                                        .build())
                                .build())
                )
                .generateScreen(parent);
    }

}
