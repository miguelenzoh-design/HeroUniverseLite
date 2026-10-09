# Hero Universe Lite — Forge 1.20.1

Projeto inicial de mod fan-made para **Minecraft Java 1.20.1 + Forge 47.4.5**, pensado para testar no PojavLauncher/Android. O GitHub Actions compila o JAR; não é necessário instalar MCreator.

## O que já existe nesta base
- Registro de itens: Núcleo Heroico, Célula Solar, Módulo de Vibranium, Reator Arc, Lançador de Teias e Bastão de Energia.
- Comandos de protótipo para modos inspirados em Superman, Homem-Aranha, Pantera Negra, Homem de Ferro, Flash e Thor.
- Carga solar persistente (0–100), carga manual/diurna e bônus de poder por nível.
- Alternância de força controlada / força total.
- Comando para limpar os dados de cooldown.
- GitHub Actions com Java 17 que publica o JAR como artefato.

## Comandos
- `/hero help`
- `/hero superman`
- `/hero spiderman`
- `/hero panther`
- `/hero ironman`
- `/hero flash`
- `/hero thor`
- `/hero solar status`
- `/hero solar absorb`
- `/hero solar add <1-100>` (operador)
- `/hero full_power`
- `/hero controlled`
- `/hero cooldown_clear`
- `/hero give core|solar_cell|vibranium|arc_reactor|web_shooter|baton` (operador)

## Compilar no GitHub
1. Crie um repositório **separado** para este mod.
2. Extraia este ZIP no celular e envie o conteúdo da pasta do projeto para a raiz do repositório (não envie a pasta externa envolvendo tudo).
3. Abra **Actions** e execute `Build Hero Universe Lite` se o build não iniciar automaticamente.
4. Quando ficar verde, abra a execução e baixe o artefato `HeroUniverseLite-Forge-1.20.1`.
5. Dentro do artefato estará o `.jar`; coloque-o em `.minecraft/mods` no perfil Forge 1.20.1 do PojavLauncher.

## Limitações honestas desta versão
Esta é uma **base jogável/protótipo**, não um mod enorme já finalizado. Os poderes são ativados por comandos e usam efeitos vanilla; ainda não há modelos 3D de trajes, HUD solar laranja, ataques visuais customizados, sistema real de absorção cinética, criação de poderes por interface, personagens/mobs/bosses próprios, nem dezenas de heróis e vilões. Esses recursos podem ser adicionados em commits posteriores. Os itens usam a textura vanilla de item ausente até serem criadas texturas/modelos próprios.

Teste primeiro em um mundo novo ou cópia do mundo. O projeto não inclui bibliotecas extras e visa reduzir o impacto no desempenho. Nenhum mod pode garantir ausência total de bugs em todos os aparelhos.

## Observações
- Java 17 é exigido pelo Forge 1.20.1.
- Fan project não oficial; sem afiliação com Marvel, DC ou seus titulares.
