import java.io.*;
import java.net.*;
import java.util.*;

public class PlartyServer {

	// static Users[] user = new Users[10];
	static ArrayList<Users> users = new ArrayList<Users>();
	static Users host;
	static boolean running;
	public static void main(String[] args) throws Exception {
		//while (true) {
			running=true;
			Socket clientSocket;
			// ServerSocket serverSocket = new
			// ServerSocket(7777,1000,InetAddress.getByName("128.62.35.149"));
			ServerSocket serverSocket = new ServerSocket(7777);
			System.out.println(serverSocket.getInetAddress().toString());
			Socket hostSocket = findHost(serverSocket);
			ServerSocket clientSockets = new ServerSocket(7778);
			while ((clientSocket = clientSockets.accept()) != null) {
			//while(running){
			//	clientSocket=clientSockets.accept();
				System.out.println("Client connected from: " + clientSocket.getInetAddress());
				// BufferedReader input = new BufferedReader(new
				// InputStreamReader(hostSocket.getInputStream()));
				// PrintWriter output = new
				// PrintWriter(hostSocket.getOutputStream(), true);
				DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
				DataInputStream input = new DataInputStream(clientSocket.getInputStream());
				users.add(new Users(output, input, false));
				Thread thread = new Thread(new Users(output, input, false)); // sends
																				// latest
																				// user
				thread.start();
			}
		//}

	}

	private static Socket findHost(ServerSocket serverSocket) throws IOException {
		System.out.println("Connect a host");
		Socket hostSocket = serverSocket.accept();
		System.out.println("Host found");
		// BufferedReader hostInput = new BufferedReader(new
		// InputStreamReader(hostSocket.getInputStream()));
		// PrintWriter hostOutput = new
		// PrintWriter(hostSocket.getOutputStream(), true);
		DataOutputStream hostOutput = new DataOutputStream(hostSocket.getOutputStream());
		DataInputStream hostInput = new DataInputStream(hostSocket.getInputStream());
		//HostListener listenEnd=new HostListener(hostInput);
		//Thread listenThread=new Thread(listenEnd);
		//listenThread.start();
		host = new Users(hostOutput, hostInput, true);
		// System.out.println("About to return");
		return hostSocket;
	}
}
