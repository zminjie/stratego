package client.protocol;

public class C1PlayerName extends ClientMessage{

	public C1PlayerName(byte[] name)
	{
		payload = name;
	}
	
	public String getUsername(){
		return new String(payload);
	}
	
	public byte getFrameType() {
		return 1;
	}
	

}
