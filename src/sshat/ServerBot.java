package sshat;

import org.jibble.pircbot.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.exec.LogOutputStream;
import org.apache.commons.exec.CommandLine;

public class ServerBot extends CryptBot {
	public ServerBot(String name, String keyString) throws GeneralSecurityException {
		super(name, keyString);
	}

	public void onDecryptedMessage(String channel, String sender, String login, String hostname, String message) {
		onPrivateDecryptedMessage(sender,login,hostname,message);
	}

	public void onPrivateDecryptedMessage(final String sender, String login, String hostname, String message) {
		Executor ex = new DefaultExecutor();
		ex.setStreamHandler(new PumpStreamHandler(new LogOutputStream() {
			@Override
			protected void processLine(String line, int level){
				sendEncryptedMessage(sender, line); //send output line to caller
	    	}
		}));
		try{
			System.out.println("sender:"+sender+" login:"+login+" hostname:"+hostname+" cmd:"+message.trim());
			//CommandLine cmd = CommandLine.parse(message.trim());
			CommandLine cmd = new CommandLine("/bin/sh").addArgument("-c").addArgument(message.trim(), false);
			int exitValue = ex.execute(cmd);
		}catch(Exception e){
			e.printStackTrace();
		}
	} 
}
