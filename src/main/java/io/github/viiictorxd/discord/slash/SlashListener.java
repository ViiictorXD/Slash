package io.github.viiictorxd.discord.slash;

import io.github.viiictorxd.discord.slash.annotation.Slash;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class SlashListener extends ListenerAdapter {

  private final SlashMap slashMap;
  private final SlashResolver slashResolver;

  @Override
  public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
    if (event.getUser().isBot())
      return;

    SlashData slashData = slashMap.getSlashData(event.getName());
    if (slashData == null)
      return;

    Slash slash = slashData.getSlash();

    Member member = event.getMember();
    if (member == null || (!member.isOwner() && !member.hasPermission(slash.permission())))
      return;

    slashResolver.resolveSlash(slashData, event);
  }
}
