package pl.com.morgoth.touring.machine;


public interface Processable {

	void moveHead(Direction directionAfter);

	void putChar(String character);

	void setState(State outState);

}
