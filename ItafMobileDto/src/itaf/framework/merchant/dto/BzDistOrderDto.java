package itaf.framework.merchant.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.order.dto.BzOrderDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * 配送单
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzDistOrderDto extends BaseDto {

	private static final long serialVersionUID = -6778902603190333299L;
	// 状态待配送
	public static final Long STATUS_WAIT_DIST = 1L;
	// 状态待收款
	public static final Long STATUS_WAIT_COLLECTION = 2L;
	// 状态结束
	public static final Long STATUS_FINISHED = 3L;
	// 距离
	public static final int ORDER_TYPE_DISTANCE = 1;
	// 信用度
	public static final int ORDER_TYPE_CREDIT = 2;
	// 时间
	public static final int ORDER_TYPE_TIME = 3;

	private BzMerchantDto bzMerchantDto;
	private BzDistCompanyDto bzDistCompanyDto;
	private BzInvoiceDto bzInvoiceDto;
	private BzOrderDto bzOrderDto;
	private Long orderStatus;
	private BigDecimal orderDistance;
	private Long orderDirection;
	private String orderSerialNo;
	private Date orderEdc;
	private Long orderNum;
	private String distAddress;
	private String distContactPerson;
	private String distContactNo;
	private String distPostcode;
	private Long orderStatementStatus;
	private BzCollectionOrderDto bzCollectionOrderDto;
	private List<BzDistOrderItemDto> bzDistOrderItemDtos = new ArrayList<BzDistOrderItemDto>();

	public BzDistOrderDto() {
	}

	public BzDistOrderDto(Date orderEdc) {
		this.orderEdc = orderEdc;
	}

	public BzMerchantDto getBzMerchantDto() {
		return bzMerchantDto;
	}

	public void setBzMerchantDto(BzMerchantDto bzMerchantDto) {
		this.bzMerchantDto = bzMerchantDto;
	}

	public BzDistCompanyDto getBzDistCompanyDto() {
		return bzDistCompanyDto;
	}

	public void setBzDistCompanyDto(BzDistCompanyDto bzDistCompanyDto) {
		this.bzDistCompanyDto = bzDistCompanyDto;
	}

	public BzInvoiceDto getBzInvoiceDto() {
		return bzInvoiceDto;
	}

	public void setBzInvoiceDto(BzInvoiceDto bzInvoiceDto) {
		this.bzInvoiceDto = bzInvoiceDto;
	}

	public BzOrderDto getBzOrderDto() {
		return bzOrderDto;
	}

	public void setBzOrderDto(BzOrderDto bzOrderDto) {
		this.bzOrderDto = bzOrderDto;
	}

	public Long getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Long orderStatus) {
		this.orderStatus = orderStatus;
	}

	public BigDecimal getOrderDistance() {
		return orderDistance;
	}

	public void setOrderDistance(BigDecimal orderDistance) {
		this.orderDistance = orderDistance;
	}

	public Long getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(Long orderDirection) {
		this.orderDirection = orderDirection;
	}

	public String getOrderSerialNo() {
		return orderSerialNo;
	}

	public void setOrderSerialNo(String orderSerialNo) {
		this.orderSerialNo = orderSerialNo;
	}

	public Date getOrderEdc() {
		return orderEdc;
	}

	public void setOrderEdc(Date orderEdc) {
		this.orderEdc = orderEdc;
	}

	public Long getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public String getDistAddress() {
		return this.distAddress;
	}

	public void setDistAddress(String distAddress) {
		this.distAddress = distAddress;
	}

	public String getDistContactPerson() {
		return this.distContactPerson;
	}

	public void setDistContactPerson(String distContactPerson) {
		this.distContactPerson = distContactPerson;
	}

	public String getDistContactNo() {
		return this.distContactNo;
	}

	public void setDistContactNo(String distContactNo) {
		this.distContactNo = distContactNo;
	}

	public String getDistPostcode() {
		return this.distPostcode;
	}

	public void setDistPostcode(String distPostcode) {
		this.distPostcode = distPostcode;
	}

	public Long getOrderStatementStatus() {
		return this.orderStatementStatus;
	}

	public void setOrderStatementStatus(Long orderStatementStatus) {
		this.orderStatementStatus = orderStatementStatus;
	}

	public BzCollectionOrderDto getBzCollectionOrderDto() {
		return bzCollectionOrderDto;
	}

	public void setBzCollectionOrderDto(
			BzCollectionOrderDto bzCollectionOrderDto) {
		this.bzCollectionOrderDto = bzCollectionOrderDto;
	}

	public List<BzDistOrderItemDto> getBzDistOrderItemDtos() {
		return this.bzDistOrderItemDtos;
	}

	public void setBzDistOrderItemDtos(
			List<BzDistOrderItemDto> bzDistOrderItemDtos) {
		this.bzDistOrderItemDtos = bzDistOrderItemDtos;
	}

}
