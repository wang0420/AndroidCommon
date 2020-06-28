package com.android.newcommon.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * 动画工具类
 */
public class AnimationUtils {

    /**
     * Don't let anyone instantiate this class.
     */
    private AnimationUtils() {
        throw new Error("Do not need instantiate!");
    }


    /**
     * 默认动画持续时间
     */
    public static final long DEFAULT_ANIMATION_DURATION = 400;




    /**
     * 获取一个旋转动画
     *
     * @param fromDegrees       开始角度
     * @param toDegrees         结束角度
     * @param pivotXType        旋转中心点X轴坐标相对类型
     * @param pivotXValue       旋转中心点X轴坐标
     * @param pivotYType        旋转中心点Y轴坐标相对类型
     * @param pivotYValue       旋转中心点Y轴坐标
     * @param durationMillis    持续时间
     * @param animationListener 动画监听器
     * @return 一个旋转动画
     */
    public static RotateAnimation getRotateAnimation(float fromDegrees, float toDegrees, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue, long durationMillis, Animation.AnimationListener animationListener) {
        RotateAnimation rotateAnimation = new RotateAnimation(fromDegrees,
                toDegrees, pivotXType, pivotXValue, pivotYType, pivotYValue);
        rotateAnimation.setDuration(durationMillis);
        if (animationListener != null) {
            rotateAnimation.setAnimationListener(animationListener);
        }
        return rotateAnimation;
    }


