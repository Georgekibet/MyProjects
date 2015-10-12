package data.collect.com.mydata;

import android.app.Fragment;

/**
 * Created by george on 10/10/2015.
 */
public class BaseFragment extends Fragment  {

    public interface OnFabsetup{
        public void setUpFab(int resId);
        public void onFabClicked();
    }




}
