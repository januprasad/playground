package playground.jenuine;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import playground.jenuine.db.Todo;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private TodosViewModel mTodosViewModel;
    private List<Todo> mTodos = new ArrayList<Todo>();



    @BindView(R.id.title)
    EditText title;

    @BindView(R.id.description)
    EditText description;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mTodosViewModel = ViewModelProviders.of(this).get(TodosViewModel.class);
        mTodosViewModel.todos.observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(@Nullable final List<Todo> todos) {
                DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {

                    @Override
                    public int getOldListSize() {
                        return mTodos.size();
                    }


                    @Override
                    public int getNewListSize() {
                        return todos.size();
                    }

                    @Override
                    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                        return mTodos.get(oldItemPosition).id ==
                                todos.get(newItemPosition).id;
                    }

                    @Override
                    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                        Todo oldTodo = mTodos.get(oldItemPosition);
                        Todo newTodo = todos.get(newItemPosition);
                        return oldTodo.equals(newTodo);
                    }

                });
//                result.dispatchUpdatesTo(todosAdapter);
                mTodos = todos;
                iterate();
            }
        });
    }

    private void iterate() {
        Timber.v(mTodos.size()+"");
    }



    @OnClick(R.id.save)
    void saveTodo() {
        mTodosViewModel = ViewModelProviders.of(this).get(TodosViewModel.class);
        String titleString = String.valueOf(title.getText());
        String descriptionString = String.valueOf(description.getText());
        long date = (new Date()).getTime();
        mTodosViewModel.addTodo(titleString, date,descriptionString);
        finish();
        startActivity(new Intent(this, TodoListActivity.class));
    }

/*




*/
}
