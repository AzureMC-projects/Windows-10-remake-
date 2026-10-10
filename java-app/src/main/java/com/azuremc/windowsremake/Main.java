package com.azuremc.windowsremake;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    static JFrame frame;
    static JDesktopPane desktop;
    static int cascade;
    static final Color BLUE = new Color(0, 120, 215);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Windows Remake — Java Edition");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setSize(1100, 720); frame.setMinimumSize(new Dimension(760, 500));
            frame.setLocationRelativeTo(null); frame.setLayout(new BorderLayout());
            desktop = new JDesktopPane(); desktop.setBackground(new Color(20, 72, 132));
            JPanel wallpaper = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 16));
            wallpaper.setOpaque(false);
            wallpaper.add(icon("🖥\nThis PC", Main::explorer));
            wallpaper.add(icon("📝\nNotepad", Main::notepad));
            wallpaper.add(icon("🧮\nCalculator", Main::calculator));
            wallpaper.add(icon("🌐\nBrowser", Main::browser));
            wallpaper.add(icon("⚙\nSettings", Main::settings));
            desktop.setLayout(new BorderLayout()); desktop.add(wallpaper, BorderLayout.NORTH);
            JPanel taskbar = new JPanel(new BorderLayout()); taskbar.setBackground(new Color(25,32,43));
            JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 4)); left.setOpaque(false);
            JButton start = new JButton("⊞ Start"); start.addActionListener(e -> startMenu(start)); left.add(start);
            for (String[] a : new String[][]{{"📁","File Explorer"},{"🌐","Browser"},{"📝","Notepad"},{"🧮","Calculator"}}) {
                JButton b = new JButton(a[0]); b.setToolTipText(a[1]); b.addActionListener(e -> launch(a[1])); left.add(b);
            }
            JLabel clock = new JLabel(); clock.setForeground(Color.WHITE); clock.setBorder(new EmptyBorder(0,10,0,12));
            Runnable tick = () -> clock.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm   dd/MM/yyyy")));
            tick.run(); new Timer(1000, e -> tick.run()).start();
            taskbar.add(left, BorderLayout.WEST); taskbar.add(clock, BorderLayout.EAST);
            frame.add(desktop, BorderLayout.CENTER); frame.add(taskbar, BorderLayout.SOUTH); frame.setVisible(true);
        });
    }

    static JButton icon(String text, Runnable action) {
        JButton b = new JButton("<html><center>" + text.replace("\n","<br>") + "</center></html>");
        b.setPreferredSize(new Dimension(95,70)); b.setForeground(Color.WHITE); b.setOpaque(false);
        b.setContentAreaFilled(false); b.setBorderPainted(false); b.addActionListener(e -> action.run()); return b;
    }
    static void startMenu(Component anchor) {
        JPopupMenu menu = new JPopupMenu();
        for (String s : new String[]{"File Explorer","Browser","Notepad","Calculator","Paint","Settings","Windows Apps","About"}) {
            JMenuItem i = new JMenuItem(s); i.addActionListener(e -> launch(s)); menu.add(i);
        }
        menu.show(anchor, 0, -menu.getPreferredSize().height);
    }
    static void launch(String s) {
        switch (s.toLowerCase()) {
            case "file explorer","explorer","this pc" -> explorer();
            case "browser" -> browser(); case "notepad" -> notepad(); case "calculator" -> calculator();
            case "paint" -> paint(); case "settings" -> settings(); case "windows apps" -> apps();
            case "about" -> about(); default -> about();
        }
    }
    static void win(String title, int w, int h, Component body) {
        JInternalFrame f = new JInternalFrame(title,true,true,true,true);
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); f.setContentPane(new JPanel(new BorderLayout()));
        f.getContentPane().add(body, BorderLayout.CENTER); f.setSize(w,h);
        int o = (cascade++ % 10) * 22; f.setLocation(45+o,30+o);
        desktop.add(f, JLayeredPane.PALETTE_LAYER); f.setVisible(true);
        try { f.setSelected(true); } catch (Exception ignored) {}
    }
    static void browser() {
        JPanel p = new JPanel(new BorderLayout(8,10)); p.setBorder(new EmptyBorder(16,16,16,16));
        p.add(new JLabel("<html><h2>Windows Remake Browser</h2>Sites open in your default browser, avoiding iframe 'refused to connect' errors.</html>"),BorderLayout.NORTH);
        JTextField address = new JTextField("https://www.google.com");
        JTextArea info = new JTextArea("Type a domain (example.com) or a search phrase, then press Enter or Open website.\n\nThe real page opens in Edge, Chrome, Firefox, or your default browser.");
        info.setEditable(false); info.setLineWrap(true); info.setWrapStyleWord(true);
        JButton go = new JButton("Open website ↗");
        Runnable open = () -> {
            String s = address.getText().trim(); if (s.isEmpty()) return;
            String url = s.matches("(?i)^https?://.*") ? s :
                s.matches("(?i)^[\\w-]+(\\.[\\w-]+)+([/:?#].*)?$") ? "https://" + s :
                "https://www.google.com/search?q=" + java.net.URLEncoder.encode(s, StandardCharsets.UTF_8);
            try { Desktop.getDesktop().browse(URI.create(url)); info.setText("Opened in your default browser:\n" + url); }
            catch (Exception ex) { info.setText("Could not open automatically. Copy this URL into your browser:\n" + url + "\n" + ex.getMessage()); }
        };
        go.addActionListener(e -> open.run()); address.addActionListener(e -> open.run());
        JPanel bar = new JPanel(new BorderLayout(6,0)); bar.add(address,BorderLayout.CENTER); bar.add(go,BorderLayout.EAST);
        JPanel quick = new JPanel(new FlowLayout(FlowLayout.LEFT));
        for (String[] link : new String[][]{{"Google","https://google.com"},{"Wikipedia","https://wikipedia.org"},{"GitHub","https://github.com"}}) {
            JButton b = new JButton(link[0]); b.addActionListener(e -> { address.setText(link[1]); open.run(); }); quick.add(b);
        }
        JPanel north = new JPanel(new BorderLayout(0,8)); north.add(bar,BorderLayout.NORTH); north.add(quick,BorderLayout.SOUTH);
        p.add(north,BorderLayout.CENTER); p.add(new JScrollPane(info),BorderLayout.SOUTH); win("Browser",650,360,p);
    }
    static void notepad() {
        JTextArea area = new JTextArea(); area.setFont(new Font(Font.MONOSPACED,Font.PLAIN,14));
        JPanel p = new JPanel(new BorderLayout()); JPanel tools = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton open = new JButton("Open…"), save = new JButton("Save as…"); tools.add(open); tools.add(save);
        open.addActionListener(e -> { JFileChooser c=new JFileChooser(); if(c.showOpenDialog(frame)==0) try { area.setText(Files.readString(c.getSelectedFile().toPath())); } catch(Exception x){error(x);} });
        save.addActionListener(e -> { JFileChooser c=new JFileChooser(); if(c.showSaveDialog(frame)==0) try { Files.writeString(c.getSelectedFile().toPath(),area.getText()); } catch(Exception x){error(x);} });
        p.add(tools,BorderLayout.NORTH); p.add(new JScrollPane(area),BorderLayout.CENTER); win("Notepad",600,430,p);
    }
    static void calculator() {
        JPanel p=new JPanel(new BorderLayout(6,6)); p.setBorder(new EmptyBorder(10,10,10,10));
        JTextField d=new JTextField("0"); d.setHorizontalAlignment(SwingConstants.RIGHT); d.setFont(new Font("SansSerif",1,24)); p.add(d,BorderLayout.NORTH);
        JPanel grid=new JPanel(new GridLayout(4,4,5,5)); String[] keys={"7","8","9","÷","4","5","6","×","1","2","3","−","C","0","=","+"};
        double[] first={0}; String[] op={""}; boolean[] fresh={true};
        for(String k:keys){ JButton b=new JButton(k); grid.add(b); b.addActionListener(e->{
            if(k.length()==1 && "0123456789.".contains(k)){ d.setText(fresh[0]||d.getText().equals("0")?k:d.getText()+k); fresh[0]=false; }
            else if(k.equals("C")){d.setText("0");first[0]=0;op[0]="";fresh[0]=true;}
            else if(k.equals("=")){try{double v=Double.parseDouble(d.getText()); double r=switch(op[0]){case "+"->first[0]+v;case "−"->first[0]-v;case "×"->first[0]*v;case "÷"->first[0]/v;default->v;};d.setText(Double.isFinite(r)?Double.toString(r):"Error");}catch(Exception x){d.setText("Error");}fresh[0]=true;}
            else {try{first[0]=Double.parseDouble(d.getText());}catch(Exception ignored){}op[0]=k;fresh[0]=true;}
        });} p.add(grid); win("Calculator",320,380,p);
    }
    static void explorer() {
        JPanel p=new JPanel(new BorderLayout(10,10)); p.setBorder(new EmptyBorder(16,16,16,16));
        p.add(new JLabel("<html><h2>This PC</h2>Open your actual Windows folders.</html>"),BorderLayout.NORTH);
        JPanel grid=new JPanel(new GridLayout(0,2,8,8));
        for(String s:new String[]{"Desktop","Documents","Downloads","Pictures","Music","Videos"}){ JButton b=new JButton("📁  "+s);
            b.addActionListener(e->{File f=new File(System.getProperty("user.home"),s);try{Desktop.getDesktop().open(f.exists()?f:new File(System.getProperty("user.home")));}catch(Exception x){error(x);}});grid.add(b);}
        JButton home=new JButton("Open user folder");home.addActionListener(e->{try{Desktop.getDesktop().open(new File(System.getProperty("user.home")));}catch(Exception x){error(x);}});
        p.add(grid,BorderLayout.CENTER);p.add(home,BorderLayout.SOUTH);win("File Explorer",450,330,p);
    }
    static void settings() {
        JPanel p=new JPanel(new GridLayout(0,1,8,8));p.setBorder(new EmptyBorder(18,18,18,18));
        JButton colour=new JButton("Personalisation — change desktop colour"), system=new JButton("System information"), nativeSettings=new JButton("Open Windows Settings");
        colour.addActionListener(e->{Color c=JColorChooser.showDialog(frame,"Desktop colour",desktop.getBackground());if(c!=null)desktop.setBackground(c);});
        system.addActionListener(e->about());nativeSettings.addActionListener(e->{try{new ProcessBuilder("cmd","/c","start","","ms-settings:").start();}catch(Exception x){error(x);}});
        p.add(colour);p.add(system);p.add(nativeSettings);win("Settings",430,260,p);
    }
    static void paint() {
        JPanel canvas=new JPanel(){ java.util.List<Point> pts=new java.util.ArrayList<>(); {setBackground(Color.WHITE);addMouseMotionListener(new java.awt.event.MouseMotionAdapter(){public void mouseDragged(java.awt.event.MouseEvent e){pts.add(e.getPoint());repaint();}});}
            protected void paintComponent(Graphics g){super.paintComponent(g);g.setColor(BLUE);for(Point q:pts)g.fillOval(q.x,q.y,5,5);}};
        win("Paint",560,380,canvas);
    }
    static void apps(){ JPanel p=new JPanel(new GridLayout(0,2,8,8));p.setBorder(new EmptyBorder(16,16,16,16));
        for(String s:new String[]{"File Explorer","Browser","Notepad","Calculator","Paint","Settings","About"}){JButton b=new JButton(s);b.addActionListener(e->launch(s));p.add(b);}win("Windows Apps",420,320,p);}
    static void about(){JOptionPane.showMessageDialog(frame,"Windows Remake — Java Edition\nA lightweight desktop simulation. It does not replace Windows.\nJava: "+System.getProperty("java.version"),"About",JOptionPane.INFORMATION_MESSAGE);}
    static void error(Exception x){JOptionPane.showMessageDialog(frame,x.getMessage(),"Windows Remake",JOptionPane.ERROR_MESSAGE);}
}
