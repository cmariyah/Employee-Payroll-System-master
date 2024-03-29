package org.payroll.Employee;

import org.payroll.Main;
import org.payroll.Manager.DashboardFrame;
import org.payroll.UserTypeFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;



public class EmployeeFrame extends JFrame implements Runnable {
    private JPanel EmployeePanel;
    private JLabel JLblClock;
    private JTextField JTFIdEmp;
    private JLabel JLblDate;
    private JButton JBtnClockIn;
    private JButton JBtnClockOut;
    private JLabel JLblTitle;
    private JButton JBtnLogin;
    private JButton JBtnBack;

    int day, month, year,seconds, minutes, hours;
    String id;

    public EmployeeFrame(){
        super();
        setTitle("EMPLOYEE PAYROLL SYSTEM");
        setContentPane(EmployeePanel);
        setMinimumSize(new Dimension(500, 400));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Thread t = new Thread(this::run);
        t.start();
        setResizable(false);
        setVisible(true);

        if (JBtnClockIn.isVisible() && JBtnClockOut.isVisible()){

            JBtnClockIn.setVisible(false);
            JBtnClockOut.setVisible(false);
        }

        JBtnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                String id = JTFIdEmp.getText();
                if (Main.dbManager.existsEmployeeID(JTFIdEmp.getText())){
                    JOptionPane.showMessageDialog(
                            null,
                            "Login Successful",
                            "Login Successful",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    JBtnClockIn.setVisible(true);
                    JBtnClockOut.setVisible(true);
                    JBtnLogin.setVisible(false);
                }
                else{
                    JOptionPane.showMessageDialog(
                            null,
                            "Wrong username or password",
                            "Login Failed",
                            JOptionPane.ERROR_MESSAGE
                    );

                    JTFIdEmp.setText("");
                }
            }
        });


        JBtnClockIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(Main.dbManager.VerifyClockin(JTFIdEmp.getText()))){
                    Main.dbManager.PunchIn(JTFIdEmp.getText());
                    JOptionPane.showMessageDialog(
                            null,
                            "Clock-In Successful",
                            "Clock-In Successful",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
                else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Clock-In Failed\nYou already Clock-In for today",
                            "Clock-In Failed",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });

        JBtnClockOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Main.dbManager.VerifyClockin(JTFIdEmp.getText())){
                    if (!(Main.dbManager.VerifyClockOut(JTFIdEmp.getText()))) {
                        Main.dbManager.PunchOut(JTFIdEmp.getText());
                        Main.dbManager.InsertEmployeeSalaryFromAttandance(JTFIdEmp.getText());
                        JOptionPane.showMessageDialog(
                                null,
                                "Clock-Out Successful",
                                "Clock-Out Successful",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                    else {
                        JOptionPane.showMessageDialog(
                                null,
                                "Clock-Out Failed\nYou already Clock-Out for today",
                                "Clock-Out Failed",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                }
                else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Clock-Out Failed\nYou have not yet Clock-In for today",
                            "Clock-Out Failed",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        });

        JBtnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                (new UserTypeFrame()).setVisible(true);
                dispose();
            }
        });
    }

    @Override
    public void run() {
            int c = 1;
            while (c == 1) {
                Calendar cal = new GregorianCalendar();
                day = cal.get((Calendar.DAY_OF_MONTH));
                month = cal.get(Calendar.MONTH);
                year = cal.get(Calendar.YEAR);

                seconds = cal.get(Calendar.SECOND);
                minutes = cal.get(Calendar.MINUTE);
                hours = cal.get(Calendar.HOUR);

                SimpleDateFormat sdf12 = new SimpleDateFormat("HH:mm:ss aa");
                Date dat = cal.getTime();
                String time12 = sdf12.format(dat);

                JLblClock.setText(time12);

                SimpleDateFormat sdfdate = new SimpleDateFormat("dd/MM/yyyy");
                Date dat1 = cal.getTime();
                String strdate = sdfdate.format(dat1);

                JLblDate.setText(strdate);
            }
    }
}
