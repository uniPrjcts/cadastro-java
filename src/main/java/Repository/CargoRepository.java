package Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import DBConfig.DBConfig;
import model.Cargo;
import org.bson.types.ObjectId;

public class CargoRepository {
    private final MongoCollection<Document> collection;
    
    public CargoRepository() {
        MongoDatabase database = DBConfig.getDataBase();
        this.collection = database.getCollection("cargo");
    }
    
    public void salvarCargo(Cargo cargo) {
        Document doc = new Document("nome", cargo.getNome()).append("salarioBase", cargo.getSalarioBase());
        collection.insertOne(doc);
    }
    
    public List<Cargo> listarCargos() {
        List<Cargo> cargos = new ArrayList<>();
        for (Document doc : collection.find()) {
            Cargo cargo = new Cargo(
                doc.getObjectId("_id").toString(),
                doc.getString("nome"),
                doc.getDouble("salarioBase")
            );
            cargos.add(cargo);
        }
        return cargos;
    }
    
    public void atualizarCargo(String id, Cargo cargoAtualizado) {
        Document updatedDoc = new Document("nome", cargoAtualizado.getNome()).append("salarioBase", cargoAtualizado.getSalarioBase());
        collection.updateOne(Filters.eq("_id", new ObjectId(id)), new Document("$set", updatedDoc));
    }
    
    public void removerCargo(String id) {
        collection.deleteOne(Filters.eq("_id", new ObjectId(id)));
    }
}