package Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SeatFrame extends JFrame {
    int seatId;

    class TimePanel extends JPanel{
        Integer num =8;
        TimePanel(boolean[] isAvailebel){
            setLayout(new GridLayout(2,7));
            for(int i=1;i<15;i++){
                JLabel label =new JLabel(Integer.toString(num++),JLabel.CENTER);
                label.setOpaque(true);
                Color color = isAvailebel[i-1]?new Color(193,255,193):new Color(255,106,106);
                label.setBackground(color);
                add(label);
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
                    System.out.println(start);
                    System.out.println(end);
                }
            });
            setLayout(new GridLayout(1,3));
            add(startTime);
            add(endTime);
            add(reserveButton);
        }
    }

    SeatFrame(){
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane=getContentPane();
        contentPane.setLayout(new BorderLayout());
        boolean[] temp =new boolean[14];
        for(int i=0;i<14;i++){
            temp[i]= true;
        }
        temp[5]=false;
        contentPane.add(new TimePanel(temp),BorderLayout.CENTER);
        contentPane.add(new FunctionPanel(),BorderLayout.SOUTH);
        setTitle("详细信息");
        setSize(600,200);
    }

    public void setSeatInfo(){

    }

    public static void main(String[] args) {
        SeatFrame seatFrame=new SeatFrame();
        seatFrame.setVisible(true);
    }
}
