import java.io.PrintStream;
import java.util.*;
import java.io.FileOutputStream;
import java.io.File;

public class p1_16f_cjd62 {
	int currentState;
	
	// constructor for DFA initialized to 0
	public p1_16f_cjd62() {
		currentState = 0;
	}
	
	// loop through each character of the string and transition through the DFA depending
	// on the state and the character being tested
	// strings must be lower case
	public static void DFATransition(final p1_16f_cjd62 d, final String stringToCheck) {
		String strToCheckLower = stringToCheck.toLowerCase();
		char[] Arr = strToCheckLower.toCharArray();
		System.out.println("starting in state q" + d.currentState);
		System.out.println();
		
		for (int i = 0; i < Arr.length; i++) {	
			p1_16f_cjd62.applyTransition(d, Arr[i]);
		}
		
		// used to assign a state as an accepting state or non-accepting stateThe state q19 is the trap state and loops to itself with all possible characters of the language. The trap state is reached when a "." character is tested after q4, when a characters other than c are tested after q12,  and when any character in the language are input after q15 and q18.

		HashMap<Integer, String> DFAStates = new HashMap<Integer, String>();
		DFAStates.put(0, "string is rejected by DFA");
		DFAStates.put(1, "string is rejected by DFA");
		DFAStates.put(2, "string is rejected by DFA");
		DFAStates.put(3, "string is rejected by DFA");
		DFAStates.put(4, "string is rejected by DFA");
		DFAStates.put(5, "string is rejected by DFA");
		DFAStates.put(6, "string is rejected by DFA");
		DFAStates.put(7, "string is rejected by DFA");
		DFAStates.put(8, "string is rejected by DFA");
		DFAStates.put(9, "string is accepted by DFA");
		DFAStates.put(10, "string is accepted by DFA");
		DFAStates.put(11, "string is rejected by DFA");
		DFAStates.put(12, "string is rejected by DFA");
		DFAStates.put(13, "string is rejected by DFA");
		DFAStates.put(14, "string is rejected by DFA");
		DFAStates.put(15, "string is accepted by DFA");
		DFAStates.put(16, "string is rejected by DFA");
		DFAStates.put(17, "string is rejected by DFA");
		DFAStates.put(18, "string is accepted by DFA");
		DFAStates.put(19, "string is rejected by DFA");
		
		System.out.println(DFAStates.get(d.currentState));
		System.out.println();
		d.currentState = 0;
	}
	
	// compares the current state of the DFA and uses the appropriate function
	// to allow the DFA to transition states properly.
	// each number represents a state in the DFA
	public static void applyTransition(final p1_16f_cjd62 d, final char letter)  {
		int state = d.currentState;
		
		if (state < 4) {
			d.dfaCheck1(d, letter);	
		}
		else if (state >=4 && state < 6) {
			d.dfaCheck2(d, letter);
		}
		else if (state >= 6 && state < 11){
			d.dfaCheck3(d, letter);
		}
		else if (state == 11) {
			d.dfaCheck4(d, letter);
		}
		else if (state >= 12 && state < 19) {
			d.dfaCheck5(d, letter);
		}
		else {
			d.errorState(d, letter);
		}

		System.out.println("Current character: " + letter);
		System.out.println("Current State: q"+ d.currentState);
		System.out.println();
	}
	
	// each of the functions below are used to transition the DFA based on the range of states each function contains
	// functions dfaCheck2 and dfaCheck4 are the loops in the DFA in which it will accept gamma until a . is added
	// the other functions transtion through the states which help determine if the string contains the set {www.}
	// and also determines if {.com, co.ca, ca} is in the string
	public void dfaCheck1(final p1_16f_cjd62 d, final char letter) {
		if (letter == 'w') {
			if (d.currentState == 0) {
				d.currentState = 1;
			}
			else if (d.currentState == 1) {
				d.currentState = 2;
			}
			else if (d.currentState == 2) {
				d.currentState = 3;
			}
			else {
				d.currentState = 11;
				d.dfaCheck4(d, letter);
			}
		}
		else if (letter == '.') {
			if (d.currentState == 3) {
				d.currentState = 4;
			}
			else if (d.currentState == 0) {
				d.errorState(d, letter);
			}
			else if (d.currentState <= 3 && d.currentState > 0) {
				d.currentState = 11;
				d.dfaCheck4(d, letter);
			}
		}
		else {
			d.currentState = 11;
			d.dfaCheck4(d, letter);
		}
	}
	
