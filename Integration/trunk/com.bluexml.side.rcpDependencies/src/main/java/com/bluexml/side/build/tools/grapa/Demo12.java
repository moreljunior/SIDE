/*
    Copyright (C) 2007-20013  BlueXML - www.bluexml.com

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.bluexml.side.build.tools.grapa;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import att.grappa.Graph;
import att.grappa.Grappa;
import att.grappa.GrappaAdapter;
import att.grappa.GrappaConstants;
import att.grappa.GrappaPanel;
import att.grappa.GrappaSupport;
import att.grappa.Parser;

public class Demo12 implements GrappaConstants {
	public DemoFrame frame = null;

	public final static String SCRIPT = "../formatDemo";

	public static void main(String[] args) {
		InputStream input = System.in;
		if (args.length > 1) {
			System.err.println("USAGE: java Demo12 [input_graph_file]");
			System.exit(1);
		} else if (args.length == 1) {
			if (args[0].equals("-")) {
				input = System.in;
			} else {
				try {
					input = new FileInputStream(args[0]);
				} catch (FileNotFoundException fnf) {
					System.err.println(fnf.toString());
					System.exit(1);
				}
			}
		}
		Demo12 demo = new Demo12();
		demo.doDemo(input);
	}

	Demo12() {
	}

	void doDemo(InputStream input) {
		Parser program = new Parser(input, System.err);
		try {
			//program.debug_parse(4);
			program.parse();
		} catch (Exception ex) {
			System.err.println("Exception: " + ex.getMessage());
			ex.printStackTrace(System.err);
			System.exit(1);
		}
		Graph graph = null;

		graph = program.getGraph();

		System.err.println("The graph contains " + graph.countOfElements(Grappa.NODE | Grappa.EDGE | Grappa.SUBGRAPH) + " elements.");

		graph.setEditable(true);
		//graph.setMenuable(true);
		graph.setErrorWriter(new PrintWriter(System.err, true));
		//graph.printGraph(new PrintWriter(System.out));

		System.err.println("bbox=" + graph.getBoundingBox().getBounds().toString());

		frame = new DemoFrame(graph);

		frame.show();
	}

	class DemoFrame extends JFrame implements ActionListener {
		GrappaPanel gp;
		Graph graph = null;

		JButton layout = null;
		JButton printer = null;
		JButton draw = null;
		JButton quit = null;
		JPanel panel = null;

		public DemoFrame(Graph graph) {
			super("DemoFrame");
			this.graph = graph;

			setSize(600, 400);
			setLocation(100, 100);

			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent wev) {
					Window w = wev.getWindow();
					w.setVisible(false);
					w.dispose();
					System.exit(0);
				}
			});

			JScrollPane jsp = new JScrollPane();
			jsp.getViewport().setBackingStoreEnabled(true);

			gp = new GrappaPanel(graph);
			gp.addGrappaListener(new GrappaAdapter());
			gp.setScaleToFit(false);

			java.awt.Rectangle bbox = graph.getBoundingBox().getBounds();

			GridBagLayout gbl = new GridBagLayout();
			GridBagConstraints gbc = new GridBagConstraints();

			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.anchor = GridBagConstraints.NORTHWEST;

			panel = new JPanel();
			panel.setLayout(gbl);

			draw = new JButton("Draw");
			gbl.setConstraints(draw, gbc);
			panel.add(draw);
			draw.addActionListener(this);

			layout = new JButton("Layout");
			gbl.setConstraints(layout, gbc);
			panel.add(layout);
			layout.addActionListener(this);

			printer = new JButton("Print");
			gbl.setConstraints(printer, gbc);
			panel.add(printer);
			printer.addActionListener(this);

			quit = new JButton("Quit");
			gbl.setConstraints(quit, gbc);
			panel.add(quit);
			quit.addActionListener(this);

			getContentPane().add("Center", jsp);
			getContentPane().add("West", panel);

			setVisible(true);
			jsp.setViewportView(gp);
		}

		public void actionPerformed(ActionEvent evt) {
			if (evt.getSource() instanceof JButton) {
				JButton tgt = (JButton) evt.getSource();
				if (tgt == draw) {
					graph.repaint();
				} else if (tgt == quit) {
					System.exit(0);
				} else if (tgt == printer) {
					graph.printGraph(System.out);
					System.out.flush();
				} else if (tgt == layout) {
					Object connector = null;
					try {
						connector = Runtime.getRuntime().exec(Demo12.SCRIPT);
					} catch (Exception ex) {
						System.err.println("Exception while setting up Process: " + ex.getMessage() + "\nTrying URLConnection...");
						connector = null;
					}
					if (connector == null) {
						try {
							connector = (new URL("http://www.research.att.com/~john/cgi-bin/format-graph")).openConnection();
							URLConnection urlConn = (URLConnection) connector;
							urlConn.setDoInput(true);
							urlConn.setDoOutput(true);
							urlConn.setUseCaches(false);
							urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
						} catch (Exception ex) {
							System.err.println("Exception while setting up URLConnection: " + ex.getMessage() + "\nLayout not performed.");
							connector = null;
						}
					}
					if (connector != null) {
						if (!GrappaSupport.filterGraph(graph, connector)) {
							System.err.println("ERROR: somewhere in filterGraph");
						}
						if (connector instanceof Process) {
							try {
								int code = ((Process) connector).waitFor();
								if (code != 0) {
									System.err.println("WARNING: proc exit code is: " + code);
								}
							} catch (InterruptedException ex) {
								System.err.println("Exception while closing down proc: " + ex.getMessage());
								ex.printStackTrace(System.err);
							}
						}
						connector = null;
					}
					graph.repaint();
				}
			}
		}
	}
}
