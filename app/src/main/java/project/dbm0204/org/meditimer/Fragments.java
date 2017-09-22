package project.dbm0204.org.meditimer;

import android.support.v4.app.Fragment;

public enum Fragments {

  ONE(FragmentOne.class), TWO(FragmentTwo.class), THREE(FragmentThree.class), ABOUT(
      FragmentAbout.class);

  final Class<? extends Fragment> fragment;

  Fragments(Class<? extends Fragment> fragment) {
    this.fragment = fragment;
  }

  public String getFragment() {
    return fragment.getName();
  }
}
