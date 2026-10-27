# Among Us Mod Launcher — Android starter

Protótipo nativo Android em Java. O app permite selecionar arquivos pelo seletor de documentos e listar os itens importados durante a sessão atual.

## O que esta versão faz
- Interface Android inicial.
- Seleção de arquivos com o seletor de documentos do sistema.
- Lista temporária dos arquivos selecionados.
- Tentativa de abrir um arquivo usando outro app compatível.

## O que ainda NÃO faz
- Não instala nem injeta mods no Among Us.
- Não converte mods de PC para Android.
- Não valida a segurança de APKs, DLLs, ZIPs ou assemblies.
- A lista ainda não é persistida quando o app é fechado.

## Construir pelo Android
1. Instale um IDE Android no celular que suporte projetos Gradle/Android (por exemplo, AndroidIDE, se disponível para seu aparelho).
2. Extraia o ZIP e abra a pasta `AmongUsModLauncher` como projeto Gradle.
3. Use JDK 17 e uma versão do Android Gradle Plugin compatível com o IDE.
4. Sincronize o Gradle e compile o módulo `app`.
5. Gere o APK de depuração pelo menu Build do IDE.

Se o IDE não conseguir baixar o Android Gradle Plugin 8.6.1 ou o SDK 35, atualize os componentes do IDE ou ajuste as versões para as que ele suporta.

## Próximas etapas sugeridas
1. Persistir catálogo local com URI e permissões de leitura.
2. Detectar extensão e calcular hash SHA-256.
3. Criar catálogo de mods com fontes verificadas e versões suportadas.
4. Implementar instalação apenas para formatos e versões cujo método seja conhecido e testado.

## Compatibilidade
Compatibilidade não é universal. Mods de PC que dependem de DLLs, BepInEx ou IL2CPP não são diretamente compatíveis com Android. A instalação e modificação do jogo podem ser limitadas por versão, assinatura, permissões, arquitetura e termos do jogo.


## Gerar APK automaticamente pelo GitHub Actions

O workflow `.github/workflows/android-apk.yml` compila o APK de depuração em um runner Linux hospedado pelo GitHub e publica `app-debug.apk` como artefato por 14 dias. **Este ZIP ainda não contém um APK compilado.**

### Pelo celular Android
1. Entre em https://github.com/ e crie uma conta, se necessário.
2. Crie um repositório novo, por exemplo `AmongUsModLauncher`, e deixe-o vazio (sem README/licença automáticos, para evitar conflito).
3. Extraia este ZIP no celular.
4. Envie todos os arquivos e pastas extraídos para a raiz do repositório, preservando a pasta oculta `.github/workflows/`. Se o gerenciador de arquivos ocultar `.github`, ative a opção para mostrar arquivos ocultos.
5. No GitHub, abra a aba **Actions** do repositório. Se solicitado, confirme a ativação dos workflows.
6. Selecione **Build Android APK** e toque em **Run workflow**. Também haverá uma execução automática ao enviar commits para `main` ou `master`.
7. Abra a execução mais recente e aguarde o job ficar verde (**Success**).
8. Na parte **Artifacts**, toque em `AmongUsModLauncher-debug-apk` para baixar o ZIP do artefato. Extraia-o e localize `app-debug.apk`.
9. Abra o APK no celular. Se o Android pedir, autorize a instalação desse app de origem usada para abrir o arquivo.

### Se falhar
Abra a execução em Actions e veja o primeiro passo vermelho; copie a mensagem de erro para ajudar a corrigir. O workflow instala Android SDK Platform 35 / Build Tools 35.0.0 e usa JDK 17 + Gradle 8.7.

### Segurança e limitações
- O APK é de depuração, assinado com uma chave de debug temporária do ambiente de build; não é uma versão de lançamento.
- Não guarde senhas, chaves de assinatura ou tokens no repositório.
- O app atual importa e lista arquivos durante a sessão; não instala nem injeta mods no Among Us.
- A compilação do APK só ocorre depois que o projeto for enviado ao GitHub e o workflow for executado com sucesso.
