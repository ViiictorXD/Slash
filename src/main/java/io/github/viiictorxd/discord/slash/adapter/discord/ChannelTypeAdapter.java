package io.github.viiictorxd.discord.slash.adapter.discord;

import io.github.viiictorxd.discord.slash.adapter.TypeAdapter;
import io.github.viiictorxd.discord.slash.annotation.Data;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class ChannelTypeAdapter implements TypeAdapter<Channel> {

  @Override
  public Channel adapter(OptionMapping mapping) {
    return mapping.getAsChannel();
  }

  @Override
  public OptionData getOptionData(Data data) {
    return new OptionData(
     OptionType.CHANNEL,
     data.name(),
     data.description(),
     data.required()
    );
  }
}
