package pl.com.morgoth.touring.machine;

import java.util.LinkedList;
import java.util.List;

public class TouringMachine implements Processable{

	private int index = 0;
	private List<String> tape = new LinkedList<>();
	private State actualState;
	private String blankCharacter;
	
	public TouringMachine(String input, String blankCharacter, State startingState){
		this.blankCharacter = blankCharacter;
		actualState=startingState;
		for(int i=0;i<1;++i){
			tape.add(blankCharacter);
		}
		for(byte b : input.getBytes()){
			byte[] bytes = new byte[1];
			bytes[0]=b;
			tape.add(new String(bytes));
		}
		for(int i=0;i<1;++i){
			tape.add(blankCharacter);
		}
		index = 1;
	}
	
	public void run(){
		Move ruch;
		do{
			String joinedTape = joinTape(); 
			System.out.println(joinedTape+"\tactual state: "+actualState);
			for(int i=0;i<index;++i){
				System.out.print(" ");
			}
			System.out.println("^");
			ruch = actualState.getMove(tape.get(index));
			System.out.println("function used: ("+actualState.name()+", "+tape.get(index)+") => ("+ ruch.toString()+")");
			System.out.println();
		}while(ruch.process(this)==false);
	}

	private String joinTape() {
		StringBuilder sb = new StringBuilder();
		for(String ch : tape){
			sb.append(ch);
		}
		return sb.toString();
	}

	@Override
	public void moveHead(Direction directionAfter) {
		if(Direction.LEFT==directionAfter){
			if(index==0){
				tape.add(0,blankCharacter);
			}else{
			--index;}
		}else{
			if(index==tape.size()-1){
				tape.add(tape.size(),blankCharacter);
			}
			++index;
		}
	}

	@Override
	public void putChar(String character) {
		tape.remove(index);
		tape.add(index, character);
	}

	@Override
	public void setState(State outState) {
		actualState = outState;		
	}
	
}
