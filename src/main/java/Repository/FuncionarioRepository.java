package Repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;
import DBConfig.DBConfig;
import model.Funcionario;
import model.Departamento;
import model.Cargo;

public class FuncionarioRepository {
    private final MongoCollection<Document> collection;
    private final DepartamentoRepository departamentoRepository;
    private final CargoRepository cargoRepository;
    
    public FuncionarioRepository() {
        MongoDatabase database = DBConfig.getDataBase();
        this.collection = database.getCollection("funcionario");
        this.departamentoRepository = new DepartamentoRepository();
        this.cargoRepository = new CargoRepository();
    }
    
    public void salvarFuncionario(Funcionario funcionario) {
        Document doc = new Document("nome", funcionario.getNome())
            .append("departamentoId", new ObjectId(funcionario.getDepartamento().getId()))
            .append("cargoId", new ObjectId(funcionario.getCargo().getId()));
        collection.insertOne(doc);
    }
    
    public List<Funcionario> listarFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();
        List<Departamento> departamentos = departamentoRepository.listarDepartamentos();
        List<Cargo> cargos = cargoRepository.listarCargos();
        
        for (Document doc : collection.find()) {
            String departamentoId = doc.getObjectId("departamentoId").toString();
            String cargoId = doc.getObjectId("cargoId").toString();
            
            Departamento departamento = departamentos.stream()
                .filter(d -> d.getId().equals(departamentoId))
                .findFirst()
                .orElse(null);
                
            Cargo cargo = cargos.stream()
                .filter(c -> c.getId().equals(cargoId))
                .findFirst()
                .orElse(null);
            
            if (departamento != null && cargo != null) {
                Funcionario funcionario = new Funcionario(
                    doc.getObjectId("_id").toString(),
                    doc.getString("nome"),
                    departamento,
                    cargo
                );
                funcionarios.add(funcionario);
            }
        }
        return funcionarios;
    }
    
    public void atualizarFuncionario(String id, Funcionario funcionarioAtualizado) {
        Document updatedDoc = new Document("nome", funcionarioAtualizado.getNome())
            .append("departamentoId", new ObjectId(funcionarioAtualizado.getDepartamento().getId()))
            .append("cargoId", new ObjectId(funcionarioAtualizado.getCargo().getId()));
        collection.updateOne(
            Filters.eq("_id", new ObjectId(id)),
            new Document("$set", updatedDoc)
        );
    }
    
    public void removerFuncionario(String id) {
        collection.deleteOne(Filters.eq("_id", new ObjectId(id)));
    }
}