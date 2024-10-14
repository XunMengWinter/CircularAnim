package top.wefor.circularanimdemo.fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import top.wefor.circularanimdemo.R;

/**
 * Created on 2018/7/15.
 *
 * @author ice
 */
public class Test1Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test1, container, false);
        return view;
    }
}
