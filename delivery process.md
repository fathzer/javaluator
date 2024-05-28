# Delivery process.

## Deploy the artifact on Maven central

1. Push the code in `master` branch.  
It will trigger some workflows. Please note that `tutorial` workflow may fail! It only means [Javaluator tutorial on Javaluator's site](https://javaluator.fathzer.com) require some updates.  
If Sonar quality gate fail ... try to fix its complaints ;-)

2. Run `mvn clean deploy` on the project.  
As the compiler target is Java 1.6, it will fail with recent release of Java. Java 8 is able to make the compile.
The easiest way to use java 8 is to [configure a *toolchain*](#HowToConfigureToolchain).  
Of course, signing material (certificate and its password) are not included in this project: There should be `fathzer_private_key.asc` and `fathzer_key_pwd.txt` files in the user's home directory.

3. Create a release in Github.

## Javaluator's site update

1. Update the [`father/hosting`](https://github.com/fathzer/hosting) private project.
	- If the `tutorial` workflow fails, have a look at its logs, it should contains the list of files that should be updated in the project. Once the project is updated, retry the failed action, it should succeed.

	- Add the version release notes to the `javaluator/www/en/doc/relnotes.txt` file. 

2. Don't forget to push the updates in the production site!

## How to configure toolchain
Add a *toolchain.xml* file](#HowToConfigureToolchain) in your `.m2` directory.

This file should contain something like:  
```
<?xml version="1.0" encoding="UTF-8"?>
<toolchains>
  <toolchain>
    <type>jdk</type>
    <provides>
      <version>8</version>
      <vendor>sun</vendor>
    </provides>
    <configuration>
      <jdkHome>C:\Program Files\Java\jdk1.8.0_341</jdkHome>
    </configuration>
  </toolchain>
<toolchains>
```
Once it is done, java 8 is activated with the `jdk` system property: `mvn -Djdk=8 clean deploy`

