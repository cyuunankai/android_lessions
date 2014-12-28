package com.example.anglepoint;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.ActionMode;
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
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;

import com.example.anglepoint.bean.Point;
import com.example.anglepoint.db.WildFishingDatabase;

public class TempPointArrayAdapter extends ArrayAdapter<Point>
 {

	  WildFishingDatabase db;
	  private final List<Point> list;
	  private final Activity context;
	  protected Object mActionMode;
	  
	public TempPointArrayAdapter(Activity context, List<Point> list) {
	    super(context, R.layout.activity_listview_each_item, list);
	    this.context = context;
	    this.list = list;
	    db = new WildFishingDatabase(context);
	  }
	
	public int getCount() 
    {
        // return the number of records in cursor
        return list.size();
    }

	  static class ViewHolder {
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
	      ll.setTag(list.get(position));
//	      ll.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				Point element = (Point) viewHolder.checkbox.getTag();
//				Intent intent = new Intent(context, PointDetailActivity.class);
//				intent.putExtra(PointDetailActivity.ROD_LENGTH_NAME, element.getRodLengthName());
//				intent.putExtra(PointDetailActivity.DEPTH, element.getDepth());
//				intent.putExtra(PointDetailActivity.LURE_METHOD_NAME, element.getLureMethodName());
//				intent.putExtra(PointDetailActivity.BAIT, element.getBaitName());
//				context.startActivity(intent);
//			}
//	      });
	      
	      ll.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View paramView) {

//				if (mActionMode != null) {
//		            return false;
//		        }
//
//		        // Start the CAB using the ActionMode.Callback defined above
//		        mActionMode = context.startActionMode(mActionModeCallback);
//		        paramView.setSelected(true);
				final Point element = (Point) viewHolder.checkbox
		                  .getTag();
				
				PopupMenu popup = new PopupMenu(context, paramView);
				popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {
					
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						String placeId = element.getId();
						switch (item.getItemId()) {
				        case R.id.edit:
				        	
				            return true;
				        case R.id.delete:
				        	Intent intent = new Intent(context, MainActivity.class);
				        	context.startActivity(intent);
				        	//context.notify();
				        	//db.deletePoint(placeId);
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
	    holder.textRodLength.setText(list.get(position).getRodLengthName() + "รื");
	    holder.textDepth.setText(list.get(position).getDepth() + "รื");
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
	  
	  

	  
	  private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

		    // Called when the action mode is created; startActionMode() was called
		    @Override
		    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		        // Inflate a menu resource providing context menu items
		        MenuInflater inflater = mode.getMenuInflater();
		        inflater.inflate(R.menu.select_point_context_menu, menu);
		        return true;
		    }

		    // Called each time the action mode is shown. Always called after onCreateActionMode, but
		    // may be called multiple times if the mode is invalidated.
		    @Override
		    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		        return false; // Return false if nothing is done
		    }

		    // Called when the user selects a contextual menu item
		    @Override
		    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		        switch (item.getItemId()) {
		            case R.id.edit:
		                
		                mode.finish(); // Action picked, so close the CAB
		                return true;
		            case R.id.delete:
		                
		                mode.finish(); // Action picked, so close the CAB
		                return true;
		            default:
		                return false;
		        }
		    }

		    // Called when the user exits the action mode
		    @Override
		    public void onDestroyActionMode(ActionMode mode) {
		        mActionMode = null;
		    }
		};


} 
