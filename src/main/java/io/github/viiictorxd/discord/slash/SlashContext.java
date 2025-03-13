package io.github.viiictorxd.discord.slash;

import lombok.Getter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

@Getter
public class SlashContext {

  private final SlashCommandInteractionEvent event;

  private final Member member;
  private final Guild guild;

  private final MessageChannel channel;

  public SlashContext(SlashCommandInteractionEvent event) {
    this.event = event;

    this.member = event.getMember();
    this.guild = event.getGuild();

    this.channel = event.getChannel();
  }
}
