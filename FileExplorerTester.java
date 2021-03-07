///////////////////// TOP OF FILE COMMENT BLOCK ///////////////////////////////
//
// Title: File Explorer Tester
// Course: CS 300 Fall 2020
//
// Author: Ben Milas
// Email: bmilas@wisc.edu
// Lecturer: Hobbes LeGault

///////////////////////////// OUTSIDE HELP CREDIT /////////////////////////////
//
// Persons: NONE
// Online Sources: NONE
//
///////////////////////////////////////////////////////////////////////////////

import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A class that tests the ability of FileExplorer.java to list contents of folders and searches for
 * files using recursive methods
 * 
 * @author Ben Milas
 *
 */
public class FileExplorerTester {

  /**
   * Tests the ability of FileExplorer.listContents to return a list of the names of all files and
   * directories in the the given folder and throw expected exceptions for invalid inputs
   * 
   * @param folder the File passed into the method to be searched
   * @return true if all the tests pass, false otherwise
   */
  public static boolean testListContents(File folder) {
    try {
      // Scenario 1
      // list the basic contents of the cs300 folder
      ArrayList<String> listContent = FileExplorer.listContents(folder);
      // expected output content
      String[] contents = new String[] {"grades", "lecture notes", "programs",
          "quizzes preparation", "reading notes", "syllabus.txt", "todo.txt"};
      List<String> expectedList = Arrays.asList(contents);
      // check the size and the contents of the output
      if (listContent.size() != 7) {
        System.out.println("Problem detected: cs300 folder must contain 7 elements.");
        return false;
      }
      for (int i = 0; i < expectedList.size(); i++) {
        if (!listContent.contains(expectedList.get(i))) {
          System.out.println("Problem detected: " + expectedList.get(i)
              + " is missing from the output of the list contents of cs300 folder.");
          return false;
        }
      }

      // Scenario 2 - list the contents of the grades folder
      File f = new File(folder.getPath() + File.separator + "grades");
      listContent = FileExplorer.listContents(f);
      if (listContent.size() != 0) {
        System.out.println("Problem detected: grades folder must be empty.");
        return false;
      }

      // Scenario 3 - list the contents of the p02 folder
      f = new File(folder.getPath() + File.separator + "programs" + File.separator + "p02");
      listContent = FileExplorer.listContents(f);
      if (listContent.size() != 1 || !listContent.contains("WisconsinPrairie.java")) {
        System.out.println("Problem detected: p02 folder must contain only one file named "
            + "WisconsinPrairie.java.");
        return false;
      }

      // Scenario 4 - Try to list the contents of a file
      f = new File(folder.getPath() + File.separator + "todo.txt");
      try {
        listContent = FileExplorer.listContents(f);
        System.out.println("Problem detected: Your FileExplorer.listContents() must "
            + "throw a NotDirectoryException if it is provided an input which is not"
            + "a directory.");
        return false;
      } catch (NotDirectoryException e) { // catch only the expected exception
        // Expected behavior -- no problem detected
      }

      // Scenario 5 - Try to list the contents of not found directory/file
      f = new File(folder.getPath() + File.separator + "music.txt");
      try {
        listContent = FileExplorer.listContents(f);
        System.out.println("Problem detected: Your FileExplorer.listContents() must "
            + "throw a NotDirectoryException if the provided File does not exist.");
        return false;
      } catch (NotDirectoryException e) {
        // catch only the expected exception to be thrown -- no problem detected
      }
    } catch (Exception e) {
      System.out.println("Problem detected: Your FileExplorer.listContents() has thrown"
          + " a non expected exception.");
      e.printStackTrace();
      return false;
    }

    // all other tests passed
    return true;
  }

  /**
   * Tests the ability of FileExplorer.deepListContents to pass a recursive base case where it
   * attempts to list the files of an empty folder
   * 
   * @param folder the File passed into the method to be searched
   * @return true if the base case passes, false otherwise
   */
  public static boolean testDeepListBaseCase(File folder) {
    try {
      // Base case - list the files of the empty grades folder
      File f = new File(folder.getPath() + File.separator + "grades");
      ArrayList<String> deepListContent = FileExplorer.deepListContents(f);

      if (deepListContent.size() != 0) {
        System.out.println("Problem detected: grades folder must be empty.");
        return false;
      }
    } catch (Exception e) {
      System.out.println("Problem detected: Your FileExplorer.deepListContents() has thrown"
          + " an unexpected exception.");
      e.printStackTrace();
      return false;
    }

    // base case passed
    return true;
  }

