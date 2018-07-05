package com.example.daypop_up;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private ProgressDialog progress;

    private Calendar c;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progress.setProgress(msg.what);
            if(msg.what==100){
                progress.setMessage("下载完成");
                progress.dismiss();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        }
    public  void click(View view){
        switch (view.getId()){
            case R.id.btn1:/*弹出简单提示框*/
               showts();
                break;
            case R.id.btn2://弹出确认框
                showokDilong();
                break;
            case R.id.btn3://弹出单选框
              showsingDilog();
                break;
            case R.id.btn4://弹出多选框
                showMoreDilog();
                break;

            case R.id.btn5://普通列表框
                showItems();
                break;

        }
    }
    private void showts() {//弹出简单提示框
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setIcon(R.mipmap.ic_launcher);//设置标题图标
        dialog.setTitle("这是标题");//设置标题
        dialog.setMessage("这就是我的内容");
        dialog.show();//弹出弹框
    }
    private void showokDilong() {//确认框
        new AlertDialog.Builder(this).setIcon(R.mipmap.ic_launcher).setTitle("标题").setMessage("确认要删除吗？")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("取消",null).show();
    }
    private void showsingDilog() {//单选框
        final String[] arr={"吃","喝","玩","乐"};
        new AlertDialog.Builder(this).setIcon(R.mipmap.ic_launcher)
                .setTitle("标题")
                .setSingleChoiceItems(arr, 1, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,arr[which],Toast.LENGTH_SHORT).show();
                        dialog.dismiss();//关闭弹框
                    }
                }).show();
    }
    private void showMoreDilog() {//多选框
        final String[] arr2={"吃","喝","玩","乐"};
        final StringBuilder sb=new StringBuilder();
        new AlertDialog.Builder(this).setIcon(R.mipmap.ic_launcher)
                .setTitle("标题")
                .setMultiChoiceItems(arr2, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked){
                            sb.append(arr2[which]);
                        }else {
                            //找到当前选中项在sb中的起始位置
                            int start=sb.indexOf(arr2[which]);
                            int end=start+arr2[which].length();
                            sb.delete(start,end);
                        }
                    }
                }).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,sb.toString(),Toast.LENGTH_SHORT).show();
            }
        }).show();
    }
    private void showItems() {//普通列表框
        final String[] arr3={"裴浩宇","李忠厚","孔繁钧","敖尔琪楞"};
        //final StringBuilder sb=new StringBuilder();
        new AlertDialog.Builder(this).setIcon(R.mipmap.ic_launcher)
                .setTitle("标题")
                .setItems(arr3, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,arr3[which],Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }
    public void date(View view) {//日历

        Calendar calendar = Calendar.getInstance();//获取日历类
        DatePickerDialog datePickerDialog=new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Toast.makeText(MainActivity.this,year+"年"+(month+year)+"月"+dayOfMonth+"日",Toast.LENGTH_SHORT).show();
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void time(View view) {//时间
        Calendar calendar = Calendar.getInstance();//获取日历类
        TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Toast.makeText(MainActivity.this,hourOfDay+":"+minute,Toast.LENGTH_SHORT).show();
            }
        },calendar.get(Calendar.HOUR),calendar.get(Calendar.MINUTE),true);
        timePickerDialog.show();
    }

    public void per(View view) {
        progress =new ProgressDialog(this);
        progress.setTitle("shuiping");
        progress.setMessage("正在下载中...");
        progress.setProgressStyle(progress.STYLE_HORIZONTAL);
        progressshow();
    }

    private void progressshow() {
        progress.show();
        new Thread(){
            @Override
            public void run() {
                super.run();
                for (int i = 0; i <=100; i+=10) {

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(i);
                }
            }
        }.start();
    }
    }