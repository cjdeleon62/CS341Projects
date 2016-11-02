/*
 * Christopher de leon
 * CS341 Project2
 * UCID: cjd62
 */

import java.io.PrintStream;
import java.util.*;
import java.io.FileOutputStream;
import java.io.File;

public class p2_16f_cjd62 {
	// keeps track of PDA's state to let the user know which state we are in while checking the string.
	int currentState;

	// stack to make sure the parenthesis in the string are matching.
	Stack<Character> pdaStack = new Stack<Character>();

	public p2_16f_cjd62() {
		// initialize the current state of the PDA to 0 because it is the starting state.
		currentState = 0;
	}

	// @params p2_16f_cjd62 PDA {object} - takes in a PDA as an object which will run the input strings to see
	// if they are accepted or not. inputString {String} - string entered by the user assumed to have characters 
	// over the alphabet of the language sigma.
	public static void PDATransition(final p2_16f_cjd62 PDA, final String inputString) {
		char[] Arr = inputString.toCharArray();

		// let the user know what state we are starting in
		System.out.println("starting in state q" + PDA.currentState);
		System.out.println();
		int i = 0;
		
		for(; i < Arr.length; i++) {
			p2_16f_cjd62.traversePDA(PDA, Arr[i]);
		}

		// Holds the different responses to the possible end states of the PDA
		// Tells the user the PDA either accepts or rejects the string.
		HashMap<Integer, String> PDAStates = new HashMap<Integer, String>();
		PDAStates.put(7, "String was accepted by PDA.");
		PDAStates.put(8, "String was rejected by PDA.");

		// if the PDA is in  the q8 error state, it means the PDA has crashed and let the user know.
		if(PDA.currentState == 8) {
			System.out.println("PDA crashed before reaching the end of the input string.");
		}

		System.out.println(PDAStates.get(PDA.currentState));
		System.out.println("-----------------------------------------------------------");
		System.out.println();

		// Reset the PDA state to allow the PDA to process another string
		PDA.currentState = 0;
	}

	// @params p2_16f_cjd62 PDA {object} - PDA that processes the string over the alphabet sigma. ch {char} -
	// characters from the string to transition states of the PDA. Prints out the state after each transition.
	public static void traversePDA(final p2_16f_cjd62 PDA, final char ch) {
		int state = PDA.currentState;
		System.out.println("Current symbol: " + ch);

		if (state == 0) {
			PDA.check0(PDA, ch);
		}
		else if(state == 1) {
			PDA.check1(PDA, ch);
		}
		else if(state == 2) {
			PDA.check2(PDA, ch);
		}
		else if(state == 3) {
			PDA.check3(PDA, ch);
		}
		else if(state == 4) {
			PDA.check4(PDA, ch);
		}
		else if(state == 5) {
			PDA.check5(PDA, ch);
		}
		else if(state == 6) {
			PDA.check6(PDA, ch);
		}
		else if(state == 7) {
			PDA.check7(PDA, ch);
		}
		else if(state == 8) {
			PDA.error8(PDA, ch);
		}

		System.out.println("Current state after reading symbol " + ch + ": q" + PDA.currentState);
		System.out.println();
	}

	// functions check0 - check8 correspond to the 8 different possible states in the PDA.
	// each function checks the symbol and makes the correct transition specified in the PDA drawing.
	// PDA object and ch are final to make sure they are not changed during the execution of the function.
	public void check0(final p2_16f_cjd62 PDA, final char ch) {
		if (ch == '$') {
			PDA.pdaStack.push(ch);
			System.out.println(printPushMessage(ch));
			System.out.println("Nothing was popped from the stack.");
			PDA.currentState = 1;
		}
		else {
			System.out.println("Nothing was pushed onto the stack or popped from the stack.");
			PDA.currentState = 8;
		}
	}

	public void check1(final p2_16f_cjd62 PDA, final char ch) {
		if(isInteger(ch)) {
			System.out.println("Nothing was pushed onto the stack or popped from the stack.");
			PDA.currentState = 3;
		}
		else if(ch == '(') {
			PDA.pdaStack.push(ch);
			System.out.println(printPushMessage(ch));
			System.out.println("Nothing was popped from the stack.");
			PDA.currentState = 2;
		}
		else if (isLetter(ch) || ch == '_' ){
			System.out.println("Nothing was pushed onto the stack or popped from the stack.");
			PDA.currentState = 4;
		}
		else {
			System.out.println("Nothing was pushed onto the stack or popped from the stack.");
			PDA.currentState = 8;
		}
	}

