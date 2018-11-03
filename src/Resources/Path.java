package Resources;

/*This class is used to verify the directory structure of the path provided
 * up to the second last folder*/

public class Path {
	private String directory;
	private Folder currentFolder;
	private Folder navigatingFolder;
	private String lastFolderName;
	
	public Path(Folder currentFolder, String directory) {
		this.currentFolder = currentFolder;
		this.directory = directory;
	}
	
	public boolean isValidPath() {	// checks only up to n - 1
		boolean pathExists = true;
		String[] path = directory.split("/");	// Splitting each folder present in path
		
		if (path[0].equals("")) {	// If directory starts with /
			navigatingFolder = currentFolder.getRoot();	// Get root of currentFolder if path directory like /dir1/dir2...
			
//			boolean pathExists = true;
			for (int j = 1; j < path.length - 1; j++) {	// Folders from 0 to n - 1 should be present to create nth folder
				if (!navigatingFolder.containsFolder(path[j])) {
					pathExists = false;
					break;
				}
				else {
					navigatingFolder = navigatingFolder.getChildFolder(path[j]);  // Get child folder
				}
			}
			
//			return pathExists;
		}
		else {	// If directory doesn't start with /
			navigatingFolder = currentFolder;
//			boolean canCreateFolder = true;
			
			for (int j = 0; j < path.length - 1; j++) {  // Folders from 0 to n - 1 should be present to create nth folder
				if (!navigatingFolder.containsFolder(path[j])) {
					pathExists = false;
					break;
				}
				else {
					navigatingFolder = navigatingFolder.getChildFolder(path[j]);
				}
			}
			
		}
		
		lastFolderName = path[path.length - 1];
		
		return pathExists;
	}
	
	public Folder getNavigatingFolder() {		// Maximum folder that it traversed to in the path
		return navigatingFolder;
	}
	
	public String getLastFolderName() {		// Name of last folder in path
		return lastFolderName;
	}
}
