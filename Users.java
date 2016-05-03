import java.io.*;

class Users implements Runnable {

	DataOutputStream out;
	DataInputStream in;
	Users[] user = new Users[10];
	String name;
	int myNum;
	boolean host;

//	public Users(PrintWriter out, BufferedReader in, Users[] user, int myNum) {
//		this.out = out;
//		this.in = in;
//		this.user = user;
//		this.myNum = myNum;
//	}

	public Users(DataOutputStream arg1, DataInputStream arg2, boolean arg3) {
		out = arg1;
		in = arg2;
		host = arg3;
	}

	public void run() {
		try {
			while(true)
			{
			String message=in.readUTF();
			System.out.println(message);
			sendToHost(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static synchronized void sendToHost(String request) throws IOException {
		System.out.println("About to send: "+request);
		PlartyServer.host.out.writeUTF(request);
		
	}
}