	public void check2(final p2_16f_cjd62 PDA, final char ch) {
		if(ch == '(') {
			PDA.pdaStack.push(ch);
			System.out.println(printPushMessage(ch));
			System.out.println("Nothing was popped from the stack.");
			PDA.currentState = 2; 
		}
		else if(isInteger(ch)) {
			System.out.println("Nothing was pushed onto the stack or popped from the stack.");
			PDA.currentState = 3;
		}
		else if(isLetter(ch) || ch == '_'){
			System.out.println("Nothing was pushed onto the stack or popped from the stack.");
			PDA.currentState = 4;
		}
		else {
			System.out.println("Nothing was pushed onto the stack or popped from the stack.");
			PDA.currentState = 8;
		}
	}

	public void check3(final p2_16f_cjd62 PDA, final char ch) {
		if(isInteger(ch)) {
			System.out.println("Nothing was pushed onto the stack or popped from the stack.");
			PDA.currentState = 3;
		}
		else if(isOperator(ch)) {
			System.out.println("Nothing was pushed onto the stack or popped from the stack.");
			PDA.currentState = 5;
		}
		else if(ch == ')') {
			if(PDA.pdaStack.peek() == '(') {
				System.out.println(printPopMessage(PDA.pdaStack.peek()));
				System.out.println("Nothing was pushed onto the stack.");
				PDA.pdaStack.pop();
				PDA.currentState = 6;
			}
			else {
				System.out.println("Nothing was pushed onto the stack or popped from the stack.");
				PDA.currentState = 8;
			}
		}
		else if(ch == '$') {
			if(PDA.pdaStack.peek() == '$') {
				System.out.println(printPopMessage(PDA.pdaStack.peek()));
				System.out.println("Nothing was pushed onto the stack.");
				PDA.pdaStack.pop();
				if(PDA.pdaStack.empty()) {
					PDA.currentState = 7;	
				}
				else {
					System.out.println("Nothing was pushed onto the stack or popped from the stack.");
					PDA.currentState = 8;
				}
			}
			else {
				System.out.println("Nothing was pushed onto the stack or popped from the stack.");
				PDA.currentState = 8;
			}
		}
		else {
			System.out.println("Nothing was pushed onto the stack or popped from the stack.");
			PDA.currentState = 8;
		}
	}

	public void check4(final p2_16f_cjd62 PDA, final char ch) {
		if(isLetter(ch) || isInteger(ch) || ch == '_') {
			System.out.println("Nothing was pushed onto the stack or popped from the stack.");
			PDA.currentState = 4;
		}
		else if(isOperator(ch)) {
			System.out.println("Nothing was pushed onto the stack or popped from the stack.");
			PDA.currentState = 5;
		}
		else if(ch == ')') {
			if(PDA.pdaStack.peek() == '(') {
				System.out.println(printPopMessage(PDA.pdaStack.peek()));
				System.out.println("Nothing was pushed onto the stack.");
				PDA.pdaStack.pop();
				PDA.currentState = 6;
			}
			else {
				System.out.println("Nothing was pushed onto the stack or popped from the stack.");
				PDA.currentState = 8;
			}
		}
		else if(ch == '$'){
			if(PDA.pdaStack.peek() == '$') {
				System.out.println(printPopMessage(PDA.pdaStack.peek()));
				System.out.println("Nothing was pushed onto the stack.");
				PDA.pdaStack.pop();
				if(PDA.pdaStack.empty()) {
					PDA.currentState = 7;
				}
				else {
					System.out.println("Nothing was pushed onto the stack or popped from the stack.");
					PDA.currentState = 8;
				}
			}
			else {
				System.out.println("Nothing was pushed onto the stack or popped from the stack.");
				PDA.currentState = 8;
			}
		}
		else {
			System.out.println("Nothing was pushed onto the stack or popped from the stack.");
			PDA.currentState = 8;
		}
	}

