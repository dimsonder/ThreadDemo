package com.example.cherubim.thread_1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    private boolean flg = false;
    private boolean isRunning =false;//线程进行的标志
    private Button button1,button2;
    private TextView textView;
    private Handler handler;
    private int timer = 0;
    private ImageView imageView;
	//图片集合
    Bitmap bitmap;
    int items[]={
            R.drawable.archibald,R.drawable.m86,R.drawable.m82,R.drawable.m8profile,
            R.drawable.m80,R.drawable.m87,R.drawable.m84,R.drawable.m81,
            R.drawable.hotfield
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button2 = (Button) findViewById(R.id.btn2);
        textView = (TextView) findViewById(R.id.textView1);
        imageView=(ImageView)findViewById(R.id.imageView);
        button1=(Button)findViewById(R.id.btn1);
		//开启线程
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning=true;
                showImageByThread(imageView);
            }
        });
//结束线程
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning=false;
            }
        });


    }



    /* 使用Handler
    1.Handler
    Handler在android里负责发送和处理消息，通过它可以实现其他线程与Main线程之间的消息通讯。

    2。 Looper
    Looper负责管理线程的消息队列和消息循环

    3. Message
    Message是线程间通讯的消息载体。两个码头之间运输货物，Message充当集装箱的功能，里面可以存放任何你想要传递的消息。
    4. MessageQueue
     MessageQueue是消息队列，先进先出，它的作用是保存有待线程处理的消息。
      它们四者之间的关系是，在其他线程中调用Handler.sendMsg（）方法（参数是Message对象），将需要Main线程处理的事件
      添加到Main线程的MessageQueue中，Main线程通过MainLooper从消息队列中取出Handler发过来的这个消息时，
      会回调 Handler的handlerMessage（）方法。*/

    //显示图片的线程

    public void showImageByThread(final ImageView imageView) {

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bitmap bitmap = (Bitmap) msg.obj;
                imageView.setImageBitmap(bitmap);
                textView.setText("计数:"+Integer.toString(timer%items.length+1));

            }
        };
        new Thread() {
            @Override
            public void run() {
                try {
                    while (isRunning) {
                        Thread.currentThread().sleep(1000);
                        timer++;

                        Bitmap bitmap = getBitmapFromURl(timer%items.length);
                        Message message = Message.obtain();
                        message.obj = bitmap;
                        handler.sendMessage(message);
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    //取出图片
  public Bitmap getBitmapFromURl(int item){
      Bitmap temp=BitmapFactory.decodeResource(getResources(),items[item]);

      return temp;

  }
    }


