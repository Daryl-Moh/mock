
import java.util.LinkedList;
import java.util.List;
import java.io.File;


public class Main {
    public static void main(String[] args) throws Exception {

        System.out.println("--- MAIN FILE ---");

        Integer portNumber = 0;
        String defaultDirectory = "./static";
        String targetDirectory = "";
        String directory = "";

        List<String> commandList = new LinkedList<>();

        for (Integer i = 0; i < args.length; i++) {
            commandList.add(args[i].trim());
        }

        System.out.println("Command List: " + commandList);

        if (commandList.contains("--port") && commandList.contains("--docRoot")) {

            portNumber = Integer.parseInt(commandList.get(1));
            System.out.printf("Port set to : %d\n", portNumber);
            String[] directorySplitter = commandList.get(3).split(":");
            targetDirectory = directorySplitter[0];
            System.out.printf("Target Directory set to : %s\n", targetDirectory);
            directory = directorySplitter[1];
            System.out.printf("Directory set to : %s\n", directory);

        } else if (commandList.contains("--port")) {

            portNumber = Integer.parseInt(commandList.get(1));
            System.out.printf("Port set to : %d\n", portNumber);
            targetDirectory = defaultDirectory;
            System.out.printf("Target directory set to default: %s\n", defaultDirectory);

        } else if (commandList.contains("--docRoot")) {

            portNumber = 3000;
            System.out.println("Port set to default: 3000");
            String[] directorySplitter = commandList.get(1).split(":");
            targetDirectory = directorySplitter[0];
            System.out.printf("Target Directory set to : %s\n", targetDirectory);
            directory = directorySplitter[1];
            System.out.printf("Directory set to : %s\n", directory);

        } else {

            portNumber = 3000;
            System.out.println("Port set to default: 3000");
            targetDirectory = "./target";
            directory = "";
            System.out.printf("Target directory set to default: %s\n", defaultDirectory);

        }

        HttpServer.startServer(portNumber);

        File file = new File(targetDirectory);
        File file2 = new File(directory);

        if (!file.exists()) {

            System.out.printf("File %s does not exist", file.getName());
            System.exit(1);

            if (file.isDirectory()) {

                System.out.printf("Directory %s does not exist", file.getName());
                System.exit(1);

                if (file.canRead()) {

                    System.out.printf("You do not have permission to read file %s ", file.getName());
                    System.exit(1);
                }
            }
        }

        if (!file2.exists()) {

            System.out.printf("File %s does not exist", file.getName());
            System.exit(1);

            if (file2.isDirectory()) {

                System.out.printf("Directory %s does not exist", file.getName());
                System.exit(1);

                if (file2.canRead()) {

                    System.out.printf("You do not have permission to read file %s ", file.getName());
                    System.exit(1);
                }
            }
        }

        

    }

}