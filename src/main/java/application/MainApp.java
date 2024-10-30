package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.CargoView;
import view.DepartamentoView;
import view.FuncionarioView;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        MenuBar menuBar = new MenuBar();

        Menu menuCadastro = new Menu("Cadastro");
        Menu menuFolhaPagto = new Menu("Folha de Pagamento");

        MenuItem itemCargo = new MenuItem("Cargos");
        itemCargo.setOnAction(e -> abrirCargoView());
        
        MenuItem itemDepartamento = new MenuItem("Departamentos");
        itemDepartamento.setOnAction(e -> abrirDepartamentoView());

        MenuItem itemFuncionario = new MenuItem("Funcionários");
        itemFuncionario.setOnAction(e -> abrirFuncionarioView());

        MenuItem itemFolhaPagto = new MenuItem("Cálculo");

        menuCadastro.getItems().addAll(itemCargo, itemDepartamento, itemFuncionario);
        menuFolhaPagto.getItems().addAll(itemFolhaPagto);

        menuBar.getMenus().add(menuCadastro);
        menuBar.getMenus().add(menuFolhaPagto);

        BorderPane root = new BorderPane();
        root.setTop(menuBar);

        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Sistema de Folha de Pagamento - MongoDB");
        stage.setScene(scene);
        stage.show();

    }

    private void abrirDepartamentoView() {
        DepartamentoView departamentoView = new DepartamentoView(new Stage());
    }

    private void abrirCargoView() {
        CargoView cargoView = new CargoView(new Stage());
    }
    
    private void abrirFuncionarioView() {
        FuncionarioView funcionarioView = new FuncionarioView(new Stage());
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}