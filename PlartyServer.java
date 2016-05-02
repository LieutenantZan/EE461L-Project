import java.io.*;
import java.net.*;
import java.util.*;

public class PlartyServer {


	// static Users[] user = new Users[10];
	static ArrayList<Users> users = new ArrayList<Users>();
	static Users host;

	public static void main(String[] args) throws Exception {
		Socket clientSocket;
		//ServerSocket serverSocket = new ServerSocket(7777,1000,InetAddress.getByName("2605:6000:e94b:3900:e51a:5184:5bc9:c0e5"));
		ServerSocket serverSocket=new ServerSocket(7777);
		System.out.println(serverSocket.getInetAddress().toString());
		Socket hostSocket = findHost(serverSocket);
		
		while ((clientSocket = serverSocket.accept()) != null) {

			System.out.println("Connection From: " + clientSocket.getInetAddress());
			BufferedReader input = new BufferedReader(new InputStreamReader(hostSocket.getInputStream()));
			PrintWriter output = new PrintWriter(hostSocket.getOutputStream(), true);
			users.add(new Users(output, input, false));
			Thread thread = new Thread(users.get(users.size() - 1)); // sends latest user											
			thread.start();

		}

	}

	private static Socket findHost(ServerSocket serverSocket) throws IOException {
		System.out.println("Connect a host");
		Socket hostSocket = serverSocket.accept();
		System.out.println("Host found");
		BufferedReader hostInput = new BufferedReader(new InputStreamReader(hostSocket.getInputStream()));
		PrintWriter hostOutput = new PrintWriter(hostSocket.getOutputStream(), true);
		host=new Users(hostOutput, hostInput, true);
		return hostSocket;
	}
}
