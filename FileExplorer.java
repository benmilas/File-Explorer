import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * A class that implements various methods, mostly recursive, to list folder contents, get specific
 * file paths, and search for files by a key or by file size
 * 
 * @author Ben Milas
 *
 */
public class FileExplorer {

  /**
   * An iterative method that adds all the files and directories in a given folder.
   * 
   * @param currentFolder the folder that is being searched
   * @return an ArrayList of the names of all files and directories in the given folder.
   * @throws NotDirectoryException with a description error message if the provided currentFolder
   *                               does not exist or if it is not a directory
   */
  public static ArrayList<String> listContents(File currentFolder) throws NotDirectoryException {
    if (currentFolder == null || !currentFolder.exists() || !currentFolder.isDirectory())
      throw new NotDirectoryException("The given folder does not exist or is not a directory.");

    ArrayList<String> folderContents = new ArrayList<>();
    String[] fileArray = currentFolder.list();

    for (int i = 0; i < fileArray.length; i++) {
      // adds files and directories to the ArrayList
      folderContents.add(fileArray[i].toString());
    }

    // automatically handles case where currentFolder is empty by returning an empty ArrayList
    return folderContents;
  }

  /**
   * Recursive method that lists all the files within a given folder and its sub-folders
   * 
   * @param currentFolder the folder that is being searched
   * @return an ArrayList of the names of all the files (not directories) in the given folder and
   *         its sub-folders.
   * @throws NotDirectoryException with a description error message if the provided currentFolder
   *                               does not exist or if it is not a directory
   */
  public static ArrayList<String> deepListContents(File currentFolder)
      throws NotDirectoryException {
    //

    if (currentFolder == null || !currentFolder.exists() || !currentFolder.isDirectory())
      throw new NotDirectoryException("The given folder does not exist or is not a directory.");

    ArrayList<String> folderContents = new ArrayList<>();
    File[] fileArray = currentFolder.listFiles();

    for (int i = 0; i < fileArray.length; i++) {
      if (fileArray[i].isDirectory()) {
        // recursively searches sub-directories looking for files
        folderContents.addAll(deepListContents(fileArray[i]));
      } else
        // base case that adds files to the ArrayList
        folderContents.add(fileArray[i].getName());
    }
    // automatically handles case where currentFolder is empty by returning an empty ArrayList
    return folderContents;
  }

  /**
   * Recursively searches the given folder and all of its sub-folders for an exact match to the
   * provided fileName.
   * 
   * @param currentFolder the folder that is being searched
   * @param fileName      the file that is being searched for
   * @return a String that is the path to the file, if it exists.
   * @throws NoSuchElementException with a descriptive error message if the search operation returns
   *                                with no results found (including the case if fileName or
   *                                currentFolder are null or currentFolder does not exist, or was
   *                                not a directory)
   */
  public static String searchByName(File currentFolder, String fileName) {
    if (fileName == null || currentFolder == null || !currentFolder.exists()
        || !currentFolder.isDirectory())
      throw new NoSuchElementException("File Not Found");

    // recursive helper method
    String fullFilePath = searchByNameHelper(currentFolder, fileName);

    // file path not found
    if (fullFilePath == null)
      throw new NoSuchElementException("File Not Found");

    // modifies the String to show path from the original currentFolder, not highest directory
    String folderFilePath =
        currentFolder.getName() + fullFilePath.substring(currentFolder.getPath().length());

    return folderFilePath;
  }

  /**
   * A helper method for searchByName() that performs the recursive aspect of the search for the
   * given folder and all of its sub-folders for an exact match to the provided fileName.
   * 
   * @param currentFolder the folder that is being searched
   * @param fileName      the file that is being searched for
   * @return a String that is the path to the file, if it exists. null otherwise
   */
  private static String searchByNameHelper(File currentFolder, String fileName) {
    if (currentFolder.isDirectory()) {
      String filePath = null;
      File[] fileArray = currentFolder.listFiles();
      for (int i = 0; i < fileArray.length; i++) {
        // recursive call to helper method
        filePath = searchByNameHelper(fileArray[i], fileName);
        if (filePath != null) {
          // returns non-null recursive searches for fileName path
          return filePath;
        }
      }
    } else {
      if (currentFolder.getName().equals(fileName)) {
        // base case in which current folder is the file path
        return currentFolder.getPath();
      }
    }
    // if file path does not exist
    return null;
  }

  /**
   * Recursive method that searches the given folder and its sub-folders for all files that contain
   * the given key in part of their name.
   * 
   * @param currentFolder the folder that is being searched
   * @param key           the character sequence is being checked for matches
   * @return an ArrayList of all the names of files that match and an empty ArrayList when the
   *         operation returns with no results found (including the case where currentFolder is not
   *         a directory).
   */
  public static ArrayList<String> searchByKey(File currentFolder, String key) {
    ArrayList<String> folderContents = new ArrayList<>();

    if (key != null && currentFolder != null && currentFolder.exists()
        && currentFolder.isDirectory()) {
      File[] fileArray = currentFolder.listFiles();

      for (int i = 0; i < fileArray.length; i++) {
        if (fileArray[i].isDirectory()) {
          // recursively searches sub-directories looking for files that match the key
          folderContents.addAll(searchByKey(fileArray[i], key));
        } else if (fileArray[i].getName().contains(key))
          // base case that adds files that contain the key
          folderContents.add(fileArray[i].getName());
      }
    }
    // automatically handles cases where currentFolder or key is null, currentFolder not a
    // directory, does not exist, or search operation returns with no results found by returning an
    // empty ArrayList
    return folderContents;
  }

  /**
   * Recursive method that searches the given folder and its sub-folders for all files whose size is
   * within the given max and min values, inclusive.
   * 
   * @param currentFolder the folder that is being searched
   * @param sizeMin       the minimum size the file can be in bytes, inclusive
   * @param sizeMax       the maximum size the file can be in bytes, inclusive
   * @return an array list of the names of all files whose size are within the boundaries and an
   *         empty ArrayList if the search operation returns with no results found (including the
   *         case where currentFolder is not a directory).
   */
  public static ArrayList<String> searchBySize(File currentFolder, long sizeMin, long sizeMax) {
    ArrayList<String> folderContents = new ArrayList<>();

    if (currentFolder != null && currentFolder.exists()) {
      File[] fileArray = currentFolder.listFiles();

      if (currentFolder.isDirectory()) {
        for (int i = 0; i < fileArray.length; i++) {
          if (fileArray[i].isDirectory()) {
            // recursively searches sub-directories looking for files within the min and max values
            folderContents.addAll(searchBySize(fileArray[i], sizeMin, sizeMax));
          } else if (sizeMin <= fileArray[i].length() && sizeMax >= fileArray[i].length())
            // base case that adds files that are within the min and max values
            folderContents.add(fileArray[i].getName());
        }
      }
    }
    // automatically handles cases where currentFolder is not a directory or search
    // operation returns with no results found by returning an empty ArrayList
    return folderContents;
  }

}
