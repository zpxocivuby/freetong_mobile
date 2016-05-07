package itaf.framework.merchant.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

import java.math.BigDecimal;

/**
 * 
 * 结算单
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzDistStatementSummaryDto extends BaseDto {

	private static final long serialVersionUID = 6399102349214996285L;

	// 状态为1的 未发起结算应收总额
	private BigDecimal unprocessedReceivableAmount;
	// 状态为1的 未发起结算应退总额
	private BigDecimal unprocessedRefundAmount;
	// 状态为2的 已发起结算应收总额
	private BigDecimal processingReceivableAmount;
	// 状态为2的 已发起结算应退总额
	private BigDecimal processingRefundAmount;
	// 状态为3的 结算完结总额
	private BigDecimal processedAmount;

	public BzDistStatementSummaryDto() {
		//
	}

	public BigDecimal getUnprocessedReceivableAmount() {
		return unprocessedReceivableAmount;
	}

	public void setUnprocessedReceivableAmount(
			BigDecimal unprocessedReceivableAmount) {
		this.unprocessedReceivableAmount = unprocessedReceivableAmount;
	}

	public BigDecimal getUnprocessedRefundAmount() {
		return unprocessedRefundAmount;
	}

	public void setUnprocessedRefundAmount(BigDecimal unprocessedRefundAmount) {
		this.unprocessedRefundAmount = unprocessedRefundAmount;
	}

	public BigDecimal getProcessingReceivableAmount() {
		return processingReceivableAmount;
	}

	public void setProcessingReceivableAmount(
			BigDecimal processingReceivableAmount) {
		this.processingReceivableAmount = processingReceivableAmount;
	}

	public BigDecimal getProcessingRefundAmount() {
		return processingRefundAmount;
	}

	public void setProcessingRefundAmount(BigDecimal processingRefundAmount) {
		this.processingRefundAmount = processingRefundAmount;
	}

	public BigDecimal getProcessedAmount() {
		return processedAmount;
	}

	public void setProcessedAmount(BigDecimal processedAmount) {
		this.processedAmount = processedAmount;
	}

}
