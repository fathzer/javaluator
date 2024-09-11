# Delivery process.

## Deploy the artifact on Maven central

1. Push the code in `master` branch.  
It will trigger some workflows. Please note that `tutorial` workflow may fail! If it fails, it means that [Javaluator tutorial on Javaluator's site](https://javaluator.fathzer.com) require some updates.  
If Sonar quality gate fails ... try to fix its complaints ;-)

2. Run `mvn clean deploy` on the project.
For unmentionable reasons, 'deploy' fails with 9+ releases of Java. But java 8 isn't able to compile the *module-info.java* files.  
The easiest way to fix this is to use java 8 and [configure a *toolchain*](#how-to-configure-toolchain).  
Then run `mvn -Djdk=21 clean deploy`.  
Of course, signing material (certificate and its password) are not included in this project: There should be `fathzer_private_key.asc` and `fathzer_key_pwd.txt` files in the user's home directory.

3. Create a release in Github.

## Javaluator's site update

1. If a new demo has to be compiled, run `mvn -P demo clean package` on the project.

2. Update the [`father/hosting`](https://github.com/fathzer/hosting) private project.
	- If the `tutorial` workflow fails, have a look at its logs, it should contains the list of files that should be updated in the project. Once the project is updated, retry the failed action, it should succeed.

	- Add the version release notes to the `javaluator/www/en/doc/relnotes.txt` file. 

3. Don't forget to push the updates in the production site!

## Update javadoc on javadoc.io

Once the Maven artifacts are available on [Maven central](https://search.maven.org/search?q=a:javaluator), open the link `https://javadoc.io/doc/com.fathzer/javaluator/*VERSION*/` were *VERSION* is the new release number.  
javadoc.io will process the request and made the new release available after a couple of minutes.

## How to configure toolchain
Add a *toolchain.xml* file in your `.m2` directory.

This file should contain something like:  
```
<?xml version="1.0" encoding="UTF-8"?>
<toolchains>
  <toolchain>
    <type>jdk</type>
    <provides>
      <version>21</version>
      <vendor>sun</vendor>
    </provides>
    <configuration>
      <jdkHome>C:\Program Files\Java\jdk1.8.0_341</jdkHome>
    </configuration>
  </toolchain>
<toolchains>
```

