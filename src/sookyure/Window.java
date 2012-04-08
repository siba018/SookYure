/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sookyure;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author siba
 */
public class Window {

    Stage stgPIN;
    Button btn;
    TextField tfPIN;
    TextField tfPost;
    Stage primaryStage;
    TableView<Users> table = new TableView<Users>();
    VBox propertyBox;
    private Label lblName;
    private TextArea taText;
    //
    int c = 0;
    //

    Window(Stage stage) {
        System.out.println("hoge");
        primaryStage = stage;
        init();
    }

    Window() {
    }
    /*
     * テーブルについてOperaのタブにあることをやる。以上 detaが何者か、Statusオブジェが何者かわかれば勝てると思う
     */
    ObservableList<Users> data =
            FXCollections.observableArrayList();
    AudioClip ac;

    private void init() {
        ac = new AudioClip(new File("koredemo.mp3").toURI().toString());
        ac.play();
        propertyBox = new VBox();
        lblName = new Label();
        taText = new TextArea();
        taText.setPrefHeight(90);
        taText.setWrapText(true);
        // taText.setDisable(true);
        taText.setEditable(false);
        //PINコード用Stage
        // 新しいウインドウを生成
        stgPIN = new Stage();
        // モーダルウインドウに設定
        // stgPIN.initModality(Modality.APPLICATION_MODAL);
        stgPIN.initModality(Modality.WINDOW_MODAL);
        // オーナーを設定
        stgPIN.initOwner(primaryStage);
        btn = new Button();
        btn.setText("OK");
        tfPIN = new TextField();
        // 新しいウインドウ内に配置するコンテンツを生成           
        HBox hbox = new HBox();
        Label label = new Label("PINコード");
        label.setFont(new Font(20d));
        hbox.getChildren().add(label);
        hbox.getChildren().add(tfPIN);
        hbox.getChildren().add(btn);
        stgPIN.setScene(new Scene(hbox));
        //ここまで
        //こっからメインウィンドウ

        TableColumn UsernameCol = new TableColumn("Username");
        TableColumn TextCol = new TableColumn("Text");
        TableColumn TimeCol = new TableColumn("Time");
        TableColumn ViaCol = new TableColumn("Via");
        UsernameCol.setCellValueFactory(
                new PropertyValueFactory<Users, String>("Username"));
        TextCol.setCellValueFactory(
                new PropertyValueFactory<Users, String>("Text"));
        TimeCol.setCellValueFactory(
                new PropertyValueFactory<Users, String>("Time"));
        ViaCol.setCellValueFactory(
                new PropertyValueFactory<Users, String>("Via"));
        TextCol.setMinWidth(400);
        TimeCol.setMinWidth(60);
        UsernameCol.setPrefWidth(140);
        ViaCol.setPrefWidth(30);
        table.getColumns().addAll(UsernameCol, TextCol, TimeCol, ViaCol);
        table.setItems(data);
        table.getSelectionModel();
        //menubar
        Menu menu1 = new Menu("hoge");
        Menu menu2 = new Menu("piyo");
        MenuItem menuItem = new MenuItem("open");
        menuItem.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                System.out.println("Opening Database Connection...");
            }
        });
        menu1.getItems().add(menuItem);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu1, menu2);
        //Boxに登録
        VBox root = new VBox();
        root.getChildren().add(menuBar);
        root.getChildren().add(table);
        tfPost = new TextField();
        propertyBox.getChildren().add(lblName);
        propertyBox.getChildren().add(taText);
        root.getChildren().add(propertyBox);
        root.getChildren().add(tfPost);
        primaryStage.setScene(new Scene(root, 800, 600));
        //逆クリックの時のアレ
        ContextMenu cm = new ContextMenu();
        cm.setOnShowing(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent t) {
                System.out.println("Showing...");
            }
        });
        MenuItem it = new MenuItem("hoge");
        cm.getItems().add(it);
        table.setContextMenu(cm);
        primaryStage.show();
    }

    public static class Users {

        private final StringProperty Username;
        private final StringProperty Text;
        private final StringProperty Time;
        private final StringProperty Via;

        private Users(String fName, String lName, String time, String via) {
            this.Username = new SimpleStringProperty(fName);
            this.Text = new SimpleStringProperty(lName);
            this.Time = new SimpleStringProperty(time);
            this.Via = new SimpleStringProperty(via);
        }

        public String getUsername() {
            return Username.get();
        }

        public void setUsername(String fName) {
            Username.set(fName);
        }

        public String getText() {
            return Text.get();
        }

        public void setText(String fName) {
            Text.set(fName);
        }

        public String getTime() {
            return Time.get();
        }

        public void setTime(String fName) {
            Time.set(fName);
        }

        public void setVia(String fName) {
            Via.set(fName);
        }

        public String getVia() {
            return Via.get();
        }
    }

    public void addPost(String id, String post, Date time, String via) {

        StringBuilder tHtml = new StringBuilder();
        tHtml.append(via);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss");
        c++;
        System.out.println("Via:" + via);
        data.add(new Users(id, post, sdf.format(time), tHtml.toString()));
        //data.add(new Users(id, post, sdf.format(time), "hogehoge"));
    }

    public void setProperty(String userName, String text) {
        lblName.setText(table.getSelectionModel().getSelectedItem().getUsername());
        taText.setText(table.getSelectionModel().getSelectedItem().getText());
    }
}
