import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.lang.StringBuilder;
import java.net.Socket;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.InetSocketAddress;

public class Server {

	public static final int PORT = 8080;
	public static final String IP = "localhost";
	public ServerSocket serverSocket;

    private static final Map<String, String> da_en;
    static
    {
    	da_en = new HashMap<String, String>();
    	da_en.put("hund", "dog");
    	da_en.put("ayy", "lmao");
    	da_en.put("kat", "cat");
    	da_en.put("Madam I'm Adam", "Frue, mit navn er Adam");
    }

	public static void main(String[] args) {
		try {
			Server demo = new Server();
			System.out.println("startinServer");
			demo.startServer();
		} catch(Exception e){}
	}

	private void startServer() throws IOException {
		serverSocket = new ServerSocket();
		serverSocket.bind(new InetSocketAddress(IP,PORT));
		while(true) {
			Socket socket = serverSocket.accept();
			System.out.println("A new client ayy");
			handleClient(socket);
		}
	}

	private void handleClient(Socket s) {
		try {
			Scanner scanner = new Scanner(s.getInputStream());
			PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
			String msg;
			while(scanner.hasNextLine()) {
				msg = scanner.nextLine();
				System.out.println("Receiveds: " + msg);

				pw.println(processCommand(msg));
				System.out.println("what");
			}

			System.out.println("Dropped client");
			s.close();

		} catch(Exception e){}
	}

	private String processCommand(String input) {
		if(input.equals("help"))
			return "command#command2#input\nAvailable commands: upper,lower,reverse,translate";
		if(input.indexOf("#") == -1)
			return input + " - is an invalid command, type help to see commands";

		String res = "";
		String msg = input.split("#")[input.split("#").length - 1];
		String cmd;

		int loopLength = input.split("#").length - 1;
		for (int i = 0 ;i < loopLength; i++) {
			cmd = input.substring(0, input.indexOf("#")).toLowerCase();
			input = input.substring(input.indexOf("#") + 1);
			System.out.println(cmd + " - " + msg);
			if(cmd.equals("upper"))
				msg = msg.toUpperCase();
			else if(cmd.equals("lower"))
				msg = msg.toLowerCase();
			else if(cmd.equals("reverse"))
				msg = new StringBuilder(msg).reverse().toString();
			else if(cmd.equals("translate")) {
				if(da_en.containsKey(msg))
					msg = da_en.get(msg);
			}
			else
				msg = "invalid command, type help to see commands";
			System.out.println("msg after transfrom: " + msg);
		}
		return msg;
	}

}