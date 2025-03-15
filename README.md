# Slash

**Slash** é uma biblioteca para facilitar a criação e o registro de comandos Slash (comandos de barra) no Discord utilizando o JDA (Java Discord API). Com esta biblioteca, você pode registrar comandos, adaptar tipos personalizados e integrar facilmente seus próprios adaptadores.

## Instalação

Você pode adicionar o [JAR](https://github.com/ViiictorXD/Slash/releases) diretamente ao seu projeto ou usá-lo via Maven ou Gradle. Veja abaixo como configurar:

1. **Adicionando a dependência no Maven:**

   Se você está utilizando Maven, adicione a dependência do projeto em seu `pom.xml`:

   ```xml
   <dependency>
       <groupId>com.github.ViiictorXD</groupId>
       <artifactId>Slash</artifactId>
       <version>1.0.4</version>
   </dependency>
   ```

2. **Adicionando a dependência via Gradle:**

   Se você estiver utilizando Gradle, adicione a dependência ao seu `build.gradle`:

   ```gradle
   implementation 'com.github.ViiictorXD:Slash:1.0.4'
   ```

## Como Usar

### Instanciando a Classe Principal

Você precisa instanciar a classe principal `SlashFrame` passando a instância do `JDA`:

```java
SlashFrame slashFrame = new SlashFrame(jda);
```

### Registrando um Comando

Após instanciar o `SlashFrame`, você pode registrar um comando de barra (slash) usando o método `registerSlash`:

```java
slashFrame.registerSlash(new YourCommandClass());
```
Após registrar todos os seus comandos, é necessário chamar o método `init()`

```java
slashFrame.init();
```
Isso permite que você consiga adicionar, _caso queira_, comandos em tempo de execução!

_Vale ressaltar que você pode criar quantos comandos quiser dentro de uma única classe!_

### Registrando um Adapter Customizado

Você também pode registrar adaptadores personalizados para tipos de dados. Para fazer isso, você pode usar o método `registerTypeAdapter`:

```java
slashFrame.registerTypeAdapter(new YourCustomTypeAdapter());
```

### Como Usar o Comando

#### Comando sem argumentos (parâmetros)

```java
@Slash(
    name = "exemplo",
    description = "Esse é um comando teste",
    alias = {"exemplo1", "alias2", "alias3"},
    permission = Permission.ADMINISTRATOR
)
public void handleExample(@Sender SlashContext context) {
    context.getEvent().reply("Hello World").queue();
}
```

No exemplo acima, o comando `exemplo` será registrado. O comando terá 3 aliases: `exemplo1`, `alias2`, e `alias3`. Ele exige permissão de administrador e, ao ser chamado, responde com a mensagem "Hello World".

#### Comando com argumentos (parâmetros)

```java
@Slash(
    name = "hello",
    description = "Esse comando não faz nada"
)
public void handleHello(@Sender SlashContext context, @Data(name = "cargo daora") Role role) {
    if (role == null) {
        context.getEvent().reply("Ops... cargo não encontrado.").queue();
        return;
    }
    
    context.getEvent().reply(String.format("O cargo %s é bacana!", role.getName())).queue();
}
```

Neste exemplo, o comando `hello` aceita um argumento chamado `cargo daora`, que é um tipo `Role`. Caso o cargo não seja encontrado, o comando responde com "Ops... cargo não encontrado.". Caso o cargo seja válido, ele retorna uma mensagem com o nome do cargo. <br><br>
Caso queira um exemplo prático, veja [aqui](https://github.com/ViiictorXD/Slash/blob/main/src/test/java/io/github/viiictorxd/discord/slash/command/TestCommand.java)

## Criando um Custom Adapter

Caso você precise de um adaptador personalizado para converter dados de entrada para um tipo específico, você pode criar sua própria classe implementando a interface `TypeAdapter`. <br><br> Veja abaixo um exemplo:

```java
public class YourCustomTypeAdapter implements TypeAdapter<MyCustomObject> {
  
  @Override
  public MyCustomObject adapter(OptionMapping mapping) {
    // Aqui você irá adaptar o mapping para o tipo desejado
    return new MyCustomObject(/* valores extraídos de mapping */);
  }

  @Override
  public OptionData getOptionData(Data data) {
    // Aqui você define a estrutura do dado para o tipo personalizado
    return new OptionData(OptionType.STRING, "your_option_name", "description", true);
  }
}
```

No exemplo acima, `YourCustomTypeAdapter` é um adaptador que converte um `OptionMapping` em um objeto customizado `MyCustomObject`.

## Contribuições

Se você deseja contribuir para este projeto, fique à vontade para enviar um pull request. Estamos sempre abertos a melhorias!

### Passos para Contribuir:

1. Fork o repositório.
2. Crie uma branch para a sua feature (`git checkout -b feature/feature-name`).
3. Faça suas alterações e commit com uma mensagem significativa (`git commit -am 'Add new feature'`).
4. Envie sua branch para o repositório remoto (`git push origin feature/feature-name`).
5. Abra um Pull Request.
