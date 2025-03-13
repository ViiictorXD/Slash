package io.github.viiictorxd.discord.slash.command;

import io.github.viiictorxd.discord.slash.SlashContext;
import io.github.viiictorxd.discord.slash.annotation.Sender;
import io.github.viiictorxd.discord.slash.annotation.Slash;

public class TestCommand {

  @Slash(
   name = "test"
  )
  public void handleTest(@Sender SlashContext context) {
    context.getEvent().reply("Hello World!").queue();
  }
}
