package nietup.shoppinglistredux;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by nietup on 11/6/2017.
 */

public class TobuyCursorAdapter extends CursorAdapter {
    public TobuyCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_tobuy, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvItemName = (TextView) view.findViewById(R.id.tv_item_name);
        TextView tvItemQuantity = (TextView) view.findViewById(R.id.tv_item_quantity);
        TextView tvPrice = (TextView) view.findViewById(R.id.it_tv_price);

        String itemName = cursor.getString(cursor.getColumnIndex(DBHelper.TOBUY_COLUMN_NAME));
        int quantity = cursor.getInt(cursor.getColumnIndex(DBHelper.TOBUY_QUANTITY));
        int price = cursor.getInt(cursor.getColumnIndex(DBHelper.TOBUY_COLUMN_PRICE));

        tvItemName.setText(itemName);
        tvItemQuantity.setText(String.valueOf(quantity));
        tvPrice.setText("$" + String.valueOf(price));
    }
}
