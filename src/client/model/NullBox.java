package client.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class NullBox extends JLayeredPane{

	protected int row,col;
	protected int piece;


	public NullBox(int r, int c)
	{
		setSize(60,60);
		setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		setPreferredSize(new Dimension(60,60));
		setMinimumSize(new Dimension(60,60));
		row = r;
		col = c;
		piece = 0;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getCol()
	{
		return col;
	}
	
	public int getPiece()
	{
		return piece;
	}
	
	public void setPiece(int id)
	{
		piece=id;
	}
}
