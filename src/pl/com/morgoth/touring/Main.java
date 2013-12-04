package pl.com.morgoth.touring;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import pl.com.morgoth.touring.machine.State;
import pl.com.morgoth.touring.machine.TouringMachine;
import pl.com.morgoth.touring.parsing.InputParser;

public class Main {

	public static void main(String[] args) throws IOException {

		if (args[0].equals("--help") || args[0].equals("-h")) {
			System.out.println("Turing machine simulator, configured by a file which must be given in the form:\n"+
			"\n"+
			"<begin>\n"+
			"/The content of the task, it\n"+ 
			"/will be discharged at\n"+ 
			"...\n"+
			"/the beginning of the print simulator\n"+
			"/each line starts with \"/\"\n"+
			"initial value of tape (without blank characters)\n"+
			"blank character, np $ (only not \"/\")\n"+
			"starting state, np start, q_s ..\n"+
			"table of transition function, in form:\n"+
			"\t\tcharacter\tcharacter\tcharacter\t...\n"+
			"state\tfunction\tfunction\tfunction\t...\n"+
			"...\t...\t...\t...\t...\n"+
			"<end>\n"+
			"\n"+
			"where the function is in the form:\n"+
			"nextCharacter,nextState,headMoveDirection\n"+
			"\n\n"+
			"Example:\n\n"+
			"/This configuration of Turing machine is able\n"+
			"/to adding 1 to binary number\n"+
			"$\n"+
			"q_s\n"+
			"\t\t0\t\t1\t\t$\n"+
			"q_s\t0,q_s,->\t1,q_s,->\t$,q_a,<-\n"+
			"q_a\t1,q_y,->\t0,q_a,<-\t1,q_y,<-\n"+
			"q_y\taccept\t\taccept\taccept\n"
		);
			return;
		}

		FileInputStream is = new FileInputStream(args[0]);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		StringBuilder contentList = new StringBuilder();
		List<String> linesList = new LinkedList<String>();
		String line;

		while ((line = br.readLine()) != null) {
			if (!line.trim().equals("")) {
				if (line.trim().startsWith("/")) {
					contentList.append(System.lineSeparator()).append(line.trim().substring(1));
				} else {
					linesList.add(line.trim());
					break;
				}
			}
		}

		while ((line = br.readLine()) != null) {
			if (!line.trim().equals("")) {
				linesList.add(line.trim());
			}
		}
		br.close();

		String blankCharacter = linesList.get(0);
		String startingStateName = linesList.get(1);
		try {
			System.out.println("\n\nTask:"+contentList.toString()+"\n");
			State starting = new InputParser().sparse(
					linesList.subList(2, linesList.size()), blankCharacter,
					startingStateName);
			TouringMachine m = new TouringMachine(args[1], blankCharacter,
					starting);
			m.run();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
