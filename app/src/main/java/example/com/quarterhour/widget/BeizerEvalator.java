package example.com.quarterhour.widget;

public class BeizerEvalator implements TypeEvaluator<PointF> {

    private PointF point1,point2;

    public BeizerEvalator(PointF point1,PointF point2){
        this.point1 = point1;
        this.point2 = point2;
    }

    //直接套用贝塞尔三阶公式
    @Override
    public PointF evaluate(float fraction, PointF point0, PointF point3) {

        PointF pointF = new PointF();

        pointF.x = point0.x*(1-fraction)*(1-fraction)*(1-fraction)
                + 3*point1.x*fraction*(1-fraction)*(1-fraction)
                + 3*point2.x*fraction*fraction*(1-fraction)
                + point3.x*fraction*fraction*fraction;

        pointF.y = point0.y*(1-fraction)*(1-fraction)*(1-fraction)
                + 3*point1.y*fraction*(1-fraction)*(1-fraction)
                + 3*point2.y*fraction*fraction*(1-fraction)
                + point3.y*fraction*fraction*fraction;

        return pointF;
    }
}