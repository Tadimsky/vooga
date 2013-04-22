package vooga.rts.networking.client.GUI;

import java.awt.GridLayout;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import vooga.rts.networking.client.Player;
import vooga.rts.networking.communications.ExpandedLobbyInfo;

public class WaitingLobby implements Observer{
	private JPanel myPanel;
	public WaitingLobby(ExpandedLobbyInfo info) {
		info.addObserver(this);
		myPanel = new JPanel();
	}
	
	public JPanel getPanel(){
		return myPanel;
	}
	
	@Override
	public void update(Observable obj, Object arg1) {
		myPanel = createTeamPanel((ExpandedLobbyInfo) obj);	
	}
	
	 /**
	*
	* @return panel with team player names
	*/
	    private JPanel createTeamPanel(ExpandedLobbyInfo info){
	        JPanel teamPanel = new JPanel();
	        int index = 1;
	        List<List<Player>> teams = info.getTeams();
	        teamPanel.setLayout(new GridLayout(0,2));
	         
	        for(int i = 0 ; i < teams.size() ; i++){
	            JPanel names = new JPanel();
	            names.setOpaque(false);
	            names.setLayout(new BoxLayout(names,BoxLayout.PAGE_AXIS));
	            
	            names.add(new JLabel("Team "+index));
	            names.add(Box.createVerticalGlue());
	 
	            for (int j = 0 ; j < teams.get(i).size(); j++) {
	                names.add(new JLabel(teams.get(i).get(j).getName()));
	            }
	            names.add(Box.createVerticalGlue());
	            teamPanel.add(names);
	            index ++;
	        }
	        teamPanel.setOpaque(false);
	        teamPanel.setBorder(BorderFactory.createTitledBorder
	                            (BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Teams"));
	        return teamPanel;
	    }

}
