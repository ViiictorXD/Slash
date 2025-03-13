package io.github.viiictorxd.discord.slash.adapter.primitives;

import io.github.viiictorxd.discord.slash.adapter.TypeAdapter;
import io.github.viiictorxd.discord.slash.annotation.Data;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class BooleanTypeAdapter implements TypeAdapter<Boolean> {

  @Override
  public Boolean adapter(OptionMapping mapping) {
    return mapping.getAsBoolean();
  }

  @Override
  public OptionData getOptionData(Data data) {
    return new OptionData(
     OptionType.BOOLEAN,
     data.name(),
     data.description(),
     data.required()
    );
  }
}
