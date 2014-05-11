package com.Doric.CarBook.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.*;
import com.Doric.CarBook.R;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Collections;

public class ConditionSearch extends Activity {
    Spinner spinner;
    CheckBox carSize;
    Button search;
    Grade grade;
    ArrayAdapter<String> adapter;
    ArrayList<PriceGrade> priceGrades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.sea_condition_search);
        getActionBar().setTitle("��������");
        priceGrades =new ArrayList<PriceGrade>();
        grade = new Grade();
        spinner = (Spinner)findViewById(R.id.carPrice);
        search = (Button) findViewById(R.id.csearchbutton);
        createCarPriceGrades();
        String[] text  = new String[priceGrades.size()];
        for(int i=0;i<text.length;i++){
            text[i] =  priceGrades.get(i).text;
        }

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,text);
        //���������б�ķ��

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //��adapter ��ӵ�spinner��

        spinner.setAdapter(adapter);

        //����Ĭ��ֵ

        spinner.setVisibility(View.VISIBLE);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !grade.isChoose()) {
                    createGrade(grade);
                    String text =(String)spinner.getSelectedItem();
                    Double l=new Double(0.0);
                    Double h=new Double(0.0);
                    findPrice(text,l,h);
                    Intent it = new Intent();
                    Toast.makeText(ConditionSearch.this, text,Toast.LENGTH_LONG).show();
                    it.putExtra("pricelow",l);
                    it.putExtra("pricehig",h);
                    it.putExtra("grade",grade);
                    it.setClass(ConditionSearch.this, Result.class);
                    //ConditionSearch.this.startActivity(it);
                    //ConditionSearch.this.finish();
                    }
                }

        });


    }

    private boolean isInt(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {

            } else {
                return false;
            }
        }
        return true;
    }

    private void createCarPriceGrades()
    {
        PriceGrade p =new PriceGrade();
        p.text="5������";
        p.higPrice=50000.0;
        p.lowPrice=0.0;
        priceGrades.add(p);

        p=new PriceGrade();
        p.text="5��8��";
        p.higPrice=80000.0;
        p.lowPrice=50000.0;
        priceGrades.add(p);

        p=new PriceGrade();
        p.text="8������";
        p.higPrice=999999999.0;
        p.lowPrice=80000.0;
        priceGrades.add(p);
    }

    private void createGrade(Grade g){
        carSize  = (CheckBox)findViewById(R.id.checkbox00);
        CharSequence cs = carSize.getText();
        Boolean b= carSize.isChecked();
        g.setGradeMap(cs.toString(),b);

        carSize  = (CheckBox)findViewById(R.id.checkbox01);
        cs = carSize.getText();
        b= carSize.isChecked();
        g.setGradeMap(cs.toString(),b);

        carSize  = (CheckBox)findViewById(R.id.checkbox02);
        cs = carSize.getText();
        b= carSize.isChecked();
        g.setGradeMap(cs.toString(),b);

        carSize  = (CheckBox)findViewById(R.id.checkbox10);
        cs = carSize.getText();
        b= carSize.isChecked();
        g.setGradeMap(cs.toString(),b);

        carSize  = (CheckBox)findViewById(R.id.checkbox11);
        cs = carSize.getText();
        b= carSize.isChecked();
        g.setGradeMap(cs.toString(),b);

        carSize  = (CheckBox)findViewById(R.id.checkbox12);
        cs = carSize.getText();
        b= carSize.isChecked();
        g.setGradeMap(cs.toString(),b);

        carSize  = (CheckBox)findViewById(R.id.checkbox20);
        cs = carSize.getText();
        b= carSize.isChecked();
        g.setGradeMap(cs.toString(),b);

        carSize  = (CheckBox)findViewById(R.id.checkbox21);
        cs = carSize.getText();
        b= carSize.isChecked();
        g.setGradeMap(cs.toString(),b);

        carSize  = (CheckBox)findViewById(R.id.checkbox22);
        cs = carSize.getText();
        b= carSize.isChecked();
        g.setGradeMap(cs.toString(),b);

        carSize  = (CheckBox)findViewById(R.id.checkbox30);
        cs = carSize.getText();
        b= carSize.isChecked();
        g.setGradeMap(cs.toString(),b);

        carSize  = (CheckBox)findViewById(R.id.checkbox31);
        cs = carSize.getText();
        b= carSize.isChecked();
        g.setGradeMap(cs.toString(),b);

        carSize  = (CheckBox)findViewById(R.id.checkbox32);
        cs = carSize.getText();
        b= carSize.isChecked();
        g.setGradeMap(cs.toString(),b);

        carSize  = (CheckBox)findViewById(R.id.checkbox40);
        cs = carSize.getText();
        b= carSize.isChecked();
        g.setGradeMap(cs.toString(),b);

        carSize  = (CheckBox)findViewById(R.id.checkbox41);
        cs = carSize.getText();
        b= carSize.isChecked();
        g.setGradeMap(cs.toString(),b);

        carSize  = (CheckBox)findViewById(R.id.checkbox42);
        cs = carSize.getText();
        b= carSize.isChecked();
        g.setGradeMap(cs.toString(),b);

        carSize  = (CheckBox)findViewById(R.id.checkbox50);
        cs = carSize.getText();
        b= carSize.isChecked();
        g.setGradeMap(cs.toString(),b);

        carSize  = (CheckBox)findViewById(R.id.checkbox51);
        cs = carSize.getText();
        b= carSize.isChecked();
        g.setGradeMap(cs.toString(),b);

    }

    private void findPrice(String text, Double lowPrice,Double higPrice){
        for(int i=0;i<priceGrades.size();i++){
            PriceGrade p = priceGrades.get(i);
            if(p.text.equals(text)){
                lowPrice = p.lowPrice;
                higPrice = p.higPrice;
            }
        }
    }

}

class PriceGrade{
    public String text;
    public  Double lowPrice;
    public  Double higPrice;
}
