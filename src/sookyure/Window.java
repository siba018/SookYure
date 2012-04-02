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
import javafx.stage.Stage;

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
    TableColumn TextCol;
    VBox propertyBox;
    private Label lblName;
    private TextArea taText;
    
    //
    int c=0;
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
        UsernameCol.setCellValueFactory(
                new PropertyValueFactory<Users, String>("Username"));

        //       TableColumn TextCol = new TableColumn("Text");
        TextCol = new TableColumn("Text");
        TextCol.setCellValueFactory(
                new PropertyValueFactory<Users, String>("Text"));
        TextCol.setMinWidth(400);
        TableColumn TimeCol = new TableColumn("Time");
        TimeCol.setCellValueFactory(
                new PropertyValueFactory<Users, String>("Time"));
        TimeCol.setMinWidth(60);
        //     UsernameCol.setMinWidth(140);
        UsernameCol.setPrefWidth(140);
        TimeCol.setCellValueFactory(
                new PropertyValueFactory<Users, String>("Time"));

        table.getColumns().addAll(UsernameCol, TextCol, TimeCol);
        table.setItems(data);
        table.getSelectionModel();


        //     data.add(new User("c", "b", "a"));
        VBox root = new VBox();
        root.getChildren().add(table);
        tfPost = new TextField();
        propertyBox.getChildren().add(lblName);
        propertyBox.getChildren().add(taText);
        root.getChildren().add(propertyBox);
        root.getChildren().add(tfPost);
        primaryStage.setScene(new Scene(root, 800, 600));

        primaryStage.show();
    }

    public static class Users {

        private final StringProperty Username;
        private final StringProperty Text;
        private final StringProperty Time;

        private Users(String fName, String lName, String Time) {
            this.Username = new SimpleStringProperty(fName);
            this.Text = new SimpleStringProperty(lName);
            this.Time = new SimpleStringProperty(Time);

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
    }

    public void addPost(String id, String post, Date time) {
       // System.out.println("Data add");
        //System.out.println(id + ":" + post + ":" + time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss");
        c++;
       // data.add(new Users(id, post, sdf.format(time)));
        System.out.println(c);
        // table.getColumns().set(2, TextCol)\;
        //  table.setFocusModel(data.size());
        //table.setItems(data);
    }

    public void setProperty(String userName, String text) {
        lblName.setText(table.getSelectionModel().getSelectedItem().getUsername());
        taText.setText(table.getSelectionModel().getSelectedItem().getText());
    }
}
