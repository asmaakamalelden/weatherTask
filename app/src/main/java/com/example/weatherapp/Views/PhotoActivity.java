package com.example.weatherapp.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.example.weatherapp.R;
import com.example.weatherapp.Views.ViewModels.WeatherViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoActivity extends AppCompatActivity {
    @BindView(R.id.sharebtn)
    FloatingActionButton sharebtn;
    @BindView(R.id.img)
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);

        initScreen();
    }

    public void initScreen() {
        Intent intent = getIntent();
        final String weatherTxt = intent.getStringExtra("WeatherTxtKey");
        if (weatherTxt.equals("fullsizephoto")) {
            final Uri imgUri = (Uri) intent.getParcelableExtra("key");
            img.setImageURI(imgUri);
            sharebtn.setVisibility(View.GONE);
        } else {
            final Bitmap bitmap = (Bitmap) intent.getParcelableExtra("key");
            sharebtn.setVisibility(View.VISIBLE);
            final WeatherViewModel viewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
            Bitmap updatedBitmap = drawMultilineTextToBitmap(getApplicationContext(), bitmap, weatherTxt);
            img.setImageBitmap(updatedBitmap);
            sharebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sharephoto(updatedBitmap);
                }
            });
        }
    }

    public Bitmap drawMultilineTextToBitmap(Context gContext, Bitmap bitmap, String gText) {
        // prepare canvas
        Resources resources = gContext.getResources();
        float scale = resources.getDisplayMetrics().density;
        // Bitmap bitmap = BitmapFactory.decodeResource(resources, gResId);
        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();
        // set default bitmap config if none
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);
        // new antialiased Paint
        TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(Color.WHITE);
        // text size in pixels
        paint.setTextSize((int) (3 * scale));
        // text shadow
        //paint.setShadowLayer(1f, 0f, 1f, Color.RED);
        // set text width to canvas width minus 16dp padding
        int textWidth = canvas.getWidth() - (int) (16 * scale);
        // init StaticLayout for text
        StaticLayout textLayout = new StaticLayout(
                gText, paint, textWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        // get height of multiline text
        int textHeight = textLayout.getHeight();
        // get position of text's top left corner
        float x = (bitmap.getWidth() - textWidth) / 2;
        float y = (bitmap.getHeight() - textHeight) / 2;
        // draw text to the Canvas center
        canvas.save();
        canvas.translate(x, y);
        textLayout.draw(canvas);
        canvas.restore();

        return bitmap;
    }

    public void sharephoto(Bitmap bitmap) {
        Uri uri = getBitmapFromView(bitmap);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Share Cover Image"));
    }

    private Uri getBitmapFromView(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File file = new File(directory, "albumImg" + System.currentTimeMillis() + ".jpg");

            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("url", bmpUri.toString());
        return bmpUri;
    }
}