import java.util.HashMap;
import java.util.Map;
import java.lang.StringBuilder;
import java.net.Socket;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.InetSocketAddress;

public class Client {

	Socket socket;
	PrintWriter pw;
	Scanner scanner;
	String ip;
	int port;

	public static void main(String[] args) {
		Client client = new Client();
		try {
			client.connect("localhost", 8080);
			client.send("upper#reverse#Madam I'm Adam");
			System.out.println(client.receive());
			client.send("lower#translate#reverse#AYY");
			System.out.println(client.receive());
		} catch(Exception e){}
	}

	public void connect(String ip, int port) throws IOException {
		this.ip = ip;
		this.port = port;
		socket = new Socket(ip, port);
		scanner = new Scanner(socket.getInputStream());
		pw = new PrintWriter(socket.getOutputStream(), true);
	}

	public void send(String msg) {
		pw.println(msg);
	}

	public String receive() {
		return scanner.nextLine();
	}

}