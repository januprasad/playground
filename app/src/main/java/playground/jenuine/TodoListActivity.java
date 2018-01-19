package playground.jenuine;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import playground.jenuine.db.Todo;
import timber.log.Timber;

public class TodoListActivity extends AppCompatActivity {


    @BindView(R.id.todo_list)
    RecyclerView todoList;
    private List<Todo> mTodos = new ArrayList<>();
    private TodosViewModel mTodosViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        ButterKnife.bind(this);
        mTodosViewModel = ViewModelProviders.of(this).get(TodosViewModel.class);
//        final TodoListAdapter todosAdapter = new TodoListAdapter(mTodos, mTodosViewModel);
        final TodosAdapter todosAdapter = new TodosAdapter();
        todoList.setAdapter(todosAdapter);
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
                mTodos = todos;
//                iterate();
//                todosAdapter.notifyDataSetChanged();
                result.dispatchUpdatesTo(todosAdapter);
            }
        });


    }

   @OnClick(R.id.fab)
    void addNewTodo() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    private void iterate() {
        Timber.v(mTodos.size() + "");
        for(Todo t:mTodos){
            Timber.v(t.name);
            Timber.v(t.description);
        }

    }

    public class TodosAdapter extends RecyclerView.Adapter<TodosAdapter.TodoViewHolder> {

        @Override
        public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new TodoViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(TodoViewHolder holder, int position) {
            Todo todo = mTodos.get(position);
            holder.getNameTextView().setText(todo.name);
            Timber.v(todo.description);
            holder.getDescriptionView().setText(todo.description);
            holder.getDateTextView().setText((new Date(todo.date).toString()));
        }

        @Override
        public int getItemCount() {
            return mTodos.size();
        }

        class TodoViewHolder extends RecyclerView.ViewHolder {

            private final TextView title;
            private final TextView date;
            private final TextView description;

            TodoViewHolder(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.item_title);
                date = itemView.findViewById(R.id.item_date);
                description = itemView.findViewById(R.id.item_description);
                Button btnDelete = itemView.findViewById(R.id.item_delete);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = getAdapterPosition();
                        Todo todo = mTodos.get(pos);
                        mTodosViewModel.removeTodo(todo.id);
                    }
                });
            }

            TextView getNameTextView() {
                return title;
            }

            TextView getDateTextView() {
                return date;
            }

            public TextView getDescriptionView() {
                return description;
            }
        }
    }

}
