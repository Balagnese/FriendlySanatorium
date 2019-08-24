package com.example.balagnese.testapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.balagnese.testapp.DisplayMessageActivity;
import com.example.balagnese.testapp.Network.NetworkService;
import com.example.balagnese.testapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = findViewById(R.id.textView2);

//        NetworkService.getInstance()
//                .getJSONApi()
//                .getPostWithID(1)
//                .enqueue(new Callback<Post>() {
//                    @Override
//                    public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
//                        Post post = response.body();
////                        textView.setText(post.getBody());
////                        TextView textView = findViewById(R.id.textView);
////                        textView.append(post.getId() + "\n");
////                        textView.append(post.getUserId() + "\n");
////                        textView.append(post.getTitle() + "\n");
////                        textView.append(post.getBody() + "\n");
//                        Log.d("onResponse", post.getBody());
//                    }
//
//                    @Override
//                    public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {
//                        textView.append("Error occurred while getting request!");
//                        t.printStackTrace();
//                    }
//                });
//
//        NetworkService
//                .getInstance()
//                .getJSONApi()
//                .getAllPosts()
//                .enqueue(new Callback<List<Post>>() {
//                    @Override
//                    public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
//                        List<Post> post = response.body();
//                        StringBuilder stringBuilder = new StringBuilder();
//                        for (int i = 0; i < post.size(); i++) {
//                            stringBuilder.append(post.get(i).getBody());
//                        }
////                        textView.setText(stringBuilder.toString());
//
//                    }
//                    @Override
//                    public void onFailure(Call<List<Post>> call, Throwable t) {
//
//                    }
//                });
//
//        NetworkService
//                .getInstance()
//                .getJSONApi()
//                .getPostWithID(6)
//                .enqueue(new Callback<Post>() {
//                    @Override
//                    public void onResponse(Call<Post> call, Response<Post> response) {
//                        Post post = response.body();
//                        textView.setText(post.getBody());
//                    }
//
//                    @Override
//                    public void onFailure(Call<Post> call, Throwable t) {
//
//                    }
//                });
//        User u = new User("1", "2", "3");
//        NetworkService
//                .getInstance()
//                .getJSONApi()
//                .createUser(u)
//                .enqueue(new Callback<User>() {
//                    @Override
//                    public void onResponse(Call<User> call, Response<User> response) {
//                        Log.v("createUser", "successfull");
//                        String s = response.toString();
//                        int i = 0;
//                    }
//
//                    @Override
//                    public void onFailure(Call<User> call, Throwable t) {
//
//                    }
//                });



//        NetworkService
//                .getInstance()
//                .getJSONApi()
//                .getUsers()
//                .enqueue(new Callback<List<User>>() {
//                    @Override
//                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                        List<User> users = response.body();
//                        int i = 0;
//                        try {
//                            List<User> users1 = response.body();
//                            Log.v("users", users.toString());
//                            textView.setText(users.toString());
////                            Log.v("post", post.toString());
////                            StringBuilder stringBuilder = new StringBuilder();
////                            for (int i = 0; i < post.size(); i++) {
////                                stringBuilder.append(post.get(i).getBody());
////                            }
////                            textView.setText(stringBuilder.toString());
//                        }
//                        catch(Throwable t) {
//                            Log.v("post", "Empty users");
//                            Log.v("post", t.toString());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<User>> call, Throwable t) {
//                        Log.v("userd", "failure");
//                    }
//                });
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
