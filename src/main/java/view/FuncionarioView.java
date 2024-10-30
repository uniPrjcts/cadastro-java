package view;

import Repository.FuncionarioRepository;
import Repository.DepartamentoRepository;
import Repository.CargoRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Funcionario;
import model.Departamento;
import model.Cargo;

public class FuncionarioView {
    private FuncionarioRepository funcionarioRepository = new FuncionarioRepository();
    private DepartamentoRepository departamentoRepository = new DepartamentoRepository();
    private CargoRepository cargoRepository = new CargoRepository();
    private final ObservableList<Funcionario> funcionariosList = FXCollections.observableArrayList();
    private final ObservableList<Departamento> departamentosList = FXCollections.observableArrayList();
    private final ObservableList<Cargo> cargosList = FXCollections.observableArrayList();
    
    public FuncionarioView(Stage stage) {

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);
        
        ListView<Funcionario> listView = new ListView<>(funcionariosList);
        GridPane.setConstraints(listView, 0, 0, 2, 4);
        listView.setPrefWidth(300);
        
        TextField nomeFuncionario = new TextField();
        nomeFuncionario.setPromptText("Digite o nome do Funcionário");
        GridPane.setConstraints(nomeFuncionario, 0, 5);
        
        ComboBox<Departamento> departamentoComboBox = new ComboBox<>(departamentosList);
        departamentoComboBox.setPromptText("Selecione o Departamento");
        GridPane.setConstraints(departamentoComboBox, 0, 6);
        
        ComboBox<Cargo> cargoComboBox = new ComboBox<>(cargosList);
        cargoComboBox.setPromptText("Selecione o Cargo");
        GridPane.setConstraints(cargoComboBox, 0, 7);
        
        // Botões
        Button salvarButton = new Button("Salvar");
        GridPane.setConstraints(salvarButton, 0, 8);
        
        Button atualizarButton = new Button("Atualizar");
        GridPane.setConstraints(atualizarButton, 1, 8);
        
        Button removerButton = new Button("Remover");
        GridPane.setConstraints(removerButton, 1, 9);
        
        // Eventos dos Botões
        salvarButton.setOnAction(e -> {
            String nome = nomeFuncionario.getText();
            Departamento departamento = departamentoComboBox.getValue();
            Cargo cargo = cargoComboBox.getValue();
            
            if (!nome.isEmpty() && departamento != null && cargo != null) {
                Funcionario funcionario = new Funcionario(null, nome, departamento, cargo);
                funcionarioRepository.salvarFuncionario(funcionario);
                atualizarListView();
                limparCampos(nomeFuncionario, departamentoComboBox, cargoComboBox);
            } else {
                mostrarAlerta("Erro", "Todos os campos são obrigatórios!");
            }
        });
        
        atualizarButton.setOnAction(e -> {
            Funcionario funcionarioSelecionado = listView.getSelectionModel().getSelectedItem();
            if (funcionarioSelecionado != null && !nomeFuncionario.getText().isEmpty() && 
                departamentoComboBox.getValue() != null && cargoComboBox.getValue() != null) {
                
                funcionarioSelecionado.setNome(nomeFuncionario.getText());
                funcionarioSelecionado.setDepartamento(departamentoComboBox.getValue());
                funcionarioSelecionado.setCargo(cargoComboBox.getValue());
                
                funcionarioRepository.atualizarFuncionario(funcionarioSelecionado.getId(), funcionarioSelecionado);
                atualizarListView();
                limparCampos(nomeFuncionario, departamentoComboBox, cargoComboBox);
            } else {
                mostrarAlerta("Erro", "Selecione um funcionário e preencha todos os campos!");
            }
        });
        
        removerButton.setOnAction(e -> {
            Funcionario funcionarioSelecionado = listView.getSelectionModel().getSelectedItem();
            if (funcionarioSelecionado != null) {
                funcionarioRepository.removerFuncionario(funcionarioSelecionado.getId());
                atualizarListView();
                limparCampos(nomeFuncionario, departamentoComboBox, cargoComboBox);
            } else {
                mostrarAlerta("Erro", "Selecione um funcionário para remover!");
            }
        });
        
        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nomeFuncionario.setText(newSelection.getNome());
                departamentoComboBox.setValue(newSelection.getDepartamento());
                cargoComboBox.setValue(newSelection.getCargo());
            }
        });
        
        grid.getChildren().addAll(listView, nomeFuncionario, departamentoComboBox, cargoComboBox, salvarButton, atualizarButton, removerButton);
        
        atualizarListView();
        atualizarComboBoxes();
        
        Scene scene = new Scene(grid, 400, 450);
        stage.setScene(scene);
        stage.setTitle("Cadastro de Funcionários");
        stage.show();
    }
    
    private void atualizarListView() {
        funcionariosList.clear();
        funcionariosList.addAll(funcionarioRepository.listarFuncionarios());
    }
    
    private void atualizarComboBoxes() {
        departamentosList.clear();
        departamentosList.addAll(departamentoRepository.listarDepartamentos());
        
        cargosList.clear();
        cargosList.addAll(cargoRepository.listarCargos());
    }
    
    private void limparCampos(TextField nome, ComboBox<Departamento> depto, ComboBox<Cargo> cargo) {
        nome.clear();
        depto.setValue(null);
        cargo.setValue(null);
    }
    
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}