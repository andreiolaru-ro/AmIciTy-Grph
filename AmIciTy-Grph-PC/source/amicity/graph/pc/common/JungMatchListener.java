package amicity.graph.pc.common;

import java.util.List;

import amicity.graph.pc.jung.JungMatch;

public interface JungMatchListener {
	public void publish(List<JungMatch> matches);
}
