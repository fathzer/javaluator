package com.fathzer.soft.deployer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.vfs2.FileSelectInfo;
import org.apache.commons.vfs2.FileSelector;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
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
//		System.out.println ("copy "+file.getCanonicalPath()+" to "+webRoot+"/"+remoteLocation+"/"+remoteName);
		fsManager.resolveFile(webRoot+"/"+remoteLocation+"/"+remoteName, opts).copyFrom(fsManager.toFileObject(file), getDummySelector());
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
