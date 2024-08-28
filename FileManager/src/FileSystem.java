/**
 * @author Harrison Le
 * Email: Hal117@ucsd.edu
 * 
 * 
 * This file represents the structure of filesystem.
 * There are various methods within this file to filter through and access files. 
 */
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Comparator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 * @author harrisonle
 * 
 * This file represents the entire structure of FileSystem.
 * 
 */
public class FileSystem {

    BST<String, FileData> nameTree;
    BST<String, ArrayList<FileData>> dateTree;
    
    // TODO
    //constructor
    public FileSystem() {
    	this.nameTree = new BST<>();
    	this.dateTree = new BST<>();
    }


    // TODO
    /**
     * Creates a FileSystem object and initializes it. 
     * @param inputFile: File containing FileData
     */
    public FileSystem(String inputFile) {
    	// Add your code here
    	this.nameTree = new BST<>();
    	this.dateTree = new BST<>();
        try {
            File f = new File(inputFile);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String[] data = sc.nextLine().split(", ");
                // Add your code here
                String filedata = data[0];
                String filedir = data[1];
                String filedate = data[2];
                add(filedata, filedir, filedate);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);

        }
    }


    // TODO
    /**
     * Create a file data object with a given name, directory, and modified date and
     * add it to the file system.
     * @param name = name of file
     * @param dir =  name of directory
     * @param date = name of date
     */
    public void add(String name, String dir, String date) {
        if (name == null || dir == null || date == null) {
            return;
        }

        FileData existingFile = nameTree.get(name);
        if (existingFile != null) {
            if (existingFile.lastModifiedDate != null && existingFile.lastModifiedDate.compareTo(date) < 0) {
                existingFile.lastModifiedDate = date;
            }
            if (existingFile.dir != null && !existingFile.dir.equals(dir)) {
                existingFile.dir = dir;
            }
            // No need to update the dateTree
        } else {
            FileData newFile = new FileData(name, dir, date);
            nameTree.set(name, newFile);
            addToDateTree(newFile);
        }
    }

    /**
     * Helper method for add method
     * @param file
     */
    private void addToDateTree(FileData file) {
        if (file.lastModifiedDate == null) {
            return; // Skip files with null lastModifiedDate
        }

        ArrayList<FileData> fileList = dateTree.get(file.lastModifiedDate);
        if (fileList == null) {
            fileList = new ArrayList<>();
            dateTree.put(file.lastModifiedDate, fileList);
        }
        fileList.add(file);
    }

    // TODO
    /**
     * 
     * Method to search for files by the specified date.
     * @param date: specified date to be found
     * @return a file list with filenames with the same date
     */
    public ArrayList<String> findFileNamesByDate(String date) {
    	 ArrayList<String> fileList = new ArrayList<>();

         ArrayList<FileData> files = dateTree.get(date);
         if (files != null) {
             for (FileData data : files) {
                 fileList.add(data.name);
             }
         }

         return fileList;
    }



    // TODO
    /**
     * Given a startDate and an endDate (format: yyyy/mm/dd), 
     * return a new FileSystem that contains only the files that are within the range (startDate is inclusive, endDate is exclusive).
     * @param startDate
     * @param endDate
     * @return
     */
    public FileSystem filter(String startDate, String endDate) {
        FileSystem filesys = new FileSystem();
        for (String key : dateTree.keys()) {
            if (isWithinDateRange(key, startDate, endDate)) {
                ArrayList<FileData> files = dateTree.get(key);
                for (FileData file : files) {
                    filesys.add(file.name, file.dir, file.lastModifiedDate);
                }
            }
        }
        return filesys;
    }

    /**
     * Helper method to check if a date is within the given range
     * @param date modified date
     * @param startDate specified startDate
     * @param endDate specified endDate
     * @return true or false if the date is within the given range
     */
    private boolean isWithinDateRange(String date, String startDate, String endDate) {
        return date.compareTo(startDate) >= 0 && date.compareTo(endDate) < 0;
    }
    
    
    // TODO
    /**
     * Give a string wildCard, return a new FileSystem that contains only the files with names that contain the wildCard string.
     * @param wildCard specified key we are using to filter
     * @return files that contain the wildcard string.
     */
    public FileSystem filter(String wildCard) {
    	  FileSystem filesys = new FileSystem();
    	    for (String key : nameTree.keys()) {
    	        if (key.contains(wildCard)) {
    	            FileData file = nameTree.get(key);
    	            filesys.add(file.name, file.dir, file.lastModifiedDate);
    	        }
    	    }
    	    return filesys;
    }
    
    
    // TODO
    /**
     * Output for nameTree
     * @return a List that contains the nametree where each entry is formatted as: ": <FileData toString()>"
     */
    public List<String> outputNameTree(){
    	List<String> list = new ArrayList<>();
        for (String key : nameTree.keys()) {
            FileData fileData = nameTree.get(key);
            String entry = key + ": " + fileData.toString();
            list.add(entry);
        }
        return list;
    }

    
    
    
    // TODO
    /**
     * Output for dateTree
     * @return a List that contains the dateTree where each entry is formatted as  ": <FileData toString()>"
     */
    public List<String> outputDateTree() {
        List<String> list = new ArrayList<>();
        List<String> sortedKeys = new ArrayList<>(dateTree.keys());
        sortedKeys.sort(Comparator.reverseOrder()); // Sort in descending order
        for (String key : sortedKeys) {
            ArrayList<FileData> files = dateTree.get(key);
            for (int i = files.size() - 1; i >= 0; i--) {
                FileData fileData = files.get(i);
                String entry = key + ": " + fileData.toString();
                list.add(entry);
            }
        }
        return list;
    }


}

