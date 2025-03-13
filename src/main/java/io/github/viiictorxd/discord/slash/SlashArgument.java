package io.github.viiictorxd.discord.slash;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

@Getter
@AllArgsConstructor(staticName = "of")
public class SlashArgument {

  private int index;
  private OptionData data;
}
