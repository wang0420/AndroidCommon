package com.example.module.tablayout.yangfan.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.module.tablayout.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static androidx.viewpager.widget.ViewPager.SCROLL_STATE_IDLE;

/**
 * Created by yangfan
 * nrainyseason@163.com
 */

public class ModifyTabLayout extends HorizontalScrollView {
    private OnTabLayoutItemSelectListener onTabLayoutItemSelectListener;
    private List<TextView> mViewsList;
    private int mTextColorUnSelect;
    private int mTextColorSelect;
    private int mTextBgUnSelectResId;
    private int mTextBgSelectResId;
    private List<CharSequence> mTabList;
    private LinearLayout layContent;
    private View bottomLine;
    private int bottomLineHeight, bottomLineWidth;// 底部 线  宽高
    private int bottomLineHeightBgResId = R.drawable.ttt;// 底部 线  背景

    private int mScrollViewWidth = 0, mScrollViewMiddle = 0, selectedTabPosition = -1, tabCount;
    private Handler mHandler = null;
    private int viewHeight, innerLeftMargin, innerRightMargin, itemInnerPaddingLeft, itemInnerPaddingRight;// item高度，item距左，item距右，item 内部左padding，item 内部右padding
    private float textSize = 14;
    private ViewPager mViewPager;
    private TabLayoutOnPageChangeListener mPageChangeListener;
    private boolean needEqual; // 是否等分
    //总宽
    private int mWidth;


    public ModifyTabLayout(Context context) {
        this(context, null);
    }


    public ModifyTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewHeight = dip2px(getContext(), 40);
        innerLeftMargin = dip2px(getContext(), 15);
        innerRightMargin = dip2px(getContext(), 15);
        mHandler = new StaticHandler(context, this);
        mViewsList = new ArrayList<>();
        mTextColorUnSelect = Color.BLACK;
        mTextColorSelect = Color.RED;

        setHorizontalScrollBarEnabled(false);
        setHorizontalFadingEdgeEnabled(false);

