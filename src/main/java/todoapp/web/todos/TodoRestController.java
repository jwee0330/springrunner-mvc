package todoapp.web.todos;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import todoapp.core.todos.application.TodoEditor;
import todoapp.core.todos.application.TodoFinder;
import todoapp.core.todos.domain.Todo;

@RestController
public class TodoRestController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private TodoFinder finder;
	private TodoEditor editor;

	public TodoRestController(TodoFinder finder, TodoEditor editor) {
		this.finder = finder;
		this.editor = editor;
	}

	@GetMapping("/api/todos")
	public List<Todo> list() {
		return finder.getAll();
	}

	@PostMapping("/api/todos")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void create(@RequestBody @Valid TodoWriteCommand command) {
		logger.info("command: {}", command);
		editor.create(command.getTitle());
	}

	@PutMapping("/api/todos/{id}")
	public void update(@PathVariable Long id, @RequestBody @Valid TodoWriteCommand command) {
		logger.info("id: {}, command: {}", id, command);
		editor.update(id, command.getTitle(), command.isCompleted());
	}

	@DeleteMapping("/api/todos/{id}")
	public void delete(@PathVariable Long id) {
		logger.info("id: {}", id);
		editor.delete(id);
	}

	static class TodoWriteCommand {
		@Size(min = 4, max = 140)
		private String title;
		private boolean completed;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public boolean isCompleted() {
			return completed;
		}

		public void setComplete(boolean completed) {
			this.completed = completed;
		}

	}

}
