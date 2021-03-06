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
package com.bluexml.side.alfresco.applet;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;
import java.net.URLConnection;

import netscape.javascript.JSObject;



public class EditingDocument extends Applet {

	private static final long serialVersionUID = 1L;
	String text = "debug: ";
	String textInfo = "";
	File myFile = null;
	URL url = null;
	Exec monAppli = null;
	String fileName = null;
	long lastModified = 0;
	JSObject jso = null;

	public void init() {
		try {
			jso = JSObject.getWindow(this);
			String tmpdir = System.getProperty("java.io.tmpdir");
			String tmpdirReplaced = tmpdir.replaceAll("/", "\\\\");
			String webdav = getParameter("webdavUrl");
			webdav = webdav.replaceAll(" ", "%20");
			webdav = webdav.replaceAll("à", "%c3%a0");
			webdav = webdav.replaceAll("â", "%c3%a2");
			webdav = webdav.replaceAll("ç", "%c3%a7");
			webdav = webdav.replaceAll("è", "%c3%a8");
			webdav = webdav.replaceAll("é", "%C3%A9");
			webdav = webdav.replaceAll("ê", "%c3%aa");
			webdav = webdav.replaceAll("î", "%c3%ae");
			webdav = webdav.replaceAll("ô", "%c3%b4");
			webdav = webdav.replaceAll("ù", "%c3%b9");
			webdav = webdav.replaceAll("û", "%c3%bb");
			//webdav += "?ticket=" + getParameter("ticket");
			//webdav = URLEncoder.encode(webdav, "UTF-8");
			url = new URL(webdav);
			URLConnection uc = url.openConnection();
			uc.setUseCaches(false);
			uc.setDoInput(true);
			uc.setDoOutput(true);
			uc.addRequestProperty("ticket", getParameter("ticket"));
			uc.connect();

			InputStream input = uc.getInputStream();
			fileName = getFileName(getParameter("webdavUrl"));
			fileName = fileName.replaceAll(" ", "_");
			myFile = new File(tmpdirReplaced + fileName + "_tmp.doc");
			FileOutputStream fos = new FileOutputStream(myFile);
			byte[] buffer = new byte[1024];
			int count = 0;
			while ((count = input.read(buffer)) > 0) {
				fos.write(buffer, 0, count);
			}
			input.close();
			fos.close();

			Thread.sleep(5000);
			
			
			//text += " " + fileName;
			String[] command = { "cmd", "/c", "copy /y " + tmpdirReplaced + fileName + "_tmp.doc" + " " + tmpdirReplaced + fileName };
			ProcessBuilder copyFiles = new ProcessBuilder(command);
			copyFiles.redirectErrorStream(true);
			Process p = copyFiles.start();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			do {
			    line = reader.readLine();
			    if (line != null) { System.out.println(line); }
			} while (line != null);
			reader.close();

			
			p.waitFor();
			//Runtime runtime = Runtime.getRuntime();
			//Process p = runtime.exec("cmd /c start " + "copy" + " "
			//		+ "C:\\Users\\Public\\tmp.doc" + " " + "C:\\Users\\Public\\" + fileName);
			//Thread.sleep(5000);
			myFile.delete();
			myFile = new File(tmpdirReplaced + fileName);
			if (!getParameter("mode").equals("write")) {
				myFile.setReadOnly();
			}
			if (getParameter("mime").equals("application/vnd.ms-powerpoint") || getParameter("mime").equals("application/vnd.ms.powerpoint") || getParameter("mime").equals("application/vnd.powerpoint") || getParameter("mime").equals("application/vnd.openxmlformats-officedocument.presentationml.presentation")) {
				String appli = checkAppli("powerpnt.exe");
				if (appli != null) {
					monAppli = new Exec(appli + "powerpnt.exe", fileName);
					monAppli.start();
				} else {
					textInfo = "Votre application de Microsoft PowerPoint n'a pas ete trouve,";
					repaint();
					monAppli = new Exec("powerpnt.exe", fileName);
					monAppli.start();
				}
			} else if (getParameter("mime").equals("application/vnd.ms.excel") || getParameter("mime").equals("application/vnd.ms-excel") || getParameter("mime").equals("application/vnd.excel") || getParameter("mime").equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
				String appli = checkAppli("excel.exe");
				if (appli != null) {
					monAppli = new Exec(appli + "excel.exe", fileName);
					monAppli.start();
				} else {
					textInfo = "Votre instance de Microsoft Excel n'a pas ete trouve,";
					repaint();
					monAppli = new Exec("excel.exe", fileName);
					monAppli.start();
				}
			} else if (getParameter("mime").equals("application/msword") || getParameter("mime").equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
				String appli = checkAppli("winword.exe");
				System.out.println(appli);
				if (appli != null) {
					monAppli = new Exec(appli + "winword.exe", fileName);
					monAppli.start();
				} else {
					textInfo = "Votre instance de Microsoft Word n'a pas ete trouve,";
					repaint();
					monAppli = new Exec("winword.exe", fileName);
					monAppli.start();
				}
			} else if (getParameter("mime").equals("application/vnd.oasis.opendocument.spreadsheet") || getParameter("mime").equals("application/vnd.oasis.opendocument.text") || getParameter("mime").equals("application/vnd.oasis.opendocument.presentation")) {
				String appli = checkAppli("soffice.exe");
				if (appli != null) {
					monAppli = new Exec(appli, fileName);
					monAppli.start();
				} else {
					textInfo = "Votre instance de OpenOffice.org n'a pas ete trouve,";
					repaint();
					monAppli = new Exec("soffice.exe", fileName);
					monAppli.start();
				}
			} else {
				System.out.println("MimeType not recognized "+getParameter("mime"));
				throw new Exception("Invalid MimeType"+getParameter("mime"));
			}
			Thread.sleep(1000);
		} catch (Exception e) {
            System.out.println("Exception in main editingDocument for File "+fileName);
			e.printStackTrace();
		}
	}

