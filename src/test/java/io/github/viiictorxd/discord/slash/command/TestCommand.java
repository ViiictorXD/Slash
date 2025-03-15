package io.github.viiictorxd.discord.slash.command;

import io.github.viiictorxd.discord.slash.SlashContext;
import io.github.viiictorxd.discord.slash.annotation.Sender;
import io.github.viiictorxd.discord.slash.annotation.Slash;
import net.dv8tion.jda.api.Permission;

public class TestCommand {

  @Slash(
   name = "test",
   permission = {Permission.ADMINISTRATOR, Permission.MESSAGE_SEND}
  )
  public void handleTest(@Sender SlashContext context) {
    context.getEvent().reply("Hello World!").queue();
  }
}
