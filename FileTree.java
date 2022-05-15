package Assignment1;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.*;

public class FileTree {
	
	private static long getDirSize(File directory) {
		long length = 0;
		
		File[] files = directory.listFiles();
		
		int count = files.length;
		
		for (int i = 0; i < count; ++i) {
			if (files[i].isFile()) {
				length += files[i].length();
			} else {
				length += getDirSize(files[i]);
			}
		}
		return length;
	}
	
	private static int fileCount(File directory) {
		int count = 0;
		File[] files = directory.listFiles();
		
		int size = files.length;
		
		for (int i = 0; i < size; ++i) {
			if (files[i].isFile()) {
				++count;
			} else {
				count += fileCount(files[i]);
			}
		}
		
		return count;
	}

	public static void main(String[] args) throws Exception {
		Scanner scnr = new Scanner(System.in);
		
		System.out.print("Enter file path:  ");
		String dirPath = scnr.nextLine();
				//"C:\\Users\\alyso\\OneDrive\\Pictures";
		
		File currentDir = new File(dirPath);
		Path currentPath = Paths.get(dirPath);
		
		long dirSize = getDirSize(currentDir);
		
		
		//Print File Name
		System.out.println("Displaying files from: " + currentDir);
		
		//Print Number of Files
		//FIXME
		int count = fileCount(currentDir);
		
		System.out.println("Total number of files: " + count);
		
		//Print total size of files
		System.out.print("Directory size: ");
		System.out.printf("%.2f", (double)dirSize / (1024 * 1024));
		System.out.println(" MB");
		
		listDir(currentPath, 0);
		//listDir(arr, 0);

	}

	public static void listDir(Path path, int depth) throws Exception {
		
		BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
		
		// if directory, list files, and traverse down inside each
		if(attr.isDirectory()) {
			
			DirectoryStream<Path> paths = Files.newDirectoryStream(path);
			
			System.out.println(spacesForDepth(depth) + " > " + path.getFileName());
			
			for (Path tempPath: paths) {
				listDir(tempPath, depth + 1);
			}
		} else {
			System.out.println(spacesForDepth(depth) + " -- " + path.getFileName());
		}
		
	}
	
	public static String spacesForDepth(int depth) { 
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < depth; ++i) {
			builder.append("    ");
		}
		return builder.toString();
	}
}
	
