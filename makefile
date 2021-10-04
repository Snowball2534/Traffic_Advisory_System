run: FrontEnd.class BackEnd.class CS400Graph.class GraphADT.class
	java FrontEnd
FrontEnd.class: FrontEnd.java
	javac FrontEnd.java
BackEnd.class: BackEnd.java
	javac BackEnd.java
CS400Graph.class: CS400Graph.java
	javac CS400Graph.java
GraphADT.class: GraphADT.java
	javac GraphADT.java

test: TestFinalProject.class BackEnd.class CS400Graph.class GraphADT.class junit5.jar
	java -jar junit5.jar -cp . --scan-classpath
TestFinalProject.class: TestFinalProject.java
	javac -cp .:junit5.jar *.java

clean:
	rm *class