	private String checkAppli(String appli) {
		if (appli.equals("soffice.exe")) {
			//LibreOffice
			String values = WindowsReqistry.readRegistry("HKEY_LOCAL_MACHINE\\SOFTWARE\\LibreOffice\\LibreOffice", null);
	    	System.out.println(values);
			if (values != null) {
				String [] value = values.split("\\\n");
				if (value.length >= 1) {
					System.out.println("value.length >= 1");
					for (int i = 0; i < value.length; i++) {
				        values = WindowsReqistry.readRegistry(value[i], "Path");
				        System.out.println(value[i]);
				        System.out.println(values);
				        if (values != null) {
				        	System.out.println("values.length >= 1");
				        	String Newvalues = values.replaceAll("\\n", "");
							File exe = new File (Newvalues.replaceAll("\\r", ""));
					        if (exe.exists()) {
					        	System.out.println(exe + " 1");
					        	return Newvalues.replaceAll("\\r", "");
					        }
							String[] splitedValues = values.split("\\\n");
							splitedValues = splitedValues[2].split("[\\s][\\s][\\s][\\s]");
					        exe = new File (splitedValues[splitedValues.length - 1].replaceAll("\\r", ""));
					        if (exe.exists()) {
					        	System.out.println(exe + " 2");
					        	return splitedValues[splitedValues.length - 1].replaceAll("\\r", "");
					        }
				        }
					}
				}
	    	}
			
			//OpenOffice
			values = WindowsReqistry.readRegistry("HKEY_LOCAL_MACHINE\\SOFTWARE\\OpenOffice.org\\OpenOffice.org", null);
	    	System.out.println(values);
			if (values != null) {
				String [] value = values.split("\\\n");
				if (value.length >= 1) {
					System.out.println("value.length >= 1");
					for (int i = 0; i < value.length; i++) {
				        values = WindowsReqistry.readRegistry(value[i], "Path");
				        System.out.println(value[i]);
				        System.out.println(values);
				        if (values != null) {
				        	System.out.println("values.length >= 1");
				        	String Newvalues = values.replaceAll("\\n", "");
							File exe = new File (Newvalues.replaceAll("\\r", ""));
					        if (exe.exists()) {
					        	System.out.println(exe + " 1");
					        	return Newvalues.replaceAll("\\r", "");
					        }
							String[] splitedValues = values.split("\\\n");
							splitedValues = splitedValues[2].split("[\\s][\\s][\\s][\\s]");
					        exe = new File (splitedValues[splitedValues.length - 1].replaceAll("\\r", ""));
					        if (exe.exists()) {
					        	System.out.println(exe + " 2");
					        	return splitedValues[splitedValues.length - 1].replaceAll("\\r", "");
					        }
				        }
					}
				}
	    	}
	    	return null;
		} else if (appli.equals("winword.exe") || appli.equals("excel.exe") || appli.equals("powerpnt.exe")) {
			String values = WindowsReqistry.readRegistry("HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Office", null);
			System.out.println(values);
			if (values != null) {
		    	String [] value = values.split("\\\n");
				for (String ligne : value) {
					ligne = ligne.replaceAll("\\r","");
					String[] splited = ligne.split("\\\\");
					String version = splited[splited.length - 1];
					if (version.equals("9.0") || version.equals("10.0") || version.equals("11.0") || version.equals("12.0") || version.equals("14.0")) {
						 values = WindowsReqistry.readRegistry("HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Office\\" + version + "\\Common\\InstallRoot", "Path");
						 if (values != null) {
							String Newvalues = values.replaceAll("\\n", "");
							File exe = new File (Newvalues.replaceAll("\\r", "") + appli);
					        if (exe.exists()) {
					        	return Newvalues.replaceAll("\\r", "");
					        }
							value = values.split("\\\n");
							for (String line : value) {
								String [] newValue = line.split("[\\s][\\s][\\s][\\s]");
								if (newValue.length >= 1) {
									exe = new File (newValue[newValue.length - 1].replaceAll("\\r", "") + appli);
									if (exe.exists()) {
										return newValue[newValue.length - 1].replaceAll("\\r", "");
									}
								}
							}
						}
					}
				}
			}
			values = WindowsReqistry.readRegistry("HKEY_LOCAL_MACHINE\\SOFTWARE\\Wow6432Node\\Microsoft\\Office", null);
			System.out.println(values);
			if (values != null) {
		    	String [] value = values.split("\\\n");
				for (String ligne : value) {
					ligne = ligne.replaceAll("\\r","");
					String[] splited = ligne.split("\\\\");
					String version = splited[splited.length - 1];
					if (version.equals("9.0") || version.equals("10.0") || version.equals("11.0") || version.equals("12.0") || version.equals("14.0")) {
						values = WindowsReqistry.readRegistry("HKEY_LOCAL_MACHINE\\SOFTWARE\\Wow6432Node\\Microsoft\\Office\\" + version + "\\Common\\InstallRoot", "Path");
						if (values != null) {
							value = values.split("\\\n");
							for (String line : value) {
								line = line.replaceAll("\\n", "");
								String [] newValue = line.split("[\\s][\\s][\\s][\\s]");
								if (newValue.length >= 1) {
							        File exe = new File (newValue[newValue.length - 1].replaceAll("\\r", "") + appli);
							        if (exe.exists()) {
							        	return newValue[newValue.length - 1].replaceAll("\\r", "");
							        }
								}
					        }
						}
					}
				}
			}
		}
		return null;
	}


