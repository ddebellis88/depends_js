package depends.extractor.javascript;

import depends.entity.Entity;
import depends.entity.FileEntity;
import depends.entity.PackageEntity;
import depends.entity.repo.EntityRepo;
import depends.extractor.UnsolvedBindings;
import depends.importtypes.Import;
import depends.relations.ImportLookupStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class JavaScriptImportLookupStrategy extends ImportLookupStrategy{

    public JavaScriptImportLookupStrategy(EntityRepo repo) {
        super(repo);
    }

    @Override
    public Entity lookupImportedType(String name, FileEntity fileEntity) {
        //JavaScript Strategy
        String importedString = fileEntity.importedSuffixMatch(name);
        if (importedString==null) return null;
        return repo.getEntity(importedString);
    }


    @Override
    public List<Entity> getImportedRelationEntities(List<Import> importedList) {
        ArrayList<Entity> result = new ArrayList<>();
        for (Import importedItem:importedList) {
            Entity imported = repo.getEntity(importedItem.getContent());
            if (imported==null) continue;
            if (imported instanceof PackageEntity) {
                //ignore wildcard import relation
            }else {
                result.add(imported);
            }
        }
        return result;
    }

    @Override
    public List<Entity> getImportedTypes(List<Import> importedList, Set<UnsolvedBindings> unsolvedBindings) {
        ArrayList<Entity> result = new ArrayList<>();
        for (Import importedItem:importedList) {
            Entity imported = repo.getEntity(importedItem.getContent());
            if (imported==null) {
                unsolvedBindings.add(new UnsolvedBindings(importedItem.getContent(),null));
                continue;
            }
            if (imported instanceof PackageEntity) {
                //expand import of package to all classes under the package due to we dis-courage the behavior
                for (Entity child:imported.getChildren()) {
                    if (child instanceof FileEntity) {
                        child.getChildren().forEach(item->result.add(item));
                    }else {
                        result.add(child);
                    }
                }
            }else {
                result.add(imported);
            }
        }
        return result;
    }

    @Override
    public List<Entity> getImportedFiles(List<Import> importedList) {
        return new ArrayList<Entity>();
    }


    @Override
    public boolean supportGlobalNameLookup() {
        return true;
    }

}
