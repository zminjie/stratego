package server.game;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class StrategoOutputStream {

	private DataOutputStream out;

	public StrategoOutputStream(Socket s) {

		try {
			this.out = new DataOutputStream(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void send(Message frame) {
		try {
			out.write('*'); // byte 42
			out.write(frame.getFrameType());
			out.writeShort(frame.getPayloadLength());
			out.write(frame.getPayload());

		} catch (IOException e) {
			System.out.println("Error in StrategoOutputStream send method");
			e.printStackTrace();
		}
	}

}
