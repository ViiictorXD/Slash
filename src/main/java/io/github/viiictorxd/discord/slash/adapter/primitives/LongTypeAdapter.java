package io.github.viiictorxd.discord.slash.adapter.primitives;

import io.github.viiictorxd.discord.slash.adapter.TypeAdapter;
import io.github.viiictorxd.discord.slash.annotation.Data;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class LongTypeAdapter implements TypeAdapter<Long> {

  @Override
  public Long adapter(OptionMapping mapping) {
    return mapping.getAsLong();
  }

  @Override
  public OptionData getOptionData(Data data) {
    return new OptionData(
     OptionType.INTEGER,
     data.name(),
     data.description(),
     data.required()
    );
  }
}