	public void dfaCheck2(final p1_16f_cjd62 d, final char letter) {
		if (letter == 'c' && d.currentState == 4) {
			d.dfaCheck3(d, letter);
		}
		else if (letter == '.' && d.currentState == 4) {
			d.currentState = 19;
			d.errorState(d, letter);
		}
		else if (letter >= 'a' && letter <= 'z' && letter != 'c'){
			d.currentState = 5;
		}
		else {
			d.dfaCheck3(d, letter);
		}
	}
	
	public void dfaCheck3(final p1_16f_cjd62 d, final char letter) {
		if (letter == '.' && d.currentState == 5) {
			d.currentState = 6;
		}
		else if (letter == 'c' && (d.currentState == 6 || d.currentState == 4)) {
			d.currentState = 7;
		}
		else if (d.currentState == 7) {
			if (letter == 'o') {
				d.currentState = 8;
			}
			else if (letter == 'a') {
				d.currentState = 10;
			}
			else if (letter >= 'a' && letter <= 'z') {
				d.currentState = 11;
			}
		}
		else if (d.currentState == 8) {
			if (letter == 'm') {
				d.currentState = 9;
			}
			else if (letter >= 'a' && letter <= 'z') {
				d.currentState = 11;
			}
			else if (letter == '.') {
				d.currentState = 12;
			}
		}
		else if (d.currentState == 9) {
			if (letter >= 'a' && letter <= 'z') {
				d.currentState = 11;
			}
			else if (letter == '.') {
				d.currentState = 12;
			}
		}
		else if (d.currentState == 10) {
			if (letter >= 'a' && letter <= 'z') {
				d.currentState = 11;
			}
			else if (letter == '.') {
				d.currentState = 12;
			}
		}
	}
	
	public void dfaCheck4(final p1_16f_cjd62 d, final char letter) {
			if (d.currentState == 11) {
				if (letter == '.') {
					d.currentState = 12;
				}
				else {
					d.currentState = 11;
				}
			}
		}

	public void dfaCheck5(final p1_16f_cjd62 d, final char letter) {
		if (d.currentState == 12) {
			if (letter == 'c') {
				d.currentState = 13;
			}
			else {
				d.currentState = 19;
			}
		}
		else if (d.currentState == 13) {
			if (letter == 'o') {
				d.currentState = 14;
			}
			else if (letter == 'a') {
				d.currentState = 18;
			}
		}
		else if (d.currentState == 14) {
			if (letter == 'm') {
				d.currentState = 15;
			}
			else if (letter == '.') {
				d.currentState = 16;
			}
		}
		else if (d.currentState == 16) {
			if (letter == 'c') {
				d.currentState = 17;
			}
		}
		else if (d.currentState == 17) {
			if (letter == 'a') {
				d.currentState = 18;
			}
		}
		else if (d.currentState == 15 || d.currentState == 18) {
			if (letter == '.' || letter <= 'z' && letter >= 'a') {
				d.currentState = 19;
			}
		}
	}

	// function to loop over error state once it is reached
	public void errorState(final p1_16f_cjd62 d, final char letter) {
		if (d.currentState == 19) {
			d.currentState = 19;
		}
	}
		
	public static void main(String[] args) {
		// make a file prog1output.txt and print any errors that may occur
		try {
			File file = new File("prog1output.txt");
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			PrintStream out = new PrintStream(fos);
			System.setOut(out);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("Do you want to enter a string? (y or n)");
		System.out.println();
		String userInput = input.nextLine();
		
		// because of the file name requirement the class name for the DFA is pq_16f_cjd62
		p1_16f_cjd62 DFA = new p1_16f_cjd62();
		
		while (userInput.equals("y")) {
			System.out.println("Please enter a string to see if the DFA accepts it: ");
			System.out.println();
			String userString = input.nextLine();
			System.out.println("You entered: " + userString);
			System.out.println();
			
			p1_16f_cjd62.DFATransition(DFA, userString);	
			
			System.out.println("Do you want to enter a string? (y or n)");
			System.out.println();
			userInput = input.nextLine();
		}
		
		if (userInput.equals("n")) {
			System.out.println("goodbye.");
		}
		
		input.close();
	}
	
}
