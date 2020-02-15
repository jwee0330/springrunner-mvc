package todoapp.core.todos.infrastructure;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import todoapp.core.todos.application.TodoEditor;

@Component
public class TodoRegister implements InitializingBean, CommandLineRunner, ApplicationRunner {
	private TodoEditor editor;
	
	public TodoRegister(TodoEditor editor) {
		this.editor = editor;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		editor.create("Task Two!");
		
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		editor.create("Task Three!");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		editor.create("Task One!");
	}
	
	

}