    /**
     * 获取一个根据视图自身中心点旋转的动画
     *
     * @param durationMillis    动画持续时间
     * @param animationListener 动画监听器
     * @return 一个根据中心点旋转的动画
     */
    public static RotateAnimation getRotateAnimationByCenter(long durationMillis, Animation.AnimationListener animationListener) {
        return getRotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, durationMillis,
                animationListener);
    }


    /**
     * 获取一个根据中心点旋转的动画
     *
     * @param duration 动画持续时间
     * @return 一个根据中心点旋转的动画
     */
    public static RotateAnimation getRotateAnimationByCenter(long duration) {
        return getRotateAnimationByCenter(duration, null);
    }


    /**
     * 获取一个根据视图自身中心点旋转的动画
     *
     * @param animationListener 动画监听器
     * @return 一个根据中心点旋转的动画
     */
    public static RotateAnimation getRotateAnimationByCenter(Animation.AnimationListener animationListener) {
        return getRotateAnimationByCenter(DEFAULT_ANIMATION_DURATION,
                animationListener);
    }


    /**
     * 获取一个根据中心点旋转的动画
     *
     * @return 一个根据中心点旋转的动画，默认持续时间为DEFAULT_ANIMATION_DURATION
     */
    public static RotateAnimation getRotateAnimationByCenter() {
        return getRotateAnimationByCenter(DEFAULT_ANIMATION_DURATION, null);
    }


    /**
     * 获取一个透明度渐变动画
     *
     * @param fromAlpha         开始时的透明度
     * @param toAlpha           结束时的透明度都
     * @param durationMillis    持续时间
     * @param animationListener 动画监听器
     * @return 一个透明度渐变动画
     */
    public static AlphaAnimation getAlphaAnimation(float fromAlpha, float toAlpha, long durationMillis, Animation.AnimationListener animationListener) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
        alphaAnimation.setDuration(durationMillis);
        if (animationListener != null) {
            alphaAnimation.setAnimationListener(animationListener);
        }
        return alphaAnimation;
    }


    /**
     * 获取一个透明度渐变动画
     *
     * @param fromAlpha      开始时的透明度
     * @param toAlpha        结束时的透明度都
     * @param durationMillis 持续时间
     * @return 一个透明度渐变动画
     */
    public static AlphaAnimation getAlphaAnimation(float fromAlpha, float toAlpha, long durationMillis) {
        return getAlphaAnimation(fromAlpha, toAlpha, durationMillis, null);
    }


    /**
     * 获取一个透明度渐变动画
     *
     * @param fromAlpha         开始时的透明度
     * @param toAlpha           结束时的透明度都
     * @param animationListener 动画监听器
     * @return 一个透明度渐变动画，默认持续时间为DEFAULT_ANIMATION_DURATION
     */
    public static AlphaAnimation getAlphaAnimation(float fromAlpha, float toAlpha, Animation.AnimationListener animationListener) {
        return getAlphaAnimation(fromAlpha, toAlpha, DEFAULT_ANIMATION_DURATION,
                animationListener);
    }


    /**
     * 获取一个透明度渐变动画
     *
     * @param fromAlpha 开始时的透明度
     * @param toAlpha   结束时的透明度都
     * @return 一个透明度渐变动画，默认持续时间为DEFAULT_ANIMATION_DURATION
     */
    public static AlphaAnimation getAlphaAnimation(float fromAlpha, float toAlpha) {
        return getAlphaAnimation(fromAlpha, toAlpha, DEFAULT_ANIMATION_DURATION,
                null);
    }


    /**
     * 获取一个由完全显示变为不可见的透明度渐变动画
     *
     * @param durationMillis    持续时间
     * @param animationListener 动画监听器
     * @return 一个由完全显示变为不可见的透明度渐变动画
     */
    public static AlphaAnimation getHiddenAlphaAnimation(long durationMillis, Animation.AnimationListener animationListener) {
        return getAlphaAnimation(1.0f, 0.0f, durationMillis, animationListener);
    }


    /**
     * 获取一个由完全显示变为不可见的透明度渐变动画
     *
     * @param durationMillis 持续时间
     * @return 一个由完全显示变为不可见的透明度渐变动画
     */
    public static AlphaAnimation getHiddenAlphaAnimation(long durationMillis) {
        return getHiddenAlphaAnimation(durationMillis, null);
    }


    /**
     * 获取一个由完全显示变为不可见的透明度渐变动画
     *
     * @param animationListener 动画监听器
     * @return 一个由完全显示变为不可见的透明度渐变动画，默认持续时间为DEFAULT_ANIMATION_DURATION
     */
    public static AlphaAnimation getHiddenAlphaAnimation(Animation.AnimationListener animationListener) {
        return getHiddenAlphaAnimation(DEFAULT_ANIMATION_DURATION,
                animationListener);
    }


    /**
     * 获取一个由完全显示变为不可见的透明度渐变动画
     *
     * @return 一个由完全显示变为不可见的透明度渐变动画，默认持续时间为DEFAULT_ANIMATION_DURATION
     */
    public static AlphaAnimation getHiddenAlphaAnimation() {
        return getHiddenAlphaAnimation(DEFAULT_ANIMATION_DURATION, null);
    }


    /**
     * 获取一个由不可见变为完全显示的透明度渐变动画
     *
     * @param durationMillis    持续时间
     * @param animationListener 动画监听器
     * @return 一个由不可见变为完全显示的透明度渐变动画
     */
    public static AlphaAnimation getShowAlphaAnimation(long durationMillis, Animation.AnimationListener animationListener) {
        return getAlphaAnimation(0.0f, 1.0f, durationMillis, animationListener);
    }


    /**
     * 获取一个由不可见变为完全显示的透明度渐变动画
     *
     * @param durationMillis 持续时间
     * @return 一个由不可见变为完全显示的透明度渐变动画
     */
    public static AlphaAnimation getShowAlphaAnimation(long durationMillis) {
        return getAlphaAnimation(0.0f, 1.0f, durationMillis, null);
    }


    /**
     * 获取一个由不可见变为完全显示的透明度渐变动画
     *
     * @param animationListener 动画监听器
     * @return 一个由不可见变为完全显示的透明度渐变动画，默认持续时间为DEFAULT_ANIMATION_DURATION
     */
    public static AlphaAnimation getShowAlphaAnimation(Animation.AnimationListener animationListener) {
        return getAlphaAnimation(0.0f, 1.0f, DEFAULT_ANIMATION_DURATION,
                animationListener);
    }


    /**
     * 获取一个由不可见变为完全显示的透明度渐变动画
     *
     * @return 一个由不可见变为完全显示的透明度渐变动画，默认持续时间为DEFAULT_ANIMATION_DURATION
     */
    public static AlphaAnimation getShowAlphaAnimation() {
        return getAlphaAnimation(0.0f, 1.0f, DEFAULT_ANIMATION_DURATION, null);
    }


    /**
     * 获取一个缩小动画
     *
     * @param durationMillis    时间
     * @param animationListener 监听
     * @return 一个缩小动画
     */
    public static ScaleAnimation getLessenScaleAnimation(long durationMillis, Animation.AnimationListener animationListener) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f,
                0.0f, ScaleAnimation.RELATIVE_TO_SELF,
                ScaleAnimation.RELATIVE_TO_SELF);
        scaleAnimation.setDuration(durationMillis);
        scaleAnimation.setAnimationListener(animationListener);

        return scaleAnimation;
    }


    /**
     * 获取一个缩小动画
     *
     * @param durationMillis 时间
     * @return 一个缩小动画
     */
    public static ScaleAnimation getLessenScaleAnimation(long durationMillis) {
        return getLessenScaleAnimation(durationMillis, null);

    }


    /**
     * 获取一个缩小动画
     *
     * @param animationListener 监听
     * @return 返回一个缩小的动画
     */
    public static ScaleAnimation getLessenScaleAnimation(Animation.AnimationListener animationListener) {
        return getLessenScaleAnimation(DEFAULT_ANIMATION_DURATION,
                animationListener);

    }


    /**
     * 获取一个放大动画
     *
     * @param durationMillis    时间
     * @param animationListener 监听
     * @return 返回一个放大的效果
     */
    public static ScaleAnimation getAmplificationAnimation(long durationMillis, Animation.AnimationListener animationListener) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f,
                1.0f, ScaleAnimation.RELATIVE_TO_SELF,
                ScaleAnimation.RELATIVE_TO_SELF);
        scaleAnimation.setDuration(durationMillis);
        scaleAnimation.setFillAfter(false);
        scaleAnimation.setAnimationListener(animationListener);
        return scaleAnimation;
    }


    /**
     * 获取一个放大动画
     *
     * @param durationMillis 时间
     * @return 返回一个放大的效果
     */
    public static ScaleAnimation getAmplificationAnimation(long durationMillis) {
        return getAmplificationAnimation(durationMillis, null);

    }


    /**
     * 获取一个放大动画
     *
     * @param animationListener 监听
     * @return 返回一个放大的效果
     */
    public static ScaleAnimation getAmplificationAnimation(Animation.AnimationListener animationListener) {
        return getAmplificationAnimation(DEFAULT_ANIMATION_DURATION,
                animationListener);

    }


    //动画旋转(旋转中心为view中心)
    public static void startRotateAnimation(final View v, long duration, int fromDegrees, int toDegrees) {
        final RotateAnimation rotateAnimation = new RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(duration);// 动画执行一次需要的时间
        rotateAnimation.setInterpolator(new LinearInterpolator()); // 设置插入器 匀速
        rotateAnimation.setRepeatCount(0);//重复次数  -1 无限次
        rotateAnimation.setFillAfter(true);    //动画执行完毕后是否停在结束时的角度上
        v.startAnimation(rotateAnimation);

    }

    /**
     * 折叠与展开属性动画
     * start 折叠前高度
     * end 折叠后高度
     * isVisible 动画结束后是否可见
     * 需要折叠的布局高度最好固定 不然测量会有问题
     */
    public static void foldAnimation(final View view, int start, int end, final boolean isVisible) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取动画过程中的渐变值
                int animatedValue = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = animatedValue;
                view.setLayoutParams(layoutParams);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isVisible) {
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.GONE);

                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(300);
        animator.start();
    }


    /**
     * 获取一个放大动画
     * repeatCount 动画重复次数
     */
    public static void getScaleAnimation(View v, long duration) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.05f, 1.0f,
                1.05f, ScaleAnimation.RELATIVE_TO_SELF,
                ScaleAnimation.RELATIVE_TO_SELF);
        scaleAnimation.setDuration(duration);
        scaleAnimation.setFillAfter(false);//动画结束之后是否保持动画的最终状态；true，表示保持动画的最终状态
        scaleAnimation.setRepeatCount(0);//动画重复的次数。指定动画重复播放的次数，如果你需要无限循环播放，请填写一个小于0的数值，一般写-1
        scaleAnimation.setRepeatMode(ObjectAnimator.RESTART);//设置动画的重复模式：反转REVERSE和重新开始RESTART
        scaleAnimation.setInterpolator(new CycleInterpolator(1));//cycles是周期值，默认为1，cycles=2表示动画会执行两次
        v.startAnimation(scaleAnimation);

    }


    public static void playAnimationOne(View v, long duration, int mDisplayWidth) {
        AnimatorSet animatorSetPeople = new AnimatorSet();
        animatorSetPeople.setDuration(15000);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(v, "translationX", 0, -300, 0);
        translationX.setRepeatCount(-1);//设置动画重复次数
        translationX.setRepeatMode(ObjectAnimator.RESTART);//动画重复模式
        ObjectAnimator translationY = ObjectAnimator.ofFloat(v, "translationY", 0, 180, 0);//平移到180后回到原位置
        translationY.setRepeatCount(-1);//设置动画重复次数
        translationY.setRepeatMode(ObjectAnimator.RESTART);//动画重复模式*/
        ObjectAnimator alpha = ObjectAnimator.ofFloat(v, "alpha", 1, 0, 1);
        alpha.setRepeatCount(-1);//设置动画重复次数
        alpha.setRepeatMode(ObjectAnimator.RESTART);//动画重复模式*/
        animatorSetPeople.playTogether(translationX, translationY, alpha);
        //   animatorSetPeople.setInterpolator(new BounceInterpolator());
        animatorSetPeople.start();


    }


    /*
     * view  当前需要动画的视图
     * durationMillis  动画时间
     * fromX   平移X轴的起始位置
     * toX   平移到 x轴的位置
     * fromY  平移y轴的起始位置
     * toY   平移到 y轴的位置
     * scaleToX   X轴放大倍数    1为原始状态不放大   此处放大1.2倍
     * scaleToY  Y轴放大倍数
     * */
    public static void dianZan(final View view, int durationMillis, float fromX, float toX, float fromY, float toY,
                               float scaleToX, float scaleToY) {
        //初始化 Translate动画
        TranslateAnimation translateAnimation = new TranslateAnimation(fromX, toX, fromY, toY);
        //初始化 Alpha动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
        //初始化scale动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, scaleToX, 1f, scaleToY, 50, 50);//  50  沿着中心放大
        //动画集
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(scaleAnimation);
        set.addAnimation(translateAnimation);
        set.addAnimation(alphaAnimation);
        //设置动画时间 (作用到每个动画)
        set.setDuration(durationMillis);
        view.startAnimation(set);//为控件设置动画


    }


    public static void playAnimationDaShang(View view) {
        AnimatorSet animatorSetPeople = new AnimatorSet();
        animatorSetPeople.setDuration(1000);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", -110, 110, 0);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1, 2, 1);//从原始状态放大2倍再回到原始状态
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1, 2, 1);

        translationX.setRepeatCount(-1);//设置动画重复次数
        translationX.setRepeatMode(ObjectAnimator.RESTART);//动画重复模式
        translationX.setStartDelay(1000);//动画延时执行
        translationX.setInterpolator(new AccelerateInterpolator());//Interpolator可以定义动画播放的速度
        /*after(Animator anim) 将现有动画插入到传入的动画之后执行
after(long delay) 将现有动画延迟指定毫秒后执行
before(Animator anim) 将现有动画插入到传入的动画之前执行
with(Animator anim) 将现有动画和传入的动画同时执行*/
        animatorSetPeople.play(translationX).before(scaleX).before(scaleY);
        // animatorSetPeople.playTogether(translationX, scaleX, scaleY);
        animatorSetPeople.start();


    }

    public static void OptionDaShang(View view) {
        AnimatorSet animatorSetPeople = new AnimatorSet();
        animatorSetPeople.setDuration(1000);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1, 1.15f, 1);//从原始状态放大 1.25倍再回到原始状态
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1, 1.15f, 1);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0.6f, 1, 1);
        alpha.setRepeatCount(1);//设置动画重复次数
        alpha.setRepeatMode(ObjectAnimator.RESTART);//动画重复模式
        scaleX.setRepeatCount(1);//设置动画重复次数
        scaleX.setRepeatMode(ObjectAnimator.RESTART);//动画重复模式
        scaleY.setRepeatCount(1);//设置动画重复次数
        scaleY.setRepeatMode(ObjectAnimator.RESTART);//动画重复模式
        animatorSetPeople.playTogether(scaleX, scaleY);
        animatorSetPeople.start();


    }


    //股价提醒折叠动画
    public static void ExcuteAnimation(final View view, int start, int end, final boolean isVisible) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取动画过程中的渐变值
                int animatedValue = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = animatedValue;
                view.setLayoutParams(layoutParams);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isVisible) {
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }



    //动画渐变
    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        v.startAnimation(alphaAnimation);
    }
}

