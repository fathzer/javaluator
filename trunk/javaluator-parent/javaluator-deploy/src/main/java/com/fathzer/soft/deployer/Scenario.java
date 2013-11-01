package com.fathzer.soft.deployer;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSelectInfo;
import org.apache.commons.vfs2.FileSelector;
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
	
	private transient boolean somethingReleased;

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
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void open(String user, char[] password) {
		somethingReleased = false;
		try {
			StaticUserAuthenticator auth = new StaticUserAuthenticator(null, user, new String(password));
			DefaultFileSystemConfigBuilder.getInstance().setUserAuthenticator(opts, auth);
		} catch (IOException e) {
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
	
	public void copyToWeb(File file, String remoteLocation) throws IOException {
		copyToWeb(file, remoteLocation, file.getName());
	}
	
	public void copyToWeb(File file, String remoteLocation, String remoteName) throws IOException {
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
	
	private FileObject getAlternate(FileObject dest) throws IOException {
		FileObject result;
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			result = fsManager.resolveFile(dest.getParent(), dest.getName()+"."+i);
			if (result.getType().equals(FileType.IMAGINARY)) return result;
		}
		return null;
	}
	
	/** Deletes the content of a web site directory.
	 * <br>The content of sub-directories is leaved untouched.
	 * @param remoteLocation The directory path, relative to the web root path.
	 * @param fileSuffix The suffix of the files to be deleted (null to delete all files).
	 * @param excludes A set of file names to ignore (these files will not be deleted).
	 * @throws IOException 
	 */
	public void deleteWebDirContent(String remoteLocation, final String fileSuffix, Set<String> excludes) throws IOException {
		final FileObject tuto = fsManager.resolveFile(webRoot+"/"+remoteLocation, opts);
		FileObject[] sources = tuto.findFiles(new FileSelector() {
			@Override
			public boolean traverseDescendents(FileSelectInfo info) throws Exception {
				return info.getFile().equals(tuto);
			}
			@Override
			public boolean includeFile(FileSelectInfo info) throws Exception {
				return info.getFile().getName().getBaseName().endsWith(fileSuffix);
			}
		});
		for (FileObject source : sources) {
			if (!excludes.contains(source.getName().getBaseName())) {
				source.delete();
			}
		}
	}
	
	public void copyToRelease(File file, String location) throws IOException {
		copyToRelease(file, location, file.getName());
	}
	
	private void copyToRelease(File file, String remoteLocation, String remoteName) throws IOException {
		fsManager.resolveFile(releaseRoot+"/"+remoteLocation+"/"+remoteName, opts).copyFrom(fsManager.toFileObject(file), getDummySelector());
		this.somethingReleased = true;
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

	protected DefaultFileSystemManager getFsManager() {
		return fsManager;
	}

	protected FileSystemOptions getOpts() {
		return opts;
	}
	
	public void close() {
		fsManager.close();
	}

	public boolean isFileReleased() {
		return somethingReleased;
	}
}
