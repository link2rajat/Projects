package edu.csulb.android.mortgagecalculator;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity {
    private EditText text;
    private SeekBar seekBar;
    private Double seek_value =0.0D;
    private TextView text_seekbar;
    String amount_borrowed_st;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (EditText) findViewById(R.id.editText1);
        seekBar =(SeekBar)findViewById(R.id.seekBar);
        text_seekbar = (TextView)findViewById(R.id.seek_bar_val);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                seek_value = (double)i/100;
                String temp1 = getResources().getString(R.string.seek_bar_value);
                String temp2 = null;
                if(temp1.contains(" ")){
                    temp2 = temp1.substring(0,temp1.indexOf(" "));
                }
                text_seekbar.setText(temp2+seek_value);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });}
        public void calculateMonthlyPayment(View v)
         {
             boolean f1 = true;
             boolean f2 = true;
             TextView result_field =(TextView)findViewById(R.id.textView2);
             CheckBox c1= (CheckBox)findViewById(R.id.checkBox);
              int radio_button_select;

             Double monthly_payment = 0.0D;;
             Double amount_borrowed =0.0D;
             int no_of_months =0;
             Double monthly_intrest_rate =0.0D;
             Double annual_intrest_rate =0.0D;
             Double monthly_tax_and_insurance =0.0D;

             amount_borrowed_st = text.getText().toString();

             if(amount_borrowed_st.matches(""))
             {

             text.setError(getResources().getString(R.string.error_message));
                 f1 = false;
                 result_field.setText("");
             }

             RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup1);
             RadioButton radioButton = (RadioButton)findViewById(R.id.radioButton3);

             radio_button_select = radioGroup.getCheckedRadioButtonId();

             if(radioGroup.getCheckedRadioButtonId()==-1)
             {
                 radioButton.setError("Required");
                 f2 = false;
                 result_field.setText("");
             }

             if(f1&&f2)
             {
                 radioButton.setError(null);

                 amount_borrowed = Double.parseDouble(text.getText().toString());

                 no_of_months = (Integer.valueOf(((RadioButton)findViewById(radio_button_select)).getText().toString()))*12;

                 if((seek_value.intValue())==0)
                 {

                     if(c1.isChecked())
                     {

                         monthly_tax_and_insurance =(amount_borrowed*(0.1/100));

                         if(radio_button_select == R.id.radioButton)
                         {
                           monthly_payment =((amount_borrowed/no_of_months)+monthly_tax_and_insurance);
                             result_field.setText(""+monthly_payment);

                         }

                         if(radio_button_select == R.id.radioButton2)
                         {
                             monthly_payment =((amount_borrowed/no_of_months)+monthly_tax_and_insurance);
                             result_field.setText(""+monthly_payment);

                         }

                         if(radio_button_select == R.id.radioButton3)
                         {
                             monthly_payment =((amount_borrowed/no_of_months)+monthly_tax_and_insurance);
                             result_field.setText(""+monthly_payment);

                         }
                     }

                     else
                     {
                         if(radio_button_select == R.id.radioButton)
                         {
                             monthly_payment =amount_borrowed/no_of_months;
                             result_field.setText(""+monthly_payment);
                         }
                         if(radio_button_select == R.id.radioButton2)
                         {
                             monthly_payment =amount_borrowed/no_of_months;
                             result_field.setText(""+monthly_payment);
                         }
                         if(radio_button_select == R.id.radioButton3)
                         {
                             monthly_payment =amount_borrowed/no_of_months;
                             result_field.setText(""+monthly_payment);
                         }

                     }

                 }
                else
                 {

                     monthly_intrest_rate = seek_value/1200;
                     if(c1.isChecked())
                     {
                         monthly_tax_and_insurance =(amount_borrowed*(0.1/100));

                         if(radio_button_select == R.id.radioButton)
                         {
                             monthly_payment =
                                     (((amount_borrowed)*((monthly_intrest_rate)/((1)-(Math.pow((1+monthly_intrest_rate),-(no_of_months))))))+(monthly_tax_and_insurance));

                             result_field.setText(""+monthly_payment);
                         }

                         if(radio_button_select == R.id.radioButton2)
                         {
                             monthly_payment =
                                     (((amount_borrowed)*((monthly_intrest_rate)/((1)-(Math.pow((1+monthly_intrest_rate),-(no_of_months))))))+(monthly_tax_and_insurance));

                             result_field.setText(""+monthly_payment);
                         }

                         if(radio_button_select == R.id.radioButton3)
                         {
                             monthly_payment =
                                     (((amount_borrowed)*((monthly_intrest_rate)/((1)-(Math.pow((1+monthly_intrest_rate),-(no_of_months))))))+(monthly_tax_and_insurance));

                             result_field.setText(""+monthly_payment);
                         }
                     }
                     else
                     {
                         if(radio_button_select == R.id.radioButton)
                         {
                             monthly_payment =
                                     ((amount_borrowed)*((monthly_intrest_rate)/((1)-(Math.pow((1+monthly_intrest_rate),-(no_of_months))))));

                             result_field.setText(""+monthly_payment);
                         }

                         if(radio_button_select == R.id.radioButton2)
                         {
                             monthly_payment =
                                     ((amount_borrowed)*((monthly_intrest_rate)/((1)-(Math.pow((1+monthly_intrest_rate),-(no_of_months))))));

                             result_field.setText(""+monthly_payment);
                         }
                         if(radio_button_select == R.id.radioButton3)
                         {
                             monthly_payment =
                                     ((amount_borrowed)*((monthly_intrest_rate)/((1)-(Math.pow((1+monthly_intrest_rate),-(no_of_months))))));

                             result_field.setText(""+monthly_payment);
                         }
                     }

                 }


             }

    }




}
