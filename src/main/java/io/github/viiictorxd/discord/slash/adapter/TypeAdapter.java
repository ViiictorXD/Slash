package io.github.viiictorxd.discord.slash.adapter;

import io.github.viiictorxd.discord.slash.annotation.Data;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public interface TypeAdapter<T> {

  T adapter(OptionMapping mapping);

  OptionData getOptionData(Data data);
}
