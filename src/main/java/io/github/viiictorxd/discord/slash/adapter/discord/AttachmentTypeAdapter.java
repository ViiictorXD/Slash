package io.github.viiictorxd.discord.slash.adapter.discord;

import io.github.viiictorxd.discord.slash.adapter.TypeAdapter;
import io.github.viiictorxd.discord.slash.annotation.Data;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class AttachmentTypeAdapter implements TypeAdapter<Message.Attachment> {

  @Override
  public Message.Attachment adapter(OptionMapping mapping) {
    return mapping.getAsAttachment();
  }

  @Override
  public OptionData getOptionData(Data data) {
    return new OptionData(
     OptionType.ATTACHMENT,
     data.name(),
     data.description(),
     data.required()
    );
  }
}
