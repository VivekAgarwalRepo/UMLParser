import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 * Created by Vivek Agarwal on 4/30/2017.
 */
public class Parser {
    String yumlCode;
    ArrayList<CompilationUnit> cuCollection;
    final String inPath;
    final String outPath;

    HashMap<String, Boolean> interfaceMapping;
    HashMap<String, String> classConnectionsMap;


    Parser(String inPath, String outFile) {
        this.inPath = inPath;
        this.outPath = inPath + "\\" + outFile + ".png";
        interfaceMapping = new HashMap<String, Boolean>();
        classConnectionsMap = new HashMap<String, String>();
        yumlCode = "";
    }

    /*
    * Creates Compilation Unit Array by using Path of the test cases folder
    * BuildMap is used to build HashMap of whether it is an interface or class'
    * parser() is used to parse the compilation unit and generate grammar to be fed to the Diagram generator
    * yumlCodeUniquer is used to parse yumlCode
    * */

    public void start() throws Exception {
        cuCollection = getCuArray(inPath);
        buildMap(cuCollection);
        for (CompilationUnit cu : cuCollection)
            yumlCode += parse(cu);
        yumlCode += additionParser();
//        System.out.println("yUml Code :"+yumlCode);
        yumlCode = yumCodeGenerator(yumlCode);
//        System.out.println("Unique Code: " + yumlCode);
        DiagramGenerator.generateImage(yumlCode, outPath);
    }




    private String parse(CompilationUnit cu) {
        String result = "";
        String className = "";
        String classShortName = "";
        String methods = "";
        String fields = "";
        String additions = ",";

        ArrayList<String> publicFields = new ArrayList<String>();
        List<TypeDeclaration> ltd = cu.getTypes();
        Node node = ltd.get(0);

        ClassOrInterfaceDeclaration coi = (ClassOrInterfaceDeclaration) node;
        if (coi.isInterface()) {
            className = "[" + "<<interface>>;";
        } else {
            className = "[";
        }
        className += coi.getName();
        classShortName = coi.getName();

        boolean nextParam = false;
        for (BodyDeclaration bd : ((TypeDeclaration) node).getMembers()) {
            if (bd instanceof ConstructorDeclaration) {
                ConstructorDeclaration cd = ((ConstructorDeclaration) bd);
                if (cd.getDeclarationAsString().startsWith("public")
                        && !coi.isInterface()) {
                    if (nextParam)
                        methods += ";";
                    methods += "+ " + cd.getName() + "(";
                    for (Object gcn : cd.getChildrenNodes()) {
                        if (gcn instanceof Parameter) {
                            Parameter paramCast = (Parameter) gcn;
                            String paramClass = paramCast.getType().toString();
                            String paramName = paramCast.getChildrenNodes()
                                    .get(0).toString();
                            methods += paramName + " : " + paramClass;
                            if (interfaceMapping.containsKey(paramClass)
                                    && !interfaceMapping.get(classShortName)) {
                                additions += "[" + classShortName
                                        + "] uses -.->";
                                if (interfaceMapping.get(paramClass))
                                    additions += "[<<interface>>;" + paramClass
                                            + "]";
                                else
                                    additions += "[" + paramClass + "]";
                            }
                            additions += ",";
                        }
                    }
                    methods += ")";
                    nextParam = true;
                }
            }
        }

        for (BodyDeclaration bd : ((TypeDeclaration) node).getMembers()) {
            if (bd instanceof MethodDeclaration) {
                MethodDeclaration md = ((MethodDeclaration) bd);
                // Get only public methods
                if (md.getDeclarationAsString().startsWith("public")
                        && !coi.isInterface()) {

                    if (md.getName().startsWith("set")
                            || md.getName().startsWith("get")) {
                        String varName = md.getName().substring(3);
                        publicFields.add(varName.toLowerCase());
                    } else {
                        if (nextParam)
                            methods += ";";
                        methods += "+ " + md.getName() + "(";
                        for (Object gcn : md.getChildrenNodes()) {
                            if (gcn instanceof Parameter) {
                                Parameter paramCast = (Parameter) gcn;
                                String paramClass = paramCast.getType()
                                        .toString();
                                String paramName = paramCast.getChildrenNodes()
                                        .get(0).toString();
                                methods += paramName + " : " + paramClass;
                                if (interfaceMapping.containsKey(paramClass)
                                        && !interfaceMapping.get(classShortName)) {
                                    additions += "[" + classShortName
                                            + "] uses -.->";
                                    if (interfaceMapping.get(paramClass))
                                        additions += "[<<interface>>;"
                                                + paramClass + "]";
                                    else
                                        additions += "[" + paramClass + "]";
                                }
                                additions += ",";
                            } else {
                                String methodBody[] = gcn.toString().split(" ");
                                for (String foo : methodBody) {
                                    if (interfaceMapping.containsKey(foo)
                                            && !interfaceMapping.get(classShortName)) {
                                        additions += "[" + classShortName
                                                + "] uses -.->";
                                        if (interfaceMapping.get(foo))
                                            additions += "[<<interface>>;" + foo
                                                    + "]";
                                        else
                                            additions += "[" + foo + "]";
                                        additions += ",";
                                    }
                                }
                            }
                        }
                        methods += ") : " + md.getType();
                        nextParam = true;
                    }
                }
            }
        }

        boolean nextField = false;
        for (BodyDeclaration bd : ((TypeDeclaration) node).getMembers()) {
            if (bd instanceof FieldDeclaration) {
                FieldDeclaration fd = ((FieldDeclaration) bd);
                String fieldScope = aToSymScope(
                        bd.toStringWithoutComments().substring(0,
                                bd.toStringWithoutComments().indexOf(" ")));
                String fieldClass = changeBrackets(fd.getType().toString());
                String fieldName = fd.getChildrenNodes().get(1).toString();
                if (fieldName.contains("="))
                    fieldName = fd.getChildrenNodes().get(1).toString()
                            .substring(0, fd.getChildrenNodes().get(1)
                                    .toString().indexOf("=") - 1);

                if (fieldScope.equals("-")
                        && publicFields.contains(fieldName.toLowerCase())) {
                    fieldScope = "+";
                }

                String getDepen = "";
                boolean getDepenMultiple = false;
                if (fieldClass.contains("(")) {
                    getDepen = fieldClass.substring(fieldClass.indexOf("(") + 1,
                            fieldClass.indexOf(")"));
                    getDepenMultiple = true;
                } else if (interfaceMapping.containsKey(fieldClass)) {
                    getDepen = fieldClass;
                }
                if (getDepen.length() > 0 && interfaceMapping.containsKey(getDepen)) {
                    String connection = "-";

                    if (classConnectionsMap
                            .containsKey(getDepen + "-" + classShortName)) {
                        connection = classConnectionsMap
                                .get(getDepen + "-" + classShortName);
                        if (getDepenMultiple)
                            connection = "*" + connection;
                        classConnectionsMap.put(getDepen + "-" + classShortName,
                                connection);
                    } else {
                        if (getDepenMultiple)
                            connection += "*";
                        classConnectionsMap.put(classShortName + "-" + getDepen,
                                connection);
                    }
                }
                if (fieldScope == "+" || fieldScope == "-") {
                    if (nextField)
                        fields += "; ";
                    fields += fieldScope + " " + fieldName + " : " + fieldClass;
                    nextField = true;
                }
            }
        }

        if (coi.getExtends() != null) {
            additions += "[" + classShortName + "] " + "-^ " + coi.getExtends();
            additions += ",";
        }
        if (coi.getImplements() != null) {
            List<ClassOrInterfaceType> interfaceList = (List<ClassOrInterfaceType>) coi
                    .getImplements();
            for (ClassOrInterfaceType intface : interfaceList) {
                additions += "[" + classShortName + "] " + "-.-^ " + "["
                        + "<<interface>>;" + intface + "]";
                additions += ",";
            }
        }

        result += className;
        if (fields.length()!=0) {
            result += "|" + changeBrackets(fields);
        }
        if (methods.length()!=0) {
            result += "|" + changeBrackets(methods);
        }
        result += "]";
        result += additions;
        return result;

    }

