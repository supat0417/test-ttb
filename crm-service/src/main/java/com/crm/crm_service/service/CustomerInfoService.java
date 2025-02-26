package com.crm.crm_service.service;

import com.crm.crm_service.model.CustomerInfoRequest;
import com.crm.crm_service.model.CustomerInfoResponse;
import com.crm.crm_service.model.CustomerInquiryResponse;
import com.crm.crm_service.model.CustomerSaleProjection;
import com.crm.crm_service.model.CustomerSorting;
import com.crm.crm_service.model.CustomerSortingResponse;
import com.crm.crm_service.model.SaleRequest;
import com.crm.crm_service.model.SalesResponse;
import com.crm.crm_service.model.entity.CustomerInfoEntity;
import com.crm.crm_service.model.entity.SalesEntity;
import com.crm.crm_service.repository.CustomerInfoJoinSaleRepository;
import com.crm.crm_service.repository.CustomerInfoRepository;
import com.crm.crm_service.repository.SaleRepository;
import com.crm.crm_service.util.BusinessExeption;
import com.crm.crm_service.util.ResponseModel;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CustomerInfoService {

    private final CustomerInfoRepository customerInfoRepository;
    private final SaleRepository saleRepository;
    private final CustomerInfoJoinSaleRepository customerInfoJoinSaleRepository;

    @Autowired
    public CustomerInfoService(CustomerInfoRepository customerInfoRepository, SaleRepository saleRepository, CustomerInfoJoinSaleRepository customerInfoJoinSaleRepository) {
        this.customerInfoRepository = customerInfoRepository;
        this.saleRepository = saleRepository;
        this.customerInfoJoinSaleRepository = customerInfoJoinSaleRepository;
    }

    public CustomerInfoResponse insertCustomerInfo(CustomerInfoRequest request) {
        CustomerInfoEntity entity = customerInfoRepository.save(new CustomerInfoEntity()
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .setIsVIP(request.getIsVIP())
                .setStatusCode(request.getStatusCode())
                .setCustomerDate(OffsetDateTime.now())
                .setCreatedOn(OffsetDateTime.now()));

        SalesResponse salesResponse = null;
        if (ObjectUtils.isNotEmpty(request.getSale())) {
            salesResponse = this.insertSale(new SaleRequest()
                            .setCustomerId(entity.getCustomerId())
                            .setSaleAmount(request.getSale().getSaleAmount()))
                    .setSaleDate(paseOffet(request.getSale().getSaleDate()));
        }

        return new CustomerInfoResponse()
                .setCustomerId(entity.getCustomerId())
                .setFirstName(entity.getFirstName())
                .setLastName(entity.getLastName())
                .setIsVIP(entity.getIsVIP())
                .setStatusCode(entity.getStatusCode())
                .setCustomerDate(entity.getCustomerDate())
                .setCreatedOn(entity.getCreatedOn())
                .setModifiedOn(entity.getModifiedOn())
                .setSale(salesResponse);
    }

    public SalesResponse insertSale(@Valid SaleRequest request) {
        Optional<CustomerInfoEntity> customer =
                customerInfoRepository.findById(request.getCustomerId());

        if (ObjectUtils.isEmpty(customer)) {
            throw new BusinessExeption(ResponseModel.StatusCode.NOTFOUND);
        } else {
            SalesEntity salesEntity = saleRepository.save(new SalesEntity()
                    .setCustomerId(request.getCustomerId())
                    .setSaleAmount(request.getSaleAmount())
                    .setSaleDate(paseOffet(request.getSaleDate())));

            return new SalesResponse()
                    .setCustomerId(salesEntity.getCustomerId())
                    .setSaleAmount(salesEntity.getSaleAmount())
                    .setSaleDate(salesEntity.getSaleDate());
        }
    }

    public List<CustomerInquiryResponse> getCustomerInfo(Integer customerId) {

        List<CustomerInquiryResponse> customerInquiryResponse;

        List<CustomerSaleProjection> customerEntity = null;
        if (ObjectUtils.isNotEmpty(customerId)) {
            customerEntity = customerInfoJoinSaleRepository.findCustomerByCustomerId(customerId);
            List<CustomerInquiryResponse.Sale> sales =
                    customerEntity.stream()
                            .filter(entity -> Objects.equals(customerId, entity.getCustomerId())).toList()
                            .stream().map(cusId -> new CustomerInquiryResponse.Sale()
                                    .setSaleAmount(cusId.getSaleAmount())
                                    .setSaleDate(String.valueOf(cusId.getSaleDate()))).toList();
            customerInquiryResponse = customerEntity.stream()
                    .filter(entity -> Objects.equals(customerId, entity.getCustomerId())).toList().stream().findFirst()
                    .stream().map(entity ->
                            new CustomerInquiryResponse()
                                    .setCustomerId(entity.getCustomerId())
                                    .setFirstName(entity.getFirstName())
                                    .setLastName(entity.getLastName())
                                    .setCustomerDate(String.valueOf(entity.getCustomerDate()))
                                    .setIsVIP(entity.getIsvip())
                                    .setStatusCode(entity.getStatusCode())
                                    .setCreatedOn(String.valueOf(entity.getCreateOn()))
                                    .setModifiedOn(String.valueOf(entity.getModifiedOn()))
                                    .setSales(sales)).toList();
        } else {
            customerInquiryResponse = new ArrayList<>();
            customerEntity = customerInfoJoinSaleRepository.findCustomerAll();

            List<CustomerSaleProjection> customerDistrict = customerEntity.stream()
                    .collect(Collectors.toMap(CustomerSaleProjection::getCustomerId, p -> p, (existing, replacement) -> existing))
                    .values().stream().toList();

            List<CustomerSaleProjection> finalCustomerEntity = customerEntity;
            customerDistrict.forEach(district -> {

                List<CustomerInquiryResponse.Sale> sales =
                        finalCustomerEntity.stream()
                                .filter(entity -> Objects.equals(district.getCustomerId(), entity.getCustomerId())).toList()
                                .stream().map(cusId -> new CustomerInquiryResponse.Sale()
                                        .setSaleAmount(cusId.getSaleAmount())
                                        .setSaleDate(String.valueOf(cusId.getSaleDate()))).toList();

                customerInquiryResponse.add(new CustomerInquiryResponse()
                        .setCustomerId(district.getCustomerId())
                        .setFirstName(district.getFirstName())
                        .setLastName(district.getLastName())
                        .setCustomerDate(String.valueOf(district.getCustomerDate()))
                        .setIsVIP(district.getIsvip())
                        .setStatusCode(district.getStatusCode())
                        .setCreatedOn(String.valueOf(district.getCreateOn()))
                        .setModifiedOn(String.valueOf(district.getModifiedOn()))
                        .setSales(sales));
            });
        }
        return customerInquiryResponse;
    }

    public List<CustomerSortingResponse> getCustomerSortingByYear() {
        List<CustomerSortingResponse> responses = new ArrayList<>();

        List<CustomerSorting> entityResponse = customerInfoJoinSaleRepository.findCustomerSorting();
        List<String> years = entityResponse.stream()
                .collect(Collectors.toMap(CustomerSorting::getSaleYear, p -> p, (existing, replacement) -> existing))
                .values().stream().map(CustomerSorting::getSaleYear).toList();

        years.forEach(y ->
                responses.add(new CustomerSortingResponse()
                    .setYear(y)
                    .setCustomers(entityResponse.stream().filter(en -> ObjectUtils.equals(y, en.getSaleYear()))
                            .map(enFilter ->
                                    new CustomerSortingResponse.Customer()
                                            .setFirstName(enFilter.getFirstName())
                                            .setLastName(enFilter.getLastName())
                                            .setTotalAmount(enFilter.getTotalSales())).toList())));
        return responses;
    }


    private OffsetDateTime paseOffet(String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(value, formatter);
        return localDate.atStartOfDay().atOffset(ZoneOffset.UTC);
    }

}
