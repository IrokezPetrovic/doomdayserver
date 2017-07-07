package org.doomday.server;

import org.doomday.server.protocol.IProtocolProcessor;

public interface ITransport {

	void disconnect(IProtocolProcessor protocolProcessor);

}
