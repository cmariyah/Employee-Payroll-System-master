package org.payroll.EmployeeAttendance;

import com.toedter.calendar.JDateChooser;
import org.payroll.Main;
import org.payroll.Manager.DashboardFrame;
import org.payroll.TableToPDF;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class EmployeeAttendanceFrame extends JFrame{
    private JSplitPane EmployeeAttendancePanel;
    private JPanel JPnlEmployeeAtenndance;
    private JLabel JLblTitle;
    private JLabel JLblDate;
    private JPanel JPnlDate;
    private JLabel JLblDateTry;
    private JButton JBtnGet;
    private JScrollPane JSPnlEmpAtten;
    private JTable JTblEmpAttend;
    private JButton JBtnBack;
    private JButton JBtnDownload;

    Calendar cal = Calendar.getInstance();
    JDateChooser dateChooser = new JDateChooser();



    public EmployeeAttendanceFrame() {
        super();
        setTitle("EMPLOYEE PAYROLL SYSTEM");
        setContentPane(EmployeeAttendancePanel);
        setMinimumSize(new Dimension(750, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        setVisible(true);

        Object[][] attendance = Main.dbManager.getAllAttendance();
        String col[] ={"ID","Employee ID","Employee Full Name","Date","Clock In Time","Clock Out Time"};
        JTblEmpAttend.setModel(new DefaultTableModel(attendance, col));

        dateChooser.setDateFormatString("yyyy-MM-dd");

        JPnlDate.add(dateChooser);
        JLblDateTry.setVisible(false);

        JBtnGet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dat = dateChooser.getDate();
                String dat1 = sdf.format(dat);
                JLblDateTry.setVisible(true);
                JLblDateTry.setText(dat1);
                Object[][] Attendance_data = Main.dbManager.getAttendance(dat1);
                JTblEmpAttend.setModel(new DefaultTableModel(Attendance_data, col));
            }
        });

        JBtnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                (new DashboardFrame()).setVisible(true);
                dispose();
            }
        });
        JBtnDownload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JLblDateTry.isVisible()){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date dat = dateChooser.getDate();
                    String dat1 = sdf.format(dat);
                    Object[][] Attendance_data = Main.dbManager.getAttendance(dat1);
                    new TableToPDF().AttandancePDF(Attendance_data);
                }
                else {
                    new TableToPDF().AttandancePDF(attendance);
                }
            }
        });
    }
}
