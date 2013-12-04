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

		if (args[0].equals("--help")) {
			System.out.println("Prepare text file with data for machine in format:\n"
					+ "data\nblankCharacter\nstarting state\naction table\n\nExample:");
			System.out.println("10010101011");
			System.out.println("$");
			System.out.println("q_s");
			System.out.println("\t0\t\t1\t\t$");
			System.out.println("q_s\t0,q_s,->\t1,q_s,->\t$,q_a,<-");
			System.out.println("q_a\t1,q_y,->\t0,q_a,<-\t1,q_y,<-");
			System.out.println("q_y\taccept\t\treject\t\taccept\n");
			return;
		}

		FileInputStream is = new FileInputStream(args[0]);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		List<String> linesList = new LinkedList<String>();
		String line;
		while ((line = br.readLine()) != null) {
			if (!line.trim().equals("")) {
				linesList.add(line);
			}
		}
		br.close();

		String data = linesList.get(0);
		String blankCharacter = linesList.get(1);

		State starting = new InputParser().sparse(linesList.subList(2, linesList.size()), blankCharacter);
		TouringMachine m = new TouringMachine(data, blankCharacter, starting);
		m.run();
	}

}
