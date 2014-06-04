import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ForcedCheckpointCount {
	private static Scanner rfile;
	int num_nodes = 0;

	public static void main(String args[]) {
		int checkpointCount = 0;
		String currentLine = "";
		String[] filenames = { "machine0.out",
				"machine1.out",
				"machine2.out",
				"machine3.out",
				"machine4.out" };
		for (int i = 0; i < filenames.length; i++) {
			try {
				rfile = new Scanner(new File(filenames[i]));
				while (rfile.hasNextLine()) {
					currentLine = rfile.nextLine();
					if (currentLine.contains("Forced Checkpoints:")) {
						String[] str = currentLine
								.split("Forced Checkpoints: ");
						checkpointCount += Integer.parseInt(str[1]);
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		System.out.println(checkpointCount);
	}
}
