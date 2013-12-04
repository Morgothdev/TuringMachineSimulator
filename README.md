TuringMachineSimulator

======================

Turing machine simulator, configured by a file which must be given in the form:

<begin>
/The content of the task, it 
/will be discharged at 
...
/the beginning of the print simulator
/each line starts with "/"
initial value of tape (without blank characters)
blank character, np $ (only not "/")
starting state, np start, q_s ..
table of transition function, in form:
	character	character	character	...
state	function	function	function	...
...	...		...		...		...
<end>

where the function is in the form:
nextCharacter,nextState,headMoveDirection

Examples You can find in examples folder.

Simple run:
$ ant
$ java -jar turing.jar examples/increment 1011

Enjoy!
