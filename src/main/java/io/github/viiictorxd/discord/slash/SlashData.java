package io.github.viiictorxd.discord.slash;

import io.github.viiictorxd.discord.slash.annotation.Slash;
import lombok.*;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class SlashData {

  private String name;

  private Slash slash;

  private @Builder.Default List<OptionData> optionData = new ArrayList<>();

  private Method method;
  private Object object;

  public SlashCommandData asSlash() {
    return Commands.slash(name, slash.description());
  }
}
