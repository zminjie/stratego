package client.protocol;

public class C8StalemateReply extends ClientMessage {

	boolean reply;
	
	public C8StalemateReply(byte array[])
	{
		payload = array;
		if(payload[0]==(byte)1)
			reply = true;
		else
			reply = false;
	}
	
	public byte getFrameType() {
		return 8;
	}
	
	 /**
	  * byte array is {stalemate reply}
	  */

}
