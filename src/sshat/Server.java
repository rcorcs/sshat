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

public class Server {
	public static void main(String[] args) throws Exception {
		Options options = new Options();
		options.addOption(OptionBuilder.withLongOpt("server")
                                   .withDescription( "Server IRC name" )
                                   .hasArg()
                                   .withArgName("name")
                                   .create("s"));
		CommandLineParser parser = new BasicParser();
		CommandLine cmd = parser.parse(options, args);

		Console console = System.console();
		ServerBot serverBot = new ServerBot(cmd.getOptionValue("s"),  console.readPassword("Enter encryption keyword: "));
		serverBot.connect("irc.freenode.net");
	} 
}
