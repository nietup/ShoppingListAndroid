package nietup.shoppinglistredux;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class AddItemActivity extends SLRActivity {

    public static final String FIREBASE_TAG = "handling firebase";
    private EditText itemName;
    private EditText itemQuantity;
    private EditText itemPrice;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        itemName = (EditText) findViewById(R.id.item_name_input);
        itemQuantity = (EditText) findViewById(R.id.item_quantity_input);
        itemPrice = (EditText) findViewById(R.id.item_price_input);
        dbHelper = new DBHelper(getApplicationContext());
    }

    public void addItem(View v) {
        String name = itemName.getText().toString();
        int quantity = Integer.parseInt(itemQuantity.getText().toString());
        boolean bought = false;
        String category = "Other";
        String deadline = "2137-04-20";
        int price = Integer.parseInt(itemPrice.getText().toString());

        dbHelper.insertItem(name, quantity, bought, category, deadline, price);

        String msg =  name + " " + Integer.toString(quantity) + " inserted\nrecords in base: " + Integer.toString(dbHelper.numberOfRows());
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

        // Firebase part
        Map<String, Object> dataToSave = new HashMap<String, Object>();
        dataToSave.put("name", name);
        dataToSave.put("quantity", quantity);
        dataToSave.put("price", price);

        int index = dataToSave.hashCode();

        DocumentReference mDocRef = FirebaseFirestore.getInstance().document("sampleData/" + index);

        mDocRef.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(FIREBASE_TAG, "Document has been saved!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(FIREBASE_TAG, "Document has NOT been saved!", e);
            }
        });
    }

    public void backToList(View view) {
        startActivity(new Intent(this, ListActivity.class));
    }
}
