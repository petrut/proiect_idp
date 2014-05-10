package web;

import java.io.IOException;
import common.InfoTransfers;

public interface WebINetwork {
	public void retrieveFile(InfoTransfers filename, String ipFrom, int portFrom)
			throws IOException ;
	public void start_server(Integer port) throws IOException;
	void transmitInfo(int tip, String info_req, String ipFrom, int portFrom)
			throws IOException;
}
