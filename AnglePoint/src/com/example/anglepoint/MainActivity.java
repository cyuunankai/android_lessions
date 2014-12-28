package com.example.anglepoint;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;

import com.example.anglepoint.bean.Point;
import com.example.anglepoint.db.WildFishingDatabase;

public class MainActivity extends ActionBarActivity {

	WildFishingDatabase db;
	PointArrayAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		db = new WildFishingDatabase(this);
		List<Point> list = db.getPointsForList("1");
				
		ListView listView = (ListView) findViewById(R.id.listViewPoint);
		adapter = new PointArrayAdapter(this, list);
		listView.setAdapter(adapter);
		

	}
	
	/**
	 * 添加按钮按下
	 * @param v
	 */
	public void selectPointAddBtnClick(View v){
		Intent intent = new Intent(this, AddPointActivity.class);
		startActivityForResult(intent, 1);

//		startActivity(intent);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
            	if(data.getExtras().containsKey("pointId")){
            		
            		
            		db = new WildFishingDatabase(this);
            		List<Point> list = db.getPointsForList("1");
            		
            		// 选中
            		String pointId = data.getExtras().getString("pointId");
            		for(int i=0; i<list.size(); i++){
            			Point p = list.get(i);
            			if(p.getId().equals(pointId)){
            				p.setSelected(true);
            			}
            		}
            				
            		ListView listView = (ListView) findViewById(R.id.listViewPoint);
            		adapter = new PointArrayAdapter(this, list);
            		listView.setAdapter(adapter);
            	}
            	
            }
        }
    }

	
	/**
	 * 选择按钮按下
	 * @param v
	 */
	public void selectPointSelectBtnClick(View v){
		adapter.getSelectedPointIdList();
	}
	
	
	public class PointArrayAdapter extends ArrayAdapter<Point>
	 {

		  private final List<Point> list;
		  private final Activity context;
		  protected Object mActionMode;
		  
		public PointArrayAdapter(Activity context, List<Point> list) {
		    super(context, R.layout.activity_listview_each_item, list);
		    this.context = context;
		    this.list = list;
		  }
		
		public int getCount() 
	    {
	        // return the number of records in cursor
	        return list.size();
	    }

		   class ViewHolder {
		    protected TextView textRodLength;
		    protected TextView textDepth;
		    protected TextView textLureMethod;
		    protected TextView textBait;
		    protected CheckBox checkbox;
		  }

		  @Override
		  public View getView(int position, View convertView, ViewGroup parent) {
		    View view = null;
		    if (convertView == null) {
		      LayoutInflater inflator = context.getLayoutInflater();
		      view = inflator.inflate(R.layout.activity_listview_each_item, null);
		      final ViewHolder viewHolder = new ViewHolder();
		      
		      viewHolder.textRodLength = (TextView) view.findViewById(R.id.textViewRodLength);
		      viewHolder.textDepth = (TextView) view.findViewById(R.id.textViewDepth);
		      viewHolder.textLureMethod = (TextView) view.findViewById(R.id.textViewLureMethod);
		      viewHolder.textBait = (TextView) view.findViewById(R.id.textViewBait);
		      viewHolder.checkbox = (CheckBox) view.findViewById(R.id.check);
		      viewHolder.checkbox
		          .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

		            @Override
		            public void onCheckedChanged(CompoundButton buttonView,
		                boolean isChecked) {
		              Point element = (Point) viewHolder.checkbox
		                  .getTag();
		              element.setSelected(buttonView.isChecked());
		            }
		          });
		      view.setTag(viewHolder);
		      viewHolder.checkbox.setTag(list.get(position));
		      
		      LinearLayout ll = (LinearLayout)view.findViewById(R.id.col1Layout);
		      ll.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Point element = (Point) viewHolder.checkbox.getTag();
					Intent intent = new Intent(context, PointDetailActivity.class);
					intent.putExtra(PointDetailActivity.ROD_LENGTH_NAME, element.getRodLengthName());
					intent.putExtra(PointDetailActivity.DEPTH, element.getDepth());
					intent.putExtra(PointDetailActivity.LURE_METHOD_NAME, element.getLureMethodName());
					intent.putExtra(PointDetailActivity.BAIT, element.getBaitName());
					context.startActivity(intent);
				}
		      });
		      
		      ll.setOnLongClickListener(new View.OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View paramView) {

					final Point element = (Point) viewHolder.checkbox
			                  .getTag();
					
					PopupMenu popup = new PopupMenu(context, paramView);
					popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {
						
						@Override
						public boolean onMenuItemClick(MenuItem item) {
							final String pointId = element.getId();
							switch (item.getItemId()) {
					        case R.id.edit:
					        	
					            return true;
					        case R.id.delete:
					        	
					        	//db.deletePoint(placeId);
					        	
					        	AlertDialog.Builder adb=new AlertDialog.Builder(context);
					            adb.setTitle("Delete?");
					            adb.setMessage("Are you sure you want to delete ");
					            adb.setNegativeButton("Cancel", null);
					            adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
					                public void onClick(DialogInterface dialog, int which) {
					                	int deleteIndex = 0;
							        	for(int i=0; i<list.size(); i++){
					                    	if(pointId.equals(list.get(i).getId())){
					                    		deleteIndex = i;
					                    		break;
					                    	}
					                    }
							        	list.remove(deleteIndex);
					                    adapter.notifyDataSetChanged();
					                }});
					            adb.show();
					            return true;
					        default:
					            return false;
					    }

						}
					});
					
				    MenuInflater inflater = popup.getMenuInflater();
				    inflater.inflate(R.menu.select_point_context_menu, popup.getMenu());
				    popup.show();

			        return true;

				}
			});
		      
		    } else {
		      view = convertView;
		      ((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
		    }
		    ViewHolder holder = (ViewHolder) view.getTag();
		    holder.textRodLength.setText(list.get(position).getRodLengthName() + "米");
		    holder.textDepth.setText(list.get(position).getDepth() + "米");
		    holder.textLureMethod.setText(list.get(position).getLureMethodName());
		    holder.textBait.setText(list.get(position).getBaitName());
		    holder.checkbox.setChecked(list.get(position).isSelected());
		    return view;
		  }
		  
		  public List<String> getSelectedPointIdList() {
			  List<String> retList = new ArrayList<String>();
			  for(Point p : list){
				  if(p.isSelected()){
					  retList.add(p.getId());
				  }
			  }
		      
			  return retList;
		  }

	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
