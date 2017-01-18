# JAVA IRC Message Parser

###### JAVA IRC Parser conforming to the IRC protocol as described in [RFC1459](https://tools.ietf.org/html/rfc1459), [RFC2812](https://tools.ietf.org/html/rfc2812) and the [modern irc client protocol](http://modern.ircdocs.horse/index.html)

## Usage

**IRCParser class**
```public static IRCMessage parser(String raw_input)```

**IRCMessage class**
```
	public String getRaw();
	public HashMap<String, String> getTags();
	public String getPrefix();
	public String getCommand();
	public String[] getParameters();
	public String[] getMiddle();
	public String getTrailing();
```

**Example:**
```
String raw = ":syrk!kalt@millennium.stealth.net QUIT :Gone to have lunch";
IRCMessage parsed = IRCParser.parse(raw);
System.out.println(parsed.getCommand()); //QUIT
System.out.println(parsed.getTrailing()); //Gone to have lunch
```


