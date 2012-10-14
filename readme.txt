LexChain - How To

1. Build and Run from Eclipse
This works out of the box. Just use Import from Folder in Eclipse and then Run.

2. Build and Run from Command Line

	2.1 Build
	
	- use "ant build" in order to compile the classes. They will be stored in 
		a newly created folder : "build"
	- use "ant install" or "make" to create a runnable environment.
		After "ant install", a runnable jar is created in the root folder, called
		"lexchain.jar".
	
	2.2 Run
		Run the jar file using appropriate parameters. The tagger file and WordNet properties 
		file must be located in the same folder as the jar. By default, they are both placed in
		the root folder.
		Example : java -jar lexchain-run.jar --help
