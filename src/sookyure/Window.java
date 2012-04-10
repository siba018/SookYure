/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sookyure;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
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
    TableView<Users> table = new TableView<>();
    VBox propertyBox;
    WebView browser;
    WebEngine we;
    private Label lblName;
    private TextArea taText;
    MenuItem rp;
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
        TableColumn IdCol = new TableColumn("ID");
        UsernameCol.setCellValueFactory(
                new PropertyValueFactory<Users, String>("Username"));
        TextCol.setCellValueFactory(
                new PropertyValueFactory<Users, String>("Text"));
        TimeCol.setCellValueFactory(
                new PropertyValueFactory<Users, String>("Time"));
        ViaCol.setCellValueFactory(
                new PropertyValueFactory<Users, String>("Via"));
        IdCol.setCellValueFactory(
                new PropertyValueFactory<Users, String>("Id"));
        TextCol.setMinWidth(400);
        TimeCol.setMinWidth(60);
        UsernameCol.setPrefWidth(140);
        ViaCol.setPrefWidth(30);
        table.getColumns().addAll(UsernameCol, TextCol, TimeCol, ViaCol, IdCol);
        table.setItems(data);
        table.getSelectionModel();
        //上のメニューバー
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

        //逆クリックの時のアレ
        ContextMenu cm = new ContextMenu();

        cm.setOnShowing(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent t) {
                System.out.println("Showing...");
            }
        });
        rp = new MenuItem("reply");
        //ハンドラーは別に移動しました

        cm.getItems().add(rp);
        table.setContextMenu(cm);
        //謎のHTMLEditorを使ってみよう！（白目）
        browser = new WebView();
        browser.setPrefHeight(100);
        we = browser.getEngine();


        //Boxに登録
        VBox root = new VBox();
        root.getChildren().add(menuBar);
        root.getChildren().add(table);
        tfPost = new TextField();
        propertyBox.getChildren().add(lblName);
        //propertyBox.getChildren().add(taText);
        propertyBox.getChildren().add(browser);
        root.getChildren().add(propertyBox);
        //  root.getChildren().add(browesr);
        root.getChildren().add(tfPost);

        primaryStage.setScene(new Scene(root, 800, 600));

        primaryStage.show();
    }

    public static class Users {

        private final StringProperty Username;
        private final StringProperty Text;
        private final StringProperty Time;
        private final StringProperty Via;
        private final StringProperty Id;
        private long StatusId;

        private Users(String fName, String lName, String time, String via, String Id, Long StatusId) {
            this.Username = new SimpleStringProperty(fName);
            this.Text = new SimpleStringProperty(lName);
            this.Time = new SimpleStringProperty(time);
            this.Via = new SimpleStringProperty(via);
            this.Id = new SimpleStringProperty(Id);
            this.StatusId = StatusId;
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

        public String getId() {
            return Id.get();
        }

        public void setId(String fName) {
            Id.set(fName);
        }

        public long getStatusId() {
            return StatusId;
        }

        public void setStatusId(long fName) {
            this.StatusId = fName;
        }
    }

    public void addPost(String id, String post, Date time, String via, String Id,Long StatusId) {

        StringBuilder tHtml = new StringBuilder();
        tHtml.append(via);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss");
        c++;
        System.out.println("Via:" + via);
        data.add(new Users(id, post, sdf.format(time), tHtml.toString(), Id, StatusId));
    }

    public void setProperty(String userName, String text) {
        lblName.setText(table.getSelectionModel().getSelectedItem().getUsername());
        //taText.setText(table.getSelectionModel().getSelectedItem().getText());
        we.loadContent(convURLLink(table.getSelectionModel().getSelectedItem().getText()));
    }
    /**
     * URLを抽出するための正規表現パターン
     */
    public static final Pattern convURLLinkPtn =
            Pattern.compile("(http://|https://){1}[\\w\\.\\-/:\\#\\?\\=\\&\\;\\%\\~\\+]+",
            Pattern.CASE_INSENSITIVE);

    /**
     * 指定された文字列内のURLを、正規表現を使用し、 リンク（a href=...）に変換する。
     *
     * @param str 指定の文字列。
     * @return リンクに変換された文字列。
     */
    public static String convURLLink(String str) {
        Matcher matcher = convURLLinkPtn.matcher(str);
        return matcher.replaceAll("<a href=\"$0\">$0</a>");
    }
}
