package server.game;

public abstract class Message {

	protected byte[] payload;

	public abstract byte getFrameType();

	public byte[] getPayload() {
		return payload;
	}

	final public short getPayloadLength() {
		return (short) getPayload().length;
	}

	// @author Cytron
	public static void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (Exception e) {
			throw new Error("cannot happen " + e);
		}
	}
	
}
