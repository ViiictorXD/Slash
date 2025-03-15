package io.github.viiictorxd.discord.slash.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.HashMap;
import java.util.function.Consumer;

public class SlashMessageHolder extends HashMap<SlashMessageHolder.Type, Consumer<SlashCommandInteractionEvent>> {

  public void update(Type type, Consumer<SlashCommandInteractionEvent> consumer) {
    put(type, consumer);
  }

  @Getter
  @AllArgsConstructor
  public enum Type {

    NO_PERMISSION
  }
}
