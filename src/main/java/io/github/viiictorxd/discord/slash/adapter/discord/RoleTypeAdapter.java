package io.github.viiictorxd.discord.slash.adapter.discord;

import io.github.viiictorxd.discord.slash.adapter.TypeAdapter;
import io.github.viiictorxd.discord.slash.annotation.Data;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class RoleTypeAdapter implements TypeAdapter<Role> {
  @Override
  public Role adapter(OptionMapping mapping) {
    return mapping.getAsRole();
  }

  @Override
  public OptionData getOptionData(Data data) {
    return new OptionData(
     OptionType.ROLE,
     data.name(),
     data.description(),
     data.required()
    );
  }
}
