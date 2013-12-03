package pl.com.morgoth.touring.process;

import pl.com.morgoth.touring.machine.Direction;
import pl.com.morgoth.touring.machine.State;

public interface Processable {

	void moveHead(Direction directionAfter);

	void putChar(String character);

	void setState(State outState);

}
