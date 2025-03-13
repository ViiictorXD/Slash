package io.github.viiictorxd.discord.slash;

public class SlashBotLauncher {

  public static void main(String[] args) throws InterruptedException {
    String token = System.getenv("token");

    SlashBot slashBot = new SlashBot(token);
    slashBot.init();
  }
}
