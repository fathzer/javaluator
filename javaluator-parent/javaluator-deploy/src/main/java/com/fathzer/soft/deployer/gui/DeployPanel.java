package com.fathzer.soft.deployer.gui;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.JTextArea;
import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.border.TitledBorder;
import javax.swing.JCheckBox;

import com.fathzer.soft.ajlib.swing.widget.TextWidget;
import com.fathzer.soft.ajlib.swing.widget.PasswordWidget;
import com.fathzer.soft.ajlib.swing.worker.Worker;
import com.fathzer.soft.deployer.Parameters;
import com.fathzer.soft.deployer.Scenario;
import com.fathzer.soft.deployer.Task;

import javax.swing.JButton;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JProgressBar;

public class DeployPanel extends JPanel {
	private final class GuiListener implements PropertyChangeListener, ItemListener {
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			updateGoButton();
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			updateGoButton();
		}
	}
	private static final long serialVersionUID = 1L;

	private static final String EMPTY = "";
	private static final String PASSWORD = "PASSWORD";
	private static final String SAVE_PASSWORD = "SAVE_PASSWORD";
	private static final String USER = "USER";
	private static final String VERSION = "VERSION";

	private JPanel topPanel;
	private JPanel connectionPanel;
	private JPanel actionsPanel;
	private JTextArea log;
	private JLabel lblUser;
	private TextWidget user;
	private JLabel lblPassword;
	private PasswordWidget password;
	private JLabel lblVersion;
	private JCheckBox savePassword;
	private TextWidget version;
	private JPanel panel;
	private List<JCheckBox> taskBoxes;
	private JButton go;

	private GuiListener listener;
	private Scenario scenario;
	private JPanel centerPanel;
	private JLabel progressLabel;
	private JProgressBar progressBar;

	/**
	 * Create the panel.
	 */
	public DeployPanel(Scenario scenario) {
		this.scenario = scenario;
		listener = new GuiListener();
		setLayout(new BorderLayout(0, 0));
		add(getTopPanel(), BorderLayout.NORTH);
		add(getCenterPanel(), BorderLayout.CENTER);
	}
	
	public void saveState() {
		Preferences prefs = Preferences.userRoot().node(getClass().getName());
		prefs.put(USER, getUser().getText());
		prefs.putBoolean(SAVE_PASSWORD, getSavePassword().isSelected());
		if (getSavePassword().isSelected()) {
			prefs.put(PASSWORD, new String(getPassword().getPassword()));
		} else {
			prefs.remove(PASSWORD);
		}
		prefs.put(VERSION, getVersion().getText());
	}

	public void restoreState() {
		Preferences prefs = Preferences.userRoot().node(getClass().getName());
		getUser().setText(prefs.get(USER, EMPTY));
		getSavePassword().setSelected(prefs.getBoolean(SAVE_PASSWORD, false));
		if (getSavePassword().isSelected()) {
			getPassword().setText(prefs.get(PASSWORD, EMPTY));
		}
		getVersion().setText(prefs.get(VERSION, EMPTY));
	}

	private void updateGoButton() {
		String message = null;
		if (user.getText().isEmpty()) message = "No user is entered";
		else if (password.getPassword().length==0) message = "No password is entered";
		else if (version.getText().isEmpty()) message = "No version is specified";
		else if (!isSomethingToDo()) message = "Nothing to do";
		getGo().setEnabled(message==null);
		getGo().setToolTipText(message==null?"Click to start deployment" : message);
	}

	private boolean isSomethingToDo() {
		for (JCheckBox box : taskBoxes) {
			if (box.isSelected()) return true;
		}
		return false;
	}

	private JPanel getTopPanel() {
		if (topPanel == null) {
			topPanel = new JPanel();
			topPanel.setLayout(new BorderLayout(0, 0));
			topPanel.add(getConnectionPanel(), BorderLayout.NORTH);
			topPanel.add(getActionsPanel(), BorderLayout.SOUTH);
		}
		return topPanel;
	}
	private JPanel getConnectionPanel() {
		if (connectionPanel == null) {
			connectionPanel = new JPanel();
			connectionPanel.setBorder(new TitledBorder(null, "Connection data", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			GridBagLayout gbl_connectionPanel = new GridBagLayout();
			gbl_connectionPanel.columnWeights = new double[]{0.0, 1.0};
			connectionPanel.setLayout(gbl_connectionPanel);
			GridBagConstraints gbc_lblUser = new GridBagConstraints();
			gbc_lblUser.anchor = GridBagConstraints.WEST;
			gbc_lblUser.insets = new Insets(0, 0, 5, 5);
			gbc_lblUser.gridx = 0;
			gbc_lblUser.gridy = 0;
			connectionPanel.add(getLblUser(), gbc_lblUser);
			GridBagConstraints gbc_user = new GridBagConstraints();
			gbc_user.insets = new Insets(0, 0, 5, 0);
			gbc_user.weightx = 1.0;
			gbc_user.fill = GridBagConstraints.HORIZONTAL;
			gbc_user.gridx = 1;
			gbc_user.gridy = 0;
			connectionPanel.add(getUser(), gbc_user);
			GridBagConstraints gbc_lblPassword = new GridBagConstraints();
			gbc_lblPassword.anchor = GridBagConstraints.WEST;
			gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
			gbc_lblPassword.gridx = 0;
			gbc_lblPassword.gridy = 1;
			connectionPanel.add(getLblPassword(), gbc_lblPassword);
			GridBagConstraints gbc_password = new GridBagConstraints();
			gbc_password.insets = new Insets(0, 0, 5, 0);
			gbc_password.weightx = 1.0;
			gbc_password.fill = GridBagConstraints.HORIZONTAL;
			gbc_password.gridx = 1;
			gbc_password.gridy = 1;
			connectionPanel.add(getPassword(), gbc_password);
			GridBagConstraints gbc_savePassword = new GridBagConstraints();
			gbc_savePassword.anchor = GridBagConstraints.EAST;
			gbc_savePassword.gridwidth = 0;
			gbc_savePassword.insets = new Insets(0, 0, 0, 5);
			gbc_savePassword.gridx = 0;
			gbc_savePassword.gridy = 2;
			connectionPanel.add(getSavePassword(), gbc_savePassword);
		}
		return connectionPanel;
	}
	private JPanel getActionsPanel() {
		if (actionsPanel == null) {
			actionsPanel = new JPanel();
			GridBagLayout gbl_actionsPanel = new GridBagLayout();
			actionsPanel.setLayout(gbl_actionsPanel);
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.gridwidth = 0;
			gbc_panel.insets = new Insets(0, 0, 5, 0);
			gbc_panel.weightx = 1.0;
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 0;
			actionsPanel.add(getPanel(), gbc_panel);
			GridBagConstraints gbc_action = new GridBagConstraints();
			gbc_action.weightx = 1.0;
			gbc_action.anchor = GridBagConstraints.WEST;
			gbc_action.insets = new Insets(0, 0, 5, 0);
			gbc_action.gridx = 0;
			gbc_action.gridy = 1;
			taskBoxes = new ArrayList<JCheckBox>();
			for (Task task : scenario.getTasks()) {
				JCheckBox box = new JCheckBox(task.getName());
				box.setSelected(true);
				box.addItemListener(listener);
				taskBoxes.add(box);
				actionsPanel.add(box, gbc_action);
				gbc_action.gridy++;
			}
			GridBagConstraints gbc_go = new GridBagConstraints();
			gbc_go.weightx = 1.0;
			gbc_go.fill = GridBagConstraints.BOTH;
			gbc_go.gridheight = 0;
			gbc_go.gridx = 1;
			gbc_go.gridy = 1;
			actionsPanel.add(getGo(), gbc_go);
		}
		return actionsPanel;
	}
	private JTextArea getLog() {
		if (log == null) {
			log = new JTextArea();
		}
		return log;
	}
	private JLabel getLblUser() {
		if (lblUser == null) {
			lblUser = new JLabel("User:");
		}
		return lblUser;
	}
	private TextWidget getUser() {
		if (user == null) {
			user = new TextWidget();
			user.setColumns(10);
			user.addPropertyChangeListener(TextWidget.TEXT_PROPERTY, listener);
		}
		return user;
	}
	private JLabel getLblPassword() {
		if (lblPassword == null) {
			lblPassword = new JLabel("Password:");
		}
		return lblPassword;
	}
	private PasswordWidget getPassword() {
		if (password == null) {
			password = new PasswordWidget();
			password.setColumns(10);
			password.addPropertyChangeListener(TextWidget.TEXT_PROPERTY, listener);
		}
		return password;
	}
	private JLabel getLblVersion() {
		if (lblVersion == null) {
			lblVersion = new JLabel("Version:");
		}
		return lblVersion;
	}
	private JCheckBox getSavePassword() {
		if (savePassword == null) {
			savePassword = new JCheckBox("Remember password");
		}
		return savePassword;
	}
	private TextWidget getVersion() {
		if (version == null) {
			version = new TextWidget();
			version.setColumns(10);
			version.addPropertyChangeListener(TextWidget.TEXT_PROPERTY, listener);
		}
		return version;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			GridBagLayout gbl_panel = new GridBagLayout();
			panel.setLayout(gbl_panel);
			GridBagConstraints gbc_lblVersion = new GridBagConstraints();
			gbc_lblVersion.anchor = GridBagConstraints.WEST;
			gbc_lblVersion.insets = new Insets(0, 0, 5, 5);
			gbc_lblVersion.gridx = 0;
			gbc_lblVersion.gridy = 0;
			panel.add(getLblVersion(), gbc_lblVersion);
			GridBagConstraints gbc_version = new GridBagConstraints();
			gbc_version.weightx = 1.0;
			gbc_version.insets = new Insets(0, 0, 5, 0);
			gbc_version.anchor = GridBagConstraints.WEST;
			gbc_version.gridx = 1;
			gbc_version.gridy = 0;
			panel.add(getVersion(), gbc_version);
		}
		return panel;
	}
	private static class Progress {
		private int taskId;
		private String message;
		public Progress(int taskId, String message) {
			super();
			this.taskId = taskId;
			this.message = message;
		}
	}
	private JButton getGo() {
		if (go == null) {
			go = new JButton("Go !");
			go.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					go.setEnabled(false);
					getLog().setText("");
					getProgressBar().setValue(0);
					getProgressBar().setVisible(true);
					int nb = 0;
					for (JCheckBox box : taskBoxes) {
						if (box.isSelected()) nb++;
					}
					getProgressBar().setMaximum(nb);
					Worker<Void, Progress> worker = new Worker<Void, Progress>() {
						@Override
						protected Void doProcessing() throws Exception {
							scenario.setAuthentication(getUser().getText(), getPassword().getPassword());
							Parameters params = new Parameters(getVersion().getText());
							for (int i = 0; i < scenario.getTasks().size(); i++) {
								if (taskBoxes.get(i).isSelected()) {
									Task task = scenario.getTasks().get(i);
									publish (new Progress(i, "Running \""+task.getName()));
									task.setLogWriter(new Task.LogWriter() {
										@Override
										public void write(String string) {
											publish(new Progress(-1, string));
										}
									});
									task.doIt(params);
									publish(new Progress(i+1, null));
								}
							}
							return null;
						}

						@Override
						protected void process(List<Progress> progresses) {
							for (Progress p : progresses) {
								if (p.taskId>=0) getProgressBar().setValue(p.taskId);
								if (p.message!=null) {
									getLog().append(p.message);
									getLog().append("\n");
								}
							}
						}

						@Override
						protected void done() {
							scenario.close();
							getLog().append("done");
							go.setEnabled(true);
							getProgressBar().setVisible(false);
							super.done();
						}
					};
					worker.execute();
				}
			});
			go.setEnabled(false);
		}
		return go;
	}
	private JPanel getCenterPanel() {
		if (centerPanel == null) {
			centerPanel = new JPanel();
			GridBagLayout gbl_centerPanel = new GridBagLayout();
			centerPanel.setLayout(gbl_centerPanel);
			GridBagConstraints gbc_log = new GridBagConstraints();
			gbc_log.weighty = 1.0;
			gbc_log.weightx = 1.0;
			gbc_log.insets = new Insets(0, 0, 5, 0);
			gbc_log.anchor = GridBagConstraints.NORTH;
			gbc_log.fill = GridBagConstraints.BOTH;
			gbc_log.gridx = 0;
			gbc_log.gridy = 2;
			centerPanel.add(getLog(), gbc_log);
			GridBagConstraints gbc_progressLabel = new GridBagConstraints();
			gbc_progressLabel.anchor = GridBagConstraints.WEST;
			gbc_progressLabel.insets = new Insets(0, 0, 5, 0);
			gbc_progressLabel.gridx = 0;
			gbc_progressLabel.gridy = 0;
			centerPanel.add(getProgressLabel(), gbc_progressLabel);
			GridBagConstraints gbc_progressBar = new GridBagConstraints();
			gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
			gbc_progressBar.weightx = 1.0;
			gbc_progressBar.gridx = 0;
			gbc_progressBar.gridy = 1;
			centerPanel.add(getProgressBar(), gbc_progressBar);
		}
		return centerPanel;
	}
	private JLabel getProgressLabel() {
		if (progressLabel == null) {
			progressLabel = new JLabel("");
		}
		return progressLabel;
	}
	private JProgressBar getProgressBar() {
		if (progressBar == null) {
			progressBar = new JProgressBar();
			progressBar.setVisible(false);
		}
		return progressBar;
	}
}
