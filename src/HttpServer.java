
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    public static void startServer(Integer port) throws Exception {

        ExecutorService thrPool = Executors.newFixedThreadPool(3);

        ServerSocket ss = new ServerSocket(port);

        while (true) {

            System.out.printf("--Server Initiated--\nListening on Port: %d\n", port);
            System.out.println("Waiting for connection...");

            Socket cs = ss.accept();

            HttpClientConnection client = new HttpClientConnection(cs);

            thrPool.submit(client);

            client.run();

         
        }

        
    }

}
