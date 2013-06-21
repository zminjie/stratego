package client.controller;

public class ClientController {

	private int state; //0-idle,1-setup, 2-play
	private int player,piece; //piece:0 is nothing selected,spy=10,bomb=11,flag=12
	private int[] numRem0,numRem1;
	private String pName;

	public ClientController(int p)
	{
		player = p;
		state = 0;
		numRem0 = new int[]{1,1,2,3,4,4,4,5,8,1,6,1};
		numRem1 = new int[]{1,1,2,3,4,4,4,5,8,1,6,1};
		piece = 0;
	}

	public boolean isReady()
	{
		if(player==0)
		{
			for(int i:numRem0)
				if(i!=0)
					return false;
		}
		else
		{
			for(int i:numRem1)
				if(i!=0)
					return false;
		}
		return true;
	}
	
	public void resetnumRem()
	{
		numRem0 = new int[]{1,1,2,3,4,4,4,5,8,1,6,1};
		numRem1 = new int[]{1,1,2,3,4,4,4,5,8,1,6,1};
	}
	
	public void setName(String name)
	{
		pName = name;
	}

	public void setState(int s)
	{
		state = s;
	}

	public void setPiece(int p)
	{
		piece = p;
	}
	
	public void setPlayer(int p)
	{
		player = p;
	}

	public String getPName()
	{
		return pName;
	}
	
	public int getState()
	{
		return state;
	}

	public int getPiece()
	{
		return piece;
	}

	public int[] getnumRem(int p)
	{
		if(p==0)
			return numRem0;
		return numRem1;
	}

	public int getPlayer()
	{
		return player;
	}

	public String[] getAnimationURL(int code) //0-bomb 1-slash 2-spy
	{
		String[] ani;
		switch(code)
		{
		case 0: ani = new String[11];
		for(int i=0;i<ani.length;i++)
			ani[i] = "resources/sprites/Explosion/"+(i+1)+".png";
		return ani;
		case 1: ani = new String[20];
		for(int i=0;i<ani.length;i++)
			ani[i] = "resources/sprites/Slash/"+(i+1)+".png";
		return ani;
		case 2: ani = new String[21];
		for(int i=0;i<ani.length;i++)
			ani[i] = "resources/sprites/Spykill/"+(i+1)+".png";
		return ani;
		case 3: ani = new String[20];
		for(int i=0;i<ani.length;i++)
			ani[i] = "resources/sprites/BombKill/"+(i+1)+".png";
		return ani;
		default: return null;
		}
	}

	public String getPieceURL(int id, String dir, boolean enemy)
	{
		String color ="";
		if(!enemy){
			color = "Blue/B";
			if(player==1)
				color = "Red/R";
		}
		else
		{
			color = "Red/R";
			if(player==1)
				color = "Blue/B";
		}
		switch(id)
		{
		case 1: return "resources/sprites/Marshal"+color+"marshal"+dir+".png";
		case 2: return "resources/sprites/General"+color+"general"+dir+".png";
		case 3: return "resources/sprites/Colonel"+color+"colonel"+dir+".png";
		case 4: return "resources/sprites/Major"+color+"major"+dir+".png";
		case 5: return "resources/sprites/Captain"+color+"captain"+dir+".png";
		case 6: return "resources/sprites/Lieutenant"+color+"lieutenant"+dir+".png";
		case 7: return "resources/sprites/Sargeant"+color+"sargeant"+dir+".png";
		case 8: return "resources/sprites/Miner"+color+"miner"+dir+".png";
		case 9: return "resources/sprites/Scout"+color+"scout"+dir+".png";
		case 10: return "resources/sprites/Spy/Spy"+dir+".png";
		case 11: return "resources/sprites/bomb.png";
		case 12: return "resources/sprites/Flag" + color + "flag.gif";
		case 13: return "resources/sprites/Mystery/mystery" + dir + ".png";
		default: return null;
		}
	}

	public void modifyNumRem(int id, boolean inc, int pl)
	{
		if(pl == 0){
			if(inc)
				numRem0[id-1] = ++numRem0[id-1];
			else
				numRem0[id-1] = --numRem0[id-1];
		}
		else
		{
			if(inc)
				numRem1[id-1] = ++numRem1[id-1];
			else
				numRem1[id-1] = --numRem1[id-1];
		}
	}
}
