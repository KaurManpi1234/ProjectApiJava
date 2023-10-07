package com.example.projectapijava;



import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class AsteroidsView  extends View {
    private Paint strokeWhite;
    private Paint fillYellow;
    private Paint fillBlue;

    private Asteriods asteroids;
    private static final float EARTH_SUN_DISTANCE = 149597870;
    private int angle = 1;
    public AsteroidsView(Context context) {
        super(context);
        init();
    }

    private void init() {
        Resources resources = getResources();
        this.strokeWhite = new Paint();
        this.strokeWhite.setStyle(Paint.Style.STROKE);
        this.strokeWhite.setStrokeWidth(4f);
        this.strokeWhite.setColor(resources.getColor(R.color.orbit));

        this.fillYellow = new Paint();
        this.fillYellow.setStyle(Paint.Style.FILL);
        this.fillYellow.setColor(resources.getColor(R.color.sun));
        this.fillYellow.setColor(Color.YELLOW);

        this.fillBlue = new Paint();
        this.fillBlue.setStyle(Paint.Style.FILL);
        this.fillBlue.setColor(resources.getColor(R.color.earth));
        this.fillBlue.setColor(Color.BLUE);
    }

    public AsteroidsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AsteroidsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public AsteroidsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void setAsteroids(Asteriods asteroids) {
        this.asteroids = asteroids;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth();
        float height = getHeight();
        float radius = 100;
        canvas.translate(width/2, height/2);

        //sun
        canvas.drawCircle(0, 0, 70, fillYellow);

        //orbit
        canvas.drawCircle(0, 0, width/2-30, strokeWhite);

        canvas.rotate(angle*2, 0, 0);

        canvas.translate(width/2-30, 0);

        //Terre
        canvas.rotate(angle, 0, 0);

        canvas.drawCircle(0, 0, 40, fillBlue);

        if(asteroids != null){
            float distancePercent = Float.parseFloat(asteroids.getDistance())/EARTH_SUN_DISTANCE;

            canvas.drawCircle(0, 0, (width/2 -30)*distancePercent, strokeWhite);

            canvas.rotate(45, 0, 0);

        }

        postDelayed(new Runnable() {
            @Override
            public void run() {
                angle++;
                invalidate();
            }
        },20);
    }
    }

