package pl.com.morgoth.touring.machine;

import pl.com.morgoth.touring.process.Processable;

public class Move {

	private Direction directionAfter;
	private State outState;
	private String character;

	Move() {
	}

	Move(String character, State outState, Direction directionAfter) {
		this.character = character;
		this.outState = outState;
		this.directionAfter = directionAfter;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		return sb.append(character).append(',').append(outState.name()).append(',').append(directionAfter).toString();
	}

	public boolean process(Processable tasma) {
		tasma.putChar(character);
		tasma.setState(outState);
		tasma.moveHead(directionAfter);
		System.out.println("zastosowano " + toString());
		return false;
	}
}

class AcceptMove extends Move {

	AcceptMove(String character) {
		super();
	}

	@Override
	public boolean process(Processable tasma) {
		System.out.println("accepted");
		return true;
	}

	@Override
	public String toString() {
		return "accept";
	}

}

class RejectMove extends Move {

	RejectMove(String character) {
		super();
	}

	@Override
	public boolean process(Processable tasma) {
		System.out.println("rejected");
		return true;
	}

	@Override
	public String toString() {
		return "reject";
	}

}