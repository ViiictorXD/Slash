package io.github.viiictorxd.discord.slash.adapter.primitives;

import io.github.viiictorxd.discord.slash.adapter.TypeAdapter;
import io.github.viiictorxd.discord.slash.annotation.Data;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class ShortTypeAdapter implements TypeAdapter<Short> {

  @Override
  public Short adapter(OptionMapping mapping) {
    return Short.parseShort(mapping.getAsString());
  }

  @Override
  public OptionData getOptionData(Data data) {
    return new OptionData(
     OptionType.NUMBER,
     data.name(),
     data.description(),
     data.required()
    );
  }
}
