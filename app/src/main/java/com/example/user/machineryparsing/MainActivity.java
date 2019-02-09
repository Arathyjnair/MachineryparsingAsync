package com.example.user.machineryparsing;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.VideoView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.jar.Attributes;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
         AsyncHttpClient client;
         JSONObject jobj;
         JSONArray jary;
         LayoutInflater inflater;
         RequestParams params;
         ListView lst;
         ArrayList<String> mchneid;
         ArrayList<String> nme;
         ArrayList<String> type;
         ArrayList<String> des;
         ArrayList<String> usgpro;
         ArrayList<String> pze;
         ArrayList<String> vdo;
         ArrayList<String> imgg;
         VideoView VideoT;
         ImageView ImageT;
         TextView machineT,nameT,typeT,descrptnT,UsageproT,priceT;
         String url="http://sicsglobal.co.in/agroapp/Json/Machinerys.aspx";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client =new AsyncHttpClient();
        params=new RequestParams();
        lst=(ListView) findViewById(R.id.listvww);
        mchneid=new ArrayList<String>();
        nme=new ArrayList<String>();
        type=new ArrayList<String>();
        des=new ArrayList<String>();
        usgpro=new ArrayList<String>();
        pze=new ArrayList<String>();
        vdo=new ArrayList<String>();
        imgg=new ArrayList<String>();

        client.get(url,params,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                try {
                    Log.e("car", "inside");

                    jobj = new JSONObject(content);
                    Log.e("cards", content);
                    jary = jobj.getJSONArray("Machinerys");
                    for (int i = 0; i < jary.length(); i++)
                    {
                        JSONObject obj = jary.getJSONObject(i);
                        String machinep = obj.getString("MachineId");
                        mchneid.add("Machine id : " + machinep);
                        String namep = obj.getString("Name");
                        nme.add("Name  : " + namep);
                        String typp = obj.getString("Type");
                        type.add("Type : " + typp);
                        String desp = obj.getString("Description");
                        des.add("Description : " + desp);
                        String usgpropp = obj.getString("UsageProcedure");
                        usgpro.add("UsageProcedure : " + usgpropp);
                        String prcp = obj.getString("Price");
                        pze.add("Price : " + prcp);
                        String vdop = obj.getString("Video");
                        vdo.add("http://sicsglobal.co.in/agroapp/Admin/VideoFiles/"+vdop);
                        String imgp = obj.getString("Image");
                        imgg.add(imgp);
                    }

                        adapter adp = new adapter();
                        lst.setAdapter(adp);

                }

                catch (Exception e)
                {

                }
            }
        });

    }
    class adapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return mchneid.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.machinerylay, null);
            machineT = (TextView) convertView.findViewById(R.id.machineid);
            nameT = (TextView) convertView.findViewById(R.id.name);
            typeT = (TextView) convertView.findViewById(R.id.typ);
            descrptnT = (TextView) convertView.findViewById(R.id.desrptn);
            UsageproT = (TextView) convertView.findViewById(R.id.usgepro);
            priceT = (TextView) convertView.findViewById(R.id.prce);
            VideoT = (VideoView) convertView.findViewById(R.id.vido);
            ImageT = (ImageView) convertView.findViewById(R.id.img);

            machineT.setText(mchneid.get(position));
            nameT.setText(nme.get(position));
            typeT.setText(type.get(position));
            descrptnT.setText(des.get(position));
            UsageproT.setText(usgpro.get(position));
            priceT.setText(pze.get(position));
            VideoT.setVideoURI(Uri.parse(vdo.get(position)));
            VideoT.start();
            Picasso.with(MainActivity.this).load("http://sicsglobal.co.in/agroapp/Admin/VideoFiles/" + imgg.get(position))
                    .placeholder(R.mipmap.redcross)
                    .error(R.mipmap.redcross)
                    .into(ImageT);
            return convertView;

        }
    }
}


