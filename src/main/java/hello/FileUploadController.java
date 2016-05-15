package hello;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileUploadController {

    @RequestMapping(value="/upload", method=RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody List<Page> handleFileUpload(HttpServletResponse response, @RequestParam("name") String name,
                                                 @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {

                response.setContentType("text/json");
                PDFParser parser = new PDFParser(file.getInputStream());
                parser.parse();
                COSDocument cosDoc = parser.getDocument();
                PDFTextStripper pdfStripper = new PDFTextStripper();
                PDDocument pdDoc = new PDDocument(cosDoc);
                String parsedText = pdfStripper.getText(pdDoc);

                List<Page> pages = new ArrayList<Page>();
                for (String extrato : parsedText.split("Extrato")) {
                    pages.add(new Page(extrato));
                }

                return pages;
            } catch (Exception e) {
                throw new RuntimeException();
                //return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            throw new RuntimeException();
            //return "You failed to upload " + name + " because the file was empty.";
        }
    }

}