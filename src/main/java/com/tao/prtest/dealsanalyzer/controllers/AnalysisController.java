package com.tao.prtest.dealsanalyzer.controllers;

import com.tao.prtest.dealsanalyzer.dal.models.AnalysisSummary;
import com.tao.prtest.dealsanalyzer.service.AnalysisService;
//import com.tao.prtest.dealsanalyzer.service.PerCurrencyResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AnalysisController {

    private final AnalysisService analysisService;

    @Autowired
    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/checkResults")
    public String checkResults(@RequestParam("fileName") String fileName, RedirectAttributes redirectAttributes, Model model) {

        AnalysisSummary analysisSummary = analysisService.getAnalysisSummary(fileName);
        if (analysisSummary != null) {
            System.out.println(analysisSummary.fileName);

            model.addAttribute("results", analysisSummary);
        }

        return "index";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model) throws InterruptedException {

        if(file.isEmpty()){
            redirectAttributes.addFlashAttribute("error",
                    "File was not uploaded or is empty");
            return "redirect:/";
        }

        //check if file previously processed
        AnalysisSummary previousResultForFile = analysisService.getAnalysisSummary(file.getOriginalFilename());

        if (previousResultForFile != null) {
            redirectAttributes.addFlashAttribute("error",
                    "File previously uploaded!");
            return "redirect:/";
        }

        //start the analysis process
        AnalysisSummary analysisSummary = analysisService.analyze(file);
        redirectAttributes.addFlashAttribute("results", analysisSummary);

        redirectAttributes.addFlashAttribute("message",
                "Processing done, process took: " + analysisSummary.executionTimeInMilis / 1000 + " seconds !");

        return "redirect:/";
    }
}
