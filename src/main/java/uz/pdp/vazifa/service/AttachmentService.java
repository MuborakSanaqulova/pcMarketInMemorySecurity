package uz.pdp.vazifa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.vazifa.common.ApiResponse;
import uz.pdp.vazifa.entity.Attachment;
import uz.pdp.vazifa.entity.AttachmentContent;
import uz.pdp.vazifa.repository.AttachmentContentRepository;
import uz.pdp.vazifa.repository.AttachmentRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

@Service
public class AttachmentService {

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;


    public Page<Attachment> getAttachments(Pageable pageable) {
        return attachmentRepository.findAll(pageable);
    }

    public ApiResponse findOne(Integer id, HttpServletResponse response) throws IOException {

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()){
            Optional<AttachmentContent> byAttachmentId = attachmentContentRepository.findByAttachmentId(id);
            if (byAttachmentId.isPresent()){
                response.setContentType(optionalAttachment.get().getContentType());
                response.setHeader("Content-Disposition", "attachment; filename = \""+optionalAttachment.get().getOriginalName()+"\"");

                FileCopyUtils.copy(byAttachmentId.get().getMainContent(),response.getOutputStream());
                return new ApiResponse("success", true);
            }
        }
        return new ApiResponse("not found", false);
    }


    public ApiResponse post(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());

        if (file != null) {
            String originalFileName = file.getOriginalFilename();
            long size = file.getSize();
            String contentType = file.getContentType();
            Attachment attachment = new Attachment();
            attachment.setOriginalName(originalFileName);
            attachment.setSize(size);
            attachment.setContentType(contentType);
            Attachment savedAttachment = attachmentRepository.save(attachment);

            AttachmentContent attachmentContent = new AttachmentContent();
            attachmentContent.setMainContent(file.getBytes());
            attachmentContent.setAttachment(savedAttachment);
            attachmentContentRepository.save(attachmentContent);

            return new ApiResponse("success", true);
        }
        return new ApiResponse("something is wrong", false);
    }

    public ApiResponse delete(Integer id) {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isPresent()){
            Optional<AttachmentContent> byAttachmentId = attachmentContentRepository.findByAttachmentId(id);
            byAttachmentId.ifPresent(attachmentContent -> attachmentContentRepository.delete(attachmentContent));

            attachmentRepository.deleteById(id);
            return new ApiResponse("successfully deleted", true);
        }
        return new ApiResponse("not found", false);
    }
}
