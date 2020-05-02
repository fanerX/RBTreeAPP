package myView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import java.util.List;
import androidx.annotation.Nullable;
import myClass.RBTree;

public class RBTreeView extends View {
    private List<RBTree.RBNode> nodeList;
    private List<RBTree.Side> sideList;
    private Paint red_paint = null;          //画笔
    private Paint black_paint = null;
    private TextPaint textPaint = null;
    private Paint side_paint = null;
    private int depth;
    private int weigth;
    private int height;




    public RBTreeView(Context context) {
        super(context);
        red_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        red_paint.setStrokeWidth(100);                         //设置默认比触的宽度为1像素
        red_paint.setAntiAlias(true);                        //使用抗锯齿功能
        red_paint.setStrokeCap(Paint.Cap.ROUND);
        red_paint.setColor(Color.RED);
        black_paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        black_paint.setStrokeWidth(100);                         //设置默认比触的宽度为1像素
        black_paint.setAntiAlias(true);                        //使用抗锯齿功能
        black_paint.setStrokeCap(Paint.Cap.ROUND);
        black_paint.setColor(Color.BLACK);
        textPaint = new TextPaint();
        textPaint.setTextSize(80);
        textPaint.setColor(Color.WHITE);
        side_paint = new Paint();
        side_paint.setStrokeWidth(10);
        side_paint.setStrokeCap(Paint.Cap.ROUND);
        side_paint.setColor(Color.RED);
        side_paint.setAntiAlias(true);
        side_paint.setTextAlign(Paint.Align.CENTER);
    }


    public RBTreeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        red_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        red_paint.setStrokeWidth(100);                         //设置默认比触的宽度为1像素
        red_paint.setAntiAlias(true);                        //使用抗锯齿功能
        red_paint.setStrokeCap(Paint.Cap.ROUND);
        red_paint.setColor(Color.RED);
        black_paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        black_paint.setStrokeWidth(100);                         //设置默认比触的宽度为1像素
        black_paint.setAntiAlias(true);                        //使用抗锯齿功能
        black_paint.setStrokeCap(Paint.Cap.ROUND);
        black_paint.setColor(Color.BLACK);
        textPaint = new TextPaint();
        textPaint.setTextSize(80);
        textPaint.setColor(Color.WHITE);
        side_paint = new Paint();
        side_paint.setStrokeWidth(10);
        side_paint.setStrokeCap(Paint.Cap.ROUND);
        side_paint.setColor(Color.RED);
        side_paint.setAntiAlias(true);
        side_paint.setTextAlign(Paint.Align.CENTER);
    }

    //比之前多出这个方法重写
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        if (weigth == MeasureSpec.UNSPECIFIED) {
//            setMeasuredDimension(widthSize*2, widthSize);
//        }
        setMeasuredDimension(heightSize*10, heightSize);
        this.weigth=this.getMeasuredWidth();
        this.height=this.getMeasuredHeight();
        Log.d("Test", "onMeasure: width--"+this.weigth);
        Log.d("Test", "onMeasure: height--"+this.height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("Test", "onDraw: width--"+this.weigth);
        Log.d("Test", "onDraw: height--"+this.height);

        super.onDraw(canvas);
        //float each_size = (float) (weigth/(Math.pow(2,depth-1)+1));
        float each_size =110;
        //float each_heigth=height/(depth * 2);
        float each_heigth=110;
        float middle=weigth/2;

//        if(each_size<100){
//            red_paint.setStrokeWidth(each_size-1);
//            black_paint.setStrokeWidth(each_size-1);
//        }else {
//            each_size=101;
//        }
        if(each_size*2>each_heigth){
            each_size=each_heigth/2;
        }else {
            each_heigth=each_size*2;
        }
        textPaint.setTextSize(each_size-10);
        RBTree.RBNode node,son;
        RBTree.Side side;
        String data;
        String data_t = "9";
        for(int i=0;i<sideList.size();i++){
            side=sideList.get(i);
            node=side.getParent();
            son=side.getSon();
            canvas.drawLine(middle + node.getSize() * each_size,node.getHeight() * each_heigth,middle + son.getSize() * each_size,son.getHeight() * each_heigth,side_paint);
        }
        for(int i=0;i<nodeList.size();i++){
            node=nodeList.get(i);
            if(node.getColor()==RBTree.RED){
                canvas.drawPoint(middle + node.getSize() * each_size,node.getHeight() * each_heigth, red_paint);
            }else {
                canvas.drawPoint(middle + node.getSize() * each_size,node.getHeight() * each_heigth, black_paint);
            }
            data=node.getData()+"";
            canvas.drawText(data,middle + node.getSize() * each_size-textPaint.measureText(data)/2,node.getHeight() * each_heigth+textPaint.measureText(data_t)/2, textPaint);

        }
    }



    public void setNodeList(List<RBTree.RBNode> nodeList) {
        this.nodeList = nodeList;
    }

    public void setSideList(List<RBTree.Side> sideList) {
        this.sideList = sideList;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setInitData(int depth,List<RBTree.RBNode> nodeList,List<RBTree.Side> sideList){
        this.nodeList = nodeList;
        this.sideList = sideList;
        this.depth = depth;
        int flag_weight =(int) (55*Math.pow(2,depth-1));
        if(flag_weight>this.weigth){
            this.weigth=flag_weight;

        }
        int flag_height = (int)(110*this.depth+1);
        if(flag_height>this.height){
            this.height=flag_height;
        }
        setMeasuredDimension(this.weigth, this.height);
        invalidate();
    }

}
