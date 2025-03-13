package io.github.viiictorxd.discord.slash.adapter.primitives;

import io.github.viiictorxd.discord.slash.adapter.TypeAdapter;
import io.github.viiictorxd.discord.slash.annotation.Data;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class StringTypeAdapter implements TypeAdapter<String> {

  @Override
  public String adapter(OptionMapping mapping) {
    return mapping.getAsString();
  }

  @Override
  public OptionData getOptionData(Data data) {
    return new OptionData(
     OptionType.STRING,
     data.name(),
     data.description(),
     data.required()
    );
  }
}
