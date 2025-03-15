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
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import java.lang.reflect.Method;
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

  private boolean registered;

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

    this.registered = false;
  }

  @SuppressWarnings("all")
  public void init() {
    if (!registered) {
      jda.addEventListener(new SlashListener(slashMap, slashResolver, slashMessageHolder));
    }

    CommandListUpdateAction action = jda.updateCommands()
     .addCommands(slashMap.values()
      .stream()
      .map(SlashData::asSlash)
      .collect(Collectors.toList()));

    action.queue();

    this.registered = true;
  }

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

      List<SlashArgument> slashArguments = slashResolver.resolveArgument(method);

      List<OptionData> optionData = slashArguments.stream()
       .map(SlashArgument::getData)
       .collect(Collectors.toList());

      SlashData slashData = SlashData.builder()
       .name(slash.name())
       .slash(slash)
       .method(method)
       .object(object)
       .optionData(optionData)
       .build();

      slashMap.registerSlashData(slash.name(), slashData);
      for (String alias : slash.alias()) {
        slashData.setName(alias);

        slashMap.registerSlashData(alias, slashData);
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
    slashMessageHolder.put(SlashMessageHolder.Type.NO_PERMISSION, event -> event.reply(":x: Sem permissão.")
     .setEphemeral(true)
     .queue());
  }
}
