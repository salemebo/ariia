package org.aria.monitors;

import java.util.HashSet;
import java.util.Set;

import org.aria.util.Utils;
import org.terminal.Ansi;
import org.terminal.beans.Row;

public class TableItemsMonitor implements TableMonitor {
	
	protected Set<OneRangeMonitor> monitors;
	protected SessionMonitor session;
	protected TableItems table;
	
	public TableItemsMonitor(SessionMonitor session) {
		this.session = session;
		monitors = new HashSet<>();
		table = new TableItems(8);
		table.head("#", "Name", "Length", "Complete", "Remain", "Down", "Speed", "%");
	}
	
	
	@Override
	public boolean add(OneRangeMonitor mointor) {
		return monitors.add(mointor);
	}

	@Override
	public void remove(OneRangeMonitor mointor) {
		monitors.remove(mointor);
	}
	
	@Override
	public void clear() {
		monitors.clear();
	}
	
	@Override
	public SessionMonitor getSessionMonitor() {
		return session;
	}
	

	private void callSpeedForNextCycle() {
		
		for (OneRangeMonitor mointor : monitors) {
			mointor.demondSpeedNow();
		}
		session.demondSpeedNow();
	}
	
	private void updateInfo() {
		for (OneRangeMonitor mointor : monitors) {
			mointor.updateData();
		}
		session.rangeInfoUpdateData();
	}
	
	private void updateTable() {
		table.getRows().clear();
		//table.head("name", "Length", "TD", "Remain", "Down", "Speed", "100%");
		int index = 0;
		for (OneRangeMonitor mointor : monitors) {
			Row<String> row = table.createRow();
			row.add(++index + "");
			row.add(mointor.getName());
			row.add(mointor.getTotalLengthMB());
			
			row.add(mointor.getDownloadLengthMB());
			row.add(mointor.getRemainingLengthMB());
			row.add(mointor.getTotalReceiveMB());
			row.add(mointor.getSpeedTCPReceiveMB() + "ps");
			row.add(mointor.getPercent());	
		}
		if( ! table.getRows().isEmpty() & table.getRows().size() != 1) {
			Row<String> row = table.createRow();
			row.add("#");
			row.add("session (" + session.size() +")");
			row.add(session.getTotalLengthMB());
			
			row.add(session.getDownloadLengthMB());
			row.add(session.getRemainingLengthMB());
			row.add(session.getTotalReceiveMB());
			row.add(session.getSpeedTCPReceiveMB() + "ps");
			row.add(session.getPercent());	
		}
		
		
	}
	
	
	@Override
	public String getTableReport() {
		updateInfo();
		updateTable();
		StringBuilder message = new StringBuilder();
		message.append(Ansi.EraseDown);
		message.append('\n');
		message.append('\n');
		message.append('\n');
		message.append(table.toString());
		
		message.append('\n');
		message.append(' ');
		message.append(session.getTimer());
		message.append(' ');
		message.append(session.progressLine(78));
		message.append(' ');
		message.append(session.getRemainingTimeString());
		message.append('\n');
		
		String all = message.toString();
		int count = 1;
		for (byte c : all.getBytes()) {
			if(c == '\n')
				count++;
		}
		message.append(Utils.ANSI.cursorUp(count));
		callSpeedForNextCycle();
		return message.toString();
	}


	
	
}