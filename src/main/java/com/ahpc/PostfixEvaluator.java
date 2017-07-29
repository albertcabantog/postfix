package com.ahpc;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class PostfixEvaluator {
	private static final String VALID_OPERAND = "-|\\+|/|\\*";
	private static final String VALID_COL_ROW = "[a-cA-C][1-5]";

	public static void main(String... args) {
		if (args == null || args.length != 2) {
			System.out.println("Please enter source and output filenames");
			throw new IllegalArgumentException("Invalid parameter");
		}
		String inputFile = args[0];
		String outputFile = args[1];
		String linePostfix = "";
		Path p = Paths.get(outputFile);
		try (Scanner s = new Scanner(new FileInputStream(inputFile));
				BufferedWriter bw = Files.newBufferedWriter(p, StandardCharsets.UTF_8);) {
			String rows;
			
			while (s.hasNextLine()) {
				rows = s.nextLine();
				linePostfix = evaluateLine(rows);
				bw.write(linePostfix);
				bw.newLine();
//				System.out.format("Line: %s \n", rows);
//				System.out.format("linePostfix: %s \n", linePostfix);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Please enter a valid source file");
			throw new IllegalArgumentException("Invalid source file");
		} catch (IOException e) {
			System.out.println("Error in writing to output file");
			e.printStackTrace();
		}
	}

	private static Map<String, List<Integer>> inputData() {
		Map<String, List<Integer>> data = new HashMap<String, List<Integer>>();
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		data.put("a", list);

		list = new ArrayList<Integer>();
		list.add(6);
		list.add(7);
		list.add(8);
		list.add(9);
		list.add(10);
		data.put("b", list);

		list = new ArrayList<Integer>();
		list.add(11);
		list.add(12);
		list.add(13);
		list.add(14);
		list.add(15);
		data.put("c", list);
		return data;
	}

	private static String evaluateLine(String line) {
		String result = "";
		try (Scanner cells = new Scanner(line)) {
			cells.useDelimiter(",");
			while (cells.hasNext()) {
				String cell = cells.next();
				Float postfix = cell.trim().length() == 0 ? null : evaluateCell(cell);
				result += postfix == null ? "#ERR" : postfix;
				result += cells.hasNext() ? "," : "";
				result = result.replace(".0", "");
			}
		}

		return result;
	}

	private static Float evaluateCell(String cell) {
		String[] splitCell = cell.split(" ");
		if ((splitCell.length == 1 && Character.isDigit(splitCell[0].charAt(0)) == false)
				|| splitCell.length % 2 == 0) {
			return null;
		}
		Stack<Float> stack = new Stack<Float>();

		try (Scanner tokens = new Scanner(cell)) {
			while (tokens.hasNext()) {
				if (tokens.hasNextInt()) {
					stack.push((float) tokens.nextInt());
				} else {
					String t = tokens.next();
					if (isCellValid(t, VALID_COL_ROW)) {
						int val = getData(t);
						stack.push((float) val);
					} else if (isCellValid(t, VALID_OPERAND)) {
						Float num2 = stack.pop();
						Float num1 = stack.pop();

						if (t.equals("+")) {
							stack.push(num1 + num2);
						} else if (t.equals("-")) {
							stack.push(num1 - num2);
						} else if (t.equals("*")) {
							stack.push(num1 * num2);
						} else if (t.equals("/")) {
							stack.push(num1 / num2);
						}
					} else {
						return null;
					}
				}
			}
		}

		return stack.pop();
	}

	/**
	 * Retrieves the value from the input data with the input column and row value
	 * @param colRow
	 * @return
	 */
	private static int getData(String colRow) {
		Map<String, List<Integer>> data = inputData();
		String column = colRow.substring(0, 1);
		String row = colRow.substring(1, 2);
		List<Integer> list = data.get(column.toLowerCase());
		int val = list.get(Integer.valueOf(row) - 1);
		return val;
	}

	/**
	 * Verifies if value represents a translatable
	 * @param value
	 * @param pattern
	 * @return
	 */
	private static boolean isCellValid(String value, String pattern) {
		boolean result = false;
		Pattern p = Pattern.compile(pattern);
		Matcher matcher = p.matcher(value);
		result = matcher.matches();
		return result;
	}
}
