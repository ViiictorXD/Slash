package io.github.viiictorxd.discord.slash.adapter.discord;

import io.github.viiictorxd.discord.slash.adapter.TypeAdapter;
import io.github.viiictorxd.discord.slash.annotation.Data;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class UserTypeAdapter implements TypeAdapter<User> {

  @Override
  public User adapter(OptionMapping mapping) {
    return mapping.getAsUser();
  }

  @Override
  public OptionData getOptionData(Data data) {
    return new OptionData(
     OptionType.USER,
     data.name(),
     data.description(),
     data.required()
    );
  }
}
