CLASSPATH = .:jackson-annotations-2.15.2.jar:jackson-core-2.15.2.jar:jackson-databind-2.15.2.jar
JFLAGS = -g -classpath $(CLASSPATH)
JC = javac
.SUFFIXES: .java .class
.java.class:
        $(JC) $(JFLAGS) $*.java
CLASSES = client.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
        $(RM) *.class
