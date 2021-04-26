package ai.argrace.akeeta.react.rnpicker;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;

/**
 * Description :
 *
 * @author jianquan.pan@argrace.ai
 * @date 2020/9/2
 */
public class RNWheelView extends FrameLayout implements OnItemSelectedListener {
    WheelView mWheelView;
    public RNWheelView(ReactContext context) {
        super(context);
        mWheelView = new WheelView(context);
        mWheelView.setDividerColor(Color.BLACK);
        mWheelView.setItemsVisibleCount(7);
        mWheelView.setDividerType(WheelView.DividerType.FILL);
        mWheelView.setCyclic(false);
        mWheelView.setOnItemSelectedListener(this);
        mWheelView.setTextColorOut(Color.GRAY);
        mWheelView.setTextColorCenter(Color.BLACK);
        mWheelView.setDividerWidth(1);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(mWheelView, layoutParams);
    }

    public WheelView getWheelView() {
        return mWheelView;
    }

    @Override
    public void onItemSelected(int index) {
        WritableMap event = Arguments.createMap();
        event.putInt("position", index);
        ((ReactContext) getContext()).getJSModule(RCTEventEmitter.class).receiveEvent(
                getId(),
                "topChange",
                event);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d("onLayout", left + ", " + top + ", " + right + ", " + bottom);
        if (mWheelView != null) {
            ViewGroup.LayoutParams layoutParams = mWheelView.getLayoutParams();
            layoutParams.width = right - left;
            layoutParams.height = bottom - top;
            mWheelView.requestLayout();
        }
    }
}
