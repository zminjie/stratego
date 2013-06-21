package client.controller;

import client.gui.MainFrame;
import client.model.Board;
import client.model.Box;

import java.awt.Point;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class GUIController {

	private MainFrame mf;
	private Board board;
	private ClientController cc;
	private int enemyid;

	public GUIController(MainFrame m)
	{
		mf = m;
		cc = mf.getCC();
	}

	public void battle(final Box source, final Box target)
	{
		board = mf.getBoard();
		new Thread(){
			public void run(){
				source.setMoving(true);
				if(source.getPiece()==9)
				{
					Box ourspace;
					if(source.getRow()>target.getRow()){
						ourspace = (Box)board.getBoard()[target.getRow()+1][target.getCol()];
						moveUp(source, ourspace,false);
						enemyid = showEnemy(target,"F1");
					}
					else if(source.getRow()<target.getRow()){
						ourspace = (Box)board.getBoard()[target.getRow()-1][target.getCol()];
						moveDown(source, ourspace,false);
						enemyid = showEnemy(target,"B1");
					}
					else if(source.getCol()<target.getCol()){
						ourspace = (Box)board.getBoard()[target.getRow()][target.getCol()-1];
						moveRight(source, ourspace,false);
						enemyid = showEnemy(target,"L1");
					}
					else{
						ourspace = (Box)board.getBoard()[target.getRow()][target.getCol()+1];
						moveLeft(source, ourspace,false);
						enemyid = showEnemy(target,"R1");
					}
					if(enemyid==9)
					{
						animate(1,target);
						animate(1,ourspace);
						target.removeSprite();
						ourspace.removeSprite();
						cc.modifyNumRem(9, false, cc.getPlayer());
						cc.modifyNumRem(9, false, (cc.getPlayer()+1)%2);
					}
					else if(enemyid==10 || enemyid==12)
					{
						animate(1,target);
						target.removeSprite();
						movePiece(ourspace,target,false);
						cc.modifyNumRem(enemyid, false, (cc.getPlayer()+1)%2);
					}
					else
					{
						if(enemyid==11)
							animate(3,ourspace);
						else
							animate(1,ourspace);
						ourspace.removeSprite();
						hideEnemy(target);
						cc.modifyNumRem(9, false, cc.getPlayer());
					}
				}
				else if(source.getPiece()== 10)
				{
					if(source.getRow()>target.getRow()){
						enemyid = showEnemy(target,"F1");
						turnSprite(source,"B1",false);
					}
					else if(source.getRow()<target.getRow()){
						enemyid = showEnemy(target,"B1");
						turnSprite(source,"F1",false);
					}
					else if(source.getCol()<target.getCol()){
						enemyid = showEnemy(target,"L1");
						turnSprite(source,"R1",false);
					}
					else{
						enemyid = showEnemy(target,"R1");
						turnSprite(source,"L1",false);
					}
					if(enemyid==1 ||enemyid==12)
					{
						animate(2,target);
						target.removeSprite();
						movePiece(source,target,false);
						cc.modifyNumRem(enemyid, false, (cc.getPlayer()+1)%2);
					}
					else if(enemyid==10)
					{
						animate(2,target);
						animate(2,source);
						target.removeSprite();
						source.removeSprite();
						source.cleanUp();
						cc.modifyNumRem(10, false, cc.getPlayer());
						cc.modifyNumRem(10, false, (cc.getPlayer()+1)%2);
					}
					else
					{
						if(enemyid==11)
							animate(3,source);
						else
							animate(1,source);
						source.removeSprite();
						hideEnemy(target);
						source.cleanUp();
						cc.modifyNumRem(10, false, cc.getPlayer());
					}
				}
				else
				{
					if(source.getRow()>target.getRow()){
						enemyid = showEnemy(target,"F1");
						turnSprite(source,"B1",false);
					}
					else if(source.getRow()<target.getRow()){
						enemyid = showEnemy(target,"B1");
						turnSprite(source,"F1",false);
					}
					else if(source.getCol()<target.getCol()){
						enemyid = showEnemy(target,"L1");
						turnSprite(source,"R1",false);
					}
					else{
						enemyid = showEnemy(target,"R1");
						turnSprite(source,"L1",false);
					}
					if(enemyid==11 && source.getPiece()==8)
					{
						animate(0,target);
						target.removeSprite();
						movePiece(source,target,false);
						cc.modifyNumRem(11, false, (cc.getPlayer()+1)%2);
					}
					else if(enemyid==11)
					{
						cc.modifyNumRem(source.getPiece(), false, cc.getPlayer());
						animate(3,source);
						source.removeSprite();
						hideEnemy(target);
						source.cleanUp();
					}
					else if(enemyid>source.getPiece())
					{
						animate(1,target);
						target.removeSprite();
						movePiece(source,target,false);
						cc.modifyNumRem(enemyid, false, (cc.getPlayer()+1)%2);
					}
					else if(enemyid==source.getPiece())
					{
						cc.modifyNumRem(source.getPiece(), false, cc.getPlayer());
						cc.modifyNumRem(enemyid, false, (cc.getPlayer()+1)%2);
						animate(1,target);
						animate(1,source);
						target.removeSprite();
						source.removeSprite();
						source.cleanUp();
					}
					else
					{
						cc.modifyNumRem(source.getPiece(), false, cc.getPlayer());
						animate(1,source);
						source.removeSprite();
						hideEnemy(target);
						source.cleanUp();
					}
				}
				mf.updateLabels();
				source.setMoving(false);
			}
		}.start();
	}
	
	public void battleMystery(final Box source, final Box target)
	{
		board = mf.getBoard();
		new Thread(){
			public void run(){
				source.setMoving(true);
				if(source.getRow()>target.getRow()){
					enemyid = showEnemy(source,"B1");
					turnSprite(target,"F1",false);
				}
				else if(source.getRow()<target.getRow()){
					enemyid = showEnemy(source,"F1");
					turnSprite(target,"B1",false);
				}
				else if(source.getCol()<target.getCol()){
					enemyid = showEnemy(source,"R1");
					turnSprite(target,"L1",false);
				}
				else{
					enemyid = showEnemy(source,"L1");
					turnSprite(target,"R1",false);
				}						
				if(enemyid==9)
				{
					Box ourspace;
					if(source.getRow()>target.getRow()){
						ourspace = (Box)board.getBoard()[target.getRow()+1][target.getCol()];
						moveUp(source, ourspace,true);
					}
					else if(source.getRow()<target.getRow()){
						ourspace = (Box)board.getBoard()[target.getRow()-1][target.getCol()];
						moveDown(source, ourspace,true);
					}
					else if(source.getCol()<target.getCol()){
						ourspace = (Box)board.getBoard()[target.getRow()][target.getCol()-1];
						moveRight(source, ourspace,true);
					}
					else{
						ourspace = (Box)board.getBoard()[target.getRow()][target.getCol()+1];
						moveLeft(source, ourspace,true);
					}
					if(target.getPiece()==9)
					{
						cc.modifyNumRem(9, false, cc.getPlayer());
						cc.modifyNumRem(9, false, (cc.getPlayer()+1)%2);
						animate(1,target);
						animate(1,ourspace);
						target.removeSprite();
						ourspace.removeSprite();
					}
					else if(target.getPiece()==10 || target.getPiece()==12)
					{
						cc.modifyNumRem(target.getPiece(), false, cc.getPlayer());
						animate(1,target);
						target.removeSprite();
						hideEnemy(ourspace);
						movePiece(ourspace,target,true);
					}
					else
					{
						cc.modifyNumRem(9, false, (cc.getPlayer()+1)%2);
						if(target.getPiece()==11)
							animate(3,ourspace);
						else
							animate(1,ourspace);
						ourspace.removeSprite();
					}
				}
				else if(enemyid== 10)
				{
					if(target.getPiece()==1 ||target.getPiece()==12)
					{
						cc.modifyNumRem(target.getPiece(), false, cc.getPlayer());
						animate(2,target);
						target.removeSprite();
						hideEnemy(source);
						movePiece(source,target,true);
					}
					else if(target.getPiece()==10)
					{
						cc.modifyNumRem(10, false, cc.getPlayer());
						cc.modifyNumRem(10, false, (cc.getPlayer()+1)%2);
						animate(2,target);
						animate(2,source);
						target.removeSprite();
						source.removeSprite();
					}
					else
					{
						cc.modifyNumRem(10, false, (cc.getPlayer()+1)%2);
						if(target.getPiece()==11)
							animate(3,source);
						else
							animate(1,source);
						source.removeSprite();
					}
				}
				else
				{
					if(target.getPiece()==11 && enemyid==8)
					{
						cc.modifyNumRem(11, false, cc.getPlayer());
						animate(0,target);
						target.removeSprite();
						hideEnemy(source);
						movePiece(source,target,true);			
					}
					else if(target.getPiece()==11)
					{
						cc.modifyNumRem(enemyid, false, (cc.getPlayer()+1)%2);
						animate(3,source);
						source.removeSprite();
					}
					else if(target.getPiece()>enemyid)
					{
						cc.modifyNumRem(target.getPiece(), false, cc.getPlayer());
						animate(1,target);
						target.removeSprite();
						hideEnemy(source);
						movePiece(source,target,true);
					}
					else if(target.getPiece()==enemyid)
					{
						cc.modifyNumRem(target.getPiece(), false, cc.getPlayer());
						cc.modifyNumRem(enemyid, false, (cc.getPlayer()+1)%2);
						animate(1,target);
						animate(1,source);
						target.removeSprite();
						source.removeSprite();
					}
					else
					{
						cc.modifyNumRem(enemyid, false, (cc.getPlayer()+1)%2);
						animate(1,source);
						source.removeSprite();
					}
				}				
				mf.updateLabels();
				source.setMoving(false);
			}
		}.start();		
	}
	
	public void setEnemyID(int i)
	{
		enemyid = i;
	}
	
	private void turnSprite(Box source, String dir, boolean isEnemy)
	{
		int id = source.getPiece();
		URL imgURL = ClassLoader.getSystemResource(cc.getPieceURL(id,dir,isEnemy));
		ImageIcon icon = new ImageIcon(imgURL, "figure");
		JLabel sprite = new JLabel(icon);
		source.removeSprite();
		sprite.setBounds(0,0,60,60);
		source.setSprite(sprite);
		source.setPiece(id);
	}
	
	private int showEnemy(Box enemy, String dir)
	{
		int id = enemyid;
		URL imgURL = ClassLoader.getSystemResource(cc.getPieceURL(id,dir,true));
		ImageIcon icon = new ImageIcon(imgURL, "figure");
		JLabel sprite = new JLabel(icon);
		enemy.removeSprite();
		sprite.setBounds(0,0,60,60);
		enemy.setSprite(sprite);
		enemy.setPiece(id);
		return id;
	}
	
	private void hideEnemy(Box enemy)
	{
		URL imgURL = ClassLoader.getSystemResource(cc.getPieceURL(13,"F1",true));
		ImageIcon icon = new ImageIcon(imgURL, "figure");
		JLabel sprite = new JLabel(icon);
		enemy.removeSprite();
		sprite.setBounds(0,0,60,60);
		enemy.setSprite(sprite);
		enemy.setPiece(13);
	}

	private void animate(int code, final Box target)
	{
		String[] seq = cc.getAnimationURL(code);
		final JLabel[] jp = new JLabel[seq.length];
		for(int i=0;i<jp.length;i++)
		{
			URL imgURL = ClassLoader.getSystemResource(seq[i]);
			ImageIcon icon = new ImageIcon(imgURL, "figure");
			jp[i] = new JLabel(icon);
			jp[i].setBounds(0,0,60,60);
		}

		for(int i=0;i<jp.length;i++)
		{
			target.add(jp[i],0);
			delay(100);
			target.remove(jp[i]);
		}
	}

	public void movePiece(final Box source, final Box target, final boolean isEnemy)
	{
		board = mf.getBoard();
		new Thread(){
			public void run(){
				source.setMoving(true);
				if(source.getRow()>target.getRow())
					moveUp(source,target,isEnemy);
				else if(source.getRow()<target.getRow())
					moveDown(source,target,isEnemy);
				else if(source.getCol()<target.getCol())
					moveRight(source,target,isEnemy);
				else
					moveLeft(source,target,isEnemy);
				source.setMoving(false);
			}
		}.start();

	}

	private void moveUp(final Box source, final Box target, final boolean isEnemy)
	{
		JLabel[] sprites = new JLabel[4];
		int id = source.getPiece();
		URL imgURL = ClassLoader.getSystemResource(cc.getPieceURL(id,"B1",isEnemy));
		ImageIcon icon = new ImageIcon(imgURL, "figure");
		sprites[0] = new JLabel(icon);
		imgURL = ClassLoader.getSystemResource(cc.getPieceURL(id,"B2",isEnemy));
		icon = new ImageIcon(imgURL, "figure");
		sprites[1] = new JLabel(icon);
		imgURL = ClassLoader.getSystemResource(cc.getPieceURL(id,"B3",isEnemy));
		icon = new ImageIcon(imgURL, "figure");
		sprites[2] = new JLabel(icon);
		imgURL = ClassLoader.getSystemResource(cc.getPieceURL(id,"B4",isEnemy));
		icon = new ImageIcon(imgURL, "figure");
		sprites[3] = new JLabel(icon);

		Point ps = SwingUtilities.convertPoint(source, new Point(0,0), board);
		Point pt = SwingUtilities.convertPoint(target, new Point(0,0), board);
		source.removeSprite();

		int x=0;
		for(int i=ps.y;i>=pt.y;i-=10)
		{
			sprites[x].setBounds(ps.x, i, 60, 60);
			board.add(sprites[x]);
			board.repaint();
			delay(100);
			board.remove(sprites[x++]);
			board.repaint();
			if(x==4)
				x=0;
		}
		sprites[0].setBounds(0,0,60,60);
		target.setSprite(sprites[0]);
		target.setPiece(id);
		source.cleanUp();
	}

	private void moveDown(Box source, Box target, final boolean isEnemy)
	{
		JLabel[] sprites = new JLabel[4];
		int id = source.getPiece();
		URL imgURL = ClassLoader.getSystemResource(cc.getPieceURL(id,"F1",isEnemy));
		ImageIcon icon = new ImageIcon(imgURL, "figure");
		sprites[0] = new JLabel(icon);
		imgURL = ClassLoader.getSystemResource(cc.getPieceURL(id,"F2",isEnemy));
		icon = new ImageIcon(imgURL, "figure");
		sprites[1] = new JLabel(icon);
		imgURL = ClassLoader.getSystemResource(cc.getPieceURL(id,"F3",isEnemy));
		icon = new ImageIcon(imgURL, "figure");
		sprites[2] = new JLabel(icon);
		imgURL = ClassLoader.getSystemResource(cc.getPieceURL(id,"F4",isEnemy));
		icon = new ImageIcon(imgURL, "figure");
		sprites[3] = new JLabel(icon);

		Point ps = SwingUtilities.convertPoint(source, new Point(0,0), board);
		Point pt = SwingUtilities.convertPoint(target, new Point(0,0), board);
		source.removeSprite();

		int x=0;
		for(int i=ps.y;i<=pt.y;i+=10)
		{
			sprites[x].setBounds(ps.x, i, 60, 60);
			board.add(sprites[x]);
			board.repaint();
			delay(100);
			board.remove(sprites[x++]);
			board.repaint();
			if(x==4)
				x=0;
		}
		sprites[0].setBounds(0,0,60,60);
		target.setSprite(sprites[0]);
		target.setPiece(id);
		source.cleanUp();
	}

	private void moveLeft(Box source, Box target, final boolean isEnemy)
	{
		JLabel[] sprites = new JLabel[4];
		int id = source.getPiece();
		URL imgURL = ClassLoader.getSystemResource(cc.getPieceURL(id,"L1",isEnemy));
		ImageIcon icon = new ImageIcon(imgURL, "figure");
		sprites[0] = new JLabel(icon);
		imgURL = ClassLoader.getSystemResource(cc.getPieceURL(id,"L2",isEnemy));
		icon = new ImageIcon(imgURL, "figure");
		sprites[1] = new JLabel(icon);
		imgURL = ClassLoader.getSystemResource(cc.getPieceURL(id,"L3",isEnemy));
		icon = new ImageIcon(imgURL, "figure");
		sprites[2] = new JLabel(icon);
		imgURL = ClassLoader.getSystemResource(cc.getPieceURL(id,"L4",isEnemy));
		icon = new ImageIcon(imgURL, "figure");
		sprites[3] = new JLabel(icon);

		Point ps = SwingUtilities.convertPoint(source, new Point(0,0), board);
		Point pt = SwingUtilities.convertPoint(target, new Point(0,0), board);
		source.removeSprite();

		int x=0;
		for(int i=ps.x;i>=pt.x;i-=10)
		{
			sprites[x].setBounds(i, ps.y, 60, 60);
			board.add(sprites[x]);
			board.repaint();
			delay(100);
			board.remove(sprites[x++]);
			board.repaint();
			if(x==4)
				x=0;
		}
		sprites[0].setBounds(0,0,60,60);
		target.setSprite(sprites[0]);
		target.setPiece(id);
		source.cleanUp();
	}

	private void moveRight(Box source, Box target, final boolean isEnemy)
	{
		JLabel[] sprites = new JLabel[4];
		int id = source.getPiece();
		URL imgURL = ClassLoader.getSystemResource(cc.getPieceURL(id,"R1",isEnemy));
		ImageIcon icon = new ImageIcon(imgURL, "figure");
		sprites[0] = new JLabel(icon);
		imgURL = ClassLoader.getSystemResource(cc.getPieceURL(id,"R2",isEnemy));
		icon = new ImageIcon(imgURL, "figure");
		sprites[1] = new JLabel(icon);
		imgURL = ClassLoader.getSystemResource(cc.getPieceURL(id,"R3",isEnemy));
		icon = new ImageIcon(imgURL, "figure");
		sprites[2] = new JLabel(icon);
		imgURL = ClassLoader.getSystemResource(cc.getPieceURL(id,"R4",isEnemy));
		icon = new ImageIcon(imgURL, "figure");
		sprites[3] = new JLabel(icon);

		Point ps = SwingUtilities.convertPoint(source, new Point(0,0), board);
		Point pt = SwingUtilities.convertPoint(target, new Point(0,0), board);
		source.removeSprite();

		int x=0;
		for(int i=ps.x;i<=pt.x;i+=10)
		{
			sprites[x].setBounds(i, ps.y, 60, 60);
			board.add(sprites[x]);
			board.repaint();
			delay(100);
			board.remove(sprites[x++]);
			board.repaint();
			if(x==4)
				x=0;
		}
		sprites[0].setBounds(0,0,60,60);
		target.setSprite(sprites[0]);
		target.setPiece(id);
		source.cleanUp();
	}

	private void delay(int wait)
	{
		try {
			Thread.sleep(wait);
		} catch (InterruptedException e) {
		}
	}

}
