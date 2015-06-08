package sshat;

import java.io.Console;
import java.io.IOException;

import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;

public class Client {
	public static void main(String[] args) throws Exception {
		Options options = new Options();
		options.addOption(OptionBuilder.withLongOpt("user")
                                   .withDescription( "IRC username" )
                                   .hasArg()
                                   .withArgName("username")
                                   .create("u"));
		options.addOption(OptionBuilder.withLongOpt("server")
                                   .withDescription( "Server IRC name" )
                                   .hasArg()
                                   .withArgName("server-name")
                                   .create("s"));
		CommandLineParser parser = new BasicParser();
		CommandLine cmd = parser.parse(options, args);

		Console console = System.console();
		String user = cmd.getOptionValue("u");
		if(user==null){
			user = console.readLine("Enter username: ");
		}
		ClientBot clientBot = new ClientBot(user, console.readPassword("Enter encryption keyword: "));
		String server = cmd.getOptionValue("s");
		clientBot.connect("irc.freenode.net");
		while(true){
			String line = console.readLine().trim();
			if("exit".equals(line)) break;
			clientBot.sendEncryptedMessage(server, line);
		}
		System.out.println("Exited");
		clientBot.disconnect();
		clientBot.dispose();
	}
}
