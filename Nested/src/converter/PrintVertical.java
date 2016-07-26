package converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class PrintVertical {
	
	static PrintVertical instance = new PrintVertical();

	private static String dashes(int number) {
		String totalDashes = "";
		for (int i = 0; i < number; i++) {
			totalDashes += "-";
		}
		return totalDashes;
	}
	
	private static ArrayList<Node> composeNodes(String input) {
		char current = '0';
		String name = "";
		ArrayList<Node> resultNode = new ArrayList<Node>();
		for (int i = 0; i < input.length(); i++) {
			current = input.charAt(i);
			if (current == '(') {
				Node node = instance.new Node(name);
				int matchingIndex = matchingParenthesisIndex(i, input);
				node.children = composeNodes(input.substring(i + 1, matchingIndex));
				name = "";
				i = matchingIndex;
				resultNode.add(node);
			} else if (current == ',') {
				if (name.equals("")) {
					continue;
				}
				Node node = instance.new Node(name);
				resultNode.add(node);
				name = "";
			} else if (current == ')') {
				Node node = instance.new Node(name);
				resultNode.add(node);
				return resultNode;
			} else {
				name += current;
			}
		}
		if (!name.equals("")) {
			Node node = instance.new Node(name);
			resultNode.add(node);
		}
		return resultNode;
	}
	
	private static int matchingParenthesisIndex(int index, String input) {
		int matchingIndex = index + 1;
		int count = 0;
		while (matchingIndex++ <= input.length()) {
			if (input.charAt(matchingIndex) == ')' && count == 0) {
				return matchingIndex;
			} else if (input.charAt(matchingIndex) == '(') {
				count++;
			} else if (input.charAt(matchingIndex) == ')') {
				count--;
			}
		}
		return -999;
	}

	private static void print(ArrayList<Node> nodes) {
		print(nodes, 0);
	}
	
	private static void print(ArrayList<Node> nodes, int numDashes) {
		for (int i = 0; i < nodes.size(); i++) {
			Node current = nodes.get(i);
			String dashes = dashes(numDashes);
			if (dashes.length() == 0) {
				System.out.println(current.name);
			} else {
				System.out.println(dashes(numDashes) + " " + current.name);
			}
			print(current.children, numDashes + 1);
		}
	}

	private static void sort(ArrayList<Node> nodes) {
		for (int i = 0; i < nodes.size(); i++) {
			Node current = nodes.get(i);
			sort(current.children);
		}
		Collections.sort(nodes);
	}
	
	class Node implements Comparable<Node> {
		
		private String name;
		private ArrayList<Node> children;
		
		private Node(String name) {
			this.name = name;
			this.children = new ArrayList<Node>();
		}
		
		public int compareTo(Node other) {
	        return name.compareTo(other.name);
	    }
		
		public int hashCode() {
	        return name.hashCode();
	    }

	    public boolean equals(Object other) {
	        return (other instanceof Node) && name.equals(((Node)other).name);
	    }
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the string to convert(without quotes): ");
		String input = sc.nextLine();
		System.out.println("Sort the result(yes, no): ");
		String sorted = sc.nextLine();
		input = input.substring(1, input.length() - 1); //Remove beginning and ending parentheses
		sc.close();
		ArrayList<Node> nodes = composeNodes(input);
		if (sorted.equals("yes")) {
			sort(nodes);
		}
		System.out.println("Input: '" + input + "' converts to: ");
		print(nodes);

	}
}