  /**
   * Tests the ability of FileExplorer.deepListContents to recursively lists the names of all the
   * files (not directories) in the given folder and its sub-folders and throw expected exceptions
   * for invalid inputs
   * 
   * @param folder the File passed into the method to be searched
   * @return true if all the tests pass, false otherwise
   */
  public static boolean testDeepListRecursiveCase(File folder) {
    try {
      // Scenario 1
      // list the basic contents of the cs300 folder
      ArrayList<String> deepListContent = FileExplorer.deepListContents(folder);
      // expected output content
      String[] contents = new String[] {"ExceptionHandling.txt", "proceduralProgramming.txt",
          "UsingObjectsAndArrayLists.txt", "AlgorithmAnalysis.txt", "Recursion.txt",
          "COVIDTracker.java", "COVIDTrackerTester.java", "WisconsinPrairie.java",
          "Benchmarker.java", "ComparisonMethods.java", "Program01.pdf", "Program02.pdf",
          "Program03.pdf", "codeSamples.java", "outline.txt", "zyBooksCh1.txt", "zyBooksCh2.txt",
          "zyBooksCh3.txt", "zyBooksCh4.txt", "syllabus.txt", "todo.txt"};
      List<String> expectedList = Arrays.asList(contents);
      // check the size and the contents of the output
      if (deepListContent.size() != 21) {
        System.out.println("Problem detected: cs300 folder must contain 21 files.");
        return false;
      }
      for (int i = 0; i < expectedList.size(); i++) {
        if (!deepListContent.contains(expectedList.get(i))) {
          System.out.println("Problem detected: " + expectedList.get(i)
              + " is missing from the output of the list contents of cs300 folder.");
          return false;
        }
      }

      // Scenario 2 - list the files of the p02 folder
      File f = new File(folder.getPath() + File.separator + "programs" + File.separator + "p02");
      deepListContent = FileExplorer.deepListContents(f);
      if (deepListContent.size() != 1 || !deepListContent.contains("WisconsinPrairie.java")) {
        System.out.println("Problem detected: p02 folder must contain only one file named "
            + "WisconsinPrairie.java.");
        return false;
      }
      // Scenario 3 - Try to list the files of a file
      f = new File(folder.getPath() + File.separator + "todo.txt");
      try {
        deepListContent = FileExplorer.deepListContents(f);
        System.out.println("Problem detected: Your FileExplorer.deepListContents() must "
            + "throw a NotDirectoryException if it is provided an input which is not"
            + "a directory.");
        return false;
      } catch (NotDirectoryException e) { // catch only the expected exception
        // Expected behavior -- no problem detected
      }

      // Scenario 4 - Try to list the files of not found directory/file
      f = new File(folder.getPath() + File.separator + "music.txt");
      try {
        deepListContent = FileExplorer.deepListContents(f);
        System.out.println("Problem detected: Your FileExplorer.deepListContents() must "
            + "throw a NotDirectoryException if the provided File does not exist.");
        return false;
      } catch (NotDirectoryException e) {
        // catch only the expected exception to be thrown -- no problem detected
      }
    } catch (Exception e) {
      System.out.println("Problem detected: Your FileExplorer.deepListContents() has thrown"
          + " an unexpected exception.");
      e.printStackTrace();
      return false;
    }

    // all tests passed
    return true;
  }


