package io.github.viiictorxd.discord.slash;

import io.github.viiictorxd.discord.slash.annotation.Slash;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;

@Getter
@AllArgsConstructor(staticName = "of")
public class SlashData {

  private Slash slash;

  private Method method;
  private Object object;
}