	public void check5(final p2_16f_cjd62 PDA, final char ch) {
		if(isInteger(ch)) {
			System.out.println("Nothing was pushed onto the stack or popped from the stack.");
			PDA.currentState = 3;
		}
		else if(isLetter(ch) || ch == '_') {
			System.out.println("Nothing was pushed onto the stack or popped from the stack.");
			PDA.currentState = 4;
		}
		else if(ch == '(') {
			PDA.pdaStack.push(ch);
			System.out.println(printPushMessage(ch));
			System.out.println("Nothing was popped from the stack.");
			PDA.currentState = 2;
		}
		else {
			System.out.println("Nothing was pushed onto the stack or popped from the stack.");
			PDA.currentState = 8;
		}
	}

	public void check6(final p2_16f_cjd62 PDA, final char ch) {
		if(ch == ')') {
			if(PDA.pdaStack.peek() == '(') {
				System.out.println(printPopMessage(PDA.pdaStack.peek()));
				System.out.println("Nothing was pushed onto the stack.");
				PDA.pdaStack.pop();
				PDA.currentState = 6;
			}
			else {
				System.out.println("Nothing was pushed onto the stack or popped from the stack.");
				PDA.currentState = 8;
			}
		}
		else if(ch == '$') {
			if(PDA.pdaStack.peek() == '$') {
				System.out.println(printPopMessage(PDA.pdaStack.peek()));
				System.out.println("Nothing was pushed onto the stack.");
				PDA.pdaStack.pop();
				PDA.currentState = 7;
			}
			else {
				System.out.println("Nothing was pushed onto the stack or popped from the stack.");
				PDA.currentState = 8;
			}
		}
		else if(isOperator(ch)) {
			System.out.println("Nothing was pushed onto the stack or popped from the stack.");
			PDA.currentState = 5;
		}
		else {
			System.out.println("Nothing was pushed onto the stack or popped from the stack.");
			PDA.currentState = 8;
		}
	}

	public void check7(final p2_16f_cjd62 PDA, final char ch) {
		System.out.println("Nothing was pushed onto the stack or popped from the stack.");
		PDA.currentState = 7;
	}

	public void error8(final p2_16f_cjd62 PDA, final char ch) {
	System.out.println("Nothing was pushed onto the stack or popped from the stack.");
		PDA.currentState = 8;
	}

	// @params c {char} - character from the input string.
	public boolean isOperator(final char c) {
		switch(c) {
			case '*': 
				return true;
			case '+':
				return true;
			case '-':
				return true;
			case '/':
				return true;
			default:
				return false;
		}
	}

	// @params c {char} - character from the input string.
	public boolean isLetter(final char c) {
		if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
			return true;
		}
		return false;
	}

	// @params c {char} - character from the input string.
	public boolean isInteger(final char c) {
		if(c >= '0' && c <= '9') {
			return true;
		} 
		return false;
	}

	// print message to let user know when a symbol was pushed onto the stack.
	public String printPushMessage(final char c) {
		return "The character " + c + " was pushed onto the stack.";
	}

	// print message to let user know when a symbol was popped from the stack.
	public String printPopMessage(final char c) {
		return "The character " + c + " was popped from the stack.";
	} 


	public static void main(String[] args) {
		// make a file prog2output.txt and print any errors that may occur
		try {
			File file = new File("prog2output.txt");
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			PrintStream out = new PrintStream(fos);
			System.setOut(out);
			
		}
		// if there is an error with the process, print the error.
		catch (Exception e) {
			e.printStackTrace();
		}
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("Do you want to enter a string? (y or n)");
		System.out.println();
		
		String userInput = input.nextLine();
		
		// because of the file name requirement the class name for the DFA is pq_16f_cjd62
		p2_16f_cjd62 PDA = new p2_16f_cjd62();
		
		while (userInput.equals("y")) {
			System.out.println("Please enter a string to see if the PDA accepts it: ");
			System.out.println();
			String userString = input.nextLine();
			System.out.println("You entered: " + userString);
			System.out.println();
			
			// run the string on the PDA to check whether or not it is in the language.
			p2_16f_cjd62.PDATransition(PDA, userString);	
			
			System.out.println("Do you want to enter a string? (y or n)");
			System.out.println();
			userInput = input.nextLine();
		}
		
		// the user ends the program by entering "n"
		if (userInput.equals("n")) {
			System.out.println("goodbye.");
		}
		
		input.close();
	}
}