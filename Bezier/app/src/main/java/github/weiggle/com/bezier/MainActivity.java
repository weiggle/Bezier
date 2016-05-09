package github.weiggle.com.bezier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.weiggle.widget.BazierWidget;

public class MainActivity extends AppCompatActivity {

    private BazierWidget mWidget;
    private Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView(){
        mButton = (Button) findViewById(R.id.btn);
        mWidget = (BazierWidget) findViewById(R.id.widget);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWidget.addImageView();
            }
        });
    }
}
