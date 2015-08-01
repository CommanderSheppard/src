package sample;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;

@SuppressWarnings("unchecked")
public class View {
    static ComboBox beverageCB;
    static ComboBox appetizerCB;
    static ComboBox mainCourseCB;
    static ComboBox dessertCB;
    private final Controller controllerInstance = new Controller();
    private final Image savedBill = new Image("saved.png");

    private static Label createLabel(String labText, int labWid, int labHei, Pos labPos, int labLayX, int labLayY, String labCol) {
        return LabelBuilder.create().text(labText).prefWidth(labWid).prefHeight(labHei)
                .alignment(labPos).layoutX(labLayX).layoutY(labLayY)
                .style("-fx-background-color: " + labCol + ";").build();
    }

    private static ComboBox createComboBox(int cbWid, int cbHei, int cbLayX, int cbLayY, String cbCol) {
        return ComboBoxBuilder.create().prefWidth(cbWid).prefHeight(cbHei)
                .layoutX(cbLayX).layoutY(cbLayY).style("-fx-background-color: " + cbCol + ";").build();
    }

    private static TextArea createTextArea(int taWid, int taHei, int taLayX, int taLayY, String taCol) {
        return TextAreaBuilder.create().prefWidth(taWid).prefHeight(taHei)
                .layoutX(taLayX).layoutY(taLayY).style("-fx-background-color: " + taCol + ";").build();
    }

    private static Button createButton(String butText, int butWid, int butHei, int butLayX, int butLayY, String butCol) {
        return ButtonBuilder.create().text(butText).prefWidth(butWid).prefHeight(butHei)
                .layoutX(butLayX).layoutY(butLayY).style("-fx-background-color: " + butCol + ";").build();
    }

    /* static Text createText (String textVal, int textLayX, int textLayY, String textCol){
        Text text = TextBuilder.create().text(textVal).layoutX(textLayX).layoutY(textLayY)
                .style("-fx-background-color: " + textCol + ";").build();
    return text;
    }
   */

