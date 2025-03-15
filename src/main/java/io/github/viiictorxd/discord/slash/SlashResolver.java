package io.github.viiictorxd.discord.slash;

import io.github.viiictorxd.discord.slash.adapter.TypeAdapter;
import io.github.viiictorxd.discord.slash.annotation.Data;
import io.github.viiictorxd.discord.slash.exception.NoSuchSlashAdapterException;
import io.github.viiictorxd.discord.slash.exception.SlashBadProgrammingException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SlashResolver {

  private final SlashAdapter slashAdapter;

  public List<SlashArgument> resolveArgument(SlashData slashData) {
    Method method = slashData.getMethod();

    List<Parameter> filteredParameters = Arrays.stream(method.getParameters())
     .filter(parameter -> parameter.isAnnotationPresent(Data.class))
     .collect(Collectors.toList());

    List<SlashArgument> arguments = new ArrayList<>();

    int index = 0;
    for (Parameter parameter : filteredParameters) {
      Data data = parameter.getAnnotation(Data.class);
      if (data == null) continue;

      TypeAdapter<?> typeAdapter = slashAdapter.getTypeAdapter(parameter.getType());
      if (typeAdapter == null) {
        throw new NoSuchSlashAdapterException(parameter.getType());
      }

      arguments.add(SlashArgument.of(index++, typeAdapter.getOptionData(data)));
    }
    return arguments;
  }

  @SneakyThrows
  public void resolveSlash(SlashData slashData, SlashCommandInteractionEvent event) {
    SlashContext slashContext = new SlashContext(event);


    List<Object> parameters = new ArrayList<>(Collections.singletonList(slashContext));

    Object object = slashData.getObject();
    Method method = slashData.getMethod();

    List<Parameter> filteredParameters = Arrays.stream(method.getParameters())
     .filter(parameter -> parameter.isAnnotationPresent(Data.class))
     .collect(Collectors.toList());

    for (Parameter parameter : filteredParameters) {
      Data data = parameter.getAnnotation(Data.class);

      OptionMapping optionMapping = event.getOption(data.name());
      if (optionMapping == null) {
        throw new SlashBadProgrammingException();
      }

      TypeAdapter<?> typeAdapter = slashAdapter.getTypeAdapter(parameter.getType());
      if (typeAdapter == null) {
        throw new NoSuchSlashAdapterException(parameter.getType());
      }

      parameters.add(typeAdapter.adapter(optionMapping));
    }

    method.invoke(object, parameters.toArray(new Object[0]));
  }
}
