package fragments;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import application.ClickListener;

import com.capricorn.ArcMenu;
import com.sparks.medic.R;
import com.sparks.medic.activities.LauncherActivity;

import customviews.YDPeopleImageView;

public class HomeFragment extends Fragment {

    private TextView headerText;
    private ClickListener clickListener;
    private TextView textWidget;

    public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

      int[] ITEM_DRAWABLES = { R.drawable.arc_ic_home, R.drawable.arc_ic_checkin,
                R.drawable.arc_ic_alldeals, R.drawable.arc_ic_mydeals, R.drawable.arc_ic_clicknshare, R.drawable.arc_ic_friends };


        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        YDPeopleImageView iconImage = (YDPeopleImageView)rootView.findViewById(R.id.userIcon);
        iconImage.setImageDrawable(getResources().getDrawable(R.drawable.hospital));
        headerText = (TextView) rootView.findViewById(R.id.txtLabel);
        textWidget = (TextView) rootView.findViewById(R.id.textWidget);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Thin.ttf");
        headerText.setTypeface(tf);
        textWidget.setSelected(true);

        ArcMenu arcMenu = (ArcMenu) rootView.findViewById(R.id.arc_menu);

        initArcMenu(arcMenu, ITEM_DRAWABLES);



        return rootView;
    }
	
	  @Override
      public void onAttach(Activity activity) {
          super.onAttach(activity);
          try {
              clickListener = (ClickListener) activity;
          } catch (ClassCastException castException) {
              /** The activity does not implement the listener. */
          }
      }


    private void initArcMenu(ArcMenu menu, int[] itemDrawables) {
        final int itemCount = itemDrawables.length;
        for (int i = 0; i < itemCount; i++) {
            ImageView item = new ImageView(getActivity());
            item.setImageResource(itemDrawables[i]);

            final int position = i;
            menu.addItem(item, new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //Toast.makeText(getActivity(), "position:" + position, Toast.LENGTH_SHORT).show();
                	((LauncherActivity)getActivity()).callFragment(position);
                }
            });
        }
    }
}
