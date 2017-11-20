package com.example.calculator;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public double pi=4*Math.atan(1);//定义π的数值大小以便计算sin cos
    TextView textView;//显示窗口
    Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button0;//数字键
    Button add, cut, rid, divide;//四则运算符号
    Button result;
    Button point;
    Button clear;//清屏
    Button sin, cos, square, sqrt;//正弦  余弦 平方  根号
    int pointCount = 0;
    int option = 0;//运算符状态
    int d = 0;//连加连减判断符号
    boolean flag = true;//标记程序是否出错
    double a = 0, b = 0;
    double sum = 0;//结果
    double decimal = 0;//判断输出的数是否有小数部分
    View.OnClickListener MainListener  = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView text = (TextView) findViewById(R.id.textView);
            String s = text.getText().toString();//获取文本框显示的字符串
            Button btn = (Button) v;
            //如果输入的是数字
            if (btn.getId() == R.id.btn0 || btn.getId() == R.id.btn1 || btn.getId() == R.id.btn2 || btn.getId() == R.id.btn3
                    || btn.getId() == R.id.btn4 || btn.getId() == R.id.btn5 || btn.getId() == R.id.btn6 ||
                    btn.getId() == R.id.btn7 || btn.getId() == R.id.btn8 || btn.getId() == R.id.btn9 ||
                    (btn.getId() == R.id.btnPoint && pointCount == 0)) {
                if (btn.getId() == R.id.btnPoint) {
                    if ( s.equals("")) {
                        s += "0" + btn.getText();
                    } else {
                        s += btn.getText();
                    }
                    pointCount = 1;
                } else {
                    s += btn.getText();
                }
                text.setText(s);

            }
            //如果输入的是运算符
            if (btn.getId() == R.id.sin || btn.getId() == R.id.cos || btn.getId() == R.id.square || btn.getId() == R.id. sqrt) {
                if ( s.equals("")) {
                    s = "0";
                }
                b = Double.valueOf(s);
                switch (btn.getId()) {
                    case R.id.sin:
                        sum = Math.sin((b/180)*pi);
                        break;
                    case R.id.cos:
                        sum = Math.sin((b/180)*pi);
                        break;
                    case R.id.square:
                        sum = b * b;
                        break;
                    case R.id. sqrt:
                        if (b < 0) {
                            Toast.makeText(MainActivity.this, "请输入大于等于0的数", Toast.LENGTH_LONG).show();
                            text.setText("");
                            break;
                        }
                        sum = Math.sqrt(b);
                    default:
                        break;
                }
                s = "" + sum;
                text.setText(s);
            }
            if (btn.getId() == R.id.add || btn.getId() == R.id.divide || btn.getId() == R.id.cut || btn.getId() == R.id.rid) {
                //如果已经有两个数，再按运算符就直接把结果运算出来保存到a中然后继续运算
                if ( s.equals("")) {
                    s = "0";
                }
                if (option != 0) {
                    b = Double.valueOf(s);
                    switch (option) {
                        case 1:
                            sum = a + b;
                            break;
                        case 2:
                            sum = a - b;
                            break;
                        case 3:
                            sum = a * b;
                            break;
                        case 4:
                            if (b == 0) {
                                Toast.makeText(MainActivity.this, "0不能为除数", Toast.LENGTH_LONG).show();
                                text.setText("");
                                break;
                            }
                            sum = a / b;
                            break;
                        default:
                            break;
                    }
                    a = sum;

                }
                if (option == 0) {
                    a = Double.valueOf(s);
                }
                switch (btn.getId()) {
                    case R.id.add:
                        option = 1;
                        break;
                    case R.id.cut:
                        option = 2;
                        break;
                    case R.id.rid:
                        option = 3;
                        break;
                    case R.id.divide:
                        option = 4;
                        break;
                    default:
                        break;
                }

                text.setText("");


            }

            if (btn.getId() == R.id.btnResult) {
                if ( s.equals("")) {
                    s = "0";
                }

                if(option == 0){
                    if(d == 1)
                        sum =a +b ;

                    if(d == 2)
                        sum =a -b ;
                    if(d == 3)
                        sum =a * b ;
                    if(d == 4)
                        sum =a /b ;

                }
                else {
                    b = Double.valueOf(s);
                    //连加
                    sum = a;
                    switch (option) {
                        case 1:
                            sum = sum + b;
                            d = 1;
                            break;
                        case 2:
                            sum = sum - b;
                            d = 2;
                            break;
                        case 3:
                            sum = sum * b;
                            d =3;
                            break;
                        case 4:
                            if (b == 0) {
                                Toast.makeText(MainActivity.this, "0不能当除数", Toast.LENGTH_LONG).show();
                                text.setText("");
                                flag = false;
                                break;
                            }
                            sum = sum / b;
                            d = 4;
                            break;
                        default:
                            break;
                    }
                }
                decimal = sum % 1;
                if ( decimal > 0) {
                    pointCount = 1;
                }
                s = "" + sum;
                if ( decimal == 0) {
                    int end = (s.toString()).lastIndexOf(".");
                    String str = (s.toString()).substring(0, end);
                    s = "" + Integer.parseInt(str);
                    pointCount = 0;
                }
                if (flag) {
                    text.setText(s);
                }
                a = Double.valueOf(s);
                option = 0;
                flag = true;

            }

            if (btn.getId() == R.id.clear) {//清除功能
                text.setText("");
                pointCount = 0;
                option = 0;
                flag = true;
            }
        }
    };
    //以下是右上角的菜单功能
    //可以切换计算器的功能
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.calculator:
                Toast.makeText(this, "已经在计算器！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.transform:
                Intent intent=new Intent(MainActivity.this,Transform1.class);
                startActivity(intent);
                break;
            case R.id.transform2:
                Intent intent2=new Intent(MainActivity.this,Transform2.class);
                startActivity(intent2);
                break;
            case R.id.exit:
                System.exit(0);
        }


        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //判断横竖屏
        if(this.getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
        {
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            textView = (TextView) findViewById(R.id.textView);
            button1 = (Button) findViewById(R.id.btn1);
            button2 = (Button) findViewById(R.id.btn2);
            button3 = (Button) findViewById(R.id.btn3);
            button4 = (Button) findViewById(R.id.btn4);
            button5 = (Button) findViewById(R.id.btn5);
            button6 = (Button) findViewById(R.id.btn6);
            button7 = (Button) findViewById(R.id.btn7);
            button8 = (Button) findViewById(R.id.btn8);
            button9 = (Button) findViewById(R.id.btn9);
            button0 = (Button) findViewById(R.id.btn0);
            add = (Button) findViewById(R.id.add);
            cut = (Button) findViewById(R.id.cut);
            rid = (Button) findViewById(R.id.rid);
            divide = (Button) findViewById(R.id.divide);
            result = (Button) findViewById(R.id.btnResult);
            point = (Button) findViewById(R.id.btnPoint);
            clear = (Button) findViewById(R.id.clear);
            sin = (Button) findViewById(R.id.sin);
            cos = (Button) findViewById(R.id.cos);
            square = (Button) findViewById(R.id.square);
            sqrt = (Button) findViewById(R.id.sqrt);
            button0.setOnClickListener(MainListener);
            button1.setOnClickListener(MainListener);
            button2.setOnClickListener(MainListener);
            button3.setOnClickListener(MainListener);
            button4.setOnClickListener(MainListener);
            button5.setOnClickListener(MainListener);
            button6.setOnClickListener(MainListener);
            button7.setOnClickListener(MainListener);
            button8.setOnClickListener(MainListener);
            button9.setOnClickListener(MainListener);
            add.setOnClickListener(MainListener);
            cut.setOnClickListener(MainListener);
            rid.setOnClickListener(MainListener);
            divide.setOnClickListener(MainListener);
            result.setOnClickListener(MainListener);
            point.setOnClickListener(MainListener);
            clear.setOnClickListener(MainListener);
            sin.setOnClickListener(MainListener);
            cos.setOnClickListener(MainListener);
            square.setOnClickListener(MainListener);
            sqrt.setOnClickListener(MainListener);
        }
        else if(this.getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE)
        {
            Intent intent=new Intent(MainActivity.this,Land_MainActivity.class);
            startActivity(intent);

        }

    }

}
