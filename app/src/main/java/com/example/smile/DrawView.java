package com.example.smile;
import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
public class DrawView extends SurfaceView implements SurfaceHolder.Callback {
    public DrawView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    private DrawThread drawThread;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // создание SurfaceView
        drawThread = new DrawThread(getContext(),getHolder());
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // изменение размеров SurfaceView
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // уничтожение SurfaceView
        drawThread.requestStop();
        boolean retry = true;
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
                //
            }
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        drawThread.setTowardPoint((int)event.getX(),(int)event.getY());
        return false;
    }
}
