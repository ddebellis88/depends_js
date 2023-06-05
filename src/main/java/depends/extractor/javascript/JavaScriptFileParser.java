package depends.extractor.javascript;

import depends.entity.repo.EntityRepo;
import depends.extractor.FileParser;
import depends.relations.IBindingResolver;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;


public class JavaScriptFileParser extends FileParser {
    private IBindingResolver bindingResolver;
    public JavaScriptFileParser(EntityRepo entityRepo, IBindingResolver bindingResolver) {
        this.entityRepo = entityRepo;
        this.bindingResolver = bindingResolver;
    }

    @Override
    protected void parseFile(String fileFullPath) throws IOException {
        CharStream input = CharStreams.fromFileName(fileFullPath);
        Lexer lexer = new JavaScriptLexer(input);
        lexer.setInterpreter(new LexerATNSimulator(lexer, lexer.getATN(), lexer.getInterpreter().decisionToDFA, new PredictionContextCache()));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaScriptParser parser = new JavaScriptParser(tokens);
        ParserATNSimulator interpreter = new ParserATNSimulator(parser, parser.getATN(), parser.getInterpreter().decisionToDFA, new PredictionContextCache());
        parser.setInterpreter(interpreter);
        JavaListener bridge = new JavaListener(fileFullPath, entityRepo, bindingResolver);
        ParseTreeWalker walker = new ParseTreeWalker();
        try {
            walker.walk(bridge, parser.compilationUnit());
            interpreter.clearDFA();

        }catch (Exception e) {
            System.err.println("error encountered during parse..." );
            e.printStackTrace();
        }

    }

}
