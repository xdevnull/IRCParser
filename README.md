# JAVA IRC Message Parser

###### JAVA IRC Parser conforming to the IRC protocol as described in [RFC1459](https://tools.ietf.org/html/rfc1459), [RFC2812](https://tools.ietf.org/html/rfc2812) and the [modern irc client protocol](http://modern.ircdocs.horse/index.html)

## Usage Example

```
String raw = ":syrk!kalt@millennium.stealth.net QUIT :Gone to have lunch";
IRCMessage parsed = IRCParser.parse(raw);
System.out.println(parsed.getCommand()); //QUIT
System.out.println(parsed.getTrailing()); //Gone to have lunch
```


# Documentation

## `public final class IRCParser`

IRC Parser

### `public static final IRCMessage parse(String input)`

Parse raw input from server to IRCMessage

 * **Parameters:** `input` — The raw message received from server
 * **Returns:** IRCMessage
 
## `public class IRCMessage`

### `private final String raw`

Raw message before parsing

## `private final HashMap<String, String> tags`

IRCv3.2 Message Tags
```
additional and optional metadata included with relevant messages.
ABNF: [ "@" tags SPACE ] tags = tag *[ ";" tag ] tag = key [ "=" value ] key = [ vendor "/" ] 1*( ALPHA / DIGIT / "-" ) value = *valuechar valuechar = %x01-06 / %x08-09 / %x0B-0C / %x0E-1F / %x21-3A / %x3C-FF ; any octet except NUL, BELL, CR, LF, " " and ";"
```
## `private final String prefix`

Message Prefix

The prefix is used by servers to indicate the true origin of a message.

```
ABNF: prefix = servername / ( nickname [ [ "!" user ] "@" host ] )
```
## `private final String command`

Message Command

The command must either be a valid IRC command or a three-digit number represented as text.
```
ABNF: command = 1*letter / 3digit
```

## `private final String[] parameters`

Message Parameters

Parameters (or ‘params’) are extra pieces of information added to the end of a message.
```
ABNF: params = *14( SPACE middle ) [ SPACE ":" trailing ] =/ 14( SPACE middle ) [ SPACE [ ":" ] trailing ]
```


## `private final String[] middle`

Message Parameters Middle Part
```
ABNF: nospcrlfcl *( ":" / nospcrlfcl )
```
## `private final String trailing`
```
Message parameters Trailing part *( ":" / " " / nospcrlfcl )
```
## `public IRCMessage(String raw, HashMap<String, String> tags, String prefix, String command, String[] middle, String trailing)`

IRCMessage

 * **Parameters:**
   * `raw` — Raw message that received from server
   * `tags` — Message Tags
   * `prefix` — Message Prefix
   * `command` — Message Command
   * `middle` — Message Parameters Middle
   * `trailing` — Message Parameters Trailing

## `public String getRaw()`

Get raw message (Original before parsing)

 * **Returns:** Original raw message

## `public HashMap<String, String> getTags()`

Get message tags

 * **Returns:** Message tags

## `public String getPrefix()`

Get message prefix

 * **Returns:** Message prefix

## `public String getCommand()`

Get message command

 * **Returns:** Message command

## `public String[] getParameters()`

Get message parameters

 * **Returns:** Message parameters

## `public String[] getMiddle()`

Get message parameters middle part

 * **Returns:** Message parameters middle part

## `public String getTrailing()`

Get message parameters trailing part

 * **Returns:** Message parameters trailing part

## `@Override  public String toString()`

ToString
 
