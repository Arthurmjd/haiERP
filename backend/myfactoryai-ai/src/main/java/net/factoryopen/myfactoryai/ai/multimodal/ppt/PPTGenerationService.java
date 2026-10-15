package net.factoryopen.myfactoryai.ai.multimodal.ppt;

import org.apache.poi.xslf.usermodel.*;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PPTGenerationService {

    public byte[] generateReport(String title, List<ReportSection> sections) throws Exception {
        try (XMLSlideShow ppt = new XMLSlideShow()) {
            ppt.setPageSize(new Dimension(1280, 720));

            createCoverSlide(ppt, title);
            for (ReportSection section : sections) {
                createContentSlide(ppt, section.getHeading(), section.getContent());
            }
            createEndSlide(ppt);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ppt.write(baos);
            return baos.toByteArray();
        }
    }

    private void createCoverSlide(XMLSlideShow ppt, String title) {
        XSLFSlide slide = ppt.createSlide();

        XSLFTextBox titleBox = slide.createTextBox();
        titleBox.setText(title);
        titleBox.setAnchor(new Rectangle(50, 250, 1100, 100));
        XSLFTextRun titleRun = titleBox.getTextParagraphs().get(0).getTextRuns().get(0);
        titleRun.setFontSize(44.0);
        titleRun.setBold(true);

        XSLFTextBox subBox = slide.createTextBox();
        subBox.setText("FactoryOpen AI 智能生成");
        subBox.setAnchor(new Rectangle(50, 380, 1100, 50));
        subBox.getTextParagraphs().get(0).getTextRuns().get(0).setFontSize(20.0);
    }

    private void createContentSlide(XMLSlideShow ppt, String heading, String content) {
        XSLFSlide slide = ppt.createSlide();

        XSLFTextBox headingBox = slide.createTextBox();
        headingBox.setText(heading);
        headingBox.setAnchor(new Rectangle(50, 40, 1100, 60));
        XSLFTextRun headingRun = headingBox.getTextParagraphs().get(0).getTextRuns().get(0);
        headingRun.setFontSize(28.0);
        headingRun.setBold(true);

        XSLFTextBox lineBox = slide.createTextBox();
        lineBox.setText("————————————————————");
        lineBox.setAnchor(new Rectangle(50, 110, 1100, 20));

        XSLFTextBox contentBox = slide.createTextBox();
        contentBox.setText(content);
        contentBox.setAnchor(new Rectangle(50, 150, 1100, 450));
        contentBox.getTextParagraphs().get(0).getTextRuns().get(0).setFontSize(16.0);
    }

    private void createEndSlide(XMLSlideShow ppt) {
        XSLFSlide slide = ppt.createSlide();

        XSLFTextBox thankBox = slide.createTextBox();
        thankBox.setText("感谢观看\n\nFactoryOpen AI 智能助手");
        thankBox.setAnchor(new Rectangle(50, 250, 1100, 150));
        XSLFTextRun thankRun = thankBox.getTextParagraphs().get(0).getTextRuns().get(0);
        thankRun.setFontSize(36.0);
        thankRun.setBold(true);
    }

    public static class ReportSection {
        private String heading;
        private String content;

        public ReportSection(String heading, String content) {
            this.heading = heading;
            this.content = content;
        }

        public String getHeading() { return heading; }
        public String getContent() { return content; }
    }
}