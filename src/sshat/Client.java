package sshat;

import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;

import java.util.Scanner;

public class Client {
	public static void main(String[] args) throws Exception {
		Options options = new Options();
		options.addOption(OptionBuilder.withLongOpt("password")
			.withDescription("Encryption keyword")
			.hasArg()
			.withArgName("password")
			.create("pwd"));
		options.addOption(OptionBuilder.withLongOpt("client")
                                   .withDescription( "Client IRC name" )
                                   .hasArg()
                                   .withArgName("name")
                                   .create("c"));
		options.addOption(OptionBuilder.withLongOpt("server")
                                   .withDescription( "Server IRC name" )
                                   .hasArg()
                                   .withArgName("name")
                                   .create("s"));
		CommandLineParser parser = new BasicParser();
		CommandLine cmd = parser.parse(options, args);

		String keyString = cmd.getOptionValue("pwd");
		ClientBot clientBot = new ClientBot(cmd.getOptionValue("c"), keyString);
		String server = cmd.getOptionValue("s");
		clientBot.connect("irc.freenode.net");
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			String line = scanner.nextLine().trim();
			if("exit".equals(line)) break;
			clientBot.sendEncryptedMessage(server, line);
		}
		System.out.println("Exited");
		clientBot.disconnect();
		clientBot.dispose();
	}
}
