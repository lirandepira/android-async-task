package enseirb.fr.simpleasynctask;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button btnSlowWork;
    EditText txtMsg;
    Long startingMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtMsg = findViewById(R.id.EditText01);
        btnSlowWork = findViewById(R.id.Button01);
        this.btnSlowWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new VerySlowTask().execute();
            }
        });

    }

    public class VerySlowTask extends AsyncTask<String, Long, Void> {
        private final ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        protected void onPreExecute(){
            startingMillis = System.currentTimeMillis();
            txtMsg.setText("Start time: " + startingMillis);
            this.dialog.setMessage("Wait \nSome SLOW job is being done...");
            this.dialog.show();

        }

        protected Void doInBackground(final String... args){
            try{
                for (Long i = 0L; i<3L; i++){
                    Thread.sleep(2000);
                    publishProgress((Long)i);
                }
            } catch (InterruptedException e){
                Log.v("slow-job interrupted", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Long... value) {
            super.onProgressUpdate(value);

            txtMsg.append("\nworking" + value[0]);

        }

        protected void onPostExecute(final Void unused){
            if(this.dialog.isShowing()){
                this.dialog.dismiss();
            }

            txtMsg.append("\nEnd Time:");
            txtMsg.append("" + (System.currentTimeMillis()-startingMillis)/1000);
            txtMsg.append("\nDone!");
        }

    }

}
