import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.stream.IntStream; 

public class averageAcquisition2 {

	public static boolean isStringInt(String s)
	{
	    try
	    {
	        Integer.parseInt(s);
	        return true;
	    } catch (NumberFormatException ex)
	    {
	        return false;
	    }
	}
	
	public static void main(String[] args) throws IOException, FileNotFoundException {
		Scanner in = (new Scanner(System.in));
		
		Scanner fileIn = new Scanner(new File ("averageDocument.txt")); // Create a Scanner object to read from the input file
		
		String file = "";
		while (fileIn.hasNextLine()) {
			String line = fileIn.nextLine();
			// Use cipher method to encrypt/decrypt the current line of the file
			file += line;
		} 
		
		int classNameInd = 110;
		
		String originalFile = file;
		
		// Create output file and writer
		FileWriter out = new FileWriter("averageDocument.txt");
		BufferedWriter bw = new BufferedWriter(out);
		PrintWriter outputFile = new PrintWriter(bw);
		
		boolean keepGoing = false;
		double avg = 0;
		double totalPercent = 0;
		
		
		while (keepGoing == false) {
			int ind = file.indexOf("Overall (");
			
			if (ind == -1) {
				break;
			} else if (file.substring(ind + 9, ind + 10).equals("-") == false) {
			file = file.substring(ind);
			double percent = Integer.parseInt(file.substring(9, 10));
			if (file.substring(10, 11).equals(".")) {
				double decimal = Integer.parseInt(file.substring(11, 12));
				percent += decimal / 10;
			} else if (isStringInt(file.substring(10,11)) == true) {
				percent *= 10;
				percent += Integer.parseInt(file.substring(10,11));
			}

			boolean keepGoing2 = false;
			
			file = file.substring(file.indexOf(")") + 1);
			int i = 0;
			while (keepGoing2 == false) {
				if (isStringInt(file.substring(i, 1 + i) ) == false) {
					break;
				}
				i++;
			}
			double grade = Integer.parseInt(file.substring(0, i));
			if (file.charAt(i) == '.') {
				file = file.substring(i + 1);
				i = 0;
				while (keepGoing2 == false) {
					if (isStringInt(file.substring(i, 1 + i) ) == false) {
						break;
					}
					i++;
				}
				
				String processed = file.substring(0, i);
				
				double division = 1;
				
				for (int k = 0; k < processed.length(); k++) {
					division *= 10;
				}
				
				double decimalOfIt = Integer.parseInt(processed);
				
				grade += (decimalOfIt / division);
			}
			
			int j = 0;
			
			double mark;
			
			if (file.charAt(i) == '%')
			{
				mark = grade / 100;
			}
			else
			{
			file = file.substring(file.indexOf("/") + 1);
			
			while (keepGoing2 == false) {
				String currentDissection = file.substring(0, 1 + j);
				if (isStringInt(currentDissection) == false || Integer.parseInt(currentDissection) == grade || currentDissection.length() >= 3 || grade / Integer.parseInt(currentDissection) * 10 < 86) {
					break;
				}
				j++;
			}
			String marks = file.substring(0, 1 + j);
			double marksNum = Integer.parseInt(marks);
			
			mark = grade / marksNum;
			}
			
			avg += mark * percent;
			totalPercent += percent;
			
			file = file.substring(1 + j);
			} else {
				file = file.substring(ind + 10);
			}
		}
		
		String data = originalFile.substring(classNameInd);
		int endInd = 0;
		int leftoverInds = 0;
		while (endInd + leftoverInds < classNameInd) {
			originalFile = originalFile.substring(endInd + 1);
			leftoverInds += endInd + 1;
			endInd = originalFile.indexOf("Class");
		}
		String className = "";
		endInd += leftoverInds;
		if (endInd == -1) {
			className = "[Class name not given].";
		} else {
			className = data.substring(0, endInd - classNameInd);
		}
		String outputMsg = "Class: " + className + ".\nScore: " + avg + " / " + totalPercent + " total marks.\nAverage: " + ((avg / totalPercent) * 100) + "%.";

		outputFile.println(outputMsg);
		

	outputFile.close();
	}

}