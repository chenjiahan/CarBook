package com.Doric.CarBook.member;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.Doric.CarBook.Constant;
import com.Doric.CarBook.R;
import com.Doric.CarBook.utility.JSONParser;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

public class MyInformation extends Activity implements View.OnClickListener {

    //������������ر���
    private String sexURL = Constant.BASE_URL + "/sex.php";  //��¼�����url,��ؼ���http://��https://
    private List<NameValuePair> sexParams;    //��¼ʱ���͸�������������
    private JSONObject sexInfo;       //�����������õ���json����

    private String headURL = Constant.BASE_URL + "/sex.php";  //��¼�����url,��ؼ���http://��https://
    private List<NameValuePair> headParams;    //��¼ʱ���͸�������������
    private JSONObject headInfo;       //�����������õ���json����

    private String informationURL = Constant.BASE_URL + "/information.php";  //��¼�����url,��ؼ���http://��https://
    private List<NameValuePair> informationParams;    //��¼ʱ���͸�������������
    private JSONObject informationInfo;       //�����������õ���json����


    //����ؼ�
    private ProgressDialog progressDialog;   //�첽����ʱ��ʾ�Ľ�����
    private RelativeLayout loHead, loSex, loUsername;
    private Button btnLogOut, btnBack;
    private TextView tvUsername;

    //�������
    private String name = "����", sex = "��";
    private String[] sexes = new String[]{"��", "Ů"};

