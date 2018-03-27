/**
 * Copyright © 2015 All rights reserved.
 *
 * @Title: BubbleLayout.java
 * @Prject: BubbleLayout
 * @Package: com.example.bubblelayout
 * @Description: TODO
 * @author: raot raotao.bj@cabletech.com.cn/719055805@qq.com
 * @date: 2015年3月2日 下午2:52:08
 * @version: V1.0
 */
package com.sky.beautiful.View.Random;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.sky.beautiful.R;

/**
 * @ClassName: BubbleLayout
 * @Description: TODO
 * @author: raot raotao.bj@cabletech.com.cn/719055805@qq.com
 * @date: 2015年3月2日 下午2:52:08
 */
public class BubbleLayout extends View {

    private List<Bubble> bubbles = new ArrayList<>();
    private Random random = new Random();
    private int width, height;
    private boolean starting = false;
    private boolean isPause = false;
    private int intint = 100;// 粒子大小

    public BubbleLayout(Context context) {
        super(context);
        this.setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }

    public BubbleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }

    public BubbleLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        isPause = false;
        width = getWidth();
        height = getHeight();
        if (!starting) {
            starting = true;
            new Thread() {
                public void run() {
                    while (true) {
                        if (isPause) {
                            continue;
                        }
                        try {
                            Thread.sleep(random.nextInt(10) * 500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Bubble bubble = new Bubble();
                        Bubble bubble1 = new Bubble();
                        Bubble bubble2 = new Bubble();
                        int radius = random.nextInt(intint);
                        while (radius == 0) {
                            radius = random.nextInt(intint);
                        }
                        float speedY = random.nextFloat() * 9;
                        while (speedY < 1) {
                            speedY = random.nextFloat() * 9;
                        }
                        int radius1 = random.nextInt(intint);
                        while (radius1 == 0) {
                            radius1 = random.nextInt(intint);
                        }
                        float speedY1 = random.nextFloat() * 9;
                        while (speedY1 < 1) {
                            speedY1 = random.nextFloat() * 9;
                        }
                        int radius2 = random.nextInt(intint);
                        while (radius2 == 0) {
                            radius2 = random.nextInt(intint);
                        }
                        float speedY2 = random.nextFloat() * 9;
                        while (speedY2 < 1) {
                            speedY2 = random.nextFloat() * 9;
                        }
                        bubble.setRadius(radius);
                        bubble.setSpeedY(speedY);
                        bubble.setX(width / 5);
                        bubble.setY(height);

                        bubble1.setRadius(radius1);
                        bubble1.setSpeedY(speedY1);
                        bubble1.setX(width / 2);
                        bubble1.setY(height);

                        bubble2.setRadius(radius2);
                        bubble2.setSpeedY(speedY2);
                        bubble2.setX(width * 2);
                        bubble2.setY(height);

                        float speedX = random.nextFloat() - 0.9f;
                        while (speedX == 0) {
                            speedX = random.nextFloat() - 0.9f;
                        }
                        float speedX1 = random.nextFloat() - 0.9f;
                        while (speedX1 == 0) {
                            speedX1 = random.nextFloat() - 0.9f;
                        }
                        float speedX2 = random.nextFloat() - 0.9f;
                        while (speedX2 == 0) {
                            speedX2 = random.nextFloat() - 0.9f;
                        }
                        bubble.setSpeedX(speedX * 3);
                        bubble1.setSpeedX(speedX1 * 3);
                        bubble2.setSpeedX(speedX2 * 3);
                        bubbles.add(bubble);
                        bubbles.add(bubble1);
                        bubbles.add(bubble2);
                    }
                }

                ;
            }.start();
        }
        Paint paint = new Paint();
        // 绘制渐变正方形
        Shader shader = new LinearGradient(0, 0, 0, height, new int[]{
                getResources().getColor(R.color.blue_bright),
                getResources().getColor(R.color.blue_bright),
                getResources().getColor(R.color.blue_bright)},
                null, Shader.TileMode.MIRROR);
        paint.setShader(shader);
        canvas.drawRect(0, 0, width, height, paint);
        paint.reset();
        paint.setColor(Color.rgb(255, 58, 148));
        paint.setAlpha(50);
        List<Bubble> list = new ArrayList<>(bubbles);
        for (Bubble bubble : list) {
            if (bubble.getY() - bubble.getSpeedY() <= 0) {
                bubbles.remove(bubble);
            } else {
                int i = bubbles.indexOf(bubble);
                if (bubble.getX() + bubble.getSpeedX() <= bubble.getRadius()) {
                    bubble.setX(bubble.getRadius());
                } else if (bubble.getX() + bubble.getSpeedX() >= width
                        - bubble.getRadius()) {
                    bubble.setX(width - bubble.getRadius());
                } else {
                    bubble.setX(bubble.getX() + bubble.getSpeedX());
                }
                bubble.setY(bubble.getY() - bubble.getSpeedY());
                bubbles.set(i, bubble);
                canvas.drawCircle(bubble.getX(), bubble.getY(),
                        bubble.getRadius(), paint);
            }
        }
        invalidate();
    }

    @Override
    public void invalidate() {
        super.invalidate();
        isPause = true;
    }

    private class Bubble {
        /**
         * 气泡半径
         */
        private int radius;
        /**
         * 上升速度
         */
        private float speedY;
        /**
         * 平移速度
         */
        private float speedX;
        /**
         * 气泡x坐标
         */
        private float x;
        /**
         * 气泡y坐标
         */
        private float y;

        /**
         * @return the radius
         */
        public int getRadius() {
            return radius;
        }

        /**
         * @param radius the radius to set
         */
        public void setRadius(int radius) {
            this.radius = radius;
        }

        /**
         * @return the x
         */
        public float getX() {
            return x;
        }

        /**
         * @param x the x to set
         */
        public void setX(float x) {
            this.x = x;
        }

        /**
         * @return the y
         */
        public float getY() {
            return y;
        }

        /**
         * @param y the y to set
         */
        public void setY(float y) {
            this.y = y;
        }

        /**
         * @return the speedY
         */
        public float getSpeedY() {
            return speedY;
        }

        /**
         * @param speedY the speedY to set
         */
        public void setSpeedY(float speedY) {
            this.speedY = speedY;
        }

        /**
         * @return the speedX
         */
        public float getSpeedX() {
            return speedX;
        }

        /**
         * @param speedX the speedX to set
         */
        public void setSpeedX(float speedX) {
            this.speedX = speedX;
        }

    }
}
