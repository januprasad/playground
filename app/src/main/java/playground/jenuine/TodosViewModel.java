package playground.jenuine;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import playground.jenuine.db.AppDatabase;
import playground.jenuine.db.Todo;

public class TodosViewModel extends AndroidViewModel {

    public final LiveData<List<Todo>> todos;

    private AppDatabase mDb;

    public TodosViewModel(Application application) {
        super(application);
        mDb = AppDatabase.getDatabase(getApplication());
        todos = mDb.todoModel().findAllTodos();
    }

    public void addTodo(String name, long date, String description) {
        Todo todo = new Todo();
        todo.name = name;
        todo.description = description;
        todo.date = date;
        mDb.todoModel().insertTodo(todo);
    }

    public void removeTodo(int id) {
        Todo todo = new Todo();
        todo.id = id;
        mDb.todoModel().deleteTodo(todo);
    }

}
