package com.example.listviewwithcustomadapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class SMSListAdapter  extends ArrayAdapter< MyMenuItem >
{
	private LayoutInflater mInflater ;

    int                    mResource ;
    List < MyMenuItem >    mData ;
    private Context mContext;
    
    public SMSListAdapter(Context context,int resource , int textViewResourceId , List < MyMenuItem > data ) 
    {
            super(context , resource , textViewResourceId , data);
            mContext=context;
            mData = data ;
            mResource = resource ;
            mInflater = ( LayoutInflater ) mContext.getSystemService ( Context.LAYOUT_INFLATER_SERVICE ) ;
           
    }
       
    public int getCount() 
    {
        // return the number of records in cursor
        return mData.size();
    }

    // getView method is called for each item of ListView
    public View getView(int position,  View convertView, ViewGroup parent) 
    {
    	ViewHolder holder = null ;
        if ( convertView == null ) {
            convertView = mInflater.inflate ( mResource , null ) ;
            holder = new ViewHolder() ;
            holder.icon = ( ImageView ) convertView.findViewById ( R.id.icon ) ;
            holder.text = ( TextView ) convertView.findViewById ( R.id.text ) ;
            holder.comment = ( TextView ) convertView.findViewById ( R.id.comment ) ;
            LinearLayout lin = ( LinearLayout ) convertView.findViewById ( R.id.linerList ) ;
            RadioButton rbtn = new RadioButton ( mContext );
            LayoutParams lparam = new LayoutParams ( LayoutParams.WRAP_CONTENT , LayoutParams.WRAP_CONTENT );
            rbtn.setSelected ( false );
            holder.check = rbtn;
            //radioGroup.addView ( rbtn );
            lin.addView ( rbtn , 0 );

            convertView.setTag ( holder ) ;
        } else {
            holder = ( ViewHolder ) convertView.getTag ( ) ;
        }

        holder.text.setText ( mData.get ( position ).getText ( ) ) ;
        holder.comment.setText ( mData.get ( position ).getComment ( ) ) ;
        holder.icon.setImageResource(android.R.drawable.arrow_down_float);
//        holder.icon.setImageResource ( getApplicationContext ( ).getResources ( ).getIdentifier ( mData.get ( position ).getIcon ( ) ,
//                "drawable" , getPackageName ( ) )

//        ) ;

        return convertView ;
    }


}
