import java.io.File;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSelectInfo;
import org.apache.commons.vfs2.FileSelector;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.auth.StaticUserAuthenticator;
import org.apache.commons.vfs2.impl.DefaultFileSystemConfigBuilder;
import org.apache.commons.vfs2.impl.DefaultFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;

/** Deploys the javaluator updates on its web site.
 * <br>This class requires that the Javaluator build has been already done with success.
 * <br>It verifies that all material is ready to be deployed. 
 * @author Jean-Marc-Marc Astesana
 */
public class DeployJavaluator {
	private static final String RELEASE_ROOT = "sftp://web.sourceforge.net/home/pfs/project/javaluator";
	private static final String WEB_ROOT = "sftp://web.sourceforge.net/home/project-web/javaluator/htdocs";
	private DefaultFileSystemManager fsManager;
	private FileSystemOptions opts;
	private SrcDescription src;
	private FileSelector dummySelector;
	
	private DeployJavaluator(String user, String password, String srcPath) throws FileSystemException {
		this.src = new SrcDescription(new File(srcPath));
		fsManager = (DefaultFileSystemManager) VFS.getManager();
		opts = new FileSystemOptions();
		SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
		SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(opts, false); // Use absolute paths
		StaticUserAuthenticator auth = new StaticUserAuthenticator(null, user, password);
		DefaultFileSystemConfigBuilder.getInstance().setUserAuthenticator(opts, auth);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println ("Closing source forge connection");
		fsManager.close();
	}

	/**
	 * @param args user, password, srcFolder
	 */
	public static void main(String[] args) {
		if (args.length!=3) {
			System.err.println("Invalid number of arguments");
			System.out.println("usage: java "+DeployJavaluator.class.getName()+" user password srcFolder");
			System.exit(-1);
		}
		try {
			DeployJavaluator deploy = new DeployJavaluator(args[0], args[1], args[2]);
			deploy.test();
			deploy.doIt();
		} catch (FileSystemException e) {
			System.err.println("An exception occurred");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	protected void test() {
		//TODO
	}

	protected void doIt() throws FileSystemException {
		doApplet();
		doJavadoc();
		doRelease();
		System.out.println ("Finished :-)");
	}

	private void doRelease() throws FileSystemException {
		System.out.println ("Posting release");
		fsManager.resolveFile(RELEASE_ROOT+"/"+this.src.getReleaseFile().getName(), opts).copyFrom(fsManager.toFileObject(this.src.getReleaseFile()), getDummySelector());
	}

	private void doJavadoc() throws FileSystemException {
		System.out.println ("Copying documentation");
		// Relnotes
		File file = src.getRelNotesFile();
		fsManager.resolveFile(WEB_ROOT+"/en/doc/"+file.getName(), opts).copyFrom(fsManager.toFileObject(file), getDummySelector());

		// Copy javadoc to a temporary folder
		FileObject fDocNew = fsManager.resolveFile(WEB_ROOT+"/en/doc/javadoc-new", opts);
		fDocNew.copyFrom(fsManager.toFileObject(src.getJavaDocFile()), getDummySelector());
		// Rename old javadoc folder to a new folder
		FileObject fDoc = fsManager.resolveFile(WEB_ROOT+"/en/doc/javadoc", opts);
		FileObject fDocOld = fsManager.resolveFile(WEB_ROOT+"/en/doc/javadoc-old", opts);
		fDoc.moveTo(fDocOld);
		// Rename new javadoc
		fDocNew.moveTo(fDoc);
		// Delete old javadoc
		fDocOld.delete(getDummySelector());
	}

	private void doApplet() throws FileSystemException {
		System.out.println ("Copying demo");
		// Get previous jar name
		final FileObject fDemo = fsManager.resolveFile(WEB_ROOT+"/site/demo", opts);
		FileObject[] foundFiles = fDemo.findFiles(new FileSelector() {
			@Override
			public boolean traverseDescendents(FileSelectInfo info) throws Exception {
				return info.getFile().equals(fDemo);
			}
			
			@Override
			public boolean includeFile(FileSelectInfo info) throws Exception {
				return info.getFile().getName().getBaseName().endsWith(".jar");
			}
		});
		if (foundFiles.length>1) throw new IllegalArgumentException("More than one jar is deployed in "+WEB_ROOT+"/site/demo");
		
		// Copy the jar file
		File jarFile = this.src.getDemoJarFile();
		fsManager.resolveFile(WEB_ROOT+"/site/demo/"+jarFile.getName(), opts).copyFrom(fsManager.toFileObject(jarFile), getDummySelector());
		// Copy the demoId file
		fsManager.resolveFile(WEB_ROOT+"/site/demo/demoId.txt", opts).copyFrom(fsManager.toFileObject(this.src.getDemoIdFile()), getDummySelector());
		// Erase the old jar file
		if ((foundFiles.length==1) && (!foundFiles[0].getName().getBaseName().equals(jarFile.getName()))) {
			if (!foundFiles[0].delete()) {
				System.err.println("ALERT : unable to delete file "+foundFiles[0]);
			}
		}
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
}
