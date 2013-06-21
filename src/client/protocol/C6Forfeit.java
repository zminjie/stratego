package client.protocol;

public class C6Forfeit extends ClientMessage {

	boolean forfeit;
	
	public C6Forfeit(byte[] array)
	{
		payload = array;
		if(payload[0]==(byte)1)
			forfeit = true;
		else
			forfeit = false;
	}
	
	public byte getFrameType() {
		return 6;
	}


	 /**
	  * byte array is {forfeit}
	  */
	 
	
}
