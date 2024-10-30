package view;

import Repository.DepartamentoRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Departamento;

public class DepartamentoView {

    private DepartamentoRepository departamentoRepository = new DepartamentoRepository();
    private final ObservableList<Departamento> departamentosList = FXCollections.observableArrayList();

    public DepartamentoView(Stage stage) {
        // Layout da interface
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(8);
        grid.setHgap(10);

        // ListView para exibir os departamentos
        ListView<Departamento> listView = new ListView<>(departamentosList);
        GridPane.setConstraints(listView, 0, 0, 2, 4);
        listView.setPrefWidth(300);

        // Campos de entrada
        TextField nomeDepartamento = new TextField();
        nomeDepartamento.setPromptText("Digite o nome do Departamento");
        GridPane.setConstraints(nomeDepartamento, 0, 5);

        // Botões
        Button salvarButton = new Button("Salvar");
        GridPane.setConstraints(salvarButton, 0, 6);

        Button atualizarButton = new Button("Atualizar");
        GridPane.setConstraints(atualizarButton, 1, 6);

        Button removerButton = new Button("Remover");
        GridPane.setConstraints(removerButton, 1, 7);

        // Eventos dos Botões
        salvarButton.setOnAction(e -> {
            String nome = nomeDepartamento.getText();
            if (!nome.isEmpty()) {
                Departamento departamento = new Departamento(null, nome);
                departamentoRepository.salvarDepartamento(departamento);
                atualizarListView();
                nomeDepartamento.clear();
            }
        });

        atualizarButton.setOnAction(e -> {
            Departamento departamentoSelecionado = listView.getSelectionModel().getSelectedItem();
            if (departamentoSelecionado != null && !nomeDepartamento.getText().isEmpty()) {
                departamentoSelecionado.setNome(nomeDepartamento.getText());
                departamentoRepository.atualizarDepartamento(departamentoSelecionado.getId(), departamentoSelecionado);
                atualizarListView();
                nomeDepartamento.clear();
            }
        });

        removerButton.setOnAction(e -> {
            Departamento departamentoSelecionado = listView.getSelectionModel().getSelectedItem();
            if (departamentoSelecionado != null) {
                departamentoRepository.removerDepartamento(departamentoSelecionado.getId());
                atualizarListView();
                nomeDepartamento.clear();
            }
        });

        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nomeDepartamento.setText(newSelection.getNome());
            }
        });

        grid.getChildren().addAll(listView, nomeDepartamento, salvarButton, atualizarButton, removerButton);

        atualizarListView();

        Scene scene = new Scene(grid, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Cadastro de Departamentos");
        stage.show();
    }

    private void atualizarListView() {
        departamentosList.clear();
        departamentosList.addAll(departamentoRepository.listarDepartamentos());
    }
}