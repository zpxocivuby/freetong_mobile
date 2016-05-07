package itaf.framework.merchant.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * 结算单
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzDistStatementDto extends BaseDto {

	private static final long serialVersionUID = 6399102349214996285L;

	// 未发起结算
	public static final Long STATUS_UNPROCESSED = 1L;
	// 已发起结算
	public static final Long STATUS_PROCESSING = 2L;
	// 结算完结
	public static final Long STATUS_PROCESSED = 3L;

	private BzMerchantDto bzMerchantDto;
	private BzDistCompanyDto bzDistCompanyDto;
	private String statementSerialNo;
	private Long statementStatus;
	private Date statementTime;
	private BigDecimal merchantReceivableAmount;
	private BigDecimal merchantReceivedAmount;
	private BigDecimal distCompanyReceivableAmount;
	private BigDecimal distCompanyReceivedAmount;
	private BigDecimal zzAmount;
	private List<BzDistStatementItemDto> bzDistStatementItemDtos = new ArrayList<BzDistStatementItemDto>();

	public BzDistStatementDto() {
		//
	}

	public BzMerchantDto getBzMerchantDto() {
		return this.bzMerchantDto;
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

	public String getStatementSerialNo() {
		return statementSerialNo;
	}

	public void setStatementSerialNo(String statementSerialNo) {
		this.statementSerialNo = statementSerialNo;
	}

	public Long getStatementStatus() {
		return statementStatus;
	}

	public void setStatementStatus(Long statementStatus) {
		this.statementStatus = statementStatus;
	}

	public Date getStatementTime() {
		return statementTime;
	}

	public void setStatementTime(Date statementTime) {
		this.statementTime = statementTime;
	}

	public BigDecimal getMerchantReceivableAmount() {
		return merchantReceivableAmount;
	}

	public void setMerchantReceivableAmount(BigDecimal merchantReceivableAmount) {
		this.merchantReceivableAmount = merchantReceivableAmount;
	}

	public BigDecimal getMerchantReceivedAmount() {
		return merchantReceivedAmount;
	}

	public void setMerchantReceivedAmount(BigDecimal merchantReceivedAmount) {
		this.merchantReceivedAmount = merchantReceivedAmount;
	}

	public BigDecimal getDistCompanyReceivableAmount() {
		return distCompanyReceivableAmount;
	}

	public void setDistCompanyReceivableAmount(
			BigDecimal distCompanyReceivableAmount) {
		this.distCompanyReceivableAmount = distCompanyReceivableAmount;
	}

	public BigDecimal getDistCompanyReceivedAmount() {
		return distCompanyReceivedAmount;
	}

	public void setDistCompanyReceivedAmount(
			BigDecimal distCompanyReceivedAmount) {
		this.distCompanyReceivedAmount = distCompanyReceivedAmount;
	}

	public List<BzDistStatementItemDto> getBzDistStatementItemDtos() {
		return bzDistStatementItemDtos;
	}

	public void setBzDistStatementItemDtos(
			List<BzDistStatementItemDto> bzDistStatementItemDtos) {
		this.bzDistStatementItemDtos = bzDistStatementItemDtos;
	}

}
