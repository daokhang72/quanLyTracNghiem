package Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import BLL.AnswerBLL;
import BLL.ExamBLL;
import BLL.QuestionBLL;
import BLL.TestBLL;
import BLL.TopicBLL;
import DTO.AnswerDTO;
import DTO.ExamDTO;
import DTO.QuestionDTO;
import DTO.TestDTO;
import DTO.TopicDTO;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuanLyExam extends JPanel {
	private JTable tableTest, tableExam;
	private DefaultTableModel modelTest, modelExam;
	private JTextField txtTestCode, txtTestTitle, txtNumMedium, txtTestTime, txtNumEasy, txtNumDiff, txtTestLimit,
			txtTestDate, txtTestStatus;
	private JTextField txtExamCode, txtExamOrder;
	private JComboBox cbxTopic;
	JDateChooser dateChooser;
	TestBLL testBll;
	ExamBLL examBll;
	QuestionBLL qsBll;
	AnswerBLL awBll;
	
	public QuanLyExam() {
		examBll = new ExamBLL();
		testBll = new TestBLL();
		qsBll = new QuestionBLL();
		awBll = new AnswerBLL();
		setLayout(new BorderLayout());

		JLabel title = new JLabel("Quản Lý Exam", SwingConstants.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 20));
		add(title, BorderLayout.NORTH);

		String[] testColumns = { "testID", "testCode", "testTitle", "testTime", "tpID", "numEasy", "numMedium",
				"numDiff", "testLimit", "testDate", "testStatus" };
		modelTest = new DefaultTableModel(testColumns, 0);
		tableTest = new JTable(modelTest);
		JScrollPane scrollTest = new JScrollPane(tableTest);

		String[] examColumns = { "exCode", "exOrder", "testCode", "ex_quesIDs" };
		modelExam = new DefaultTableModel(examColumns, 0);
		tableExam = new JTable(modelExam);
		JScrollPane scrollExam = new JScrollPane(tableExam);

		JPanel panelTables = new JPanel(new GridLayout(1, 2, 10, 10));
		panelTables.add(scrollTest);
		panelTables.add(scrollExam);
		add(panelTables, BorderLayout.CENTER);

		JPanel panelTestForm = new JPanel(new GridBagLayout());
		panelTestForm.setBorder(BorderFactory.createTitledBorder("Thông tin Test"));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5); // Khoảng cách giữa các thành phần
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;

		gbc.gridx = 0;
		gbc.gridy = 1;
		panelTestForm.add(new JLabel("Tên Test:"), gbc);

		gbc.gridx = 1;
		txtTestTitle = new JTextField(15);
		panelTestForm.add(txtTestTitle, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		panelTestForm.add(new JLabel("Thời Gian:"), gbc);

		gbc.gridx = 1;
		txtTestTime = new JTextField(15);
		panelTestForm.add(txtTestTime, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		panelTestForm.add(new JLabel("Chủ Đề:"), gbc);

		gbc.gridx = 1;
		cbxTopic = new JComboBox<>();
		panelTestForm.add(cbxTopic, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		panelTestForm.add(new JLabel("Số câu dễ:"), gbc);

		gbc.gridx = 1;
		txtNumEasy = new JTextField(15);
		panelTestForm.add(txtNumEasy, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		panelTestForm.add(new JLabel("Số câu trung bình:"), gbc);

		gbc.gridx = 1;
		txtNumMedium = new JTextField(15);
		panelTestForm.add(txtNumMedium, gbc);

		gbc.gridx = 0;
		gbc.gridy = 6;
		panelTestForm.add(new JLabel("Số câu khó:"), gbc);

		gbc.gridx = 1;
		txtNumDiff = new JTextField(15);
		panelTestForm.add(txtNumDiff, gbc);

		gbc.gridx = 0;
		gbc.gridy = 7;
		panelTestForm.add(new JLabel("Giới hạn thời gian:"), gbc);

		gbc.gridx = 1;
		txtTestLimit = new JTextField(15);
		panelTestForm.add(txtTestLimit, gbc);

		gbc.gridx = 0;
		gbc.gridy = 8;
		panelTestForm.add(new JLabel("Ngày thi:"), gbc);

		gbc.gridx = 1;
		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("yyyy-MM-dd"); // Định dạng ngày tháng
		panelTestForm.add(dateChooser, gbc);

		gbc.gridx = 0;
		gbc.gridy = 10;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;

		JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		FadeButton btnAddTest = new FadeButton(new Color(230, 247, 230), new Color(0, 150, 136), new Color(0, 0, 0),
				"Thêm");
		FadeButton btnDeleteTest = new FadeButton(new Color(230, 247, 230), new Color(0, 150, 136), new Color(0, 0, 0),
				"Xóa");
		

		
		panelButtons.add(btnAddTest);
		panelButtons.add(btnDeleteTest);
		panelTestForm.add(panelButtons, gbc);

		JPanel panelExamForm = new JPanel(new GridBagLayout());
		panelExamForm.setBorder(BorderFactory.createTitledBorder("Thông tin Exam"));

		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.insets = new Insets(5, 5, 5, 5); // Khoảng cách giữa các thành phần
		gbc2.fill = GridBagConstraints.HORIZONTAL;
		gbc2.anchor = GridBagConstraints.WEST;

		// Hàng 1: Mã Đề
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		panelExamForm.add(new JLabel("Mã Đề:"), gbc2);

		gbc2.gridx = 1;
		txtExamOrder = new JTextField("", 15);
		panelExamForm.add(txtExamOrder, gbc2);

		// Hàng 2: Nút Thêm & Xóa (Căn giữa)
		gbc2.gridx = 0;
		gbc2.gridy = 1;
		gbc2.gridwidth = 2;
		gbc2.anchor = GridBagConstraints.CENTER;

		JPanel panelButtonsExam = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		FadeButton btnAddExam = new FadeButton(new Color(230, 247, 230), new Color(0, 150, 136), new Color(0, 0, 0),
				"Thêm");
		FadeButton btnDeleteExam = new FadeButton(new Color(230, 247, 230), new Color(0, 150, 136), new Color(0, 0, 0),
				"Xóa");
		FadeButton btnDocx = new FadeButton(new Color(230, 247, 230), new Color(0, 150, 136), new Color(0, 0, 0),
				"xuất docx");
		
		panelButtonsExam.add(btnAddExam);
		panelButtonsExam.add(btnDeleteExam);
		panelButtonsExam.add(btnDocx);
		
		panelExamForm.add(panelButtonsExam, gbc2);

		JPanel panelForms = new JPanel(new GridLayout(1, 2, 10, 10));
		panelForms.add(panelTestForm);
		panelForms.add(panelExamForm);
		add(panelForms, BorderLayout.SOUTH);

		tableTest.getSelectionModel().addListSelectionListener(e -> {
			int selectedRow = tableTest.getSelectedRow();
			if (selectedRow != -1) {
				loadExamData(modelTest.getValueAt(selectedRow, 1).toString());
			}
		});

		btnAddExam.addActionListener(e -> {
		    int selectedRowTest = tableTest.getSelectedRow();
		    
		    
		    if (selectedRowTest == -1) {
		        JOptionPane.showMessageDialog(QuanLyExam.this, "Vui lòng chọn một đề thi trước khi thêm!");
		        return;
		    }

		    String order = txtExamOrder.getText();
		    
		    if (order.isEmpty()) {
		        JOptionPane.showMessageDialog(QuanLyExam.this, "Vui lòng nhập mã đề");
		        return;
		    }

		    String testCode = modelTest.getValueAt(selectedRowTest, 1).toString();
		    String testId = modelTest.getValueAt(selectedRowTest, 0).toString();
		    List<QuestionDTO> listQuestion = qsBll.getRandomQuestionsByTestID(Integer.parseInt(testId));	
		    String listQuestionString = listQuestion.stream()
		    	    .map(question -> question.toString())
		    	    .collect(Collectors.joining(", "));
		    ExamDTO examDto = new ExamDTO(testCode, order, "1", listQuestionString);
		    examBll.addExam(examDto);
		    loadExamData(testCode);
		});


		btnDeleteExam.addActionListener(e -> {
			int selectedRow = tableExam.getSelectedRow();
			int selectedRowTest = tableTest.getSelectedRow();
			if (selectedRow != -1) {
				examBll.deleteExam((modelExam.getValueAt(selectedRow, 0).toString()));
				loadExamData(modelTest.getValueAt(selectedRowTest, 1).toString());
			}
		});

		btnAddTest.addActionListener(e -> {
			TestDTO newTest = getTestFromForm();

			if (newTest == null) {
				JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại thông tin đầu vào!");
				return;
			}

			boolean isSuccess = testBll.addTest(newTest);

			if (isSuccess) {
				JOptionPane.showMessageDialog(null, "Thêm bài kiểm tra thành công!");
				loadTest(); // Load lại dữ liệu vào bảng
			} else {
				JOptionPane.showMessageDialog(null, "Thêm bài kiểm tra thất bại!");
			}
		});

		btnDeleteTest.addActionListener(e -> {
			int selectedRow = tableTest.getSelectedRow();
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(null, "Vui lòng chọn bài kiểm tra cần xóa!");
				return;
			}

			int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa bài kiểm tra này?",
					"Xác nhận xóa", JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {
				int testID = (int) tableTest.getValueAt(selectedRow, 0);

				boolean isDeleted = testBll.deleteTest(testID);

				if (isDeleted) {
					JOptionPane.showMessageDialog(null, "Xóa bài kiểm tra thành công!");
					loadTest(); // Cập nhật lại dữ liệu
				} else {
					JOptionPane.showMessageDialog(null, "Xóa bài kiểm tra thất bại!");
				}
			}
		});
		
		btnDocx.addActionListener(e -> {
			int selectedRow = tableExam.getSelectedRow();
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(null, "Vui lòng chọn bài kiểm tra cần xuất!");
				return;
			}
			
			try (XWPFDocument document = new XWPFDocument()) {
	            XWPFParagraph titleDocx = document.createParagraph();
	            titleDocx.setAlignment(ParagraphAlignment.CENTER);
	            XWPFRun titleRun = titleDocx.createRun();
	            titleRun.setText("ĐỀ THI");
	            titleRun.setBold(true);
	            titleRun.setFontSize(20);
	            
	            int cauhoi =0;
				String listQuestion = (String) tableExam.getValueAt(selectedRow, 3);
				
				
				for(String id: listQuestion.split(",")) {
					cauhoi = cauhoi+1;
					QuestionDTO question = qsBll.getQuestionByID(Integer.parseInt(id));
					if(question==null) {
						JOptionPane.showMessageDialog(null, "Lỗi Câu Hỏi Không Tồn Tại!");
						return ;
					}
					XWPFParagraph paraQuestion = document.createParagraph();
	                XWPFRun runQuestion = paraQuestion.createRun();
	                runQuestion.setText("Câu " + cauhoi + ": " + question.getQContent());
	                runQuestion.setBold(true);
	                runQuestion.addBreak();

	                ArrayList<AnswerDTO> answers = awBll.dsIDAnswer(Integer.parseInt(id));
	                if(answers==null) {
						JOptionPane.showMessageDialog(null, "Lỗi Đáp Án");
						return ;
					}
	                for (AnswerDTO answer : answers) {
	                    XWPFParagraph paraAnswer = document.createParagraph();
	                    XWPFRun runAnswer = paraAnswer.createRun();
	                    runAnswer.setText("- " + answer.getAwContent());
	                }
				}
				

	            try (FileOutputStream out = new FileOutputStream("DeThi.docx")) {
	                document.write(out);
	                System.out.println("Đề thi đã được tạo thành công: DeThi.docx");
	            }

	        } catch (IOException e1) {
	            System.err.println("Lỗi khi tạo file: " + e1.getMessage());
	        }
			JOptionPane.showMessageDialog(null, "Xuất bài kiểm tra thành công!");
		});
		loadTest();
	}

	private TestDTO getTestFromForm() {
		try {
			int testID = 0;
			String testTitle = txtTestTitle.getText().trim();
			int testTime = Integer.parseInt(txtTestTime.getText().trim());
			int numEasy = Integer.parseInt(txtNumEasy.getText().trim());
			int numMedium = Integer.parseInt(txtNumMedium.getText().trim());
			int numDiff = Integer.parseInt(txtNumDiff.getText().trim());
			int testLimit = Integer.parseInt(txtTestLimit.getText().trim());

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			TopicDTO selectedTopic = (TopicDTO) cbxTopic.getSelectedItem();
			int tpID = selectedTopic.getTpID();
			java.util.Date selectedDate = dateChooser.getDate();
			return new TestDTO(testID, "", testTitle, testTime, tpID, numEasy, numMedium, numDiff, testLimit,
					new java.sql.Date(selectedDate.getTime()), 1);

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng định dạng số!");
			return null;
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, "Ngày kiểm tra không hợp lệ!");
			return null;
		}
	}

	private void loadTest() {
		loadTopics();
		modelTest.setRowCount(0);

		ArrayList<TestDTO> arrTestDto = testBll.getAllTest();

		for (TestDTO test : arrTestDto) {
			modelTest.addRow(new Object[] { test.getTestID(), test.getTestCode(), test.getTestTitle(),
					test.getTestTime(), test.getTpID(), test.getNumEasy(), test.getNumMedium(), test.getNumDiff(),
					test.getTestLimit(), test.getTestDate(), test.getTestStatus() });
		}
	}

	private void loadTopics() {
		cbxTopic.removeAllItems();

		TopicBLL testBll = new TopicBLL();
		ArrayList<TopicDTO> arrTopicDto = testBll.getAllTopic();

		if (arrTopicDto.isEmpty()) {
			cbxTopic.addItem("Không có chủ đề nào");
			return;
		}

		for (TopicDTO topic : arrTopicDto) {
			cbxTopic.addItem(topic);
		}

		cbxTopic.addActionListener(e -> {
			TopicDTO selectedTopic = (TopicDTO) cbxTopic.getSelectedItem();
			if (selectedTopic != null) {
				System.out.println(
						"Chủ đề đã chọn: " + selectedTopic.getTpTitle() + " (ID: " + selectedTopic.getTpID() + ")");
			}
		});
	}

	private void loadExamData(String testCode) {
		modelExam.setRowCount(0);
		ArrayList<ExamDTO> arrExamDto = examBll.getExamsByTestCode(testCode);
		if(arrExamDto==null) {
			return ;
		}
		for (int i = 0; i < arrExamDto.size(); i++) {
			modelExam.addRow(new Object[] { arrExamDto.get(i).getExCode(), arrExamDto.get(i).getExOrder(),
					arrExamDto.get(i).getTestCode(), arrExamDto.get(i).getExQuesIDs(), });
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