  /**
   * Tests the ability of FileExplorer.searchByName to recursively search and return a path from the
   * given folder and all of its subfolders for an exact match to the provided fileName and throw
   * expected exceptions for invalid inputs
   * 
   * @param folder the File passed into the method to be searched
   * @return true if all the tests pass, false otherwise
   */
  public static boolean testSearchByFileName(File folder) {
    try {
      // Scenario 1
      // search for a file within the cs300 folder
      String fileName = FileExplorer.searchByName(folder, "todo.txt");
      if (!fileName.equals("cs300" + File.separator + "todo.txt")) {
        System.out.println("Problem detected: todo.txt path not correctly displayed");
        return false;
      }

      // Scenario 2
      // search for a file from reading notes, a subfolder of the cs300 folder
      File f = new File(folder.getPath() + File.separator + "reading notes");
      fileName = FileExplorer.searchByName(f, "zyBooksCh1.txt");
      if (!fileName.equals("reading notes" + File.separator + "zyBooksCh1.txt")) {
        System.out.println("Problem detected: zyBooksCh1.txt path not correctly displayed");
        return false;
      }

      // Scenario 3
      // search for a file COVIDTracker.java, a file within a folder from programs, a subfolder of
      // the
      // cs300 folder
      f = new File(folder.getPath() + File.separator + "programs");
      fileName = FileExplorer.searchByName(f, "COVIDTracker.java");
      if (!fileName
          .equals("programs" + File.separator + "p01" + File.separator + "COVIDTracker.java")) {
        System.out.println("Problem detected: COVIDTracker.java path not correctly displayed");
        return false;
      }
      // Scenario 4
      // search from a file that does not exist (null file)
      try {
        f = null;
        fileName = FileExplorer.searchByName(f, "COVIDTracker.java");
        System.out.println("Problem detected: Your FileExplorer.searchByName() must"
            + "\"throw a NoSuchElementException if the provided File does not exist.\"");

        return false;
      } catch (NoSuchElementException nsee) {
        // catch only the expected exception to be thrown -- no problem detected
      }
      // Scenario 5
      // search from a file that is not a directory
      try {
        f = new File(folder.getPath() + File.separator + "todo.txt");
        fileName = FileExplorer.searchByName(f, "");
        System.out.println("Problem detected: Your FileExplorer.searchByName() must"
            + "\"throw a NoSuchElementException if the provided File is not a directory.\"");
        return false;
      } catch (NoSuchElementException nsee) {
        // catch only the expected exception to be thrown -- no problem detected
      }

      // Scenario 6
      // search for a null fileName
      try {
        f = new File(folder.getPath());
        fileName = FileExplorer.searchByName(f, null);
        System.out.println("Problem detected: Your FileExplorer.searchByName() must"
            + "\"throw a NoSuchElementException if the fileName is null.\"");
        return false;
      } catch (NoSuchElementException nsee) {
        // catch only the expected exception to be thrown -- no problem detected
      }

      // Scenario 7
      // search cs300 folder returns with no results found
      try {
        f = new File(folder.getPath());
        fileName = FileExplorer.searchByName(f, "COVIDTracker2.java");
        System.out.println("Problem detected: Your FileExplorer.searchByName() must"
            + "\"throw a NoSuchElementException if the search returns with no results found.\"");
        return false;
      } catch (NoSuchElementException nsee) {
        // catch only the expected exception to be thrown -- no problem detected
      }
    } catch (Exception e) {
      System.out.println("Problem detected: Your FileExplorer.searchByName() has thrown"
          + " an unexpected exception.");
      e.printStackTrace();
      return false;
    }

    // all tests passed
    return true;
  }

  /**
   * Tests the ability of FileExplorer.searchByKey to pass a recursive base case where it attempts
   * to search an empty folder for files that match the key
   * 
   * @param folder the File passed into the method to be searched
   * @return true if all the tests pass, false otherwise
   */
  public static boolean testSearchByKeyBaseCase(File folder) {
    try {
      // Base case - searching the empty grades folder
      File f = new File(folder.getPath() + File.separator + "grades");
      ArrayList<String> searchByKey = FileExplorer.searchByKey(f, ".txt");

      if (searchByKey.size() != 0) {
        System.out.println("Problem detected: grades folder should not contain any files.");
        return false;
      }
    } catch (Exception e) {
      System.out.println("Problem detected: Your FileExplorer.searchByKey() has thrown"
          + " an unexpected exception.");
      e.printStackTrace();
      return false;
    }

    // base case passed
    return true;
  }

  /**
   * Calls the methods implemented in this class which test their corresponding methods outlined in
   * FileExplorer.java
   * 
   * @param args input arguments if any
   */
  public static void main(String[] args) {
    File f = new File("cs300");
    System.out.println("testListContents: " + testListContents(f));
    System.out.println("testDeepListBaseCase: " + testDeepListBaseCase(f));
    System.out.println("testDeepListRecursiveCase: " + testDeepListRecursiveCase(f));
    System.out.println("testSearchByFileName: " + testSearchByFileName(f));
    System.out.println("testSearchByKeyBaseCase: " + testSearchByKeyBaseCase(f));
  }
}
