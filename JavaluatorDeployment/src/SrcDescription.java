import java.io.File;
import java.io.FilenameFilter;

public class SrcDescription {
	private File demoJarFile;
	private File demoIdFile;
	private File releaseFile;
	private File javaDocFile;
	private File relNotesFile;
	private File tutoSourcesFile;

	public SrcDescription(File folder) {
		String[] files = folder.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".zip");
			}
		});
		if (files.length==1) releaseFile = new File (folder, files[0]);
		File file = new File(folder, "demo");
		files = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".jar");
			}
		});
		if ((files!=null) && (files.length==1)) demoJarFile = new File (file, files[0]);
		this.demoIdFile = new File(file, "demoId.txt");
		this.javaDocFile = new File(folder, "doc/javadoc");
		this.relNotesFile = new File(folder, "doc/relnotes.txt");
		this.tutoSourcesFile = new File(folder, "doc/tutorial");
	}
	
	public File getDemoJarFile() {
		return this.demoJarFile;
	}

	public File getDemoIdFile() {
		return demoIdFile;
	}

	public File getReleaseFile() {
		return releaseFile;
	}

	public File getRelNotesFile() {
		return relNotesFile;
	}

	public File getJavaDocFile() {
		return javaDocFile;
	}

	/**
	 * @return the tutoSourcesFile
	 */
	public File getTutoSourcesFile() {
		return tutoSourcesFile;
	}
}
