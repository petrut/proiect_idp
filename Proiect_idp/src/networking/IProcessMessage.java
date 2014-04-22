package networking;

import common.IMediator;

public interface IProcessMessage {
	public boolean proccesMessage(SocketOperationAPI sockOP);
	public void setMediator(IMediator med);
}
