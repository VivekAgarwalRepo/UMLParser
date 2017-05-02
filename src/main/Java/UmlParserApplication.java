import com.github.javaparser.ast.CompilationUnit;

public class UmlParserApplication {
    public static void main(String[] args) throws Exception {
        if (args[0].equals("class")) {
            Parser parser = new Parser(args[1], args[2]);
            parser.start();
        } else if (args[0].equals(("seq"))) {
//            SequenceEngine sequenceEngine = new SequenceEngine(args[1], args[2], args[3], args[4]);
//            sequenceEngine.start();
        } else {
            System.out.println("Invalid keyword " + args[0]);
        }

    }
}
