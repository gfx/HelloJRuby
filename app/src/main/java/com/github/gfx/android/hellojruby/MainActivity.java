package com.github.gfx.android.hellojruby;

import org.jruby.Ruby;
import org.jruby.RubyInstanceConfig;
import org.jruby.runtime.builtin.IRubyObject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        long t0 = System.currentTimeMillis();

        Log.d(TAG, "Ruby starting");

        RubyInstanceConfig rubyInstanceConfig = new RubyInstanceConfig();
        rubyInstanceConfig.setCompileMode(RubyInstanceConfig.CompileMode.OFF);
        Ruby runtime = Ruby.newInstance(rubyInstanceConfig);

        Log.d(TAG, "Ruby instantiated (" + (System.currentTimeMillis() - t0) + "ms)");

        String script = "require 'java'\n"+
                "Java::AndroidUtil::Log.d %Q{Ruby}, %Q{This is JRuby (Ruby #{RUBY_VERSION})}\n"+
                "Java::ComGithubGfxAndroidHellojruby::BuildConfig::APPLICATION_ID";

        IRubyObject result = runtime.evalScriptlet(script);

        Log.d(TAG, "Ruby evaluated (" + (System.currentTimeMillis() - t0) + "ms)");

        TextView textView = (TextView) findViewById(R.id.text);
        assert textView != null;
        textView.setText(result.toJava(String.class).toString());
    }
}
