package com.xdevnull.irc.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;


/**
 * IRCMessage
 * 
 * @author xdevnull
 * Jan 18, 2017
 */
public class IRCMessage {
	
	/**
	 * RAW Message
	 */
	private final String raw;
	
	/**
	 * IRCv3.2 Message Tags
	 * 
	 * additional and optional metadata included with relevant messages.
	 * 
	 * ABNF:
	 *  [ "@" tags SPACE ]
	 *    tags        =  tag *[ ";" tag ]
	 *	  tag         =  key [ "=" value ]
	 *	  key         =  [ vendor "/" ] 1*( ALPHA / DIGIT / "-" )
	 *	  value       =  *valuechar
	 *	  valuechar   =  %x01-06 / %x08-09 / %x0B-0C / %x0E-1F / %x21-3A / %x3C-FF
	 *	                   ; any octet except NUL, BELL, CR, LF, " " and ";"
     *
	 *  
	 * Status:
	 * 	Optional 
	 */
	private final HashMap<String, String> tags;
	
	/**
	 * Message Prefix 
	 * 
	 * The prefix is used by servers to indicate the true origin of a message.
	 * 
	 * ABNF: 
	 *      prefix     =  servername / ( nickname [ [ "!" user ] "@" host ] )
	 * 
	 * Status:
	 * Optional
	 */
	private final String prefix;
	
	/**
	 * Message Command
	 * 
	 * The command must either be a valid IRC command or a three-digit number represented as text.
	 * 
	 * ABNF:
	 *      command    =  1*letter / 3digit
	 * 
	 * Status:
	 * Required
	 */
	private final String command;
	
	/**
	 * Message Parameters
	 * 
	 * Parameters (or ‘params’) are extra pieces of information added to the end of a message. 
	 * 
	 * ABNF:
	 *         params     =  *14( SPACE middle ) [ SPACE ":" trailing ]
     *          =/ 14( SPACE middle ) [ SPACE [ ":" ] trailing ]
     *          
     * Status:
     * Optional         
	 */
	private final String[] parameters;
	
	/**
	 * Message Parameters Middle Part
	 * 
	 * ABNF:
	 *     nospcrlfcl *( ":" / nospcrlfcl )
	 */
	private final String[] middle;
	
	/**
	 * Message parameters Trailing part
	 * *( ":" / " " / nospcrlfcl )
	 */
	private final String trailing;
	
	/**
	 * IRCMessage
	 * 
	 * @param tags
	 * @param prefix
	 * @param command
	 * @param middle
	 * @param trailing
	 */
	public IRCMessage(String raw, HashMap<String, String> tags, String prefix, String command, String[] middle, String trailing) {
		this.raw = raw;
		this.tags = (tags == null) ? (HashMap<String, String>) Collections.<String, String>emptyMap() : tags;
		this.prefix = (prefix == null) ? "" : prefix;
		this.command = (command == null) ? "" : command;
		this.middle = (middle == null) ? new String[0] : middle;
		this.trailing = (trailing == null) ? "" : trailing;
		ArrayList<String> p = new ArrayList<String>();
		p.addAll(Arrays.asList(this.middle));
		p.add(this.trailing);
		this.parameters = (String[]) p.toArray(new String[p.size()]);
	}
	
	/**
	 * Get raw message (Original before parsing)
	 * 
	 * @return
	 */
	public String getRaw() {
		return this.raw;
	}
	
	/**
	 * Get message tags
	 * 
	 * @return
	 */
	public HashMap<String, String> getTags() {
		return this.tags;
	}
	
	/**
	 * Get message prefix
	 * 
	 * @return
	 */
	public String getPrefix() {
		return this.prefix;
	}
	
	/**
	 * Get message command
	 * 
	 * @return
	 */
	public String getCommand() {
		return this.command;
	}
	
	/**
	 * Get message parameters
	 * 
	 * @return
	 */
	public String[] getParameters() {
		return this.parameters;
	}
	
	/**
	 * Get message parameters middle part
	 * 
	 * @return
	 */
	public String[] getMiddle() {
		return this.middle;
	}
	
	/**
	 * Get message parameters trailing part
	 * 
	 * @return
	 */
	public String getTrailing() {
		return this.trailing;
	}
	
	/**
	 * ToString
	 */
	@Override
	public String toString() {
		return "IRCMessage [tags=" + tags + ", prefix=" + prefix + ", command=" + command + ", parameters="
				+ Arrays.toString(parameters) + ", middle=" + Arrays.toString(middle) + ", trailing=" + trailing + "]";
	}


}
