package example.com.quarterhour.widget;

public class PraiseView extends RelativeLayout {

    private int[] drawables;
    private Context mContext;
    //图片的宽高
    private int mDrawableHeight;
    private int mDrawableWidth;

    //随机数
    private Random mRandom;

    public PraiseView(Context context) {
        this(context,null);
    }

    public PraiseView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PraiseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.mContext = context;
        //创建随机数 在后面的贝塞尔取点钟会用到
        mRandom = new Random();
        //初始化心形图片
        drawables = new int[]{R.drawable.pl_blue,R.drawable.pl_red,R.drawable.pl_yellow};

        //获取心形图片的宽高
        Drawable drawable = ContextCompat.getDrawable(context,drawables[0]);
        mDrawableHeight = drawable.getIntrinsicHeight();
        mDrawableWidth = drawable.getIntrinsicWidth();
    }

    //在屏幕底部添加心形图片
    public void addDrawables(){
        final ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(drawables[mRandom.nextInt(drawables.length-1)]);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_PARENT_BOTTOM);
        params.addRule(CENTER_HORIZONTAL);
        imageView.setLayoutParams(params);
        addView(imageView);

        //创建并开启动画效果
        AnimatorSet animatorSet = getAnimator(imageView);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //在动画执行结束之后，移除该view
                removeView(imageView);
            }
        });
        animatorSet.start();
    }

    //创建动画

    /**
     * 刚开始做缩放和渐变动画  结束后开始做移动动画
     * @param imageView
     * @return
     */
    private AnimatorSet getAnimator(ImageView imageView){
        //缩放动画
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(imageView,"scaleX",0.3f,1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(imageView,"scaleY",0.3f,1f);
        //渐变动画
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(imageView,"alpha",0.3f,1f);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(scaleXAnimator,scaleYAnimator,alphaAnimator);
        set.setDuration(300);

        //创建平移动画并添加到set执行完成之后执行
        AnimatorSet wholeAnimator = new AnimatorSet();
        //按照顺序执行
        wholeAnimator.playSequentially(set,getBeizerAnimator(imageView));
        return wholeAnimator;
    }

    private ValueAnimator getBeizerAnimator(final ImageView imageView){
        //首先先确定需要用到的四个点
        //点0是在图片开始的中心点
        final PointF point0 = new PointF(getWidth()/2 - mDrawableWidth/2,getHeight() - mDrawableHeight);
        //点1 点2是贝塞尔曲线的控制点  需要控制的是，点2的高度要大于点1的高度
        PointF point1 = getPoint(1);
        PointF point2 = getPoint(2);
        PointF point3 = new PointF(mRandom.nextInt(getWidth()/2) - mDrawableWidth/2,0);
        BeizerEvalator beizerEvalator = new BeizerEvalator(point1,point2);
        ValueAnimator beizerAnimator = ObjectAnimator.ofObject(beizerEvalator,point0,point3);
        beizerAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        beizerAnimator.setDuration(4000);
        beizerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //在运行过程中会调用该方法
                PointF pointF = (PointF) animation.getAnimatedValue();
                imageView.setX(pointF.x);
                imageView.setY(pointF.y);

                //设置移动过程中的渐变
                //首先获取当前的fraction
                float fraction = animation.getAnimatedFraction();
                imageView.setAlpha(1-fraction);
            }
        });
        return beizerAnimator;
    }

    private PointF getPoint(int i){
        return new PointF(mRandom.nextInt(getWidth())- mDrawableWidth,mRandom.nextInt(getHeight()/2) + (i-1)*getHeight()/2);
    }
}