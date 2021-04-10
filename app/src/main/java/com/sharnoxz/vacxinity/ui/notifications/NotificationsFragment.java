package com.sharnoxz.vacxinity.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.sharnoxz.vacxinity.R;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    WebView webView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        webView = root.findViewById(R.id.webview2);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://covid19-india-chatbot.herokuapp.com/");
        return root;
    }
}
