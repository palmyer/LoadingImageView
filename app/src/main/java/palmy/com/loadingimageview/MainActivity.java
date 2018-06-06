package palmy.com.loadingimageview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    LoadingImageView mLoadingImageView;
    TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingImageView = findViewById(R.id.loading);
        mTextView = findViewById(R.id.textview);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, MainActivity.class));
                mLoadingImageView.setLoadingStatus(!mLoadingImageView.isLoading());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
