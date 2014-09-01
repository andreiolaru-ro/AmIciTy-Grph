package amicity.graph.pc.common;

import java.util.List;
import net.xqhs.graphs.matcher.Match;

public interface MatchListener {
	public void publishMatch(List<Match> match);
}
