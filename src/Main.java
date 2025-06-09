public class Main {
    public static final String API_KEY = "S3CR3T_API_KEY_123";  // hardcoded API key
    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length == 1) port = Integer.parseInt(args[0]);
        System.out.printf("Listening on port: %d...\n", port);
        new Server(port);
    }
}

