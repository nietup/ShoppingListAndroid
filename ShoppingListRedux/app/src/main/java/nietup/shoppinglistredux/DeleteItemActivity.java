package nietup.shoppinglistredux;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

public class DeleteItemActivity extends Activity {

    public static final String FIREBASE_TAG = "item removal";
    private TextView twItemPreview;
    private String row = "ffff";
    private String document = "ddd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_item);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                row = extras.getString("ROW");
                document = extras.getString("DOCUMENT");
            }
        } else {
            row = (String) savedInstanceState.getSerializable("ROW");
            document = (String) savedInstanceState.getSerializable("DOCUMENT");
        }

        twItemPreview = (TextView) findViewById(R.id.textView_item_preview);
        twItemPreview.setText(row);
    }

    public void removeItem(View view) {
        FirebaseFirestore.getInstance().collection("sampleData").document(document)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(FIREBASE_TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(FIREBASE_TAG, "Error deleting document", e);
                    }
                });

        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}
