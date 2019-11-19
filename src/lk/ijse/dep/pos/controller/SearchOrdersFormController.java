package lk.ijse.dep.pos.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.dep.pos.business.BOFactory;
import lk.ijse.dep.pos.business.BOTypes;
import lk.ijse.dep.pos.business.custom.OrderBO;
import lk.ijse.dep.pos.dto.OrderDTO2;

import lk.ijse.dep.pos.util.OrderTM;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;

import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class SearchOrdersFormController {
    public TextField txtSearch;
    public TableView<OrderTM> tblOrders;

    private OrderBO searchOrder = BOFactory.getInstance().getBO(BOTypes.ORDER);

    public void initialize() throws Exception {
        // Let's map
        tblOrders.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("orderId"));
        tblOrders.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        tblOrders.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tblOrders.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tblOrders.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));

        ObservableList<OrderTM> olOrders = tblOrders.getItems();
        List<OrderDTO2> orderInfo = searchOrder.getOrderInfo();

        for (OrderDTO2 order : orderInfo) {
            String customerId = order.getCustomerId();
            String customerName = order.getCustomerName();
            Date orderDate = order.getOrderDate();
            int orderId = order.getOrderId();
            double total = order.getTotal();
            OrderTM orderTM = new OrderTM(Integer.toString(orderId),String.valueOf(orderDate),customerId,customerName,total);
            olOrders.add(orderTM);
        }

        ObservableList<OrderTM> olAllOrders =
                FXCollections.observableArrayList(olOrders);

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                String searchText = txtSearch.getText();

                ObservableList<OrderTM> tempOrders = FXCollections.observableArrayList();

                for (OrderTM order : olAllOrders) {
                    if (order.getOrderId().contains(searchText) ||
                            order.getOrderDate().contains(searchText) ||
                            order.getCustomerId().contains(searchText) ||
                            order.getCustomerName().contains(searchText)) {
                        tempOrders.add(order);
                    }
                }

                tblOrders.setItems(tempOrders);
            }
        });

    }

    @FXML
    private void navigateToHome(MouseEvent event) throws IOException {
        URL resource = this.getClass().getResource("/lk.ijse.dep.pos.view/MainForm.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.txtSearch.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void tblOrders_OnMouseClicked(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() == 2) {

            URL resource = this.getClass().getResource("/lk.ijse.dep.pos.view/PlaceOrderForm.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            Parent root = fxmlLoader.load();
            Scene placeOrderScene = new Scene(root);
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(placeOrderScene);
            secondaryStage.centerOnScreen();
            secondaryStage.setTitle("View Order");
            secondaryStage.setResizable(false);

            PlaceOrderFormController ctrl = fxmlLoader.getController();
            OrderTM selectedOrder = tblOrders.getSelectionModel().getSelectedItem();
          //  ctrl.initializeForSearchOrderForm(selectedOrder.getOrderId());

            secondaryStage.show();
        }
    }
}
