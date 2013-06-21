/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Board.java
 *
 * Created on Apr 22, 2010, 4:38:46 PM
 */

package client.model;

import client.gui.MainFrame;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class Board extends javax.swing.JPanel {

	private String map;
	private NullBox[][] board;
	private MainFrame mf;
	
    public Board(MainFrame frame) {
        map = "resources/sprites/classicmap.jpeg";
        board = new NullBox[10][10];
        mf = frame;
    	initComponents();      
    }

    public void paintComponent(Graphics g)
    {
    	URL imgURL = ClassLoader.getSystemResource(map);
    	Image img = new ImageIcon(imgURL).getImage();
    	g.drawImage(img, 0, 0, null);
    }
    
    public void changeMap()
    {
    	if(map.equals("resources/sprites/classicmap.jpeg"))
    		map = "resources/sprites/ruinsmap.jpeg";
    	else
    		map = "resources/sprites/classicmap.jpeg";
    	repaint();
    }
    
    public void fillEnemies()
    {
    	if(mf.getCC().getState()==2)
    	{
    		URL imgURL = ClassLoader.getSystemResource(mf.getCC().getPieceURL(13,"F1",false));
    		ImageIcon icon = new ImageIcon(imgURL, "figure");
    		for(int i =0;i<4;i++)
    			for(int j=0;j<10;j++)
    			{
    				JLabel jp = new JLabel(icon);
    				jp.setBounds(0, 0, 60, 60);
    				((Box)board[i][j]).setSprite(jp);
    				board[i][j].setPiece(13);
    			}
    	}
    }
    
    public NullBox[][] getBoard()
    {
    	return board;
    }
    
    private void initComponents() {
        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(600, 600));
        setMinimumSize(new java.awt.Dimension(600, 600));
        setPreferredSize(new java.awt.Dimension(600, 600));
        setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));
        
        for(int i=0;i<board.length;i++)
        	for(int j=0;j<board[i].length;j++){
        		if((i==4 || i==5) && (j==2 || j==3 || j==6 || j==7))
        			board[i][j] = new NullBox(i,j);
        		else
        			board[i][j] = new Box(i,j, mf, this);
        		add(board[i][j]);
        	}
    }
    
    public ArrayList<Box> getCross(Box box)
    {
    	int row = box.row;
    	int col = box.col;
    	ArrayList<Box> boxes = new ArrayList<Box>();
    	if(row!=0)
    	{
    		int first = 0;
    		for(int i=row-1;i>=0;i--)
    		{
    			if(board[i][col] instanceof Box && board[i][col].getPiece()==0 && first ==0)
    				boxes.add((Box)board[i][col]);
    			else if(board[i][col] instanceof Box && board[i][col].getPiece()==13 && first ==0)
    			{
    				boxes.add((Box)board[i][col]);
    				first++;
    			}
    			else break;
    		}
    	}
    	if(col!=0)
    	{
    		int first = 0;
    		for(int i=col-1;i>=0;i--)
    		{
    			if(board[row][i] instanceof Box && board[row][i].getPiece()==0)
    				boxes.add((Box)board[row][i]);
    			else if(board[row][i] instanceof Box && board[row][i].getPiece()==13 && first ==0)
    			{
    				boxes.add((Box)board[row][i]);
    				first++;
    			}
    			else break;
    		}
    	}
    	if(row!=9)
    	{
    		int first = 0;
    		for(int i=row+1;i<=9;i++)
    		{
    			if(board[i][col] instanceof Box && board[i][col].getPiece()==0)
    				boxes.add((Box)board[i][col]);
    			else if(board[i][col] instanceof Box && board[i][col].getPiece()==13 && first ==0)
    			{
    				boxes.add((Box)board[i][col]);
    				first++;
    			}
    			else break;
    		}
    	}
    	if(col!=9)
    	{
    		int first =0;
    		for(int i=col+1;i<=9;i++)
    		{
    			if(board[row][i] instanceof Box && board[row][i].getPiece()==0)
    				boxes.add((Box)board[row][i]);
    			else if(board[row][i] instanceof Box && board[row][i].getPiece()==13 && first ==0)
    			{
    				boxes.add((Box)board[row][i]);
    				first++;
    			}
    			else break;
    		}
    	}
    	return boxes;
    }
    
    public ArrayList<Box> getNeighbors(Box box)
    {
    	int row = box.row;
    	int col = box.col;
    	ArrayList<Box> boxes = new ArrayList<Box>();
    	if(row!=0)
    	{
    		if(board[row-1][col] instanceof Box)
    		{
    			boxes.add((Box)board[row-1][col]);
    		}
    	}
    	if(col!=0)
    	{
    		if(board[row][col-1] instanceof Box)
    		{
    			boxes.add((Box)board[row][col-1]);
    		}
    	}
    	if(row!=9)
    	{
    		if(board[row+1][col] instanceof Box)
    		{
    			boxes.add((Box)board[row+1][col]);
    			
    		}
    	}
    	if(col!=9)
    	{
    		if(board[row][col+1] instanceof Box)
    		{
    			boxes.add((Box)board[row][col+1]);
    		}
    	}
    	return boxes;
    }
}
