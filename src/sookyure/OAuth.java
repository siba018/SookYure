package sookyure;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class OAuth {

    boolean Auth_flg = true;
    private Twitter twitter = null;
    AccessToken accessToken;
    private RequestToken requestToken = null;
    Configuration conf;
    String[] consumer;

    public Configuration setOAuth() {
        twitter = new TwitterFactory().getInstance();

        Loader loader = new Loader();
        String[] loaderAccessToken = new String[2];
        consumer = new String[2];
        consumer = loader.consumer();
        loaderAccessToken = loader.accessToken();
        twitter.setOAuthConsumer(consumer[0], consumer[1]);

        if (loaderAccessToken[0] == null) {
            //アクセストークンがなかったら
          //  showPINWindow();
            conf = null;
        } else {
            //アクセストークンが既に登録されてたら
            accessToken = new AccessToken(loaderAccessToken[0], loaderAccessToken[1]);
            conf = setConfig(consumer, accessToken);
            twitter = setTwitter(conf);
            // setStream(conf);

        }
        return conf;
    }

    public void showOAuthWindow() {

        try {
            requestToken = twitter.getOAuthRequestToken();
        } catch (TwitterException e) {
            System.out.println("request token erroer");
            e.printStackTrace();
        }

        showBlouser(requestToken.getAuthorizationURL());
    }

    public Configuration getAcessToken(String pin) {

        accessToken = null;

        System.out.print("PINcode:");
  //      stgPIN.show();
        //PIN konohen


        try {
            if (pin.length() > 0) {
                accessToken = twitter.getOAuthAccessToken(requestToken, pin);
            } else {
                accessToken = twitter.getOAuthAccessToken();
            }
        } catch (TwitterException te) {
            if (401 == te.getStatusCode()) {
                System.out.println("Access token erroer");
            } else {
                te.printStackTrace();
            }
        }
        try {
            storeAccessToken(twitter.verifyCredentials().getId(), accessToken);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        System.out.println("end");

        return setConfig(consumer, accessToken);
    }

    public Twitter setTwitter(Configuration configuration) {
        TwitterFactory twitterFactory = new TwitterFactory(configuration);
        twitter = twitterFactory.getInstance();
        return twitter;
    }

    private Configuration setConfig(String consumer[], AccessToken accessToken) {

        ConfigurationBuilder cBuilder = new ConfigurationBuilder();
        Configuration configuration;
        cBuilder.setDebugEnabled(false);
        cBuilder.setOAuthConsumerKey(consumer[0]);
        cBuilder.setOAuthConsumerSecret(consumer[1]);
        cBuilder.setOAuthAccessToken(accessToken.getToken());
        cBuilder.setOAuthAccessTokenSecret(accessToken.getTokenSecret());
        cBuilder.setUserStreamRepliesAllEnabled(false);

        configuration = cBuilder.build();

        return configuration;
    }

    private void storeAccessToken(long l, AccessToken accessToken) {
        try {
            File file = new File("./Token/AccsesToken.dat");
            file.createNewFile();
            if (checkBeforeWritefile(file)) {
                FileWriter filewriter = new FileWriter(file, true);
                filewriter.write(accessToken.getToken() + "\n");
                filewriter.write(accessToken.getTokenSecret());
                filewriter.close();
            } else {
                System.out.println("write error");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private boolean checkBeforeWritefile(File file) {
        if (file.exists()) {
            if (file.isFile() && file.canWrite()) {
                return true;
            }
        }
        return false;
    }

    public void showBlouser(String URL) {
        Desktop desktop = Desktop.getDesktop();

        try {
            desktop.browse(new URI(URL));
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void updateStatus(String post) {
        try {
            twitter.updateStatus(post);
        } catch (TwitterException e) {
            // TODO 自動生成された catch ブロック
            System.out.println("post失敗");
            e.printStackTrace();
        }
    }
}
