package networking;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import common.IMediator;
import common.InfoTransfers;

public interface INetwork {
	public void retrieveFile(InfoTransfers filename,String ipFrom, int portFrom)
			throws IOException ;
	public void start_server(Integer port) throws IOException;
	public void setMediator(IMediator med);
}
