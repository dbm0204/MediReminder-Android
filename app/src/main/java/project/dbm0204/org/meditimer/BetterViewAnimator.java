package project.dbm0204.org.meditimer;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.AttributeSet;
import android.widget.ViewAnimator;

/**
 * Created by dbm0204 on 9/11/17.
 */

public class BetterViewAnimator extends ViewAnimator {
    public BetterViewAnimator(Context context) {
        super(context);
    }

    public BetterViewAnimator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDisplayedChildId(int id){
        if (getDisplayedChild()==id){
            return;
        }
        for(int i=0, count=getChildCount(); i<count;i++){
            if(getChildAt(i).getId()==id) {
                setDisplayedChild(i);
                return;
            }
        }
        throw new IllegalArgumentException("No view with ID"+id);
    }
    public int getDisplayedChildId(){
        return getChildAt(getDisplayedChildId()).getId();
    }
}
