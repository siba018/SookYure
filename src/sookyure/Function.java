/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sookyure;

import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import twitter4j.*;
import twitter4j.conf.Configuration;

/**
 *
 * @author siba
 */
public class Function extends Window {

    Configuration conf;
    OAuth oAuth;
    TwitterStream twitterStream;
    long replyto = 0;

    Function(Stage stage) {
        super(stage);
        System.out.println("hoge");
        oAuth = new OAuth();
        btn.setOnAction(new EventHandler<ActionEvent>() {

            /*
             * ボタンのハンドラここやで
             */
            @Override
            public void handle(ActionEvent event) {
                setStream(oAuth.getAcessToken(tfPIN.getText()));
                stgPIN.close();
            }
        });

        tfPost.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (replyto == 0) {
                    oAuth.updateStatus(tfPost.getText());
                } else {
                    System.out.println("hogeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee" + replyto);
                    oAuth.updateStatus(tfPost.getText(), replyto);
                }
                tfPost.setText("");
                replyto = 0;
            }
        });
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                setProperty(table.getSelectionModel().getSelectedItem().getUsername(), table.getSelectionModel().getSelectedItem().getText());
                // System.out.println("hoge" +  table.getSelectionModel().getSelectedItem().getStatusId());
            }
        });
        table.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if ("DOWN".equals(event.getCode().toString())) {
                    System.out.println(table.getSelectionModel().getSelectedIndex());
                    int index = table.getSelectionModel().getSelectedIndex();
                    //   table.getFocusModel().focus(index);
                    setProperty(table.getSelectionModel().getSelectedItem().getUsername(), table.getSelectionModel().getSelectedItem().getText());
                }
            }
        });
        rp.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {

                tfPost.setText("@" + table.getSelectionModel().getSelectedItem().getId() + " ");
                replyto = table.getSelectionModel().getSelectedItem().getStatusId();
            }
        });
    }

    public void setTwitter() {
        conf = oAuth.setOAuth();
        if (conf != null) {
            setStream(conf);
        } else {
            oAuth.showOAuthWindow();
            stgPIN.show();
        }
    }
    // 3. UserStream 受信時に応答する（UserStreamListener）リスナーを実装する

    public void getQuake(String sook, Date time) {
        System.out.println(sook);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        if ((sook.indexOf("ゆれ") >= 0) || (sook.indexOf("揺れ") >= 0) || (sook.indexOf("ユレ") >= 0)) {
            ac.play();
            oAuth.updateStatus("スークが揺れを感知したよ！" + sdf.format(time));
            System.out.println("ゆれたのです");
        }
        if (sook.indexOf("新聞配達のバイク") >= 0) {
            oAuth.updateStatus("スークの家に新聞屋のバイクが来ました。寝ましょう" + sdf.format(time));
        }
    }

    public void stopStream() {
        twitterStream.shutdown();
    }

    public void setStream(Configuration c) {
        UserStreamAdapter mMyUserStreamAdapter;
        // 1. TwitterStreamFactory をインスタンス化する
        //   Configuration conf = oAuth.setOAuth();
        TwitterStreamFactory twitterStreamFactory = new TwitterStreamFactory(c);
        // 2. TwitterStream をインスタンス化する
        twitterStream = twitterStreamFactory.getInstance();
        //      mMyUserStreamAdapter.onFa
        mMyUserStreamAdapter = new UserStreamAdapter() {

            @Override
            public void onStatus(Status status) {

                addPost(status.getUser().getName(), status.getText(), status.getCreatedAt(),
                        status.getSource(), status.getUser().getScreenName(), status.getUser().getId());
                // if (status.getUser().getScreenName().equals("shirono77") || status.getUser().getScreenName().equals("y_sook")) 
                if (status.getUser().getScreenName().equals("y_sook") || status.getUser().getScreenName().equals("shirono77")) {
                    getQuake(status.getText(), status.getCreatedAt());
                }
                System.out.println(status.getUser().getScreenName());
                System.out.println("status update");

            }

            @Override
            public void onFavorite(User source, User target, Status favoritedStatus) {
                System.out.println(source.getId() + " favorited " + target.getName() + "'s Status. " + "StatusId: "
                        + favoritedStatus.getId());
                //addPost("hoge", "hoge", favoritedStatus.getCreatedAt());
            }

            @Override
            public void onException(Exception ex) {
                //  editorPane.setText(ex.getMessage() + snl + editorPane.getText() + snl);
            }
        };
        // ユーザーストリーム操作
        {
            // 4. TwitterStream に UserStreamListener を実装したインスタンスを設定する
            twitterStream.addListener(mMyUserStreamAdapter);
            // 5. TwitterStream#user() を呼び出し、ユーザーストリームを開始する
            twitterStream.user();
        }
    }
}