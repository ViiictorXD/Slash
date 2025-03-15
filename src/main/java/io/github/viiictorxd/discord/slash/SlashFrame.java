package io.github.viiictorxd.discord.slash;

import io.github.viiictorxd.discord.slash.adapter.TypeAdapter;
import io.github.viiictorxd.discord.slash.adapter.discord.*;
import io.github.viiictorxd.discord.slash.adapter.primitives.*;
import io.github.viiictorxd.discord.slash.annotation.Slash;
import io.github.viiictorxd.discord.slash.exception.SlashAlreadyRegisteredException;
import io.github.viiictorxd.discord.slash.message.SlashMessageHolder;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SlashFrame {

  private final JDA jda;

  private final SlashMap slashMap;
  private final SlashAdapter slashAdapter;

  private final SlashResolver slashResolver;
  private final SlashMessageHolder slashMessageHolder;

  public SlashFrame(JDA jda) {
    this.jda = jda;

    this.slashMap = new SlashMap();
    this.slashAdapter = new SlashAdapter();

    this.slashResolver = new SlashResolver(slashAdapter);
    /* Registering default type adapters */
    registerDefaultTypeAdapters();

    this.slashMessageHolder = new SlashMessageHolder();
    /* Registering default message holder */
    registerDefaultMessageHolder();

    this.init();
  }

  private void init() {
    jda.addEventListener(new SlashListener(slashMap, slashResolver, slashMessageHolder));

    for (Guild guild : jda.getGuilds()) {
      CommandListUpdateAction commandListUpdateAction = guild.updateCommands();
      commandListUpdateAction.queue();
    }
  }

  @SuppressWarnings("all")
  public void registerSlash(Object object) {
    Class<?> clazz = object.getClass();

    List<Method> methods = Arrays.stream(clazz.getDeclaredMethods())
     .filter(method -> method.isAnnotationPresent(Slash.class))
     .collect(Collectors.toList());

    for (Method method : methods) {
      Slash slash = method.getAnnotation(Slash.class);

      if (slashMap.hasSlashData(slash.name())) {
        throw new SlashAlreadyRegisteredException(slash.name());
      }

      SlashData slashData = SlashData.of(slash, method, object);

      for (Guild guild : jda.getGuilds()) {
        List<SlashArgument> slashArguments = slashResolver.resolveArgument(slashData);

        List<OptionData> optionData = slashArguments.stream()
         .map(SlashArgument::getData)
         .collect(Collectors.toList());

        List<CommandCreateAction> actions = new ArrayList<>(slash.alias().length + 1);
        actions.add(guild.upsertCommand(slash.name(), slash.description()).addOptions(optionData));

        for (String alias : slash.alias()) {
          actions.add(guild.upsertCommand(alias, slash.description()).addOptions(optionData));
        }

        for (CommandCreateAction action : actions) {
          action.queue(command -> slashMap.registerSlashData(command.getName(), slashData));
        }
      }
    }
  }

  public void registerSlashes(Object... objects) {
    Arrays.stream(objects).forEach(this::registerSlash);
  }

  public void registerTypeAdapter(Class<?> clazz, TypeAdapter<?> adapter) {
    slashAdapter.registerTypeAdapter(clazz, adapter);
  }

  private void registerDefaultTypeAdapters() {
    slashAdapter.registerTypeAdapter(Message.Attachment.class, new AttachmentTypeAdapter());
    slashAdapter.registerTypeAdapter(Channel.class, new ChannelTypeAdapter());
    slashAdapter.registerTypeAdapter(Member.class, new MemberTypeAdapter());
    slashAdapter.registerTypeAdapter(Role.class, new RoleTypeAdapter());
    slashAdapter.registerTypeAdapter(User.class, new UserTypeAdapter());
    slashAdapter.registerTypeAdapter(Boolean.class, new BooleanTypeAdapter());
    slashAdapter.registerTypeAdapter(boolean.class, new BooleanTypeAdapter());
    slashAdapter.registerTypeAdapter(Byte.class, new ByteTypeAdapter());
    slashAdapter.registerTypeAdapter(byte.class, new ByteTypeAdapter());
    slashAdapter.registerTypeAdapter(Short.class, new ShortTypeAdapter());
    slashAdapter.registerTypeAdapter(short.class, new ShortTypeAdapter());
    slashAdapter.registerTypeAdapter(Integer.class, new IntTypeAdapter());
    slashAdapter.registerTypeAdapter(int.class, new IntTypeAdapter());
    slashAdapter.registerTypeAdapter(Long.class, new LongTypeAdapter());
    slashAdapter.registerTypeAdapter(long.class, new LongTypeAdapter());
    slashAdapter.registerTypeAdapter(Float.class, new FloatTypeAdapter());
    slashAdapter.registerTypeAdapter(float.class, new FloatTypeAdapter());
    slashAdapter.registerTypeAdapter(Double.class, new DoubleTypeAdapter());
    slashAdapter.registerTypeAdapter(double.class, new DoubleTypeAdapter());
    slashAdapter.registerTypeAdapter(String.class, new StringTypeAdapter());
  }

  private void registerDefaultMessageHolder() {
    slashMessageHolder.put(SlashMessageHolder.Type.NO_PERMISSION, event -> event.reply(":x: Sem permissão.").queue());
  }
}
