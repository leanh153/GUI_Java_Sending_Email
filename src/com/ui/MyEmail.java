/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ui;

import com.business.MyMail;
import com.entity.MailMessage;
import com.entity.SMTPServer;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author leanh
 */
public class MyEmail extends JFrame implements ActionListener, ItemListener {

    // Declare needed java swing components
    private JTextField txtFrom, txtTo, txtSubject, txtUserName, txtFilePath;
    private JPasswordField txtPassword;
    private JComboBox<SMTPServer> cbxServer;
    private JTextArea txtMessage;
    private JButton btnSend, btnSelect;
    private File attachment;
    
    public MyEmail() throws HeadlessException {
        // Set windows title
        super("Send E-mail Client Gmail");
        // create exit button exit when click close button
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // JFrame's layout is borderLayout. Use default layout
        // not layout setting needed
        setBackground(SystemColor.window);
        initView();
        pack();
    }

    // init component add add to JFrame, devided to two panel:page start and page
    // page end
    private void initView() {
        // ============= Create 2 panel ===========
        GridLayout gl = new GridLayout(0, 2, 0, 10);
        JPanel pnlPageStart = new JPanel(gl);
        pnlPageStart.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel pnlPageEnd = new JPanel(new BorderLayout());
        pnlPageEnd.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel pnlFileChooser = new JPanel(new BorderLayout());
        pnlFileChooser.setBorder(new EmptyBorder(10, 0, 10, 0));
        // ============= Finish Create 2 panel ===========

        // =============  Create component ===========
        txtFrom = new JTextField();
        txtTo = new JTextField();
        txtSubject = new JTextField();
        // create Jcombobox and add items, to dispay string not SMTPServer
        // it self must override method tostring in SMTPServer and yse adItem
        // method
        cbxServer = new JComboBox();
        // google mail ssl
        cbxServer.addItem(
                new SMTPServer("smtp.gmail.com", "SSL", "465", "Gmail"));
        // google mail startTLS
        cbxServer.addItem(
                new SMTPServer("smtp.gmail.com", "StartTLS", "587", "Gmail"));
        // hot mail
        cbxServer.addItem(
                new SMTPServer("smtp.live.com", "StartTLS", "587", "Outlook or HotMail"));
        // yahoo mail
        cbxServer.addItem(
                new SMTPServer("smtp.mail.yahoo.com", "SSL", "465", "Yahoo"));
        txtUserName  = new JTextField();
        txtPassword = new JPasswordField();

        txtFilePath = new JTextField();
        btnSelect = new JButton("Attachment");

        txtMessage = new JTextArea(10, 40);
        btnSend = new JButton("Send E-mail");
        // ============= Finish Create component ===========

        // ============= add component to top panel  ===========
        pnlPageStart.add(new JLabel("From:"));
        pnlPageStart.add(txtFrom);
        pnlPageStart.add(new JLabel("To:"));
        pnlPageStart.add(txtTo);
        pnlPageStart.add(new JLabel("Subject:"));
        pnlPageStart.add(txtSubject);
        pnlPageStart.add(new JLabel("SMTPServer:"));
        pnlPageStart.add(cbxServer);
        pnlPageStart.add(new JLabel("User Name:"));
        pnlPageStart.add(txtUserName);
        pnlPageStart.add(new JLabel("Password:"));
        pnlPageStart.add(txtPassword);
        pnlPageStart.add(new JLabel("Message:"));
        // ============= Finish add component to top panel  ===========

        // ============= Add component to file chooser panel ===========
        pnlFileChooser.add(txtFilePath, BorderLayout.CENTER);
        pnlFileChooser.add(btnSelect, BorderLayout.LINE_END);
        // ============= Finish Add component to file chooser panel ===========

        // ============= Add component to bottom ===========
        pnlPageEnd.add(txtMessage, BorderLayout.PAGE_START);
        pnlPageEnd.add(pnlFileChooser, BorderLayout.CENTER);
        pnlPageEnd.add(btnSend, BorderLayout.PAGE_END);
        // ============= Finish Add component to bottom ===========

        // ============= Add panel to Frame===========
        add(pnlPageStart, BorderLayout.PAGE_START);
        add(pnlPageEnd, BorderLayout.PAGE_END);
        // ============= Fnish add panel to Frame===========

        // ============= Add listener===========
        cbxServer.addItemListener(this);
        btnSelect.addActionListener(this);
        btnSend.addActionListener(this);
        // ============= Add listener===========
    }

    // Get message from fields
    private MailMessage getMailMessage() {
        MailMessage mailMessage = new MailMessage();
        mailMessage.setFrom(txtFrom.getText().trim());
        mailMessage.setTo(txtTo.getText().trim());
        mailMessage.setSubject(txtSubject.getText());
        mailMessage.setMessage(txtMessage.getText());
        mailMessage.setAttachment(attachment);
        return mailMessage;
    }

    // Show message 
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Message",
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSelect) {
            getAttacmentFile();
            return;
        }

        MailMessage msg = getMailMessage();
        
        String message = "Message sent to " + msg.getTo();
        try {
            Session session = MyMail.getMailSession((SMTPServer) cbxServer
                    .getSelectedItem(), txtUserName.getText().trim(),
                    txtPassword.getText().trim());
            MyMail.sendEmail(msg, session);

        } catch (MessagingException | IOException ex) {
            Logger.getLogger(MyEmail.class.getName()).log(Level.SEVERE, null, ex);
            message = ex.getMessage();
        }

        showMessage(message);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        setTitle("Send E-mail Client " + ((SMTPServer) cbxServer.getSelectedItem())
                .getProvider());
    }

    private void getAttacmentFile() {
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            attachment = fileChooser.getSelectedFile();
            txtFilePath.setText(attachment.getAbsolutePath());
        }
    }

}
