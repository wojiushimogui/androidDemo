package wu.rang.hao.layout_define;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class textLayout extends LinearLayout {

	public textLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.text, this);
		Button buttonBack=(Button)findViewById(R.id.buttonIdBack);
		Button buttonEdit=(Button)findViewById(R.id.buttonEdit);
		buttonBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((Activity)getContext()).finish();
				
			}
		});
		buttonEdit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getContext(), "Äãµã»÷ÁË±à¼­°´Å¥", Toast.LENGTH_SHORT).show();
			}
		});
	}

}
