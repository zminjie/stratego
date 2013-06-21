package server.controller;

import server.protocol.ServerMessage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerSender {

	Socket socket;
	DataOutputStream out;

	public ServerSender(Socket s) {
		socket = s;

		try {
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void send(ServerMessage frame) {
		try {
			out.write('*'); // byte 42
			out.write(frame.getFrameType());
			out.writeShort(frame.getPayloadLength());
			out.write(frame.getPayload());

		} catch (IOException e) {
			System.out.println("Error in ServerSender send method");
			e.printStackTrace();
		}
	}

}
