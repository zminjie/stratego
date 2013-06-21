/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client.model;
import client.controller.ClientController;
import client.controller.GUIController;
import client.gui.MainFrame;
import client.protocol.C2PlacePiece;
import client.protocol.C3PieceRemove;
import client.protocol.C5PlayerTurn;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;
/**
 *
 * @author ss37
 */
public class Box extends NullBox implements MouseListener{

	private static Box selected;
	private static ArrayList<Box> movable;
	private static boolean moving, turn;
	private JLabel jp;
	private ClientController cc;
	private MainFrame mf;
	private ArrayList<Box> neighbors,cross;
	private Board board;
	private GUIController gc;


	public Box(int r, int c, MainFrame frame, Board bo)
	{
		super(r, c);
		cc = frame.getCC();
		mf = frame;
		board = bo;
		selected = null;
		gc = frame.getGC();
		movable = new ArrayList<Box>();
		moving = false;
		turn = false;
		this.addMouseListener(this);
	}
	
	public static void setTurn(boolean t)
	{
		turn = t;
	}
	
	public static boolean getTurn()
	{
		return turn;
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
		Box source = (Box)e.getSource();
		Point p = SwingUtilities.convertPoint(source, e.getPoint(), source.getParent());
		NullBox target = (NullBox)source.getParent().getComponentAt(p);
		if(target == null || source != target )
			return;
		else if(cc.getState()==1 && row>5){
			processClick(e);
		}
		else if(cc.getState()==2)
		{
			if(turn)		
				processMove(e);
		}
	}
	
	public JLabel getSprite()
	{
		return jp;
	}
	
	public void setSprite(JLabel j)
	{
		jp = j;
		jp.setBounds(0, 0, 60, 60);
		add(jp);
	}
	
	public void removeSprite()
	{
		remove(jp);
		jp = null;
		piece=0;
		repaint();
	}
	
	public void cleanUp()
	{
		selected = null;
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		for(Box b : movable)
		{
			b.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
	}
	
	public void setMoving(boolean boo)
	{
		moving = boo;
	}

	private void processClick(MouseEvent e)
	{
		//place piece
		if(e.getButton()==1 && piece==0 && cc.getPieceURL(cc.getPiece(),"B1",false)!=null && cc.getnumRem(cc.getPlayer())[cc.getPiece()-1] >0){
			int id = cc.getPiece();
			URL imgURL = ClassLoader.getSystemResource(cc.getPieceURL(cc.getPiece(),"B1",false));
			final ImageIcon icon = new ImageIcon(imgURL, "figure");
			jp = new JLabel(icon);
			jp.setBounds(0, 0, 60, 60);
			add(jp);
			piece = id;
			cc.modifyNumRem(id, false, cc.getPlayer());
			byte[] b = new byte[]{(byte) row,(byte) col, (byte) piece};
			if(cc.getPlayer()==1)
				for(int i=0; i<b.length-1; i++)
					b[i] = (byte) (9-b[i]);
			(mf.getClientConnect().getOutput()).send(new C2PlacePiece(b));
		}
		else if(e.getButton()==3 && jp!=null)
		{
			cc.modifyNumRem(piece, true, cc.getPlayer());
			byte[] b = new byte[]{(byte) row,(byte) col, (byte) piece};
			if(cc.getPlayer()==1)
				for(int i=0; i<b.length-1; i++)
					b[i] = (byte) (9-b[i]);
			(mf.getClientConnect().getOutput()).send(new C3PieceRemove(b));
			removeSprite();
			repaint();		
		}
		mf.updateLabels();

	}

	private void processMove(MouseEvent e)
	{
		//get the moves
		neighbors = board.getNeighbors(this);
		cross = board.getCross(this);
		if(e.getButton()==1 && piece !=0 && piece !=13 && selected==null && !moving) //select piece when nothing selected
		{
			setBorder(BorderFactory.createLineBorder(Color.CYAN,3));
			selected = this;
			showSelectedMove();
		}
		else if(e.getButton()==1 && piece !=0 && piece !=13 && selected!=this && !moving) //something else is selected
		{
			selected.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			for(Box b : selected.cross)
			{
				b.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			}
			setBorder(BorderFactory.createLineBorder(Color.CYAN,3));
			selected = this;			
			showSelectedMove();
		}
		else if(e.getButton()==1 && piece !=0 && piece !=13 && !moving) //same piece is selected
		{
			cleanUp();
		}
		else if(e.getButton()==1 && piece == 0 && selected!=null && movable.contains(this) && !moving)
		{
			gc.movePiece(selected, this, false);
			byte[] b = new byte[]{(byte) selected.getRow(),(byte) selected.getCol(),
					(byte) this.getRow(),(byte)this.getCol(),(byte) selected.piece};
			if(cc.getPlayer()==1)
				
				for(int i=0; i<b.length-1; i++)
					b[i] = (byte) (9-b[i]);
			(mf.getClientConnect().getOutput()).send(new C5PlayerTurn(b));
		}
		else if(e.getButton()==1 && piece == 13 && selected!=null && movable.contains(this) && !moving)
		{
			byte[] b = new byte[]{(byte) selected.getRow(),(byte) selected.getCol(),
					(byte) this.getRow(),(byte)this.getCol(),(byte) selected.piece};
			if(cc.getPlayer()==1)
				for(int i=0; i<b.length-1; i++)
					b[i] = (byte) (9-b[i]);		
			(mf.getClientConnect().getOutput()).send(new C5PlayerTurn(b));
		}
	}

	private void showSelectedMove()
	{
		movable = new ArrayList<Box>();
		if(piece == 9)
		{
			for(Box b : cross)
			{
				if(b.piece==0 || b.cc.getPlayer()!=cc.getPlayer() || b.piece ==13)
				{
					b.setBorder(BorderFactory.createLineBorder(Color.YELLOW,3));
					movable.add(b);
				}
			}
		}
		else if(piece >0 && piece<11)
		{
			for(Box b : neighbors)
			{
				if(b.piece==0 || b.piece==13 || b.cc.getPlayer()!=cc.getPlayer())
				{
					b.setBorder(BorderFactory.createLineBorder(Color.YELLOW,3));
					movable.add(b);
				}
			}
		}
	}
	
	public String toString()
	{
		return "Box: " + row + "," + col;
	}

}
