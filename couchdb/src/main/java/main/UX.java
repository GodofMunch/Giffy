package main;

import data.Gif;
import data.GifDAOImpl;
import db.CouchConnector;
import org.ektorp.CouchDbConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UX extends JFrame {

    private JMenuItem edit;
    private JMenuItem delete;
    private JMenuItem add;
    private JButton search;
    private JButton browse;
    private GifDAOImpl dao;
    private CouchDbConnector db;
    private java.util.List<Gif> gifs;
    private ImageIcon icon = new ImageIcon(("C:/Users/HP/Desktop/giffy.png"));
    private JFrame f;
    private JButton next;
    private JButton previous;
    private int gifSelector;

    public UX(){
        CouchConnector connector = new CouchConnector();
        db = connector.getConnector();
        dao = new GifDAOImpl(db);

        icon.setImage(icon.getImage().getScaledInstance(400, 300, Image.SCALE_DEFAULT));
        JLabel label = new JLabel(icon);
        add = new JMenuItem("Add Gif");
        delete = new JMenuItem("Delete Gif");
        edit = new JMenuItem("Edit Gif");
        f = new JFrame("Giffy");
        f.setSize(new Dimension(450, 600));
        f.setLayout(new FlowLayout());
        f.getContentPane().add(label);

        browse = new JButton("Browse");
        search = new JButton("Search");

        f.getContentPane().add(browse, FlowLayout.CENTER);
        f.getContentPane().add(search, FlowLayout.CENTER);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        next = new JButton("Next");
        previous = new JButton("Previous");
        f.pack();
        f.setLocationRelativeTo(null);
        gifSelector = 0;

        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(e.getSource().equals(browse)){
                    gifs = dao.getGifs();
                    if(gifs != null) {
                        ImageIcon newImage = dao.getAttachment(gifs.get(gifSelector));
                        icon.setImage(newImage.getImage().getScaledInstance(400, 300, Image.SCALE_DEFAULT));
                        f.setVisible(false);
                        f.getContentPane().add(next, FlowLayout.LEFT);
                        f.getContentPane().add(previous, FlowLayout.LEFT);
                        f.setVisible(true);
                    }
                }
                if(e.getSource().equals(search)){
                    String userIn = JOptionPane.showInputDialog("Please input a search term:");

                }

                if(e.getSource().equals(next)) {
                    gifSelector++;
                    if (gifs.size() == gifSelector)
                        gifSelector = 0;
                    ImageIcon newImage = dao.getAttachment(gifs.get(gifSelector));
                    icon.setImage(newImage.getImage().getScaledInstance(400, 300, Image.SCALE_DEFAULT));
                    f.setVisible(false);
                    f.setVisible(true);
                }

                if(e.getSource().equals(previous)){
                    gifSelector--;
                    if (gifSelector < 0)
                        gifSelector = gifs.size()-1;
                    ImageIcon newImage = dao.getAttachment(gifs.get(gifSelector));
                    icon.setImage(newImage.getImage().getScaledInstance(400, 300, Image.SCALE_DEFAULT));
                    f.setVisible(false);
                    f.setVisible(true);
                }
                if(e.getSource() == add){
                    String inputPath = JOptionPane.showInputDialog("Please select Path of gif to upload");
                    String inputTags = JOptionPane.showInputDialog("Please enter tags separated my commas");

                    dao.uploadGif(inputPath, inputTags);
                    gifs = dao.getGifs();
                }
                if(e.getSource()== delete){
                    if(gifs != null) {
                        dao.deleteGif(gifs.get(gifSelector));
                        gifs = dao.getGifs();
                        f.setVisible(false);
                        f.setVisible(true);
                    }
                }

                if(e.getSource()==edit){
                    if(gifs != null){
                        String tags =  "";
                        for(int i = 0; i < gifs.get(gifSelector).getTags().length; i ++) {
                            tags += gifs.get(gifSelector).getTags()[i] + "   ";
                        }

                        String input = JOptionPane.showInputDialog("Please add to the " +
                                "following with ',' between new tags! \n\nCurrent Tags: " + tags);

                        dao.editGif(gifs.get(gifSelector), input.split(","));
                    }
                }
            }
        };

        edit.addActionListener(actionListener);
        delete.addActionListener(actionListener);
        add.addActionListener(actionListener);
        search.addActionListener(actionListener);
        browse.addActionListener(actionListener);
        next.addActionListener(actionListener);
        previous.addActionListener(actionListener);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        menu.add(add);
        menu.add(delete);
        menu.add(edit);
        f.setJMenuBar(menuBar);

        f.setVisible(true);

    }
}
