package com.fathzer.soft.deployer;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSelectInfo;
import org.apache.commons.vfs2.FileSelector;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.auth.StaticUserAuthenticator;
import org.apache.commons.vfs2.impl.DefaultFileSystemConfigBuilder;
import org.apache.commons.vfs2.impl.DefaultFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;

public abstract class Scenario {
	private String name;
	private String releaseRoot;
	private String webRoot;
	private DefaultFileSystemManager fsManager;
	private FileSystemOptions opts;
	private FileSelector dummySelector;

	protected Scenario(String name, String releaseRoot, String webRoot) {
		super();
		try {
			fsManager = (DefaultFileSystemManager) VFS.getManager();
			opts = new FileSystemOptions();
			SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
			SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, false); // Use absolute paths
			this.name = name;
			this.releaseRoot = releaseRoot;
			this.webRoot = webRoot;
		} catch (FileSystemException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void setAuthentication(String user, char[] password) {
		try {
			StaticUserAuthenticator auth = new StaticUserAuthenticator(null, user, new String(password));
			DefaultFileSystemConfigBuilder.getInstance().setUserAuthenticator(opts, auth);
		} catch (FileSystemException e) {
			throw new RuntimeException(e);
		}
	}

	public String getName() {
		return name;
	}
	
	public String getReleaseRoot() {
		return releaseRoot;
	}

	public String getWebRoot() {
		return webRoot;
	}

	public abstract List<Task> getTasks();
	
	public String verify(Parameters params) {
		for (Task task : getTasks()) {
			String message = task.verify(params);
			if (message!=null) return MessageFormat.format("{0} said \"{1}\"", task.getName(), message);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		close();
		super.finalize();
	}
	
	public void copyToWeb(File file, String location) throws FileSystemException {
		copyToWeb(file, location, file.getName());
	}
	
	public void copyToWeb(File file, String remoteLocation, String remoteName) throws FileSystemException {
		FileObject dest = fsManager.resolveFile(webRoot+"/"+remoteLocation+"/"+remoteName, opts);
		boolean safeCopy = dest.getType().equals(FileType.FILE_OR_FOLDER) || dest.getType().equals(FileType.FOLDER);
		if (safeCopy) {
			// If the file already exists and is a folder, then, do the copy in a temporary folder, and switch folders in order
			// to prevent leaving the web site in an undetermined state for too long time
			FileObject tmpTarget = getAlternate(dest);
			tmpTarget.copyFrom(fsManager.toFileObject(file), getDummySelector());
			FileObject tmpTarget2 = getAlternate(dest);
			dest.moveTo(tmpTarget2);
			tmpTarget.moveTo(dest);
			tmpTarget2.delete(getDummySelector());
		} else {
			dest.copyFrom(fsManager.toFileObject(file), getDummySelector());
		}
	}
	
	private FileObject getAlternate(FileObject dest) throws FileSystemException {
		FileObject result;
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			result = fsManager.resolveFile(dest.getParent(), dest.getName()+"."+i);
			if (result.getType().equals(FileType.IMAGINARY)) return result;
		}
		return null;
	}
	
	public void copyToRelease(File file, String location) throws FileSystemException {
		copyToRelease(file, location, file.getName());
	}
	
	private void copyToRelease(File file, String remoteLocation, String remoteName) throws FileSystemException {
		fsManager.resolveFile(releaseRoot+"/"+remoteLocation+"/"+remoteName, opts).copyFrom(fsManager.toFileObject(file), getDummySelector());
	}

	private FileSelector getDummySelector() {
		if (dummySelector==null) {
			dummySelector = new FileSelector() {
				@Override
				public boolean traverseDescendents(FileSelectInfo arg0) throws Exception {
					return true;
				}
				
				@Override
				public boolean includeFile(FileSelectInfo arg0) throws Exception {
					return true;
				}
			};
		}
		return dummySelector;
	}

	public DefaultFileSystemManager getFsManager() {
		return fsManager;
	}

	public FileSystemOptions getOpts() {
		return opts;
	}
	
	public void close() {
		System.out.println ("Closing remote connection");
		fsManager.close();
	}
}
