package view;

import Repository.CargoRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Cargo;

public class CargoView {
    private CargoRepository cargoRepository = new CargoRepository();
    private final ObservableList<Cargo> cargosList = FXCollections.observableArrayList();
    
    public CargoView(Stage stage) {
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);
        
        ListView<Cargo> listView = new ListView<>(cargosList);
        GridPane.setConstraints(listView, 0, 0, 2, 4);
        listView.setPrefWidth(300);
        
        TextField nomeCargo = new TextField();
        nomeCargo.setPromptText("Digite o nome do cargo");
        GridPane.setConstraints(nomeCargo, 0, 5);
        
        TextField salarioBase = new TextField();
        salarioBase.setPromptText("Digite o salário base");
        GridPane.setConstraints(salarioBase, 0, 6);
        
        // Botões
        Button salvarButton = new Button("Salvar");
        GridPane.setConstraints(salvarButton, 0, 7);
        
        Button atualizarButton = new Button("Atualizar");
        GridPane.setConstraints(atualizarButton, 1, 7);
        
        Button removerButton = new Button("Remover");
        GridPane.setConstraints(removerButton, 1, 8);
        
        // Eventos dos Botões
        salvarButton.setOnAction(e -> {
            try {
                String nome = nomeCargo.getText();
                double salario = Double.parseDouble(salarioBase.getText());
                if (!nome.isEmpty()) {
                    Cargo cargo = new Cargo(null, nome, salario);
                    cargoRepository.salvarCargo(cargo);
                    atualizarListView();
                    nomeCargo.clear();
                    salarioBase.clear();
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Erro ao salvar cargo");
                alert.setContentText("Por favor, insira um valor válido para o salário base.");
                alert.showAndWait();
            }
        });
        
        atualizarButton.setOnAction(e -> {
            try {
                Cargo cargoSelecionado = listView.getSelectionModel().getSelectedItem();
                if (cargoSelecionado != null && !nomeCargo.getText().isEmpty()) {
                    cargoSelecionado.setNome(nomeCargo.getText());
                    cargoSelecionado.setSalarioBase(Double.parseDouble(salarioBase.getText()));
                    cargoRepository.atualizarCargo(cargoSelecionado.getId(), cargoSelecionado);
                    atualizarListView();
                    nomeCargo.clear();
                    salarioBase.clear();
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Erro ao atualizar cargo");
                alert.setContentText("Por favor, insira um valor válido para o salário base.");
                alert.showAndWait();
            }
        });
        
        removerButton.setOnAction(e -> {
            Cargo cargoSelecionado = listView.getSelectionModel().getSelectedItem();
            if (cargoSelecionado != null) {
                cargoRepository.removerCargo(cargoSelecionado.getId());
                atualizarListView();
                nomeCargo.clear();
                salarioBase.clear();
            }
        });
        
        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nomeCargo.setText(newSelection.getNome());
                salarioBase.setText(String.valueOf(newSelection.getSalarioBase()));
            }
        });
        
        grid.getChildren().addAll(listView, nomeCargo, salarioBase, salvarButton, atualizarButton, removerButton);
        
        atualizarListView();
        
        Scene scene = new Scene(grid, 400, 350);
        stage.setScene(scene);
        stage.setTitle("Cadastro de Cargos");
        stage.show();
    }
    
    private void atualizarListView() {
        cargosList.clear();
        cargosList.addAll(cargoRepository.listarCargos());
    }
}