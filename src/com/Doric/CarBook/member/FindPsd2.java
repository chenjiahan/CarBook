package com.Doric.CarBook.member;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.Doric.CarBook.Constant;
import com.Doric.CarBook.R;
import com.Doric.CarBook.utility.JSONParser;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FindPsd2 extends Activity implements View.OnClickListener {

    //������������ر���
    private String url = Constant.BASE_URL + "/changePsd.php";  //��¼�����url,��ؼ���http://��https://
    private List<NameValuePair> changePsdParams;    //��¼ʱ���͸�������������
    private JSONObject changePsdInfo;       //�����������õ���json����

    private String name;

    //����ؼ�
    private Button btnSubmit, btnBack;
    private ProgressDialog progressDialog;   //�첽����ʱ��ʾ�Ľ�����
    private EditText edtPsd,edtPsd2;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_psd);

        //���ÿؼ�
        btnSubmit = (Button) findViewById(R.id.submit);
        btnBack = (Button) findViewById(R.id.back);
        edtPsd = (EditText)findViewById(R.id.username);
        edtPsd2 = (EditText)findViewById(R.id.email);

        //��Ӽ�����
        btnSubmit.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        //����Actionbar
        getActionBar().hide();

        //ȡ��������Activity��Intent����
        Intent intent =getIntent();

        //ȡ��Intent�и��ӵ�����
        if ( intent.getStringExtra("name") !=  null ) {
            name = intent.getStringExtra("name");
        }
    }

    public void onClick(View v) {
        int id = v.getId();

        //"����"��ť
        if (id == R.id.back) {
            FindPsd2.this.finish();
        }

        //"�ύ"��ť
        if( id == R.id.submit) {

            //��ȡ�û�������
            String psd = edtPsd.getText().toString();
            String psd2 = edtPsd2.getText().toString();

            //�ж��û����Ƿ�Ϊ��
            if (psd.equals("")) {
                Toast.makeText(FindPsd2.this, "������������", Toast.LENGTH_LONG).show();
            }
            //�ж������Ƿ�Ϊ��
            else if (psd2.equals("")) {
                Toast.makeText(FindPsd2.this, "����һ������������", Toast.LENGTH_LONG).show();
            }
            //�ж���������������Ƿ�һ��
            else if ( !psd.equals(psd2)) {
                Toast.makeText(FindPsd2.this, "������������벻ͬ������������", Toast.LENGTH_LONG).show();
            }
            else{
                //�����û���Ϣ��������
                changePsdParams = new ArrayList<NameValuePair>();
                changePsdParams.add(new BasicNameValuePair("tag", "findPsd"));
                changePsdParams.add(new BasicNameValuePair("username", name));
                changePsdParams.add(new BasicNameValuePair("psd", psd));

                //�첽�����ж��û��Ƿ��¼�ɹ�
                new changePsd().execute();
            }
        }
    }


    private class changePsd extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {
            super.onPreExecute();
            //����"������֤"��
            progressDialog = new ProgressDialog(FindPsd2.this);
            progressDialog.setMessage("�����޸�..");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        protected Void doInBackground(Void... params) {
            //���������������
            JSONParser jsonParser = new JSONParser();
            changePsdInfo = jsonParser.getJSONFromUrl(url, changePsdParams);
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            //�ж��յ���json�Ƿ�Ϊ��
            if (changePsdInfo != null) {
                try {
                    //��֤ʧ��
                    if (changePsdInfo.getString("success").equals("0")) {
                        Toast.makeText(FindPsd2.this, "�޸�ʧ�ܣ�����������", Toast.LENGTH_LONG).show();
                    }
                    //�˻���Ϣ��֤�ɹ�
                    else {
                        Toast.makeText(FindPsd2.this, "�޸ĳɹ���", Toast.LENGTH_LONG).show();
                        FindPsd2.this.finish();
                        Intent intent = new Intent(FindPsd2.this, Login.class);
                        startActivity(intent);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(FindPsd2.this, "�޸�ʧ�ܣ��������������Ƿ�����", Toast.LENGTH_LONG).show();
            }
        }
    }
}