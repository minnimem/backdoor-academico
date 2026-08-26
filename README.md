# Backdoor Acadêmico - Cliente/Servidor via Sockets

## Integrantes

- Nome: DIEGO DE JESUS MODESTO SILVA
- Matrícula: 202420362

## 1. Objetivo

Este projeto foi desenvolvido para estudar a comunicação de rede entre um cliente e um servidor utilizando sockets TCP em Java.

O projeto também demonstra, em ambiente controlado de laboratório, como um processo remoto pode interagir com o shell do sistema operacional Windows.

## 2. Tecnologias utilizadas

- Java
- Socket TCP
- ServerSocket
- BufferedReader
- BufferedWriter
- ProcessBuilder
- Windows CMD
- Git

## 3. Estrutura do projeto

```text
backdoor-academico/
├── src/
│   ├── Client.java
│   └── Server.java
├── .gitignore
├── README.md
└── LICENSE
O diretório src contém os arquivos de código-fonte Java.

O arquivo .gitignore impede que arquivos compilados, como .class, sejam
enviados para o repositório.

O arquivo README.md contém a documentação do projeto.

O arquivo LICENSE contém a licença utilizada no projeto.

4. Compilação

A compilação foi realizada utilizando o Java instalado no Windows.

Primeiro, acessar a pasta do projeto:

cd C:\backdoor-academico

Depois acessar a pasta src:

cd src

Para compilar os arquivos:

javac -encoding windows-1252 Server.java Client.java

Após a compilação, os arquivos .class são gerados localmente.

Esses arquivos não são enviados ao GitHub devido às regras configuradas no
arquivo .gitignore.

5. Execução
5.1 Iniciar o servidor

Em um PowerShell:

cd C:\backdoor-academico\src
java Server

O servidor apresenta:

Backdoor Acadêmico - servidor local
Escutando somente em 127.0.0.1:5000

O servidor permanece aguardando uma conexão do cliente.

5.2 Iniciar o cliente

Em outro PowerShell:

cd C:\backdoor-academico\src
java Client

O cliente apresenta:

Cliente do Backdoor Acadêmico
Conectando em 127.0.0.1:5000
Conectado.
Digite comandos do Windows. Use 'exit' ou 'quit' para sair.
shell>

Durante os testes locais, foi utilizado o endereço:

127.0.0.1:5000

O endereço 127.0.0.1 corresponde à interface de loopback da própria
máquina.

6. Demonstração de Uso

Após o cliente estabelecer a conexão com o servidor, é possível enviar
comandos do Windows.

6.1 Comando hostname

Comando enviado:

hostname

Exemplo de saída:

DESKTOP-JH11PPB
[código de saída: 0]

O código de saída 0 indica que o comando foi executado com sucesso.

6.2 Comando echo

Comando enviado:

echo TESTE

Saída:

TESTE
[código de saída: 0]

Esse teste demonstra o envio de um comando e o retorno de sua saída através
da conexão TCP.

6.3 Comando inválido

Foi realizado também um teste utilizando um comando incorreto:

exite

O Windows retornou uma mensagem informando que o comando não foi reconhecido.

Exemplo:

'exite' não é reconhecido como um comando interno
ou externo, um programa operável ou um arquivo em lotes.
[código de saída: 1]

Esse teste demonstra que as mensagens de erro produzidas pelo sistema
operacional também são retornadas ao cliente.

6.4 Encerramento

Para encerrar a conexão, pode ser utilizado:

exit

ou:

quit

Exemplo de resposta:

Conexão encerrada pelo servidor.
7. Funcionamento

O sistema utiliza uma arquitetura cliente/servidor baseada em comunicação
TCP.

O servidor utiliza a classe ServerSocket para abrir a porta 5000 e aguardar
uma conexão.

Quando o cliente se conecta, os dois processos passam a utilizar o Socket
para trocar informações.

O cliente envia uma string contendo o comando.

O servidor recebe essa string e verifica se ela corresponde aos comandos de
encerramento exit ou quit.

Caso não seja um comando de encerramento, o servidor utiliza a classe
ProcessBuilder para iniciar o cmd.exe e executar o comando recebido.

A saída produzida pelo processo é capturada pelo servidor e enviada de volta
para o cliente.

O cliente recebe as informações e apresenta o resultado no PowerShell.

Fluxo de comunicação
Cliente
   |
   | comando TCP
   v
Servidor
   |
   | ProcessBuilder
   v
cmd.exe
   |
   | saída do comando
   v
Servidor
   |
   | resposta TCP
   v
Cliente
8. Mecanismo de Encerramento

O sistema reconhece dois comandos específicos para encerramento:

exit
quit

Quando o servidor recebe um desses comandos, ele envia uma mensagem
informando que a conexão será encerrada e finaliza a comunicação.

O cliente também identifica esses comandos e encerra sua execução de forma
controlada.

Além disso, o servidor utiliza o marcador:

<<<FIM_OUTPUT>>>

Esse marcador indica ao cliente que a resposta referente ao comando enviado
foi concluída.

9. Análise Teórica de Segurança

O projeto explora o conceito de backdoor com comunicação cliente/servidor,
demonstrando como um processo pode receber comandos por meio de uma conexão
TCP e interagir com o shell do sistema operacional. Em um cenário real, esse
comportamento poderia permitir uma execução remota não autorizada. Uma equipe
de defesa (Blue Team) poderia detectar esse tipo de atividade por meio do
monitoramento de portas e conexões TCP abertas, análise de processos em
execução e identificação de processos que realizam comunicação de rede e
posteriormente iniciam interpretadores de comandos, como o cmd.exe. Como
medidas de mitigação, podem ser utilizadas regras de firewall para bloquear
conexões não autorizadas, restrição de portas e serviços desnecessários,
monitoramento de processos e conexões de rede, aplicação do princípio do
menor privilégio e ferramentas de detecção e resposta a incidentes (EDR).

10. Diretrizes Éticas e de Segurança

Este projeto foi desenvolvido exclusivamente para fins acadêmicos.

Os testes devem ser realizados somente em ambiente controlado e autorizado,
como computadores próprios ou máquinas virtuais destinadas ao laboratório.

Não é permitida a utilização do código contra computadores, redes ou sistemas
de terceiros sem autorização formal.

Durante os testes locais, foi utilizado o endereço 127.0.0.1, mantendo a
comunicação restrita à própria máquina.

O objetivo do projeto é exclusivamente educacional, permitindo compreender
conceitos de sockets TCP, comunicação cliente/servidor, execução de processos
e aspectos de segurança relacionados a backdoors.

11. Versionamento

O projeto utiliza Git para controle de versão.

Os arquivos principais versionados são:

.gitignore
README.md
LICENSE
src/Client.java
src/Server.java

Os arquivos compilados .class não são versionados.

O projeto foi publicado no GitHub:

https://github.com/minnimem/backdoor-academico

12. Conclusão

A implementação permitiu demonstrar na prática os principais conceitos de
uma comunicação cliente/servidor utilizando sockets TCP em Java.

Durante os testes, o cliente conseguiu estabelecer conexão com o servidor,
enviar comandos, receber as respostas produzidas pelo cmd.exe e encerrar a
conexão utilizando os comandos exit e quit.

Os testes com hostname, echo TESTE, comando inválido e exit permitiram
verificar diferentes situações de funcionamento.

Além da implementação técnica, o projeto possibilitou aplicar práticas de
versionamento utilizando Git e GitHub, organização de código, documentação
técnica e análise dos aspectos de segurança relacionados ao comportamento de
um backdoor.

13. Licença

Este projeto está disponibilizado sob a licença MIT.

Consulte o arquivo LICENSE para obter o texto completo da licença
