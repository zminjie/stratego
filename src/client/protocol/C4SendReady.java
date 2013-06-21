package client.protocol;

public class C4SendReady extends ClientMessage {

	boolean ready;
	
	public C4SendReady(byte[] array)
	{
		payload = array;
		if(payload[0]==(byte)1)
			ready = true;
		else
			ready = false;
		
	}
	
	public byte getFrameType() {
		return 4;
	}
	
	 
	 /**
	  * byte array is {ready}
	  */
}
