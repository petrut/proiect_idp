package common;

import java.util.List;

public interface IMediator {
	public void setUp();
	public void addTransfer(InfoTransfers it);
	public List<InfoTransfers> getUnfinishedTransfers();
}
