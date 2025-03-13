package io.github.viiictorxd.discord.slash.exception;

public class NoSuchSlashAdapterException extends RuntimeException {

  public NoSuchSlashAdapterException(Class<?> classType) {
    super(String.format("No Adapter found for class type %s.", classType.getTypeName()));
  }
}
