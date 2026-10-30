package in.obbattu.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.webkit.*;
import android.view.*;
import android.widget.*;
import android.graphics.Color;

public class MainActivity extends Activity {
    private static final String HOME = "https://obbattu.in/";
    private WebView web;
    private ValueCallback<Uri[]> uploadCallback;
    private static final int FILE_PICKER = 101;
    private LinearLayout layout;
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        layout = new LinearLayout(this); layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(Color.WHITE);
        LinearLayout bar = new LinearLayout(this); bar.setPadding(12,8,12,8); bar.setBackgroundColor(Color.rgb(255,232,104));
        TextView title = new TextView(this); title.setText("🍬 Obbattu  •  Made to Melt Hearts"); title.setTextSize(17); title.setTextColor(Color.rgb(32,94,48));
        title.setOnClickListener(v -> web.loadUrl(HOME)); bar.addView(title);
        layout.addView(bar);
        web = new WebView(this); layout.addView(web,new LinearLayout.LayoutParams(-1,0,1));
        setContentView(layout);
        WebSettings s = web.getSettings(); s.setJavaScriptEnabled(true); s.setDomStorageEnabled(true);
        s.setLoadsImagesAutomatically(true); s.setAllowFileAccess(false); s.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
        s.setJavaScriptCanOpenWindowsAutomatically(false); s.setSupportMultipleWindows(false);
        CookieManager.getInstance().setAcceptCookie(true);
        CookieManager.getInstance().setAcceptThirdPartyCookies(web,true);
        web.setWebViewClient(new WebViewClient() {
            @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest req) {
                Uri uri=req.getUrl(); String scheme=uri.getScheme();
                if (scheme==null) return true;
                if (scheme.equals("https") || scheme.equals("http")) {
                    String host=uri.getHost();
                    if (host!=null && (host.equals("obbattu.in") || host.endsWith(".obbattu.in"))) return false;
                }
                try { startActivity(new Intent(Intent.ACTION_VIEW,uri)); } catch(Exception e) { Toast.makeText(MainActivity.this,"No app available to open this link",Toast.LENGTH_SHORT).show(); }
                return true;
            }
            @Override public void onReceivedError(WebView v, WebResourceRequest r, WebResourceError e) {
                if (r.isForMainFrame()) {
                    String html="<html><meta name='viewport' content='width=device-width,initial-scale=1'><body style='font-family:sans-serif;text-align:center;padding:50px 20px;color:#225e30'><h2>Connection unavailable</h2><p>Check your internet connection and try again.</p><button style='padding:14px;background:#ffe868;border:0;border-radius:8px' onclick=\"location.href='https://obbattu.in/'\">Retry</button></body></html>";
                    v.loadDataWithBaseURL(HOME,html,"text/html","UTF-8",null);
                }
            }
        });
        web.setWebChromeClient(new WebChromeClient() {
            @Override public boolean onShowFileChooser(WebView w, ValueCallback<Uri[]> cb, FileChooserParams params) {
                if(uploadCallback!=null) uploadCallback.onReceiveValue(null);
                uploadCallback=cb;
                try { startActivityForResult(params.createIntent(),FILE_PICKER); return true; }
                catch(Exception e) { uploadCallback=null; return false; }
            }
        });
        web.setDownloadListener((url,ua,disposition,mime,length) -> {
            try { startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(url))); }
            catch(Exception e) { Toast.makeText(this,"Unable to open download",Toast.LENGTH_SHORT).show(); }
        });
        if(state!=null) web.restoreState(state); else web.loadUrl(HOME);
    }
    @Override protected void onActivityResult(int request,int result,Intent data) {
        super.onActivityResult(request,result,data);
        if(request==FILE_PICKER && uploadCallback!=null) {
            uploadCallback.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(result,data)); uploadCallback=null;
        }
    }
    @Override public void onBackPressed() { if(web.canGoBack()) web.goBack(); else super.onBackPressed(); }
    @Override protected void onSaveInstanceState(Bundle state) { web.saveState(state); super.onSaveInstanceState(state); }
    @Override protected void onDestroy() { web.destroy(); super.onDestroy(); }
}
