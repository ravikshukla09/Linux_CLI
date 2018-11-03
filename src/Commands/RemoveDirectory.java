package Commands;

import java.util.Iterator;

import Resources.Folder;
import Resources.Path;

public class RemoveDirectory {
	private Folder currentFolder;
	private Folder lastFolder;
	
	public RemoveDirectory(Folder currentFolder) {
		this.currentFolder = currentFolder;
		this.lastFolder = currentFolder;
	}

	public String removeDirectories(String[] directory) {
		// directory[0] is command, so ignore

		if (directory.length == 1)
			return "rm: missing operand(s)";
		
		else if (directory[1].equals("/")) {	// cannot delete root folder
			Folder root = currentFolder.getRoot();
			
			Iterator<Folder> iterator = root.getAllChildFolders();
			
			while(iterator.hasNext()) {
				String currentDirectory = "/" + iterator.next().getName();
				String[] tempDirectories = {"rm", currentDirectory};
				removeDirectories(tempDirectories);
			}
			
			lastFolder = root;
			return "rm: ";
		}
		
		String message = "rm: ";
		
		for (int i = 1; i < directory.length; i++) {
			Path path = new Path(currentFolder, directory[i]);
			
			boolean pathExists = path.isValidPath();
			
			if (pathExists) { // Check if last folder exists
				Folder navigationFolder = path.getNavigatingFolder();
				String lastFolderName = path.getLastFolderName();
				Folder lastFolder = navigationFolder.getChildFolder(lastFolderName);
				
				boolean isChild = currentFolder.isChildOf(lastFolder);
				
				if (navigationFolder.containsFolder(lastFolderName)) {
					if (isChild) {
						this.lastFolder = navigationFolder;
					}
					navigationFolder.removeFolder(lastFolderName);
				}
				else {
					message = message + fileDoesNotExistError(directory[i]) + "\n";
				}
			}
			else {
				message = message + fileDoesNotExistError(directory[i]) + "\n";
			}
		}
		
		return message;
	}
	
	public Folder getLastFolder() {
		return lastFolder;
	}
	
	private String fileDoesNotExistError(String name) {
		return "cannot remove '" + name + "': No such file or directory";
	}
}
