import java.io.*;

class Users implements Runnable {

	PrintWriter out;
	BufferedReader in;
	Users[] user = new Users[10];
	String name;
	int myNum;
	boolean host;

	public Users(PrintWriter out, BufferedReader in, Users[] user, int myNum) {
		this.out = out;
		this.in = in;
		this.user = user;
		this.myNum = myNum;
	}

	public Users(PrintWriter arg1, BufferedReader arg2, boolean arg3) {
		out = arg1;
		in = arg2;
		host = arg3;
	}

	public void run() {
		try {
			sendToHost(in.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static synchronized void sendToHost(String request) {
		PlartyServer.host.out.println(request);
		
	}
}