package io.github.viiictorxd.discord.slash.exception;

public class SlashAlreadyRegisteredException extends RuntimeException {

  public SlashAlreadyRegisteredException(String slash) {
    super(String.format("Command %s already registered.", slash));
  }
}
