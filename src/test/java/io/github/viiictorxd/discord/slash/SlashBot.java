package io.github.viiictorxd.discord.slash;

import io.github.viiictorxd.discord.slash.command.TestCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.Arrays;

public class SlashBot {

  private final JDA jda;

  public SlashBot(String token) {
    this.jda = JDABuilder.createDefault(token)
     .enableIntents(Arrays.asList(GatewayIntent.values()))
     .build();
  }

  public void init() throws InterruptedException {
    jda.awaitReady();

    SlashFrame slashFrame = new SlashFrame(jda);
    slashFrame.registerSlashes(
     new TestCommand()
    );
  }
}
