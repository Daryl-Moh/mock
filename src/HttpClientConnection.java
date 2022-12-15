
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class HttpClientConnection implements Runnable {

    private Socket socket;

    public HttpClientConnection(Socket socket) {
        this.socket = socket;
        
    }


    @Override
    public void run() {

        try {


            System.out.printf("New connection on port %d\n", socket.getPort());

            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String line = br.readLine();
            //System.out.printf(">>> Line: %s\n", line);
            
            String[] commandLine = line.split(" ");

            String method = commandLine[0].trim();
            String resource = commandLine[1].trim();
            String protocol = commandLine[2].trim();

            OutputStream os = socket.getOutputStream();
            HttpWriter hw = new HttpWriter(os);


            if (!method.toUpperCase().equalsIgnoreCase("GET")) {

                hw.writeString("HTTP/1.1 405 Method Not Allowed");
                hw.writeString("");
                hw.writeString(method + " not supported");
                socket.close();
            }   
            
            if (resource.trim().equalsIgnoreCase("/")) {
                resource = "/index.html";
            }

            String folder ="./static";
            Path path = Paths.get(folder, resource);
            File file = path.toFile();
            //File imageFile = new File("dynamic-duo.png");
            

            if (!file.exists()) {
                hw.writeString("HTTP/1.1 404 Not Found");
                hw.writeString("");
                hw.writeString(resource + " not found");
                socket.close();
            }   else {

                BufferedReader fbr = new BufferedReader(new FileReader(file));

                hw.writeString("HTTP/1.1 200 OK");
                hw.writeString("Content-Type: text/html");
                //hw.writeString("Content-Type: image/png");
                String fileLine;
                while ((fileLine = fbr.readLine()) != null) {
                    hw.writeString(fileLine);
                }
                hw.writeString("");
            
                hw.flush();

                socket.close();
                
            }
                 
            

        } catch (Exception ex) {
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
            }
        }

        

    }

}
