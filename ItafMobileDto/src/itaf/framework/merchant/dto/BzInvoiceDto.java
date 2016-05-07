package itaf.framework.merchant.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.order.dto.BzOrderDto;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 发货单
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzInvoiceDto extends BaseDto {

	private static final long serialVersionUID = 454066059359001817L;

	private BzMerchantDto bzMerchantDto;
	private BzOrderDto bzOrderDto;
	private BzDistCompanyDto bzDistCompanyDto;
	private String invoiceSerialNo;
	private Long invoiceStatus;
	private Long invoiceNum;
	private String distAddress;
	private String distContactPerson;
	private String distContactNo;
	private String distPostcode;
	private List<BzInvoiceItemDto> bzInvoiceItemDtos = new ArrayList<BzInvoiceItemDto>();
	private BzDistOrderDto bzDistOrderDto;

	public BzInvoiceDto() {
		//
	}

	public BzMerchantDto getBzMerchantDto() {
		return bzMerchantDto;
	}

	public void setBzMerchantDto(BzMerchantDto bzMerchantDto) {
		this.bzMerchantDto = bzMerchantDto;
	}

	public BzOrderDto getBzOrderDto() {
		return bzOrderDto;
	}

	public void setBzOrderDto(BzOrderDto bzOrderDto) {
		this.bzOrderDto = bzOrderDto;
	}

	public BzDistCompanyDto getBzDistCompanyDto() {
		return bzDistCompanyDto;
	}

	public void setBzDistCompanyDto(BzDistCompanyDto bzDistCompanyDto) {
		this.bzDistCompanyDto = bzDistCompanyDto;
	}

	public String getInvoiceSerialNo() {
		return invoiceSerialNo;
	}

	public void setInvoiceSerialNo(String invoiceSerialNo) {
		this.invoiceSerialNo = invoiceSerialNo;
	}

	public Long getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(Long invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public Long getInvoiceNum() {
		return invoiceNum;
	}

	public void setInvoiceNum(Long invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

	public String getDistAddress() {
		return distAddress;
	}

	public void setDistAddress(String distAddress) {
		this.distAddress = distAddress;
	}

	public String getDistContactPerson() {
		return distContactPerson;
	}

	public void setDistContactPerson(String distContactPerson) {
		this.distContactPerson = distContactPerson;
	}

	public String getDistContactNo() {
		return distContactNo;
	}

	public void setDistContactNo(String distContactNo) {
		this.distContactNo = distContactNo;
	}

	public String getDistPostcode() {
		return distPostcode;
	}

	public void setDistPostcode(String distPostcode) {
		this.distPostcode = distPostcode;
	}

	public List<BzInvoiceItemDto> getBzInvoiceItemDtos() {
		return bzInvoiceItemDtos;
	}

	public void setBzInvoiceItemDtos(List<BzInvoiceItemDto> bzInvoiceItemDtos) {
		this.bzInvoiceItemDtos = bzInvoiceItemDtos;
	}

	public BzDistOrderDto getBzDistOrderDto() {
		return bzDistOrderDto;
	}

	public void setBzDistOrderDto(BzDistOrderDto bzDistOrderDto) {
		this.bzDistOrderDto = bzDistOrderDto;
	}

}
