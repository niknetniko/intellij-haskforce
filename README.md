HaskForce - The IntelliJ plugin for Haskell.
=========

***This plugin is in its early stages and is not ready for use.***

Building the plugin
--------

1. Clone HaskForce.

1. If you haven't already, download and install IntelliJ IDEA and Java JDK.

1. Check out the Community Edition source files.

    ````$ git clone git@github.com:JetBrains/intellij-community.git idea````
1. Install and enable additional plugins.
  * Grammar-Kit
  * JFlex Support
  * Plugin DevKit (already installed)
  * PsiViewer
1. Configure JFlex settings.
  * Go to Preferences.  Below the IDE Settings section locate JFlex.  Set the path and skeleton to the
    idea/tools/lexer files.
1. Configure SDK and source files.
  * Create a new **IntelliJ Platform Project** from existing sources (pointed to your cloned HaskForce directory).
  * Go to File > Project Structure.  Add SDKs for JDK and IDEA Plugins.  For the IDEA Plugins, add sources
    from cloned IntelliJ to the Sourcepath.
1. Set the project SDK to the IDEA SDK.
1. Generate additional source files -
  * On UNIX, run `tools/generate.sh`
  * On Windows, open **src/com/haskforce/Haskell.bnf** and follow the instructions in the comments.
1. Right-click on the following directories and **Mark Directory As**:
  * `gen/` as Sources Root
  * `resources/` as Resources Root
  * `tests/` as Test Sources Root
1. Go to File -> Project Structure. Add a module called "jps-plugin"
1. Right-click on the following directories and **Mark Directory As**:
  * `jps-plugin/src` as Sources Root
  * `jps-plugin/resources` as Resources Root
1. From the menu go to **Run > Edit Configurations**
1. Click on the `+` sign and choose **Plugin**, click **OK**, then run your new configuration.

Testing the plugin
--------

To run the tests, you'll need to create a run configuration:

1. Go to **Run > Edit Configurations**
1. Click on the `+` sign and choose **JUnit**
1. In the Class field enter **HaskellTestCase**, which should auto-complete for you.
1. Click **OK** and run your new test configuration.

To add more tests:

* Edit Haskell\*Test.java files to add more tests of the same kind that already exists.
* Edit HaskellTestCase.java if you need to add tests of a different
  kind.
