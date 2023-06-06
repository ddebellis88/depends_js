package depends.format.detail;

import depends.format.AbstractFormatDependencyDumper;
import depends.matrix.core.DependencyMatrix;

import java.io.FileWriter;
import java.io.IOException;

public class DetailHtmlFormatDependencyDumper extends AbstractFormatDependencyDumper {
    int numOfEntities;
    int numOfFiles;
    int numOfMethods;
    int numOfDependencies;
    int y, j, k;

    @Override
    public String getFormatName() {
        return "html";
    }
    public DetailHtmlFormatDependencyDumper(DependencyMatrix matrix, String projectName, String outputDir) {
        super(matrix, projectName, outputDir);
    }
    @Override
    public boolean output() {
        FileWriter writer;
        try{
            StringBuilder sb = new StringBuilder();
            sb.append("<html>");
            sb.append("<head>");
            sb.append("<title>Summarization of Dependency Relation</title>");
            sb.append("</head>");
            sb.append("<body> <b>This project has " + getNumOfEntities(matrix) + " entities</b>");
            sb.append("<body> <b>Including " + getNumOfFiles(matrix) + " files and " + getNumOfMethods(matrix) + " methods, and " + getNumOfDependencies(matrix) + " dependencies</b>");
            sb.append("<body> <b>including j dependencies among files, and k dependencies among methods.</b>");
            sb.append("</body>");
            sb.append("</html>");

            writer = new FileWriter(composeFilename() + ".html");
            writer.write(sb.toString());

            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getNumOfEntities(DependencyMatrix matrix){
        return "a";
    }

    private int getNumOfFiles(DependencyMatrix matrix){
        return matrix.getNodes().size();
    }

    private String getNumOfMethods(DependencyMatrix matrix){
        return "c";
    }

    private int getNumOfDependencies(DependencyMatrix matrix){
        return matrix.getDependencyPairs().size();
    }
}
