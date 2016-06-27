package com.udacity.abng.tourguidapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class EatoutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.location_list, container, false);

        // Create a list of locations
        final ArrayList<Location> locations = new ArrayList<>();
        locations.add(new Location(R.string.eat_name1,
                R.string.eat_description1, R.drawable.eat_gastronhome));
        locations.add(new Location(R.string.eat_name2,
                R.string.eat_description2, R.drawable.eat_thefivefields));
        locations.add(new Location(R.string.eat_name3,
                R.string.eat_description3, R.drawable.eat_theledbury));
        locations.add(new Location(R.string.eat_name4,
                R.string.eat_description4, R.drawable.eat_theclinkrestaurant));
        locations.add(new Location(R.string.eat_name5,
                R.string.eat_description5, R.drawable.eat_thegoldenchippy));
        locations.add(new Location(R.string.eat_name6,
                R.string.eat_description6, R.drawable.eat_typingroom));
        locations.add(new Location(R.string.eat_name7,
                R.string.eat_description7, R.drawable.eat_tortellinicup));
        locations.add(new Location(R.string.eat_name8,
                R.string.eat_description8, R.drawable.eat_cafeloren));

        // Create an {@link LocationAdapter}, whose data source is a list of {@link Locations}. The
        // adapter knows how to create list items for each item in the list.
        LocationAdapter adapter = new LocationAdapter(getActivity(), locations, R.color.category_eatout);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // location_list.xml layout file.
        ListView listView = (ListView) rootView.findViewById(R.id.list);

        // Make the {@link ListView} use the {@link LocationAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link location} in the list.
        listView.setAdapter(adapter);

        return rootView;
    }
}
