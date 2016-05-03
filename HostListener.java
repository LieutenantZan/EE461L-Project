import java.net.*;
import java.util.*;
import java.io.*;

public class HostListener implements Runnable {
	DataInputStream input;

	HostListener(DataInputStream arg1) {
		input = arg1;
	}

	public void run() {
		while (true) {
			try {
				String message=input.readUTF();
				if (message.equals("END")) {
					PlartyServer.running=false;
					System.out.println("Host has left");
					return;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
