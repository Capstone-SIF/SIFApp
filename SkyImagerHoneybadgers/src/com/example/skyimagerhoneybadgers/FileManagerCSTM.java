//-----------------------------------------------------------------------------
//   Custom File Management Class (FileManagerCSTM)
//   Created by: Andrew Fruge' -  andrew.fruge@colorado.edu
//   Provides functionality to clear previous days pictures
//   Use:  1) Call constructor
//	 	   2) Call clearPicMem(root)
//   Revisions:
//         0 - 1/21/13 - Initial creation
//-----------------------------------------------------------------------------


package com.example.skyimagerhoneybadgers;
import java.io.File;
import android.content.Context;

public class FileManagerCSTM{
	
	private Context context;
	
	// ---Constructor----------------------------------------------------------
	//Context needed for internal storage
	//Internal Storage is okay b/c Critical Data Spreadsheet -> 274 MB/day
	public FileManagerCSTM(Context extContext){
		context = extContext;
	}
	// ---End Constructor------------------------------------------------------
	
	
	// ---Clear Picture Memory-------------------------------------------------
	// -Precondition: N/A
	// -Postcondition: Deletes all folder contents
	public void clearPicMem(String root){
		if(directoryExists(root)){
			File[] fullContents = popFileArray(root);
			for(File toBeDeleted:fullContents)
				toBeDeleted.delete();
			clearCacheMem();
		}
	}
	// ---End Clear Picture Memory---------------------------------------------
	
	// ---Populate File Array--------------------------------------------------
	// -Precondition: The directory exists
	// -Postcondition: Structure of names of files in directory is available
	private File[] popFileArray(String rootDir){
		File root = new File(rootDir);
		File[] files = root.listFiles();
		return files;
	}
	// ---End Populate File Array----------------------------------------------
	
	// ---Directory Exists Boolean---------------------------------------------
	// -Precondition: N/A
	// -Postcondition: True if the string corresponds to a file directory
	private boolean directoryExists(String root){
		File directory = new File(root);
		return directory.isDirectory(); 		//Test to make sure this works when Folder doesn't exist on OS
	}
	// ---End Directory Exists-------------------------------------------------
	
	// ---Clear Cache Memory---------------------------------------------------
	// -Precondition: N/A
	// -Postcondition: App cache memory is cleared
	private void clearCacheMem(){
		File cache = context.getCacheDir();
		File[] cacheFiles = cache.listFiles();
		for(File toBeDeleted:cacheFiles)
			toBeDeleted.delete();
	}
	// ---End Clear Cache Memory-----------------------------------------------
}
