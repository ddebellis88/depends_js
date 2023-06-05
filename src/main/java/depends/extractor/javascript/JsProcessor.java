package depends.extractor.javascript;

import depends.entity.repo.BuiltInType;
import depends.extractor.AbstractLangProcessor;
import depends.extractor.FileParser;
import depends.extractor.javascript.JavaScriptBuiltInType;
import depends.extractor.javascript.JavaScriptFileParser;
import depends.extractor.javascript.JavaScriptImportLookupStrategy;
import depends.relations.ImportLookupStrategy;

import java.util.ArrayList;
import java.util.List;

import static depends.deptypes.DependencyType.*;
public class JsProcessor extends AbstractLangProcessor {
    private static final String JAVASCRIPT_LANG = "javascript";
    private static final String JAVASCRIPT_SUFFIX = ".js";

    public JsProcessor() {
    }

    @Override
    public String supportedLanguage() {
        return JAVASCRIPT_LANG;
    }

    @Override
    public String[] fileSuffixes() {
        return new String[] {JAVASCRIPT_SUFFIX};
    }

    @Override
    public FileParser createFileParser() {
        return new JavaScriptFileParser(entityRepo, bindingResolver);
    }

    @Override
    public ImportLookupStrategy getImportLookupStrategy() {
        return new JavaScriptImportLookupStrategy(entityRepo);
    }

    @Override
    public BuiltInType getBuiltInType() {
        return new JavaScriptBuiltInType();
    }

    @Override
    public List<String> supportedRelations() {
        ArrayList<String> depedencyTypes = new ArrayList<>();
        depedencyTypes.add(IMPORT);
        depedencyTypes.add(CONTAIN);
        depedencyTypes.add(IMPLEMENT);
        depedencyTypes.add(INHERIT);
        depedencyTypes.add(CALL);
        depedencyTypes.add(PARAMETER);
        depedencyTypes.add(RETURN);
        depedencyTypes.add(SET);
        depedencyTypes.add(CREATE);
        depedencyTypes.add(USE);
        depedencyTypes.add(CAST);
        depedencyTypes.add(THROW);
        depedencyTypes.add(ANNOTATION);
        return depedencyTypes;
    }


}