package xyzz.shubhamsingh.flappybird;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class GameView extends View {


    Handler handler;
    Runnable runnable;
    final int UPDATE_MILLIS=30;
    Bitmap background;
    Display display;
    Point point;
    int dWidth,dHeight;

    Rect rect;
    Bitmap[] birds;
    Bitmap toptube,bottomtube;
    int numberOfTubes=4;
int[] tubeX=new int[numberOfTubes];

int[] topTubeY=new int[ numberOfTubes];


    int birdFrame=0;
    int velocity=0,gravity=3;
    int birdX,birdY;
    boolean gameState=false;
int gap=600;
int minTubeOffset,maxTubeOffset;
int tubeVelocity=15;





int distanceBetweenTubes;



Random random;







    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     */
    public GameView(Context context) {
        super(context);

        handler= new Handler();

        runnable= new Runnable() {
            /**
             * When an object implementing interface <code>Runnable</code> is used
             * to create a thread, starting the thread causes the object's
             * <code>run</code> method to be called in that separately executing
             * thread.
             * <p>
             * The general contract of the method <code>run</code> is that it may
             * take any action whatsoever.
             *
             * @see Thread#run()
             */
            @Override
            public void run() {
invalidate();
            }
        };
        background= BitmapFactory.decodeResource(getResources(),R.drawable.background);
        display =((Activity)getContext()).getWindowManager().getDefaultDisplay();
        point=new Point();
        display.getSize(point);
        toptube=BitmapFactory.decodeResource(getResources(),R.drawable.toptube);
        bottomtube=BitmapFactory.decodeResource(getResources(),R.drawable.bottomtube);
        dWidth=point.x;
        dHeight=point.y;
        rect=new Rect(0,0,dWidth,dHeight);
        birds=new Bitmap[2];
        birds[0]=BitmapFactory.decodeResource(getResources(),R.drawable.bird);
        birds[1]=BitmapFactory.decodeResource(getResources(),R.drawable.bird2);

        birdX=dWidth/2-birds[0].getWidth()/2;
        birdY=dHeight/2-birds[0].getHeight()/2;
        distanceBetweenTubes=dWidth*3/4;
        random=new Random();
        for(int i=0;i<numberOfTubes;i++){
            tubeX[i]=dWidth+i*distanceBetweenTubes;
            topTubeY[i]=minTubeOffset+random.nextInt(maxTubeOffset-minTubeOffset+1);

        }

        minTubeOffset=gap/2;
        maxTubeOffset=dHeight-minTubeOffset-gap;









    }





    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(background,null,rect,null);
if(birdFrame==0) {
    birdFrame = 1;
}else {
    birdFrame = 0;
}
if(gameState) {

    if (birdY < dHeight - birds[0].getHeight() || velocity < 0) {
        velocity += gravity;

        birdY += velocity;

    }
    for(int i=0;i<numberOfTubes;i++) {

        tubeX[i] -=tubeVelocity;

        if(tubeX[i]< -toptube.getWidth()){
            tubeX[i] +=numberOfTubes*distanceBetweenTubes;
            topTubeY[i]=minTubeOffset+random.nextInt(maxTubeOffset-minTubeOffset+1);
        }
        canvas.drawBitmap(toptube, tubeX[i], topTubeY[i] - toptube.getHeight(), null);
        canvas.drawBitmap(bottomtube, tubeX[i], topTubeY[i] + gap, null);
    }

}
        canvas.drawBitmap(birds[birdFrame],birdX,birdY,null);

handler.postDelayed(runnable,UPDATE_MILLIS);




    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {


        int action=event.getAction();
        if(action==MotionEvent.ACTION_DOWN){
            velocity =-30;
            gameState=true;




        }


        return true;



    }


}
