<h1>What is UML Parser</h1>
    <p> UML Parser is a parsing tool used for generating UML diagrams as the output after taking source code as the input</p>
    <p> UML Parser helps visualize the structure of the code and the inter-relation between the classes and hierarchical structure</p>
    <p> In this project, we develop a UML parser using java parser and YUML diagram generator to create class diagrams for a given set of java classes</p>

<h1>Requirements</h1>
    The Java version requirement is Java 8.

<h1>Tools Used</h1>
    This project has two fundamental models -
    1. Parser
    2. UML Diagram Generator

    The parser is used to parse the java source code classes and create a grammar language which will be provided as input to the UML diagram generator.
    The UML diagram generator uses the parsed grammar as the input and generates a class diagram as the resultant image output.

    Parser tool : The parser tool I have used for this project is Java Parser (https://github.com/javaparser/javaparser) library.
                  This parser is highly efficient in parsing the code and is completely open source. It makes use of a compilation unit
                  to ensemble the structural units of the Java program. It also provides access to sub-unit of the code by using various
                  methods and classes. We make use of Java Parser to develop the Parser in the application which helps in understanding the relations
                  between the classes.

    UML diagram : The UML Generator tool used is yUML Diagram generator (https://yuml.me/). yUML is a free online tool which provides us with the API to
                  generate the class diagram. After parsing the java classes, it returns a grammar which is a set of instructions outlining the relations
                  between the classes and interfaces along with other details such a methods and attributes. We make a GET request to the URL : https://yuml.me/diagram/plain/class/
                  and this generates the class diagram. Thus, you need a working internet connection to generate the diagram.

<h1>Running the project</h1>
    In order to run the project, extract the executable JAR file to a folder.
    Command: java -jar umlparser.jar class "<insert path to test classes>" class_diagram

    This command will generate a class diagram with the name class_diagram.png

    Open the image file to view the relation between the different classes.

    Example for running the project
    java -jar umlparser.jar class "C:\Users\Vivek Agarwal\UMLParser\src\test\test4\test4.zip" class_diagram
    