	public void start() {
		if (myFile != null && monAppli != null) {
			lastModified = myFile.lastModified();
			myFile.setWritable(true);
			//paint();
			while (true) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					save();
					if (myFile != null) {
						myFile.delete();
					}
					jso.eval("Close()");
					break;
				}
				if (lastModified < myFile.lastModified()) {
					lastModified = myFile.lastModified();
					save();
				}
				if (!monAppli.isAlive()) {
					if (myFile != null) {
						myFile.delete();
					}
					try {
						jso.eval("Close()");
						break;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			if (myFile != null) {
				myFile.delete();
			}
			try {
				jso.eval("Close()");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void stop() {
		myFile.setWritable(true);
		try {
			jso.eval("Close()");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//destroy monAppli
		if (monAppli != null) {
			monAppli.interrupt();
		}
		if (myFile != null) {
			myFile.delete();
		}
	}

	public String convertStreamToString(java.io.InputStream is) {
		try {
			return new java.util.Scanner(is).useDelimiter("\\A").next();
		} catch (java.util.NoSuchElementException e) {
			return "";
		}
	}

	public void save() {
		try {
			HttpURLConnection huc = (HttpURLConnection) url.openConnection();
			// text += " huc=" + huc.toString() + "\n";
			huc.setUseCaches(false);
			huc.setDoOutput(true);
			huc.setRequestMethod("PUT");
			huc.addRequestProperty("ticket", getParameter("ticket"));
			huc.connect();
			DataOutputStream os = new DataOutputStream(huc.getOutputStream());
			// text += " os=" + os.toString() + "\n";
			FileInputStream fis = new FileInputStream(myFile);
			byte[] buffer = new byte[1024];
			int count = 0;
			while ((count = fis.read(buffer)) > 0) {
				os.write(buffer, 0, count);
			}
			fis.close();
			os.close();
			os.flush();
			huc.disconnect();

			InputStream inputStream;
			int responseCode = huc.getResponseCode();
			if ((responseCode >= 200) && (responseCode <= 202)) {
				inputStream = huc.getInputStream();
				// text += convertStreamToString(inputStream);
				int j;
				while ((j = inputStream.read()) > 0) {
					System.out.println(j);
				}

			} else {
				inputStream = huc.getErrorStream();
				// text += convertStreamToString(inputStream);
			}
			huc.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getFileName(String url) {
		String[] urlSansTicket = url.split("\\?");
		String[] urlDecompose = urlSansTicket[0].split("/");
		return urlDecompose[urlDecompose.length - 1];
	}

	public void paint(Graphics g) {
		update(g);
	}

	public void update(Graphics g) {
		if (!textInfo.equals("")) {
			 g.setColor(Color.BLACK);
			 g.drawString(textInfo, 5, 10);
			 g.drawString("veuillez contacter votre administrateur.", 5, 25);
		}
	}
}
