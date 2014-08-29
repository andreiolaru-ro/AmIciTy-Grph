package amicity.graph.pc.common;

import java.util.Stack;

public class UndoManager {
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

	public boolean Undo() {
		if (past.empty()) {
			return false;
		}
		
		Command command = past.pop();
		command.Undo();
		future.add(command);
		return true;
	}


	public boolean Redo() {
		if (future.empty()) {
			return false;
		}

		Command command = future.pop();
		command.Redo();
		past.add(command);
		return true;
	}
	
	public boolean canUndo() {
		return !past.isEmpty();
	}
	
	public boolean canRedo() {
		return !future.isEmpty();
	}
	
	public void clearFuture() {
		future.clear();
	}
}