    private String changeBrackets(String foo) {
        foo = foo.replace("[", "(");
        foo = foo.replace("]", ")");
        foo = foo.replace("<", "(");
        foo = foo.replace(">", ")");
        return foo;
    }

    private String aToSymScope(String stringScope) {

        if(stringScope.equals("private"))
            return "-";
        if(stringScope.equals("public"))
            return "+";
        return "";
    }

    private void buildMap(ArrayList<CompilationUnit> cuArray) {
        for (CompilationUnit cu : cuArray) {
            List<TypeDeclaration> cl = cu.getTypes();
            for (Node n : cl) {
                ClassOrInterfaceDeclaration coi = (ClassOrInterfaceDeclaration) n;
                interfaceMapping.put(coi.getName(), coi.isInterface()); // false is class,
            }
        }
    }

    @SuppressWarnings("unused")
    private void printMaps() {
        Set<String> keys = classConnectionsMap.keySet(); // get all keys
        for (String i : keys) {
            System.out.println(i + "->" + classConnectionsMap.get(i));
        }
        System.out.println("---");
    }

    private ArrayList<CompilationUnit> getCuArray(String inPath)
            throws Exception {
        File folder = new File(inPath);
        ArrayList<CompilationUnit> cuArray = new ArrayList<CompilationUnit>();
        System.out.println(inPath);
        for (final File f : folder.listFiles()) {
            if (f.isFile() && f.getName().endsWith(".java")) {
                FileInputStream in = new FileInputStream(f);
                CompilationUnit cu;
                try {
                    cu = JavaParser.parse(in);
                    cuArray.add(cu);
                } finally {
                    in.close();
                }
            }
        }
        return cuArray;
    }

    private String yumCodeGenerator(String code) {
        String[] codeLines = code.split(",");
        String[] uniqueCodeLines = new LinkedHashSet<String>(Arrays.asList(codeLines)).toArray(new String[0]);
        StringBuilder result=new StringBuilder();

        for(String str:uniqueCodeLines){
            result.append(str);
            result.append(",");
        }

        result.delete(result.length()-1,result.length());
        return result.toString();
    }

    private String additionParser() {
        String result = "";
        Set<String> keys = classConnectionsMap.keySet(); // get all keys
        for (String i : keys) {
            String[] classes = i.split("-");
            if (interfaceMapping.get(classes[0]))
                result += "[<<interface>>;" + classes[0] + "]";
            else
                result += "[" + classes[0] + "]";
            result += classConnectionsMap.get(i); // Add connection
            if (interfaceMapping.get(classes[1]))
                result += "[<<interface>>;" + classes[1] + "]";
            else
                result += "[" + classes[1] + "]";
            result += ",";
        }
        return result;
    }

}
