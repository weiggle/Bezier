package com.weiggle.util;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by wei.li on 2016/5/8.
 */
public class BezierEvaluator implements TypeEvaluator<PointF> {

    private PointF start;
    private PointF end;

    public BezierEvaluator(PointF start,PointF end){
        this.start = start;
        this.end = end;
    }

    @Override
    public PointF evaluate(float f, PointF control1, PointF control2) {

        PointF pointF = new PointF();
        pointF.x = start.x*(1-f)*(1-f)*(1-f)
                +3*control1.x*f*(1-f)*(1-f)
                +3*control2.x*f*f*(1-f)
                +end.x*f*f*f;
        pointF.y = start.y*(1-f)*(1-f)*(1-f)
                +3*control1.y*f*(1-f)*(1-f)
                +3*control2.y*f*f*(1-f)
                +end.y*f*f*f;

        return pointF;
    }
}
