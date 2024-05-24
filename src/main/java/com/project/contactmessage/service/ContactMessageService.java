package com.project.contactmessage.service;

import com.project.contactmessage.dto.ContactMessageRequest;

import com.project.contactmessage.dto.ContactMessageResponse;
import com.project.contactmessage.entity.ContactMessage;
import com.project.contactmessage.mapper.ContactMessageMapper;
import com.project.contactmessage.messages.Messages;
import com.project.contactmessage.repository.ContactMessageRepository;
import com.project.exception.ConflictException;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.response.business.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;
    private final ContactMessageMapper createContactMessage;

    //SAVE
    public ResponseMessage<ContactMessageResponse> save(ContactMessageRequest contactMessageRequest) {

        ContactMessage contactMessage = createContactMessage.requestToContactMessage(contactMessageRequest);
        ContactMessage savedContactMessage = contactMessageRepository.save(contactMessage);
        return ResponseMessage.<ContactMessageResponse>builder()
                .message("Contact Message Created Successfully")
                .httpStatus(HttpStatus.CREATED)
                .object(createContactMessage.contactMessageToResponse(savedContactMessage))
                .build();
    }

//GETALL

    public Page<ContactMessageResponse> getAll(int page, int size, String sort, String type) {
        Pageable pageable=PageRequest.of(page, size, Sort.by(sort).ascending() );
        if (Objects.equals(type, "desc")){

            pageable=PageRequest.of(page, size, Sort.by(sort).descending());
        }
        return contactMessageRepository.findAll(pageable).map(createContactMessage::contactMessageToResponse);

    }

    public ContactMessage findById(Long id) {

        return contactMessageRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Student not found by id! id: " + id));
    }




    public Page<ContactMessageResponse> searchByEmail(String email, int page, int size, String sort, String type) {
Pageable pageable=PageRequest.of(page, size, Sort.by(sort).ascending());

if (Objects.equals(type, "desc")){
    pageable=PageRequest.of(page, size, Sort.by(sort).descending());

}
      return contactMessageRepository.findByEmailEquals(email, pageable).map(createContactMessage::contactMessageToResponse);

    }

    public Page<ContactMessageResponse> searchBySubject(String subject, int page, int size, String sort, String type) {
    Pageable pageable=PageRequest.of(page, size, Sort.by(sort).ascending());
    if (Objects.equals(type, "desc")){

        pageable=PageRequest.of(page, size, Sort.by(sort).descending());
    }
    return contactMessageRepository.findBySubjectEquals(subject, pageable).map(createContactMessage::contactMessageToResponse);



    }


    public String deleteById(Long contactMessageId) {
            getContactMessageById(contactMessageId);
        contactMessageRepository.deleteById(contactMessageId);
        return Messages.CONTACT_MESSAGE_DELETED_SUCCESSFULLY;
    }

    public ContactMessage getContactMessageById(Long contactMessageId) {

   return      contactMessageRepository.findById(contactMessageId).orElseThrow(()->new ResourceNotFoundException(Messages.NOT_FOUND_MESSAGE));

    }


    public List<ContactMessage> searchBetweenDates(String beginDateString, String endDateString) {

        try {
            LocalDate beginDate=LocalDate.parse(beginDateString);
            LocalDate endDate=LocalDate.parse(endDateString);

            return  contactMessageRepository.findMessagesBetweenDates(beginDate, endDate);
        } catch (DateTimeException e) {
            throw new ConflictException(Messages.WRONG_DATE_MESSAGE);
        }
    }

    public List<ContactMessage> searchBetweenTimes(String startHourString, String startMinuteString, String endHourString, String endMinuteString) {

        int startHour=Integer.parseInt(startHourString);
        int startMinute=Integer.parseInt(startMinuteString);
        int endHour=Integer.parseInt(endHourString);
        int endMinute=Integer.parseInt(endMinuteString);

        return contactMessageRepository.findMessagesBetweenTimes(startHour, startMinute, endHour, endMinute);

    }
}


    // Not: ******************************************** getAllByPage ***************************************

    // Not: ************************************* searchByEmailByPage ***************************************

    // Not: *************************************** searchBySubjectByPage ***************************************

    // Not: searchByDateBetween ***************************************

    // Not: searchByTimeBetween ***************************************

    // Not: *********************************** deleteByIdParam ***************************************

    // Not: ***************************************** deleteById ***************************************

    // Not: *********************************** getByIdWithParam ***************************************

    // Not: ************************************ getByIdWithPath ***************************************


