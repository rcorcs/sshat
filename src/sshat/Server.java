package sshat;

import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;

public class Server {
	public static void main(String[] args) throws Exception {
		Options options = new Options();
		options.addOption(OptionBuilder.withLongOpt("password")
			.withDescription("Encryption keyword")
			.hasArg()
			.withArgName("password")
			.create("pwd"));
		options.addOption(OptionBuilder.withLongOpt("server")
                                   .withDescription( "Server IRC name" )
                                   .hasArg()
                                   .withArgName("name")
                                   .create("s"));
		CommandLineParser parser = new BasicParser();
		CommandLine cmd = parser.parse(options, args);

		String keyString = cmd.getOptionValue("pwd");
		ServerBot serverBot = new ServerBot(cmd.getOptionValue("s"), keyString);
		serverBot.connect("irc.freenode.net");
	} 
}
