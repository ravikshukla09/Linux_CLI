package Commands;

import Resources.Folder;
import Resources.Path;

/* This class is used to make new directories based on the path specified */

public class MakeDirectory {
	private Folder currentFolder;
	
	public MakeDirectory(Folder currentFolder) {
		this.currentFolder = currentFolder;
	}
	
	public String makeDirectories(String[] directory) {
		// directory[0] is command, so ignore
		
		if (directory.length == 1)
			return "mkdir: missing operand(s)";
		
		String message = "mkdir: ";
		
		for (int i = 1; i < directory.length; i++) {
			Path path = new Path(currentFolder, directory[i]);
			
//			String[] path = directory[i].split("/");	// Splitting each folder present in path
//			
//			if (path[0].equals("")) {	// If directory starts with /
//				Folder navigatingFolder = currentFolder.getRoot();	// Get root of currentFolder if path directory like /dir1/dir2...
//				
//				boolean canCreateFolder = true;
//				for (int j = 1; j < path.length - 1; j++) {	// Folders from 0 to n - 1 should be present to create nth folder
//					if (!navigatingFolder.containsFolder(path[j])) {
//						canCreateFolder = false;
//						break;
//					}
//					else {
//						navigatingFolder = navigatingFolder.getChildFolder(path[j]);  // Get child folder
//					}
//				}
//				
//				if (canCreateFolder) { // Checking if last folder already exists
//					if (navigatingFolder.containsFolder(path[path.length - 1])) {
//						message = message + fileExistsError(directory[i]);
//					}
//					else {
//						navigatingFolder.createFolder(path[path.length - 1]);   // Create the folder
//					}
//				}
//				else {
//					message = message + fileDoesNotExistError(directory[i]);
//				}
//			}
//			else {	// If directory doesn't start with /
//				Folder navigatingFolder = currentFolder;
//				boolean canCreateFolder = true;
//				
//				for (int j = 0; j < path.length - 1; j++) {  // Folders from 0 to n - 1 should be present to create nth folder
//					if (!navigatingFolder.containsFolder(path[j])) {
//						canCreateFolder = false;
//						break;
//					}
//					else {
//						navigatingFolder = navigatingFolder.getChildFolder(path[j]);
//					}
//				}
//				
//				if (canCreateFolder) {  // Check if last folder already exists
//					if (navigatingFolder.containsFolder(path[path.length - 1])) {  // If folder exists
//						message = message + fileExistsError(directory[i]);
//					}
//					else {
//						navigatingFolder.createFolder(path[path.length - 1]);   // Create folder
//					}
//				}
//				else {
//					message = message + fileDoesNotExistError(directory[i]);
//				}
//			}
			
			boolean canCreateFolder = path.isValidPath();
			
			if (canCreateFolder) {  // Check if last folder already exists
				Folder navigatingFolder = path.getNavigatingFolder();
				String lastFolderName = path.getLastFolderName();
				if (navigatingFolder.containsFolder(lastFolderName)) {  // If folder exists
					message = message + fileExistsError(directory[i]) + "\n";
				}
				else {
					navigatingFolder.createFolder(lastFolderName);   // Create folder
				}
			}
			else {
				message = message + fileDoesNotExistError(directory[i]) + "\n";
			}
		}
		
		return message;
	}
	
	private String fileExistsError(String name) {
		return "cannot create directory '" + name + "': File exists";
	}
	
	private String fileDoesNotExistError(String name) {
		return "cannot create directory '" + name + "': No such file or directory";
	}
}
