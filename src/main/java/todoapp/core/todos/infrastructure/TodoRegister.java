package todoapp.core.todos.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import todoapp.core.todos.application.TodoEditor;

import static org.slf4j.LoggerFactory.*;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class TodoRegister implements InitializingBean, CommandLineRunner, ApplicationRunner {
	private static final Logger log = getLogger(TodoRegister.class);

	private TodoEditor editor;
	public TodoRegister(TodoEditor editor) {
		this.editor = editor;
	}

	/**
	 * ApplicationRunner - 2
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		log.info("ApplicationRunner");
		editor.create("Task Two!");
	}

	/**
	 * CommandLineRunner - 3
	 */
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		log.info("CommandLineRunner");
		editor.create("Task Three!");
	}

	/**
	 * InitializingBean - 1
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		log.info("InitializingBean");
		editor.create("Task One!");
	}

}
