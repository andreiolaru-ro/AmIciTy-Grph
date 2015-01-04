package amicity.graph.pc.gui.util;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class GraphUIEventDispatcherImpl implements GraphUIEventDispatcher
{
	protected List<WeakReference<GraphUIEventListener>> listeners;
	
	public GraphUIEventDispatcherImpl() {
		listeners = new ArrayList<WeakReference<GraphUIEventListener>>();
	}
	
	public void dispatchEvent(GraphUIEvent event) {
		for (WeakReference<GraphUIEventListener> listenerRef : listeners) {
			GraphUIEventListener listener = listenerRef.get();
			if (listener == null) {
				// FIXME(catalinb): should remove dead refs
				continue;
			}
			
			listener.handleEvent(event);
		}
	}
	
	@Override
	public void addEventListener(GraphUIEventListener listener) {
		listeners.add(new WeakReference<GraphUIEventListener>(listener));
	}
}
