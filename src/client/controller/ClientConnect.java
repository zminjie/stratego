package client.controller;

import client.protocol.C1PlayerName;
import client.protocol.C8StalemateReply;
import client.gui.MainFrame;
import client.model.Board;
import client.model.Box;
import client.model.NullBox;
import server.game.*;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class ClientConnect extends Thread{

	private Socket socket;
	private ClientController cc;
	private MainFrame mf;
	private StrategoInputStream in;
	private StrategoOutputStream out;
	private Board board;
	private NullBox[][] nb;
	private GUIController gc;
	private boolean connected = false;

	public ClientConnect(String host, int port, MainFrame mf)
	{
		try {
			socket = new Socket(host,port);
			in = new StrategoInputStream(socket.getInputStream());
			out = new StrategoOutputStream(socket);
			this.mf = mf;
			cc = mf.getCC();
			gc = mf.getGC();
			board = mf.getBoard();
			nb = board.getBoard();
			C1PlayerName cp = new C1PlayerName(cc.getPName().getBytes());
			out.send(cp);
			connected = true;
		} catch (UnknownHostException e) {
			connected = false;
			System.out.println("bad host!");
		} catch (Exception e) {
			connected = false;
			System.out.println("Connection Error");
		}
	}

	public StrategoInputStream getInput()
	{
		return in;
	}

	public StrategoOutputStream getOutput()
	{
		return out;
	}

	public void close()
	{
		try {
			socket.close();
		} catch (IOException e) {
			System.out.println("socket close error");
		}
	}

	public void run() {
		while(connected == true)
		{
			byte[]b;
			try {
				Message message = in.readFrame();
				int ft = message.getFrameType();
				switch(ft)
				{
				case 51: if(Math.round(Math.random())==0)
							mf.setMusicPath(ClassLoader.getSystemResource("resources/music/waiting.mp3"));
				else
					mf.setMusicPath(ClassLoader.getSystemResource("resources/music/waiting.mp3"));
				mf.startMusic(); break;
				case 52: b = message.getPayload();
				cc.setPlayer(b[0]);
				cc.setState(1);
				JOptionPane.showMessageDialog(mf,"You are player "+(cc.getPlayer()+1),"Inform Player Number",JOptionPane.INFORMATION_MESSAGE);
				mf.stopMusic();
				mf.setMusicPath(ClassLoader.getSystemResource("resources/music/halo.mp3"));
				mf.startMusic();
				mf.initReadyButtons();
				mf.initBottomPanel();
				break;
				case 55: mf.startState2();
				break;
				case 57: b = message.getPayload();
				if(cc.getPlayer()==1)
					gc.movePiece((Box)nb[9-b[0]][9-b[1]], (Box)nb[9-b[2]][9-b[3]], false);
				else
					gc.movePiece((Box)nb[b[0]][b[1]], (Box)nb[b[2]][b[3]], false);
				break;
				case 59: b = message.getPayload();
				Box source,target;
				if(cc.getPlayer()==1)
				{
					source = (Box)nb[9-b[4]][9-b[5]];
					target = (Box)nb[9-b[2]][9-b[3]];
				}
				else
				{
					source = (Box)nb[b[4]][b[5]];
					target = (Box)nb[b[2]][b[3]];
				}
				if(Box.getTurn())
				{
					if(b[0]==source.getPiece())
						gc.setEnemyID(b[1]);	
					else
						gc.setEnemyID(b[0]);
					gc.battle(source, target);
				}
				else
				{
					if(b[0]== target.getPiece())
						gc.setEnemyID(b[1]);
					else
						gc.setEnemyID(b[0]);
					gc.battleMystery(source, target);
				}
				break;
				case 60: b = message.getPayload();
				cc.setState(0);
				if(b[0]==cc.getPlayer()){
					mf.stopMusic();
					mf.setMusicPath(ClassLoader.getSystemResource("resources/music/victory.mp3"));
					mf.startMusic();
					JOptionPane.showMessageDialog(mf, "You won!", "Victory", JOptionPane.INFORMATION_MESSAGE);				
				}
				else{
					mf.stopMusic();
					mf.setMusicPath(ClassLoader.getSystemResource("resources/music/gameover.mp3"));
					mf.startMusic();
					JOptionPane.showMessageDialog(mf, "You Lose!", "Defeat", JOptionPane.INFORMATION_MESSAGE);
				}
				break;
				case 61: 
					int option = JOptionPane.showConfirmDialog(mf, "Accept Stalemate?", "Stalemate Request", JOptionPane.YES_NO_OPTION);
					if(option==0)
						out.send(new C8StalemateReply(new byte[]{1}));
					else
						out.send(new C8StalemateReply(new byte[]{0}));
					break;
				case 62:b = message.getPayload();
				if(b[0]==0)
					JOptionPane.showMessageDialog(mf, "Stalemate Rejected", "Rejection", JOptionPane.INFORMATION_MESSAGE);
				else{
					JOptionPane.showMessageDialog(mf, "The game ends in a draw!", "Tie", JOptionPane.INFORMATION_MESSAGE);
					cc.setState(0);
				}
				break;
				case 65: b = message.getPayload();
				mf.setChatText(new String(b));
				break;
				case 66: b = message.getPayload();
				if(b[0]==cc.getPlayer()){
					Box.setTurn(true);
					mf.startTurn();
				}
				else{
					Box.setTurn(false);
					mf.stopTurn();
				}
				break;
				default: break;
				}
			} catch (Exception e) {
				System.out.println("Message Error");
			}
		}
	}

}
