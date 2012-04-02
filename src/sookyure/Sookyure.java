/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sookyure;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author siba
 */
/*
 * トークンとかの保存をXMLにしたいです先生。いまここ。
 */
public class Sookyure extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    Function f;

    @Override
    public void start(Stage primaryStage) {
      //  f = new OAuth(primaryStage);
        f = new Function(primaryStage);
        primaryStage.setTitle("ｽｰｸﾕﾚﾒﾝﾅｰｰｰｰ");
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World5!");
            }
        });
        f.setTwitter();
        primaryStage.show();

    }
    @Override
    public void stop(){
        f.stopStream();
    }

}
