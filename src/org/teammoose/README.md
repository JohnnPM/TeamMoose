TeamMoose
===========
Folder: <i>src</i><p>
Package: <i>org.teammoose</i><br>
Since: <i>0.0.1-SNAPSHOT</i><p>
<b>Description:</b><br>
<i>the main package for the plugin</i><p>

Classes
---

- <b><i>TeamMooseMain</i></b><br>
<i><b>Added TM-0.0.1-SNAPSHOT</b>

	The main class for the plugin. 
	Contains the onLoad(), onEnable(), and onDisable() methods bukkit
	calls in the correpsonding events.
</i>
- <b><i>TMLogger</i></b><br>
<i><b>Added TM-0.0.1-SNAPSHOT</b>
	
	The logger for the project. A instance is created 
	in TeamMooseMain with a getter method to call it. 
	When using this logger, it prints the message as follows:
	
		[TM]: Awesome Moose message
		
	TM being the prefix and the message as so.
</i>

Sub-Packages
---

- <b><i>org.teammoose.chat</b></i><br>
<i><b>Added TM-0.0.1-SNAPSHOT</b>

	The classes that manage the chat such as the
	listener for the AsyncPlayerChatEvent formatting it
	with the correct colors and prefixes.
</i>
- <b><i>org.teammoose.command</b></i><br>
<i><b>Added TM-0.0.1-SNAPSHOT</b>

	This package contains the command framework
	and the command classes that use the framework.
</i>
- <b><i>org.teammoose.handler</b></i><br>
<i><b>Added TM-0.0.1-SNAPSHOT</b>

	The event handlers such as LoginEvent, QuitEvent,
	PlayerMoveEvent, etc...
</i>
- <b><i>org.teammoose.lib</b></i><br>
<i><b>Added TM-0.0.1-SNAPSHOT</b>

	Various classes that contain references that are
	used throughout the project.
</i>
- <b><i>org.teammoose.util</b></i><br>
<i><b>Added TM-0.0.1-SNAPSHOT</b>
	Utility classes that are used in this project. Some
	of the util classes are color formatters, tag util for
	the player, etc...
</i>

---
TeamMoose 2014 • http://www.teammoose.org