    //����ͷ�����ñ���
    private String whichHead = "0";
    private ImageView currentHead = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_information);

        //���ÿؼ�
        loHead = (RelativeLayout) findViewById(R.id.head_layout);
        loSex = (RelativeLayout) findViewById(R.id.sex_layout);
        loUsername = (RelativeLayout) findViewById(R.id.username_layout);
        btnLogOut = (Button) findViewById(R.id.button_log_out);
        btnBack = (Button) findViewById(R.id.back);
        tvUsername = (TextView) findViewById(R.id.username_text2);

        //��Ӽ�����
        loHead.setOnClickListener(this);
        loSex.setOnClickListener(this);
        loUsername.setOnClickListener(this);
        btnLogOut.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        //����Actionbar
        getActionBar().hide();

        //ȡ��������Activity��Intent����
        Intent intent = getIntent();

        //ȡ��Intent�и��ӵ�����
        if (intent.getStringExtra("name") != null) {
            name = intent.getStringExtra("name");
            tvUsername.setText(name);
        }

        //��ȡ�û���Ϣ
        informationParams = new ArrayList<NameValuePair>();
        informationParams.add(new BasicNameValuePair("tag", "information"));
        informationParams.add(new BasicNameValuePair("username", name));

        //�첽����
        new getInformation().execute();
    }

    //Ϊͼ����ӱ߿�
    public void handleImageView(ImageView imageView){
        currentHead.setImageDrawable(null);
        imageView.setImageResource(R.drawable.head_border);
        currentHead = imageView;
    }

    //����ͷ��
    public void setHead(String which) {
        ImageView imageHead = (ImageView) findViewById(R.id.head_image);
        switch ( Integer.parseInt(which) ){
            case 1:
                imageHead.setBackgroundResource(R.drawable.head1); break;
            case 2:
                imageHead.setBackgroundResource(R.drawable.head2); break;
            case 3:
                imageHead.setBackgroundResource(R.drawable.head3); break;
            case 4:
                imageHead.setBackgroundResource(R.drawable.head4); break;
            case 5:
                imageHead.setBackgroundResource(R.drawable.head5); break;
            case 6:
                imageHead.setBackgroundResource(R.drawable.head6); break;
            case 7:
                imageHead.setBackgroundResource(R.drawable.head7); break;
            case 8:
                imageHead.setBackgroundResource(R.drawable.head8); break;
            case 9:
                imageHead.setBackgroundResource(R.drawable.head9); break;
            default:
                imageHead.setBackgroundResource(R.drawable.pc_default_head); break;
        }
    }

    public void onClick(View v) {
        int id = v.getId();

        //"ͷ��"��ť
        if (id == R.id.head_layout) {
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.choose_head, (ViewGroup) findViewById(R.id.chooseHead));
            AlertDialog headBuilder =  new AlertDialog.Builder(this)
                    .setTitle("�Զ���ͷ��")
                    .setView(layout)
                    .setNegativeButton("ȡ��", null)
                    .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //�����û���Ϣ��������
                            headParams = new ArrayList<NameValuePair>();
                            headParams.add(new BasicNameValuePair("tag", "head"));
                            headParams.add(new BasicNameValuePair("username", name));
                            headParams.add(new BasicNameValuePair("head", whichHead));

                            //�첽����
                            new changeHead().execute();
                        }
                    })
                    .show();

            final ImageView imageHead1 = (ImageView) layout.findViewById(R.id.head1);
            final ImageView imageHead2 = (ImageView) layout.findViewById(R.id.head2);
            final ImageView imageHead3 = (ImageView) layout.findViewById(R.id.head3);
            final ImageView imageHead4 = (ImageView) layout.findViewById(R.id.head4);
            final ImageView imageHead5 = (ImageView) layout.findViewById(R.id.head5);
            final ImageView imageHead6 = (ImageView) layout.findViewById(R.id.head6);
            final ImageView imageHead7 = (ImageView) layout.findViewById(R.id.head7);
            final ImageView imageHead8 = (ImageView) layout.findViewById(R.id.head8);
            final ImageView imageHead9 = (ImageView) layout.findViewById(R.id.head9);

            //Ĭ�ϸ���һ��ͷ����ӱ߿�
            imageHead1.setImageResource(R.drawable.head_border);
            currentHead = imageHead1;

            //��Ӽ�����
            imageHead1.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) { whichHead = "1"; handleImageView(imageHead1);}
            });
            imageHead2.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) { whichHead = "2"; handleImageView(imageHead2);}
            });
            imageHead3.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) { whichHead = "3"; handleImageView(imageHead3);}
            });
            imageHead4.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) { whichHead = "4"; handleImageView(imageHead4);}
            });
            imageHead5.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) { whichHead = "5"; handleImageView(imageHead5);}
            });
            imageHead6.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) { whichHead = "6"; handleImageView(imageHead6);}
            });
            imageHead7.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) { whichHead = "7"; handleImageView(imageHead7);}
            });
            imageHead8.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) { whichHead = "8"; handleImageView(imageHead8);}
            });
            imageHead9.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) { whichHead = "9"; handleImageView(imageHead9);}
            });
        }

        //"�Ա�"��ť
        if (id == R.id.sex_layout) {
            new AlertDialog.Builder(MyInformation.this) // build AlertDialog
                    .setTitle("ѡ���Ա�") // title
                    .setItems(sexes, new DialogInterface.OnClickListener() { //content
                        public void onClick(DialogInterface dialog, int which) {
                            //�ж��Ƿ��޸����Ա�
                            if (!sexes[which].equals(sex)) {
                                //�����û���Ϣ��������
                                sexParams = new ArrayList<NameValuePair>();
                                sexParams.add(new BasicNameValuePair("tag", "sex"));
                                sexParams.add(new BasicNameValuePair("username", name));
                                sexParams.add(new BasicNameValuePair("sex", sex));

                                //�첽����
                                new changeSex().execute();
                            }
                        }
                    })
                    .show();
        }

        //"�˳���¼"��ť
        if (id == R.id.button_log_out) {
            logOutDialog();
        }

        //"����"��ť
        if (id == R.id.back) {
            MyInformation.this.finish();
        }
    }

    //�˳���¼�Ի���
    public void logOutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MyInformation.this);
        builder.setMessage("ȷ��Ҫ�˳���¼��");
        builder.setTitle("�˳���¼");
        builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                MyInformation.this.finish();
            }
        });
        builder.create().show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //��ȡ�û���Ϣ���첽����
    private class getInformation extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            super.onPreExecute();
            //����"�����޸�"��
            progressDialog = new ProgressDialog(MyInformation.this);
            progressDialog.setMessage("���ڻ�ȡ��Ϣ..");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        protected Void doInBackground(Void... params) {
            //���������������
            JSONParser jsonParser = new JSONParser();
            informationInfo = jsonParser.getJSONFromUrl(informationURL, informationParams);
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            //�ж��յ���json�Ƿ�Ϊ��
            if (informationInfo != null) {
                try {
                    if (informationInfo.getString("error").equals("0")) {

                        //��ȡ�Ա�
                        TextView tvSex = (TextView) findViewById(R.id.sex_text2);
                        tvSex.setText(informationInfo.getString("sex"));

                        //��ȡͷ��
                        whichHead = informationInfo.getString("head");
                        setHead(whichHead);
                    }
                    //��������
                    else {
                        Toast.makeText(MyInformation.this, "��ȡʧ��", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(MyInformation.this, "��ȡʧ�ܣ��������������Ƿ�����", Toast.LENGTH_LONG).show();
            }
        }
    }

    //�޸�ͷ��
    private class changeHead extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            super.onPreExecute();
            //����"�����޸�"��
            progressDialog = new ProgressDialog(MyInformation.this);
            progressDialog.setMessage("�����޸�..");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        protected Void doInBackground(Void... params) {
            //���������������
            JSONParser jsonParser = new JSONParser();
            headInfo = jsonParser.getJSONFromUrl(headURL, headParams);
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            //�ж��յ���json�Ƿ�Ϊ��
            if (headInfo != null) {
                try {
                    if (headInfo.getString("error").equals("0")) {
                        Toast.makeText(MyInformation.this, "ͷ���޸ĳɹ�", Toast.LENGTH_LONG).show();
                        setHead(whichHead);
                    }
                    //��������
                    else {
                        Toast.makeText(MyInformation.this, "�޸�ʧ��", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(MyInformation.this, "�޸�ʧ�ܣ��������������Ƿ�����", Toast.LENGTH_LONG).show();
            }
        }
    }

    //�޸��Ա�
    private class changeSex extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            super.onPreExecute();
            //����"�����޸�"��
            progressDialog = new ProgressDialog(MyInformation.this);
            progressDialog.setMessage("�����޸�..");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        protected Void doInBackground(Void... params) {
            //���������������
            JSONParser jsonParser = new JSONParser();
            sexInfo = jsonParser.getJSONFromUrl(sexURL, sexParams);
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            //�ж��յ���json�Ƿ�Ϊ��
            if (sexInfo != null) {
                try {
                    if (sexInfo.getString("error").equals("0")) {
                        if (sex.equals("��")) {
                            sex = "Ů";
                        } else {
                            sex = "��";
                        }
                        TextView tvSex = (TextView) findViewById(R.id.sex_text2);
                        tvSex.setText(sex);
                        Toast.makeText(MyInformation.this, "�Ա��޸ĳɹ�", Toast.LENGTH_LONG).show();
                    }
                    //��������
                    else {
                        Toast.makeText(MyInformation.this, "�޸�ʧ��", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(MyInformation.this, "�޸�ʧ�ܣ��������������Ƿ�����", Toast.LENGTH_LONG).show();
            }
        }
    }

}
