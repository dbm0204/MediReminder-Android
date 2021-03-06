package project.dbm0204.org.meditimer;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by dbm0204 on 09/11/17.
 */
public class FragmentTwo extends Fragment {

  @BindView(R.id.circleLayout) LinearLayout circleLayout;
  private Unbinder unbinder;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup containter,Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_two, containter, false);
    return view;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
  }
}
