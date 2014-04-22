package networking;

import java.nio.ByteBuffer;

// clasa care transforma mesajele in ByteBuffere
public class MessageToByte {

	static public ByteBuffer requestChunck(int chunckID,String filename)
	{
		byte filenameByte[] = filename.getBytes();
		ByteBuffer ret = ByteBuffer.allocate(12+filenameByte.length);
		ret.putInt(8 + filenameByte.length);
		ret.putInt(ServerConstants.RequestChunckType);
		ret.putInt(chunckID);
		ret.put(filenameByte);
		ret.flip();
		return ret;
	}
	
	static public ByteBuffer responseChunck(int chunckID,String filename,byte[] chunckData)
	{
		
		byte filenameByte[] = filename.getBytes();
		ByteBuffer ret = ByteBuffer.allocate(12+filenameByte.length+chunckData.length);
		ret.clear();
		ret.putInt(8+filenameByte.length+chunckData.length);
		ret.putInt(ServerConstants.ResponseChunckDataType);
		ret.putInt(chunckData.length);
		ret.put(chunckData);
		ret.put(filenameByte);
		ret.flip();
		return ret;
	}
	
	static public ByteBuffer responseGetChunckNumber(String filename,int chunckNumber)
	{
		
		byte filenameByte[] = filename.getBytes();
		ByteBuffer ret = ByteBuffer.allocate(12+filenameByte.length);
		ret.clear();
		ret.putInt(filenameByte.length+8);
		ret.putInt(ServerConstants.ResponseGetChunckNumber);
		ret.putInt(chunckNumber);
		ret.put(filenameByte);
		ret.flip();
		return ret;
	}
	
	static public ByteBuffer requestGetChunckNumber(String filename)
	{
		
		byte filenameByte[] = filename.getBytes();
		ByteBuffer ret = ByteBuffer.allocate(8+filenameByte.length);
		ret.clear();
		ret.putInt(filenameByte.length+4);
		ret.putInt(ServerConstants.RequestGetChunckNumber);
		ret.put(filenameByte);
		
		ret.flip();
		return ret;
	}
	
	
	
}
