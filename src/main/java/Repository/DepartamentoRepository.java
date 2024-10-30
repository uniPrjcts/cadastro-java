package Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import DBConfig.DBConfig;
import model.Departamento;
import org.bson.types.ObjectId;

public class DepartamentoRepository {

    private final MongoCollection<Document> collection;

    public DepartamentoRepository() {
        MongoDatabase database = DBConfig.getDataBase();
        this.collection = database.getCollection("departamento");
    }

    public void salvarDepartamento(Departamento departamento) {
        Document doc = new Document("nome", departamento.getNome());
        collection.insertOne(doc);
    }

    public List<Departamento> listarDepartamentos() {
        List<Departamento> departamentos = new ArrayList<>();
        for (Document doc : collection.find()) {
            Departamento departamento = new Departamento(
                    doc.getObjectId("_id").toString(),
                    doc.getString("nome")
            );
            departamentos.add(departamento);
        }
        return departamentos;
    }

    public void atualizarDepartamento(String id, Departamento departamentoAtualizado) {
        Document updatedDoc = new Document("nome", departamentoAtualizado.getNome());
        collection.updateOne(Filters.eq("_id", new ObjectId(id)), new Document("$set", updatedDoc));
    }

    public void removerDepartamento(String id) {
        collection.deleteOne(Filters.eq("_id", new ObjectId(id)));
    }
}