        FrameLayout layParent = new FrameLayout(context);
        addView(layParent);
        layContent = new LinearLayout(context);
        layParent.addView(layContent);
        bottomLine = new View(context);
        layParent.addView(bottomLine);

    }

    /* 根据手机的分辨率从 dp 的单位 转成为 px(像素) */
    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public int getViewHeight() {
        return viewHeight;
    }

    public void setViewHeight(int viewHeightPx) {
        this.viewHeight = viewHeightPx;
    }

    public void setInnerLeftMargin(int innerLeftMargin) {
        this.innerLeftMargin = innerLeftMargin;
    }

    public void setInnerRightMargin(int innerRightMargin) {
        this.innerRightMargin = innerRightMargin;
    }

    public void setmTextBgUnSelectResId(int mTextBgUnSelectResId) {
        this.mTextBgUnSelectResId = mTextBgUnSelectResId;
    }

    public void setmTextBgSelectResId(int mTextBgSelectResId) {
        this.mTextBgSelectResId = mTextBgSelectResId;
    }

    public void setmTextColorSelect(int mTextColorSelect) {
        this.mTextColorSelect = mTextColorSelect;
    }

    public void setmTextColorSelectId(int colorId) {
        this.mTextColorSelect = getResources().getColor(colorId);
    }

    public void setmTextColorUnSelect(int mTextColorUnSelect) {
        this.mTextColorUnSelect = mTextColorUnSelect;
    }

    public void setmTextColorUnSelectId(int colorId) {
        this.mTextColorUnSelect = getResources().getColor(colorId);
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public void setNeedEqual(boolean needEqual, int mWidth) {
        this.needEqual = needEqual;
        this.mWidth = mWidth;
    }

    public void setItemInnerPaddingLeft(int itemInnerPaddingLeft) {
        this.itemInnerPaddingLeft = itemInnerPaddingLeft;
    }

    public void setItemInnerPaddingRight(int itemInnerPaddingRight) {
        this.itemInnerPaddingRight = itemInnerPaddingRight;
    }

    public void setBottomLineHeight(int bottomLineHeight) {
        this.bottomLineHeight = bottomLineHeight;
    }

    public void setBottomLineWidth(int bottomLineWidth) {
        this.bottomLineWidth = bottomLineWidth;
    }

    public void setBottomLineHeightBgResId(int bottomLineHeightBgResId) {
        this.bottomLineHeightBgResId = bottomLineHeightBgResId;
    }

    public View getBottomLine() {
        return bottomLine;
    }

    private void setData(List<CharSequence> mTabList) {
        if (mTabList == null || mTabList.size() == 0) return;
        this.mTabList = mTabList;
        initView();
    }

    //不使用viewpager
    public void setTabData(List<CharSequence> mTabList, int defaultPos) {
        if (mTabList == null || mTabList.size() == 0) return;
        this.mTabList = mTabList;
        if (defaultPos >= 0 && defaultPos < mTabList.size()) {
            selectedTabPosition = defaultPos;
        } else {
            selectedTabPosition = 0;
        }
        initView();
        clickTabWithItem(selectedTabPosition);
    }

    private void initView() {
        if (mTabList == null || mTabList.size() == 0) return;
        mViewsList = new ArrayList<>();
        layContent.removeAllViews();

        for (int i = 0; i < mTabList.size(); i++) {
            TextView textView = new TextView(getContext());
            if (needEqual) {
                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setGravity(Gravity.CENTER);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                if (mWidth > 0)
                    layoutParams.width = mWidth / mTabList.size();
                linearLayout.addView(textView);
                layContent.addView(linearLayout, layoutParams);
            } else {
                layContent.addView(textView);
            }

            textView.setTextSize(textSize);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            if (i == selectedTabPosition) {
                textView.setBackgroundResource(mTextBgSelectResId);
                textView.setTextColor(mTextColorSelect);
            } else {
                textView.setBackgroundResource(mTextBgUnSelectResId);
                textView.setTextColor(mTextColorUnSelect);
            }
            textView.setTag(i);
            textView.setText(mTabList.get(i));
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = Integer.parseInt(view.getTag().toString());
                    if (mViewPager != null)
                        mViewPager.setCurrentItem(position);
                    else
                        clickTabWithItem(position);
                }
            });

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            if (!needEqual) {
                layoutParams.rightMargin = innerRightMargin;
                layoutParams.leftMargin = innerLeftMargin;
            }
            layoutParams.height = viewHeight;
            textView.setLayoutParams(layoutParams);

            textView.setPadding(itemInnerPaddingLeft, 0, itemInnerPaddingRight, 0);
            //  4.4 系统 设置 Background 后，padding失效,  故 设置 padding 在 设置 Background 后。
            mViewsList.add(textView);
        }
        initBottomLine();
        mHandler.sendEmptyMessageDelayed(0, 200);
    }

    private void initBottomLine() {
        bottomLine.setBackgroundResource(bottomLineHeightBgResId);
        LayoutParams fl = (LayoutParams) bottomLine.getLayoutParams();
        fl.width = bottomLineWidth;
        fl.height = bottomLineHeight;
        fl.gravity = Gravity.BOTTOM;
        bottomLine.setLayoutParams(fl);
    }

    private void clickTabWithItem(int position) {

        if (mViewsList == null) return;
        selectedTabPosition = position;
        if (null != onTabLayoutItemSelectListener)
            onTabLayoutItemSelectListener.onTabLayoutItemSelect(position);

        for (int i = 0; i < mViewsList.size(); i++) {
            TextView textView = mViewsList.get(i);
            if (Integer.parseInt(mViewsList.get(i).getTag().toString()) == position) {
                changeTextLocation(i);
                textView.setBackgroundResource(mTextBgSelectResId);
                textView.setTextColor(mTextColorSelect);
            } else {
                mViewsList.get(i).setBackgroundResource(mTextBgUnSelectResId);
                mViewsList.get(i).setTextColor(mTextColorUnSelect);
            }
            textView.setPadding(itemInnerPaddingLeft, 0, itemInnerPaddingRight, 0);//  4.4 系统 设置 Background 后，padding失效,重新设置 padding。
        }

    }

    public void setCurrentItem(int position) {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(position);
        } else
            clickTabWithItem(position);
    }

    public TextView getTextView(int position) {
        if (mViewsList == null || position >= mViewsList.size())
            throw new RuntimeException("mViewsList == null || position >= mViewsList.size()");
        return mViewsList.get(position);
    }

    public LinearLayout getLayContent() {
        return layContent;
    }

    /**
     * 改变栏目位置
     *
     * @param clickPosition
     */
    private void changeTextLocation(int clickPosition) {
        if (clickPosition >= 0 && clickPosition < mViewsList.size()) {
            changebottomLineLocation();
            int x = (mViewsList.get(clickPosition).getLeft() - getScrollViewMiddle() +
                    (getViewheight(mViewsList.get(clickPosition)) / 2));
            smoothScrollTo(x, 0);
        }
    }

    /**
     * 改变底部 线位置
     */
    private void changebottomLineLocation() {
        if (selectedTabPosition >= 0 && selectedTabPosition < mViewsList.size()) {
            TextView textView = getTextView(selectedTabPosition);
            int x;
            if (needEqual) {
                int[] position = new int[2];
                textView.getLocationOnScreen(position);
                int l1 = position[0];
                if (l1 == 0) {
                    int sWidth = mWidth / mViewsList.size();//tab宽度
                    int bWidth = sWidth / 2;//半个tab宽度
                    textView.measure(0, 0);
                    l1 = sWidth * selectedTabPosition + bWidth - textView.getMeasuredWidth() / 2;
                }
                x = l1 + (textView.getRight() - textView.getLeft() - bottomLineWidth) / 2;
            } else
                x = textView.getLeft() + (textView.getRight() - textView.getLeft() - bottomLineWidth) / 2;
            LayoutParams fl = (LayoutParams) bottomLine.getLayoutParams();
            fl.leftMargin = x;
            bottomLine.setLayoutParams(fl);
        }
    }

    /**
     * 返回scrollview的中间位置
     *
     * @return
     */
    private int getScrollViewMiddle() {
        if (mScrollViewMiddle == 0)
            mScrollViewMiddle = getScrollViewWidth() / 2;
        return mScrollViewMiddle;
    }

    /**
     * 返回ScrollView的宽度
     *
     * @return
     */
    private int getScrollViewWidth() {
        if (mScrollViewWidth == 0)
            mScrollViewWidth = getRight() - getLeft();
        return mScrollViewWidth;
    }

    /**
     * 返回view的宽度
     *
     * @param view
     * @return
     */
    private int getViewheight(View view) {
        return view.getBottom() - view.getTop();
    }

    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        if (viewPager != null) {
            mViewPager = viewPager;
            // Add our custom OnPageChangeListener to the ViewPager
            if (mPageChangeListener == null) {
                mPageChangeListener = new TabLayoutOnPageChangeListener(this);
            }
            mPageChangeListener.reset();
            viewPager.addOnPageChangeListener(mPageChangeListener);
            selectedTabPosition = viewPager.getCurrentItem();

            final PagerAdapter adapter = viewPager.getAdapter();
            if (adapter != null) {
                tabCount = adapter.getCount();
                List<CharSequence> tabList = new ArrayList<>();
                for (int i = 0; i < tabCount; i++) {
                    tabList.add(adapter.getPageTitle(i));
                }
                setData(tabList);
            } else {
                tabCount = 0;
            }
        }
    }

    public int getSelectedTabPosition() {
        return selectedTabPosition;
    }

    public int getTabCount() {
        return tabCount;
    }

    public void setOnTabLayoutItemSelectListener(OnTabLayoutItemSelectListener listener) {
        onTabLayoutItemSelectListener = listener;
    }

    public interface OnTabLayoutItemSelectListener {

        void onTabLayoutItemSelect(int position);//

    }

    private static class StaticHandler extends Handler {
        private final WeakReference<Context> mWeakContext;
        private final WeakReference<ModifyTabLayout> mParent;

        public StaticHandler(Context context, ModifyTabLayout view) {
            mWeakContext = new WeakReference<>(context);
            mParent = new WeakReference<>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            Context context = mWeakContext.get();
            ModifyTabLayout parent = mParent.get();
            if (null != context && null != parent) {
                switch (msg.what) {
                    case 0:
                        parent.changeTextLocation(parent.selectedTabPosition);
                        break;

                    default:
                        break;
                }

                super.handleMessage(msg);
            }
        }
    }

    public static class TabLayoutOnPageChangeListener implements ViewPager.OnPageChangeListener {
        private final WeakReference<ModifyTabLayout> mTabLayoutRef;
        private int mPreviousScrollState;
        private int mScrollState;

        public TabLayoutOnPageChangeListener(ModifyTabLayout tabLayout) {
            mTabLayoutRef = new WeakReference<>(tabLayout);
        }

        @Override
        public void onPageScrollStateChanged(final int state) {
            mPreviousScrollState = mScrollState;
            mScrollState = state;
        }

        @Override
        public void onPageScrolled(final int position, final float positionOffset,
                                   final int positionOffsetPixels) {
//            final ModifyTabLayout tabLayout = mTabLayoutRef.get();
//            if (tabLayout != null) {
//                // Only update the text selection if we're not settling, or we are settling after
//                // being dragged
//                final boolean updateText = mScrollState != SCROLL_STATE_SETTLING ||
//                        mPreviousScrollState == SCROLL_STATE_DRAGGING;
//                // Update the indicator if we're not settling after being idle. This is caused
//                // from a setCurrentItem() call and will be handled by an animation from
//                // onPageSelected() instead.
//                final boolean updateIndicator = !(mScrollState == SCROLL_STATE_SETTLING
//                        && mPreviousScrollState == SCROLL_STATE_IDLE);
//                tabLayout.setScrollPosition(position, positionOffset, updateText, updateIndicator);
//            }
        }

        @Override
        public void onPageSelected(final int position) {
            final ModifyTabLayout tabLayout = mTabLayoutRef.get();
            if (tabLayout != null && tabLayout.getSelectedTabPosition() != position
                    && position < tabLayout.getTabCount()) {
                // Select the tab, only updating the indicator if we're not being dragged/settled
                // (since onPageScrolled will handle that).
//                final boolean updateIndicator = mScrollState == SCROLL_STATE_IDLE
//                        || (mScrollState == SCROLL_STATE_SETTLING
//                        && mPreviousScrollState == SCROLL_STATE_IDLE);
//                tabLayout.selectTab(tabLayout.getTabAt(position), updateIndicator);
                tabLayout.clickTabWithItem(position);
            }
        }

        void reset() {
            mPreviousScrollState = mScrollState = SCROLL_STATE_IDLE;
        }
    }
}
