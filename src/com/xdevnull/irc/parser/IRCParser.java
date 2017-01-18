package com.xdevnull.irc.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * IRC Parser
 * 
 * @author xdevnull
 * Jan 18, 2017
 */
public final class IRCParser {
	
	/**
	 * Parse raw input from server to IRCMessage
	 * 
	 * @param input
	 * @return
	 */
	public static final IRCMessage parse(String input) {
		HashMap<String, String> message_tags = new HashMap<String, String>();
		String prefix = "";
		String command = "";
		ArrayList<String> middle = new ArrayList<String>();
		String trailing = "";
		
		//Invalid message
		if(input == null || input.equals(""))
			return null;
		
		//point to the next part that needs to be processed 
		int cursor = 0;
		
		//Determine whether input start with @ (0x40)
		//Indicates that the message contains IRCv3.2 tags
		//Tags joined by ';' semicolon and ends at the first space
		if(input.charAt(0) == '@') {

			//The end of tags
			cursor = input.indexOf(" ");
			
			//Extract tags part excluding the @
			String tags = input.substring(1, cursor);
			
			//Break tags into tokens
			//Where Key=Value or Key without any value
			StringTokenizer token = new StringTokenizer(tags, ";");
			while(token.hasMoreTokens()) {
				//Extract Key and value
				String[] kv = token.nextToken().split("=");
				if(kv.length == 2) {
					String val = kv[1];
					//Escape value
					while(val.indexOf("\\r") >= 0)
						val = val.replace("\\r", "\r");
					while(val.indexOf("\\n") >= 0)
						val = val.replace("\\n", "\n");
					while(val.indexOf("\\\\") >= 0)
						val = val.replace("\\\\", "\\");
					while(val.indexOf("\\s") >= 0)
						val = val.replace("\\s", " ");
					while(val.indexOf("\\:") >= 0)
						val = val.replace("\\:", ";");
					message_tags.put(kv[0], val);
					
				}
				else if(kv.length == 1)
					message_tags.put(kv[0], null);			
			}
			
		}
		
		//Ignore any whitespace
		while(input.charAt(cursor) == ' ')
			cursor++;
		
		//Determine whether message contains a prefix component
		//Prefix components starts with :
		if(cursor < input.length() && input.charAt(cursor) == ':') {
			
			//Prefix beginning
			int prefix_beginning = cursor + 1;
			
			//Point at the next component
			cursor = input.indexOf(" ", cursor);
			
			//Get prefix
			prefix = input.substring(prefix_beginning, cursor);
		}
		
		//Ignore any whitespace
		while(input.charAt(cursor) == ' ')
			cursor++;

		//Extract the message command component
		//Command 1 letter or three digits
		if(cursor < input.length()) {
			int command_beginning = cursor;
			cursor = input.indexOf(" ", cursor);
			command = input.substring(command_beginning, cursor);
		}
		
		//Ignore any whitespace
		while(input.charAt(cursor) == ' ')
			cursor++;
		
		//Extract message parameters component
		breakWhile:
		while(cursor < input.length()) {
			int next = input.indexOf(" ", cursor);
			
			//Trailing part
			//End of message
			if(input.charAt(cursor) == ':') {
				trailing = input.substring(++cursor, input.length());
				break breakWhile;
			}
			
			//Message contain trailing 
			if(next != -1) {
				middle.add(input.substring(cursor, next));
				cursor = next + 1;
				
				while(cursor < input.length() && input.charAt(cursor) == ' ')
					cursor++;
				continue;
			}
			
			//If message has no trailing
			//End of message
			if(next == -1) {
				middle.addAll(Arrays.asList(input.substring(cursor, input.length()).split(" ")));
				break breakWhile;
			}
		}
		
		return new IRCMessage(input, message_tags, prefix, command, middle.toArray(new String[middle.size()]), trailing);
	}
	
}