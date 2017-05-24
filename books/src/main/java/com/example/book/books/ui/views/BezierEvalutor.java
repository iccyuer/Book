package com.example.book.books.ui.views;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

//我们用贝塞尔公式来定义即可

public class BezierEvalutor implements TypeEvaluator<PointF> {
    PointF p1;
    public BezierEvalutor(PointF p1) {
        super();
        this.p1 = p1;
    }
    @Override
    public PointF evaluate(float t, PointF p0, PointF p2) {
        //时间因子t: 0~1
        PointF point=new PointF();
        //实现贝塞尔公式:
        point.x=p0.x*(1-t)*(1-t)+2*t*(1-t)*p1.x+p2.x*t*t;//实时计算最新的点X坐标
        point.y=p0.y*(1-t)*(1-t)+2*t*(1-t)*p1.y+p2.y*t*t;//实时计算最新的点X坐标
        return point;//实时返回最新计算出的点对象
    }
}