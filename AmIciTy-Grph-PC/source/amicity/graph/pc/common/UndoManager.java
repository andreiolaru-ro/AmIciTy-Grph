package amicity.graph.pc.common;

import java.util.Stack;

public class UndoManager implements Command {
	private Stack<Command> past;
	private Stack<Command> future;

	public UndoManager() {
		past = new Stack<Command>();
		future = new Stack<Command>();
	}
	
	public void addCommand(Command command) {
		past.add(command);
		clearFuture();
	}

	@Override
	public void Undo() {
		if (past.empty()) {
			return;
		}
		
		Command command = past.pop();
		command.Undo();
		future.add(command);
	}

	@Override
	public void Redo() {
		if (future.empty()) {
			return;
		}

		Command command = future.pop();
		command.Redo();
		past.add(command);
	}
	
	public void clearFuture() {
		future.clear();
	}
}
