package com.xxq.competition.service;

import com.microsoft.schemas.office.office.STInsetMode;
import com.xxq.competition.entity.Qbank;
import com.xxq.competition.mapper.QbankMapper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QbankService {

    @Autowired
    QbankMapper qbankMapper;

    public void addNewQuestion(Qbank qbank) {
        qbankMapper.insertQuestion(qbank);
    }

    public void updateQuestion(Qbank qbank) {
        qbankMapper.updateQuestion(qbank);
    }

    public Map selectQuestion(Integer id) {
        return qbankMapper.selectQuestion(id);
    }

    public void deleteQuestion(Integer id) {
        qbankMapper.deleteQuestion(id);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public boolean importQuestionExcelFile(String fileName, MultipartFile file) throws Exception {

        boolean notNull = false;
        List<Qbank> questionList = new ArrayList<Qbank>();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            System.out.println("文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }

        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }

        Sheet sheet = wb.getSheetAt(0);
        if (null != sheet) {
            notNull = true;
        }

        Qbank qbank;

        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (null == row) {
                continue;
            }

            qbank = new Qbank();

//            if (!row.getCell(0).getCellType().equals(1)) {
//                System.out.println();
//                System.out.println("导入失败(第" + (i + 1) + "行,题目请设为文本格式)");
//            }
            //从Excel读取题目
            row.getCell(0).setCellType(CellType.STRING);
            String title = row.getCell(0).getStringCellValue();
            if (title == null || title.isEmpty()) {
                System.out.println("导入失败(第" + (i + 1) + "行,题目为空)");
            }
            qbank.setTitle(title);
            //从Excel读取答案A
            row.getCell(1).setCellType(CellType.STRING);
            String answera = row.getCell(1).getStringCellValue();
            if (answera == null || answera.isEmpty()) {
                System.out.println("导入失败(第" + (i + 1) + "行,答案A为空)");
            }
            qbank.setAnswer_a(answera);
            //从Excel读取答案B
            row.getCell(2).setCellType(CellType.STRING);
            String answerb = row.getCell(2).getStringCellValue();
            if (answerb == null || answerb.isEmpty()) {
                System.out.println("导入失败(第" + (i + 1) + "行,答案B为空)");
            }
            qbank.setAnswer_b(answerb);
            //从Excel读取答案C
            row.getCell(3).setCellType(CellType.STRING);
            String answerc = row.getCell(3).getStringCellValue();
            if (answerc == null || answerc.isEmpty()) {
                System.out.println("导入失败(第" + (i + 1) + "行,答案C为空)");
            }
            qbank.setAnswer_c(answerc);
            //从Excel读取答案D
            row.getCell(4).setCellType(CellType.STRING);
            String answerd = row.getCell(4).getStringCellValue();
            if (answerd == null || answerd.isEmpty()) {
                System.out.println("导入失败(第" + (i + 1) + "行,答案D为空)");
            }
            qbank.setAnswer_d(answerd);
            //从Excel读取正确答案
            row.getCell(5).setCellType(CellType.STRING);
            String rightAnwser = row.getCell(5).getStringCellValue();
            if (rightAnwser == null || rightAnwser.isEmpty()) {
                System.out.println("导入失败(第" + (i + 1) + "行,正确答案为空)");
            }
            qbank.setRight_answer(rightAnwser);
            //从Excel读取轮次为空
//            row.getCell(6).setCellType(CellType.STRING);
//            Integer turnId = Integer.valueOf(row.getCell(6).getStringCellValue());
//            if (turnId == null) {
//                System.out.println("导入失败(第" + (i + 1) + "行,轮次为空)");
//            }
//            qbank.setTurn_id(turnId);

            questionList.add(qbank);
        }
        //写入数据库
        for (Qbank question : questionList) {
            String title = question.getTitle();
            int cnt = qbankMapper.selectByTitle(title);
            if (cnt==0){
                qbankMapper.insertQuestion(question);
                System.out.println("插入： "+question);
            }else {
                qbankMapper.updateByTitle(question);
                System.out.println("更新： "+question);
            }
        }

        return notNull;
    }

}
