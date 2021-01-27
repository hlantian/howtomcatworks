package com.zxiaoyao.htw.ex06;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @Description
 * @Author hlantian
 * @Date 2021/1/27 15:24
 */
public class MySwingApp extends JFrame {
    JButton exitButton = new JButton();
    JTextArea jTextArea = new JTextArea();
    String dir = System.getProperty("user.dir");
    String filename = "temp.txt";

    public MySwingApp() throws HeadlessException {
        exitButton.setText("退出");
        exitButton.setBounds(new Rectangle(304, 248, 76, 37));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitButton_actionPerformed(e);
            }
        });
        this.getContentPane().setLayout(null);
        jTextArea.setText("点击按钮退出");
        jTextArea.setBounds(new Rectangle(9,7,371,235));
        this.getContentPane().add(exitButton,null);
        this.getContentPane().add(jTextArea,null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setBounds(0,0,400,330);
        this.setVisible(true);
        initliaze();

    }

    private void initliaze(){
      File file = new File(dir,filename);
      try {
          System.out.println("Creating temporary file.");
          file.createNewFile();
      }catch (Exception e){
          e.printStackTrace();
          System.out.println("Failed creating temporary file");
      }
    }

    void exitButton_actionPerformed(ActionEvent e) {
        shutdown();
        System.exit(0);
    }

    private void shutdown() {
        File file = new File(dir, filename);
        if (file.exists()) {
            System.out.println("Deleting temporary file .");
            file.delete();
        }
    }

    public static void main(String[] args) {
        MySwingApp app = new MySwingApp();
    }
}
