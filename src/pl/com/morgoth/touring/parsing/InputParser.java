package pl.com.morgoth.touring.parsing;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pl.com.morgoth.touring.machine.Direction;
import pl.com.morgoth.touring.machine.Move;
import pl.com.morgoth.touring.machine.State;

public class InputParser {

	private static final String WHITE_SPACES_REGEX = "[\\s]+";
	private String[] alphabet;
	private String[] statesNames;
	private Set<String> alphabetSet;

	public State sparse(List<String> lines, String blank,String startingStateName) {

		alphabet = readAlphabetFromHeader(lines.get(0));
		alphabetSet = new HashSet<String>(Arrays.asList(alphabet));

		List<String> functionsList = lines.subList(1, lines.size());
		statesNames = readStatesFromLines(functionsList);

		Map<String, State> states = new HashMap<>();
		for (String name : statesNames) {
			states.put(name, new State(name));
		}

		for (int i = 0; i < functionsList.size(); ++i) {
			String line = functionsList.get(i);
			String[] functions = line.split(WHITE_SPACES_REGEX);
			State state = states.get(functions[0].trim());
			for (int j = 1; j < functions.length; ++j) {
				String function = functions[j].trim();
				if (function.equals("accept")) {
					state.addAcceptMove(alphabet[j - 1]);
				} else if (function.equals("reject")) {
					state.addRejectMove(alphabet[j - 1]);
				} else {

					try {
						String[] functionElements = function.trim().split(",");
						if (functionElements.length != 3) {
							throw new IllegalArgumentException(
									"Unknown function \"" + function + "\"");
						}
						String nextcharacter = checkCharacter(functionElements[0]);
						Direction direction = Direction
								.valueof(functionElements[2]);
						State out = getState(states, functionElements[1]);
						state.addMove(alphabet[j - 1], nextcharacter, out,
								direction);
					} catch (IllegalArgumentException e) {
						throw new IllegalArgumentException(e.getMessage()
								+ " in line: \""
								+ line.replaceAll("[\t]", " ").replaceAll(
										"[ ]{2,}", " ")+"\"");
					}
				}
			}
		}
		

		System.out.println(toString(states.values(), alphabet,
				states.get(startingStateName)));
		return states.get(startingStateName);
	}

	private String checkCharacter(String character) {
		if (alphabetSet.contains(character)) {
			return character;
		} else {
			throw new IllegalArgumentException("Unknown character \""
					+ character + "\"");
		}
	}

	private State getState(Map<String, State> states, String stateName) {
		State out = states.get(stateName);
		if (out == null) {
			throw new IllegalArgumentException("Unknown state \"" + stateName
					+ "\"");
		}
		return out;
	}

	private String[] readStatesFromLines(List<String> lines) {
		String[] statesStr = new String[lines.size()];
		for (int i = 0; i < lines.size(); ++i) {
			statesStr[i] = lines.get(i).trim().split(WHITE_SPACES_REGEX)[0]
					.trim();
		}
		return statesStr;
	}

	private String[] readAlphabetFromHeader(String string) {
		return string.trim().split(WHITE_SPACES_REGEX);
	}

	public String toString(Collection<State> states, String[] alphabet,
			State starting) {
		int maxCellLen = 0;
		for (State state : states) {
			if (state.name().length() > maxCellLen) {
				maxCellLen = state.name().length();
			}
			List<String> eachStateMovesList = state.movesStringList();
			for (String eachMove : eachStateMovesList) {
				if (eachMove.length() > maxCellLen) {
					maxCellLen = eachMove.length();
				}
			}
		}

		StringBuilder sb = new StringBuilder();
		sb.append("Moves table:").append(System.getProperty("line.separator"));
		sb.append("starting state: ").append(starting)
				.append(System.getProperty("line.separator"));
		appendCell(sb, maxCellLen + 1, "");
		for (String ch : alphabet) {
			appendCell(sb, maxCellLen + 1, ch);
		}
		sb.append(System.getProperty("line.separator"));
		for (State state : states) {
			appendCell(sb, maxCellLen, state.name());
			for (String ch : alphabet) {
				Move m = state.getMove(ch);
				sb.append("|");
				appendCell(sb, maxCellLen, m.toString());
			}
			sb.append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}

	private void appendCell(StringBuilder sb, int maxCellLen, String name) {
		sb.append(name);
		for (int i = name.length(); i < maxCellLen; ++i) {
			sb.append(' ');
		}
	}
}
