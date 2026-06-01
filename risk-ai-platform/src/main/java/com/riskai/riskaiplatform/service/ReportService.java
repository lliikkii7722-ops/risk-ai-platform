package com.riskai.riskaiplatform.service;

import com.riskai.riskaiplatform.entity.Project;
import com.riskai.riskaiplatform.repository.ProjectRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ReportService {

    private final ProjectRepository projectRepository;

    public ReportService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public byte[] generateProjectsExcelReport() {

        List<Project> projects = projectRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Projects Report");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Project Name");
            header.createCell(2).setCellValue("Complexity");
            header.createCell(3).setCellValue("Planned Budget");
            header.createCell(4).setCellValue("Actual Cost");
            header.createCell(5).setCellValue("Planned Days");
            header.createCell(6).setCellValue("Completed Days");
            header.createCell(7).setCellValue("Total Tasks");
            header.createCell(8).setCellValue("Completed Tasks");
            header.createCell(9).setCellValue("Defects");
            header.createCell(10).setCellValue("Testing Coverage");
            header.createCell(11).setCellValue("Available Hours");
            header.createCell(12).setCellValue("Worked Hours");

            int rowIndex = 1;

            for (Project project : projects) {
                Row row = sheet.createRow(rowIndex++);

                row.createCell(0).setCellValue(project.getId());
                row.createCell(1).setCellValue(project.getProjectName());
                row.createCell(2).setCellValue(project.getComplexity());
                row.createCell(3).setCellValue(project.getPlannedBudget());
                row.createCell(4).setCellValue(project.getActualCost());
                row.createCell(5).setCellValue(project.getPlannedDays());
                row.createCell(6).setCellValue(project.getCompletedDays());
                row.createCell(7).setCellValue(project.getTotalTasks());
                row.createCell(8).setCellValue(project.getCompletedTasks());
                row.createCell(9).setCellValue(project.getDefects());
                row.createCell(10).setCellValue(project.getTestingCoverage());
                row.createCell(11).setCellValue(project.getAvailableHours());
                row.createCell(12).setCellValue(project.getWorkedHours());
            }

            for (int i = 0; i <= 12; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(outputStream);

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Excel report");
        }
    }
}