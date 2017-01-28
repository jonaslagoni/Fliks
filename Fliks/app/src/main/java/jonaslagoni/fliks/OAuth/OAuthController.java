package jonaslagoni.fliks.OAuth;

import android.os.AsyncTask;
import android.view.View;
import android.webkit.WebView;

import com.googlecode.flickrjandroid.Flickr;
import com.googlecode.flickrjandroid.FlickrException;
import com.googlecode.flickrjandroid.oauth.OAuthToken;

import java.io.IOException;
import java.net.URL;

import jonaslagoni.fliks.R;

import static com.googlecode.flickrjandroid.auth.Permission.WRITE;

/**
 * Created by jonas on 27-01-2017.
 */

public class OAuthController extends AsyncTask<Void, Void, URL> {
    private WebView webView;
    private Flickr f = new Flickr("b665313ceeefd9095f0f6bb6fcbefa57", "9ff48e279a496c8d");
    private OAuthToken token = null;
    public OAuthController(View rootView){
        this.webView = (WebView)rootView.findViewById(R.id.OAuthWebview);
    }
    @Override
    protected URL doInBackground(Void... params) {
        String callBackUrl = "activity_browser_view.xml";
        URL url = null;
        try {
            token = f.getOAuthInterface().getRequestToken(callBackUrl);
            url = f.getOAuthInterface().buildAuthenticationUrl(WRITE, token);
            System.out.println(url);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FlickrException e) {
            e.printStackTrace();
        }
        return url;
    }

    @Override
    protected void onPostExecute(URL result) {
        webView.getSettings().setJavaScriptEnabled(true);
        WebClient webClient = new WebClient(f, token);
        webView.setWebViewClient(webClient);
        webView.loadUrl(result.toString());
    }


}