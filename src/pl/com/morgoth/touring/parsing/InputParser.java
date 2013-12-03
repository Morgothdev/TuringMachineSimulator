package pl.com.morgoth.touring.parsing;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.com.morgoth.touring.machine.Direction;
import pl.com.morgoth.touring.machine.Move;
import pl.com.morgoth.touring.machine.State;

public class InputParser {

	private static final String WHITE_SPACES_REGEX = "[ \t]+";

	public State sparse(List<String> lines, String blank) {

		String[] alphabet = readAlphabetFromHeader(lines.get(1));

		String[] statesNames = readStatesFromLines(lines.subList(2, lines.size()));

		Map<String, State> states = new HashMap<>();
		for (String name : statesNames) {
			states.put(name, new State(name));
		}

		for (String line : lines.subList(2, lines.size())) {
			String[] functions = line.split(WHITE_SPACES_REGEX);
			State state = states.get(functions[0].trim());
			for (int j = 1; j < functions.length; ++j) {
				String function = functions[j].trim();
				if (function.equals("accept")) {
					state.addAcceptMove(alphabet[j - 1]);
				} else if (function.equals("reject")) {
					state.addRejectMove(alphabet[j - 1]);
				} else {
					String[] functionElements = function.trim().split(",");
					String nextcharacter = functionElements[0];
					String nextStateName = functionElements[1];
					Direction direction = (functionElements[2].equals("<-") ? Direction.LEFT : Direction.RIGHT);
					State out = states.get(nextStateName);
					if (out == null) {
						throw new IllegalArgumentException();
					}
					state.addMove(alphabet[j - 1], nextcharacter, out, direction);
				}
			}
		}

		System.out.println(toString(states.values(), alphabet, states.get(lines.get(0))));
		return states.get(lines.get(0));
	}

	private String[] readStatesFromLines(List<String> lines) {
		String[] statesStr = new String[lines.size()];
		for (int i = 0; i < lines.size(); ++i) {
			statesStr[i] = lines.get(i).trim().split(WHITE_SPACES_REGEX)[0].trim();
		}
		return statesStr;
	}

	private String[] readAlphabetFromHeader(String string) {
		return string.trim().split(WHITE_SPACES_REGEX);
	}

	public String toString(Collection<State> states, String[] alphabet, State starting) {
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
		sb.append("starting state: ").append(starting).append(System.getProperty("line.separator"));
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
