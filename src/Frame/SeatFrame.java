package Frame;

import helper.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class SeatFrame extends JFrame {
    int seatId;
    public TimePanel timePanel;
    boolean[] isAvailebelInPanel=new boolean[24];
    public static int state =0;
    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    class TimePanel extends JPanel{
//        Integer num =8;
        JLabel[] labels;
        TimePanel(){
            setLayout(new GridLayout(2,7));
            labels =new JLabel[14];
            for(int i=0;i<14;i++){
                labels[i] =new JLabel(Integer.toString(i+8),JLabel.CENTER);
                labels[i].setOpaque(true);
                Color color = new Color(193,255,193);
                labels[i].setBackground(color);
                add(labels[i]);
            }
        }

        public void setColorFrame(boolean[] isAvailebel){
            // 对应8-21点
            for(int i=8;i<22;i++){
                isAvailebelInPanel[i]=isAvailebel[i];
                Color color = isAvailebel[i]?new Color(193,255,193):new Color(255,106,106);
                labels[i-8].setBackground(color);
            }
        }
    }

    class FunctionPanel extends JPanel{
        JTextField startTime;
        JTextField endTime;
        JButton reserveButton;
        FunctionPanel(){
            startTime=new JTextField();
            endTime=new JTextField();
            reserveButton =new JButton("预定");
            reserveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int start = Integer.parseInt(startTime.getText());
                    int end = Integer.parseInt(endTime.getText());
//                    System.out.println(start);
//                    System.out.println(end);
                    if(start>end||start<8||start>21||end<8||end>21){
                        JOptionPane.showMessageDialog(null, "输入时间不符合规则","错误提示框！", JOptionPane.ERROR_MESSAGE);
                    } else if(!isOk(start,end)){
                        JOptionPane.showMessageDialog(null, "该时间段已被预约","错误提示框！", JOptionPane.ERROR_MESSAGE);
                    }else{
                        try {
                            Controller.getController().someoneReserveSeat(seatId,start,end);
                            new Thread(()->{
                                try {
                                    Controller.getController().resetSeatFrame(seatId);
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                }
                            }).start();
                            JOptionPane.showMessageDialog(null, "预定成功","消息提示框！", JOptionPane.PLAIN_MESSAGE);
                        } catch (SQLException ex) {
                            if(ex.getMessage().equals("you have reserve this seat")){
                                JOptionPane.showMessageDialog(null, "你已经预定过这个座位了","错误提示框！", JOptionPane.ERROR_MESSAGE);
                            }
                            ex.printStackTrace();
                        }
                    }
//                    System.out.println(isOk(start,end));
                }
            });
            setLayout(new GridLayout(1,3));
            add(startTime);
            add(endTime);
            add(reserveButton);
        }
    }

    SeatFrame(int id){
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        seatId =id;
        Container contentPane=getContentPane();
        contentPane.setLayout(new BorderLayout());
        timePanel=new TimePanel();
        contentPane.add(timePanel,BorderLayout.CENTER);
        contentPane.add(new FunctionPanel(),BorderLayout.SOUTH);
        setTitle("详细信息");
        setSize(600,200);
        for(int i=0;i<24;i++){
            isAvailebelInPanel[i] =true;
        }
    }

    private boolean isOk(int start ,int end){
        for(int i=start;i<=end;i++){
            if(!isAvailebelInPanel[i]){
                return false;
            }
        }
        return true;
    }


    public void setTimePanel(boolean[] isAvailebel){
        timePanel.setColorFrame(isAvailebel);
    }
    public static void main(String[] args) {
        SeatFrame seatFrame=new SeatFrame(0);
        boolean[] temp =new boolean[24];
        for(int i=0;i<24;i++){
            temp[i]= true;
        }
        temp[9]=false;
        temp[10]=false;
        temp[11]=false;
        temp[12]=false;


        temp[17]=false;
        temp[18]=false;
        temp[19]=false;

        seatFrame.timePanel.setColorFrame(temp);
        seatFrame.setVisible(true);
    }
}
