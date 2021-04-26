package ai.argrace.akeeta.react.rnpicker;

import android.graphics.Color;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ai.argrace.akeeta.react.rnpicker.adapter.ArrayWheelAdapter;

public class WheelPickerManager extends SimpleViewManager<RNWheelView> {
    WheelView wheelPicker;
    RNWheelView mRNWheelView;
    public static final String REACT_CLASS = "WheelPicker";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected RNWheelView createViewInstance(ThemedReactContext context) {
        mRNWheelView = new RNWheelView(context);
        mRNWheelView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        wheelPicker = mRNWheelView.getWheelView();

        return mRNWheelView;
    }

    @ReactProp(name = "data")
    public void setData(RNWheelView view, ReadableArray data) {
        if (wheelPicker!=null){
            List<String> emptyList = new ArrayList<>();
            try {
                List<Integer> dataInt = new ArrayList<>();
                for (int i = 0; i <data.size() ; i++) {
                    dataInt.add(data.getInt(i));
                }
                wheelPicker.setAdapter(new ArrayWheelAdapter<Integer>(dataInt));
            } catch (Exception e){
                try {
                    List<String> dataString = new ArrayList<>();
                    for (int i = 0; i <data.size() ; i++) {
                        dataString.add(data.getString(i));
                    }
//                    wheelPicker.setArrayList((ArrayList) dataString);
                    wheelPicker.setAdapter(new ArrayWheelAdapter<String>(dataString));
                } catch (Exception ex){
                    ex.printStackTrace();
                    wheelPicker.setAdapter(new ArrayWheelAdapter<String>(emptyList));
                }
            }
        }
    }

    @ReactProp(name = "isCyclic")
    public void setCyclic(RNWheelView view, Boolean isCyclic) {
        if (wheelPicker!=null){
            wheelPicker.setCyclic(isCyclic);
        }
    }

    @ReactProp(name = "selectedItemTextColor")
    public void setSelectedItemTextColor(RNWheelView view, String selectedItemTextColor) {
        if (wheelPicker!=null){
            wheelPicker.setTextColorCenter(convertColor(selectedItemTextColor));
        }
    }

    @ReactProp(name = "itemsVisibleCount")
    public void setItemsVisibleCount(RNWheelView view, int count) {
        if (wheelPicker!=null){
            wheelPicker.setItemsVisibleCount(count);
        }
    }

    @ReactProp(name = "selectedItemTextSize", defaultInt = 24)
    public void setSelectedItemTextSize(RNWheelView view, int itemTextSize) {
        if (wheelPicker!=null){
            wheelPicker.setTextSize(itemTextSize);
        }
    }

    @ReactProp(name = "selectedItemTextFontFamily")
    public void setSelectedItemFont(RNWheelView view, String itemTextFontFamily) {
        if (wheelPicker!=null){
//            Typeface typeface = ReactFontManager.getInstance().getTypeface(itemTextFontFamily, Typeface.NORMAL, wheelPicker.getContext().getAssets());
//            wheelPicker.setite(typeface);
        }
    }

    @ReactProp(name = "indicatorWidth")
    public void setIndicatorWidth(RNWheelView view, int indicatorSize) {
        if (wheelPicker!=null){
            wheelPicker.setDividerWidth(indicatorSize);
        }
    }

    @ReactProp(name = "hideIndicator")
    public void setIndicator(RNWheelView view, Boolean renderIndicator) {
        if (wheelPicker!=null){
            wheelPicker.setDividerWidth(renderIndicator ? 0 : 2);
        }
    }

    @ReactProp(name = "indicatorColor")
    public void setIndicatorColor(RNWheelView view, String indicatorColor) {
        if (wheelPicker!=null){
            wheelPicker.setDividerColor(convertColor(indicatorColor));
        }
    }

    @ReactProp(name = "itemTextColor")
    public void setItemTextColor(RNWheelView view, String itemTextColor) {
        if (wheelPicker!=null){
            wheelPicker.setTextColorOut(convertColor(itemTextColor));
        }
    }

    @ReactProp(name = "itemTextSize")
    public void setItemTextSize(RNWheelView view, int itemTextSize) {
        if (wheelPicker!=null){
            wheelPicker.setTextSize(itemTextSize);
        }
    }

    @ReactProp(name = "itemTextFontFamily")
    public void setItemFont(RNWheelView view, String itemTextFontFamily) {
      if (wheelPicker!=null){
//        Typeface typeface = ReactFontManager.getInstance().getTypeface(itemTextFontFamily, Typeface.NORMAL, wheelPicker.getContext().getAssets());
//        wheelPicker.setItemFont(typeface);
      }
    }

    @ReactProp(name = "initPosition")
    public void setInitialPosition(RNWheelView view, int selectedItemPosition) {
        if (wheelPicker!=null){
            wheelPicker.setCurrentItem(selectedItemPosition);
        }
    }

    @ReactProp(name = "backgroundColor")
    public void setBackgroundColor(RNWheelView view, String backgroundColor) {
        if (wheelPicker!=null){
            wheelPicker.setBackgroundColor(convertColor(backgroundColor));
        }
    }


    @ReactProp(name = "selectedItem")
    public void setSelectedItem(RNWheelView view, int pos) {
        if (wheelPicker!=null){
            wheelPicker.setCurrentItem(pos);
        }
    }

    @ReactProp(name = "itemOffsetX")
    public void setPaddingLeft(RNWheelView view, int paddingLeft) {
        if (wheelPicker!=null){
            paddingLeft = (int) (view.getResources().getDisplayMetrics().density * paddingLeft);
            wheelPicker.setTextXOffset(paddingLeft);
        }
    }


//    @Override
//    public void onItemSelect(WheelView picker, int item) {
//        if (wheelPicker != null){
//            WritableMap event = Arguments.createMap();
//            event.putInt("position", item);
//            ((ReactContext) wheelPicker.getContext()).getJSModule(RCTEventEmitter.class).receiveEvent(
//                    picker.getId(),
//                    "topChange",
//                    event);
//        }
//    }

    private int convertColor(String color){
        if (!color.startsWith("rgb")) {
            return Color.parseColor(color);
        } else  {
            String[] colors = color.substring(color.indexOf("(") + 1, color.length() - 1 ).split(",");
            int red = Integer.parseInt(colors[0].trim());
            int green = Integer.parseInt(colors[1].trim());
            int blue = Integer.parseInt(colors[2].trim());
            double opacity = 1;
            if (colors.length > 3){
                opacity = Double.parseDouble(colors[3].trim());
            }
            int alpha = (int)(opacity * 255.0f);

            return Color.argb(alpha,red,green,blue);
        }
    }
}
