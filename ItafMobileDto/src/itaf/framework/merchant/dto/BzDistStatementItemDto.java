package itaf.framework.merchant.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 结算单
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzDistStatementItemDto extends BaseDto {

	private static final long serialVersionUID = 8018367167655512528L;

	// 状态待配送
	public static final Long STATUS_UNPROCESSED = 1L;
	// 状态待收款
	public static final Long STATUS_PROCESSING = 2L;
	// 状态结束
	public static final Long STATUS_PROCESSED = 3L;

	private BzDistStatementDto bzDistStatementDto;
	private BzDistOrderDto bzDistOrderDto;
	private BzMerchantDto bzMerchantDto;
	private BzDistCompanyDto bzDistCompanyDto;
	private Long statementStatus;
	private Date statementTime;
	private BigDecimal merchantReceivableAmount;
	private BigDecimal merchantReceivedAmount;
	private BigDecimal distCompanyReceivableAmount;
	private BigDecimal distCompanyReceivedAmount;

	public BzDistStatementItemDto() {
	}

	public BzDistStatementDto getBzDistStatementDto() {
		return bzDistStatementDto;
	}

	public void setBzDistStatementDto(BzDistStatementDto bzDistStatementDto) {
		this.bzDistStatementDto = bzDistStatementDto;
	}

	public BzDistOrderDto getBzDistOrderDto() {
		return this.bzDistOrderDto;
	}

	public void setBzDistOrderDto(BzDistOrderDto bzDistOrderDto) {
		this.bzDistOrderDto = bzDistOrderDto;
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

}
