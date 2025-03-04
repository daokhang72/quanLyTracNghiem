package Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import BLL.ExamBLL;
import BLL.TestBLL;
import DTO.ExamDTO;
import DTO.TestDTO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class QuanLyExam extends JPanel {
    private JTable tableTest, tableExam;
    private DefaultTableModel modelTest, modelExam;
    private JTextField txtExamCode, txtExamOrder;
    ExamBLL examBll;
    public QuanLyExam() {
    	examBll = new ExamBLL();
    	
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Quản Lý Exam", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        String[] testColumns = {"testID", "testCode", "testTitle"};
        modelTest = new DefaultTableModel(testColumns, 0);
        tableTest = new JTable(modelTest);
        JScrollPane scrollTest = new JScrollPane(tableTest);

        String[] examColumns = {"exCode","exOrder","testCode","ex_quesIDs"};
        modelExam = new DefaultTableModel(examColumns, 0);
        tableExam = new JTable(modelExam);
        JScrollPane scrollExam = new JScrollPane(tableExam);

        JPanel panelTables = new JPanel(new GridLayout(1, 2, 10, 10));
        panelTables.add(scrollTest);
        panelTables.add(scrollExam);
        add(panelTables, BorderLayout.CENTER);

        JPanel panelExamForm = new JPanel();
        panelExamForm.setBorder(BorderFactory.createTitledBorder("Thông tin Exam"));

        panelExamForm.add(new JLabel("Mã Đề:"));
        txtExamOrder = new JTextField("", 15);
        panelExamForm.add(txtExamOrder);

        FadeButton btnAddExam = new FadeButton(new Color(230, 247, 230),new Color(0, 150, 136),new Color(0, 0, 0),"Thêm");
        FadeButton btnDeleteExam = new FadeButton(new Color(230, 247, 230),new Color(0, 150, 136),new Color(0, 0, 0),"Xóa");
        panelExamForm.add(btnAddExam);
        panelExamForm.add(btnDeleteExam);


        add(panelExamForm, BorderLayout.SOUTH);

        tableTest.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = tableTest.getSelectedRow();
            if (selectedRow != -1) {
                loadExamData(modelTest.getValueAt(selectedRow, 1).toString());
            }
        });

   
        btnAddExam.addActionListener(e -> {
        	int selectedRowTest = tableTest.getSelectedRow();
            String order = txtExamOrder.getText();
            
            ExamDTO examDto = new ExamDTO((modelTest.getValueAt(selectedRowTest, 1).toString()),order,"1","1");
            examBll.addExam(examDto);
            loadExamData(modelTest.getValueAt(selectedRowTest, 1).toString());
        });

        btnDeleteExam.addActionListener(e -> {
            int selectedRow = tableExam.getSelectedRow();
            int selectedRowTest = tableTest.getSelectedRow();
            if (selectedRow != -1) {
            	examBll.deleteExam((modelExam.getValueAt(selectedRow, 0).toString()));
            	loadExamData(modelTest.getValueAt(selectedRowTest, 1).toString());
            }
        });

        loadTest();
    }
    
    private void loadTest() {
    	modelTest.setRowCount(0);
    	
    	TestBLL testBll = new TestBLL();
    	ArrayList<TestDTO> arrTestDto = testBll.getAllTest();
    	
    	for(int i =0 ; i< arrTestDto.size(); i++) {
    		modelTest.addRow(new Object[] 
    				{
    					arrTestDto.get(i).getTestID(),
    					arrTestDto.get(i).getTestCode(),
    					arrTestDto.get(i).getTestTitle(),
    						});
    	}
    	

    }
    
    private void loadExamData(String testCode) {
        modelExam.setRowCount(0);
        ArrayList<ExamDTO> arrExamDto = examBll.getExamsByTestCode(testCode);
        
        for(int i =0 ; i< arrExamDto.size(); i++) {
        	modelExam.addRow(new Object[] 
    				{
    					arrExamDto.get(i).getExCode(),
    					arrExamDto.get(i).getExOrder(),
    					arrExamDto.get(i).getTestCode(),
    					arrExamDto.get(i).getExQuesIDs(),
    						});
    	}
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Quản Lý Exam");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.add(new QuanLyExam());
        frame.setVisible(true);
    }
}