    Scene createScene() {
        final Group root = new Group();
        int windowWidth = 290;
        int windowHeight = 500;
        Scene scene = new Scene(root, windowWidth, windowHeight);
        Canvas canvas = new Canvas(windowWidth, windowHeight);
        StackPane holder = new StackPane();
        holder.getChildren().add(canvas);
        root.getChildren().add(holder);
        holder.setStyle("-fx-background-color: lightgrey");

        //    scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        root.getChildren().add(createLabel("Restaurant", 145, 22, Pos.CENTER, 75, 10, "grey"));
        root.getChildren().add(createLabel("Table number:", 110, 22, Pos.CENTER, 15, 70, "grey"));
        root.getChildren().add(createLabel("Waiter name:", 110, 22, Pos.CENTER, 15, 100, "grey"));

        final TextArea tableNumbTA = createTextArea(130, 22, 145, 70, "grey");
        final TextArea waiterNameTA = createTextArea(130, 22, 145, 100, "grey");
        root.getChildren().add(tableNumbTA);
        root.getChildren().add(waiterNameTA);

        root.getChildren().add(createLabel("Beverage:", 110, 22, Pos.CENTER, 15, 180, "grey"));
        root.getChildren().add(createLabel("Appetizer:", 110, 22, Pos.CENTER, 15, 210, "grey"));
        root.getChildren().add(createLabel("Main course:", 110, 22, Pos.CENTER, 15, 240, "grey"));
        root.getChildren().add(createLabel("Dessert:", 110, 22, Pos.CENTER, 15, 270, "grey"));

        beverageCB = createComboBox(130, 22, 145, 180, "grey");
        root.getChildren().add(beverageCB);
        appetizerCB = createComboBox(130, 22, 145, 210, "grey");
        root.getChildren().add(appetizerCB);
        mainCourseCB = createComboBox(130, 22, 145, 240, "grey");
        root.getChildren().add(mainCourseCB);
        dessertCB = (createComboBox(130, 22, 145, 270, "grey"));
        root.getChildren().add(dessertCB);

        root.getChildren().add(createLabel("Subtotal:", 60, 22, Pos.CENTER, 15, 350, "grey"));
        root.getChildren().add(createLabel("Tax:", 60, 22, Pos.CENTER, 15, 380, "grey"));
        root.getChildren().add(createLabel("Total:", 60, 22, Pos.CENTER, 15, 410, "grey"));

        final TextArea subtotalTextArea = createTextArea(60, 22, 93, 350, "grey");
        subtotalTextArea.setEditable(false);
        root.getChildren().add(subtotalTextArea);
        final TextArea taxTextArea = createTextArea(60, 22, 93, 380, "grey");
        taxTextArea.setEditable(false);
        root.getChildren().add(taxTextArea);
        final TextArea totalTextArea = createTextArea(60, 22, 93, 410, "grey");
        totalTextArea.setEditable(false);
        root.getChildren().add(totalTextArea);

        final Button saveTableButton = createButton("Save Table", 95, 22, 171, 350, "orange");
        saveTableButton.setDisable(true);
        root.getChildren().add(saveTableButton);

        Button calcBillButton = createButton("Calculate Bill", 95, 22, 171, 380, "orange");
        root.getChildren().add(calcBillButton);

        final Button payBillButton = createButton("Pay Bill", 95, 22, 171, 410, "orange");
        payBillButton.setDisable(true);
        root.getChildren().add(payBillButton);

        Controller.fillComboBox("beverage", beverageCB);
        Controller.fillComboBox("appetizer", appetizerCB);
        Controller.fillComboBox("main_course", mainCourseCB);
        Controller.fillComboBox("dessert", dessertCB);

        final ImageView savedBillView = new ImageView(savedBill);
        savedBillView.setLayoutX(268);
        savedBillView.setLayoutY(350);

        EventHandler cbEvent = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                saveTableButton.setDisable(true);
                payBillButton.setDisable(true);
                if (root.getChildren().contains(savedBillView)) {
                    savedBillView.setVisible(false);
                }
            }
        };
        beverageCB.setOnMouseClicked(cbEvent);
        appetizerCB.setOnMouseClicked(cbEvent);
        mainCourseCB.setOnMouseClicked(cbEvent);
        dessertCB.setOnMouseClicked(cbEvent);

        calcBillButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                float temp = controllerInstance.calcResult();
                float tax = (float) Math.ceil(temp * 0.05 * 100) / 100;
                subtotalTextArea.setText("$" + temp);
                taxTextArea.setText("$" + tax);
                totalTextArea.setText("$" + ((float) Math.round((temp + tax) * 100)) / 100);
                saveTableButton.setDisable(false);
                payBillButton.setDisable(false);
            }
        });

        saveTableButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if ("".equals(waiterNameTA.getText()) || "".equals(tableNumbTA.getText())) {
                    final Stage alert = new Stage();
                    alert.initModality(Modality.APPLICATION_MODAL);
                    Button okButton = createButton("Ok.", 50, 25, 92, 38, "orange");
                    alert.setScene(new Scene(VBoxBuilder.create().
                            children(new Text("Fill Table Number and Waiter Name fields!"), okButton).
                            alignment(Pos.CENTER).padding(new Insets(5)).build()));
                    alert.show();
                    okButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            alert.close();
                        }
                    });
                } else {
                    try {
                        controllerInstance.stmt.executeUpdate("insert into savedtables values (\'" + waiterNameTA.getText() + "\',"
                                + Double.parseDouble(totalTextArea.getText().substring(1, totalTextArea.getText().length())) + ")");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (!root.getChildren().contains(savedBillView)) {
                        root.getChildren().add(savedBillView);
                    }
                    if (!savedBillView.isVisible()) {
                        savedBillView.setVisible(true);
                    }
                    saveTableButton.setDisable(true);
                }
            }
        });

        payBillButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (saveTableButton.isDisabled()) {
                    tableNumbTA.setText("");
                    waiterNameTA.setText("");
                    beverageCB.setValue("");
                    appetizerCB.setValue("");
                    mainCourseCB.setValue("");
                    dessertCB.setValue("");
                    subtotalTextArea.setText("");
                    taxTextArea.setText("");
                    totalTextArea.setText("");
                    savedBillView.setVisible(false);
                } else {
                    if ("".equals(waiterNameTA.getText()) || "".equals(tableNumbTA.getText())) {
                        final Stage alert = new Stage();
                        alert.initModality(Modality.APPLICATION_MODAL);
                        Button okButton = createButton("Ok.", 50, 25, 92, 38, "orange");
                        alert.setScene(new Scene(VBoxBuilder.create().
                                children(new Text("Fill Table Number and Waiter Name fields!"), okButton).
                                alignment(Pos.CENTER).padding(new Insets(5)).build()));
                        alert.show();
                        okButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                alert.close();
                            }
                        });
                    } else {
                        try {
                            controllerInstance.stmt.executeUpdate("insert into savedtables values (\'" + waiterNameTA.getText() + "\',"
                                    + Double.parseDouble(totalTextArea.getText().substring(1, totalTextArea.getText().length())) + ")");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        if (!root.getChildren().contains(savedBillView)) {
                            root.getChildren().add(savedBillView);
                        }
                        if (!savedBillView.isVisible()) {
                            savedBillView.setVisible(true);
                        }
                        tableNumbTA.setText("");
                        waiterNameTA.setText("");
                        beverageCB.setValue("");
                        appetizerCB.setValue("");
                        mainCourseCB.setValue("");
                        dessertCB.setValue("");
                        subtotalTextArea.setText("");
                        taxTextArea.setText("");
                        totalTextArea.setText("");
                        savedBillView.setVisible(false);
                        saveTableButton.setDisable(true);
                    }
                }
            }
        });
        return scene;
    }
}