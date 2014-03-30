package common;

import java.util.List;

// interfata mediator

public interface IMediator {
	public void setUp();
	public void addTransfer(InfoTransfers it);
	public void removeTransfer(InfoTransfers it);
	public List<InfoTransfers> getUnfinishedTransfers();
}
