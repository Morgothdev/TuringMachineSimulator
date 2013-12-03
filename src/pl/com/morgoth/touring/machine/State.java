package pl.com.morgoth.touring.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class State {

	private final String name;
	Map<String, Move> moves = new HashMap<>();

	public State(String state) {
		this.name = state;
	}

	public String name() {
		return name;
	}

	public void addMove(String character, String nextCharacter, State outState, Direction direction) {
		moves.put(character, new Move(nextCharacter, outState, direction));
	}

	public List<String> movesStringList() {
		List<String> movesList = new ArrayList<>(moves.size());
		for (Move move : moves.values()) {
			movesList.add(move.toString());
		}
		return movesList;
	}

	public void addAcceptMove(String character) {
		moves.put(character, new AcceptMove(character));
	}

	public Move getMove(String character) {
		return moves.get(character);
	}

	@Override
	public String toString() {
		return name;
	}

	public void addRejectMove(String string) {
		moves.put(string, new RejectMove(string));
	}
}
