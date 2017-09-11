package project.dbm0204.org.meditimer;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.List;

/**
 * Created by dbm0204
 */
public class NavigationDrawerView extends BetterViewAnimator {

  @BindView(R.id.leftDrawerListView) ListView leftDrawerListView;

  private final NavigationDrawerAdapter adapter;

  public NavigationDrawerView(Context context, AttributeSet attrs) {
    super(context, attrs);
    adapter = new NavigationDrawerAdapter(context);
  }

  public void replaceWith(List<NavigationDrawerItem> items) {
    adapter.replaceWith(items);
    setDisplayedChildId(R.id.leftDrawerListView);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);
    leftDrawerListView.setAdapter(adapter);
  }

  public NavigationDrawerAdapter getAdapter() {
    return adapter;
  }
}
