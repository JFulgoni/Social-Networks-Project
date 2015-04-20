The jgrapht library has to be in the same directory as the java files.

The zip file already contains the compiled file (Project.class), but in case you want to compile it again, here is the command

-Compile
   javac -cp jgrapht-core-0.9.0.jar Project.java

-Run (Mac, Linux)
   java -cp .:jgrapht-core-0.9.0.jar Project

-Run (Windows)
   java -cp .;jgrapht-core-0.9.0.jar Project
