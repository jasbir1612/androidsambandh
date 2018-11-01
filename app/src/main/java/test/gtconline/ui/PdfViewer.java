package test.gtconline.ui;

import android.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.util.List;

import test.gtconline.R;

public class PdfViewer extends AppCompatActivity implements OnPageChangeListener,OnLoadCompleteListener {

    WebView mwebView;
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String SAMPLE_FILE = "pfl_instr.pdf";
    PDFView pdfView;
    Integer pageNumber = 0;
    String pdfFileName;
    String pdf = "https://firebasestorage.googleapis.com/v0/b/sambandh-a8609.appspot.com/o/pdf%2FPFL_Instr.pdf?alt=media&token=42610766-218e-46ee-80e3-c3196e4f43a4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

//        mwebView = findViewById(R.id.webview_pdf);
//        mwebView.getSettings().setJavaScriptEnabled(true);
//        mwebView.loadUrl(pdf);
//        mwebView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);

        pdfView= (PDFView)findViewById(R.id.pdfView);
        displayFromAsset(SAMPLE_FILE);


    }

    private void displayFromAsset(String assetFileName) {
        pdfFileName = assetFileName;

        pdfView.fromAsset(SAMPLE_FILE)
                .defaultPage(pageNumber)
                .enableSwipe(true)

                .swipeHorizontal(false)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }
    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");
    }
}
