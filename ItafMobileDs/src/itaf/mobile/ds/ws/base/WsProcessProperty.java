package itaf.mobile.ds.ws.base;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.cart.dto.BzCartItemDto;
import itaf.framework.certificate.dto.BzApplyDistCertificateDto;
import itaf.framework.certificate.dto.BzApplyRealnameCertificateDto;
import itaf.framework.certificate.dto.BzApplySellingCertificateDto;
import itaf.framework.common.dto.BzAttachmentDto;
import itaf.framework.consumer.dto.BzConsumerDto;
import itaf.framework.consumer.dto.BzUserAddressDto;
import itaf.framework.merchant.dto.BzCollectionOrderDto;
import itaf.framework.merchant.dto.BzDistCompanyDto;
import itaf.framework.merchant.dto.BzDistOrderDto;
import itaf.framework.merchant.dto.BzDistOrderItemDto;
import itaf.framework.merchant.dto.BzDistStatementDto;
import itaf.framework.merchant.dto.BzDistStatementItemDto;
import itaf.framework.merchant.dto.BzDistStatementSummaryDto;
import itaf.framework.merchant.dto.BzInvoiceDto;
import itaf.framework.merchant.dto.BzInvoiceItemDto;
import itaf.framework.merchant.dto.BzMerchantDto;
import itaf.framework.merchant.dto.BzMerchantFavoriteDto;
import itaf.framework.merchant.dto.BzStockOrderDto;
import itaf.framework.merchant.dto.BzStockOrderItemDto;
import itaf.framework.merchant.dto.BzUserDeliveryAddressDto;
import itaf.framework.order.dto.BzOrderDto;
import itaf.framework.order.dto.BzOrderInitDto;
import itaf.framework.order.dto.BzOrderItemDto;
import itaf.framework.order.dto.BzOrderPaymentDto;
import itaf.framework.order.dto.BzOrderRefundDto;
import itaf.framework.order.dto.BzPaymentTypeDto;
import itaf.framework.platform.dto.SysAttachmentDto;
import itaf.framework.platform.dto.SysUserDto;
import itaf.framework.position.dto.BzPositionDto;
import itaf.framework.product.dto.BzProductDto;
import itaf.framework.product.dto.BzProductFavoriteDto;
import itaf.framework.workflow.dto.BzApprovalInfoDto;
import itaf.mobile.core.exception.ClientException;
import itaf.mobile.core.utils.DateUtil;
import itaf.mobile.core.utils.StringHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import android.util.Base64;
import android.util.Log;

/**
 * 处理属性
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年11月14日
 */
public abstract class WsProcessProperty<T> extends WsBaseClient<T> {

	public WsProcessProperty(String serviceUrl) {
		super(serviceUrl);
	}

	@SuppressWarnings("unchecked")
	protected WsPageResult<T> processCallBack(int contentWhat,
			SoapObject soapObject) {
		if (soapObject == null || soapObject.getProperty("return") == null) {
			throw new ClientException("请求发生错误！");
		}
		SoapObject target = (SoapObject) soapObject.getProperty("return");
		WsPageResult<T> result = new WsPageResult<T>();
		result.setStatus(this.getProperty2String(target, "status"));
		result.setErrorMsg(this.getProperty2String(target, "errorMsg"));
		result.setStatusCode(this.getProperty2String(target, "statusCode"));
		result.setCurrentIndex(this.getProperty2Integer(target, "currentIndex"));
		result.setPageSize(this.getProperty2Integer(target, "pageSize"));
		result.setCurrentPage(this.getProperty2Integer(target, "currentPage"));
		result.setTotalCount(this.getProperty2Integer(target, "totalCount"));
		result.setParams((HashMap<String, Object>) decodeBase64(this
				.getProperty2String(target, "paramsString")));
		result.setContent(processContent(target, contentWhat));
		return result;
	}

	// 根据contentWhat来判断返回值
	protected abstract List<T> processContent(SoapObject target, int contentWhat);

	protected Date getProperty2Date(SoapObject target, String property) {
		return getProperty2Date(target, property, null);
	}

	protected Date getPropertyProcessT2Date(SoapObject target, String property,
			String formatPattern) {
		if (StringHelper.isEmpty(formatPattern)) {
			formatPattern = DateUtil.FORMAT_DATE_DEFAULT;
		}
		return getProperty2String(target, property) == null ? null : DateUtil
				.parse(getProperty2String(target, property)
						.replaceAll("T", " "), formatPattern);
	}

	/**
	 * yyyy-MM-dd HH:mm:ss.SSS
	 * 
	 * @param target
	 * @param property
	 * @return yyyy-MM-dd HH:mm:ss.SSS
	 */
	protected Date getProperty2Timestamp(SoapObject target, String property) {
		String result = getProperty2String(target, property);
		if (result == null) {
			return null;
		}
		result = result.replaceAll("T", " ");
		if (result.indexOf("+") > 0) {
			result = result.substring(0, result.indexOf("+"));
		}

		if (result.indexOf(".") > 0) {
			String subString = result.substring(result.indexOf(".") + 1);
			if (subString.length() == 0) {
				result = result + "000";
			} else if (subString.length() == 1) {
				result = result + "00";
			} else if (subString.length() == 2) {
				result = result + "0";
			}
		} else {
			int leg = result.trim().length();
			if (leg == 10) {
				result = result + " 00:00:00.000";
			}
			if (leg == 19) {
				result = result + ".000";
			}
		}
		return DateUtil.parse(result,
				DateUtil.FORMAT_DATETIME_YYYY_MM_DD_HH_MM_SS_SSS);
	}

	protected Date getProperty2Date(SoapObject target, String property,
			String formatPattern) {
		if (StringHelper.isEmpty(formatPattern)) {
			formatPattern = DateUtil.FORMAT_DATE_DEFAULT;
		}
		return getProperty2String(target, property) == null ? null : DateUtil
				.parse(getProperty2String(target, property), formatPattern);
	}

	protected Integer getProperty2Integer(SoapObject target, String property) {
		return getProperty2String(target, property) == null ? 0 : Integer
				.valueOf(getProperty2String(target, property));
	}

	protected Long getProperty2Long(SoapObject target, String property) {
		return getProperty2String(target, property) == null ? null : Long
				.valueOf(getProperty2String(target, property));
	}

	protected Float getProperty2Float(SoapObject target, String property) {
		return getProperty2String(target, property) == null ? null : Float
				.valueOf(getProperty2String(target, property));
	}

	protected Double getProperty2Double(SoapObject target, String property) {
		return getProperty2String(target, property) == null ? 0.0 : Double
				.parseDouble(getProperty2String(target, property));
	}

	protected BigDecimal getProperty2BigDecimal(SoapObject target,
			String property) {
		return getProperty2String(target, property) == null ? new BigDecimal(
				"0.0") : new BigDecimal(getProperty2String(target, property));
	}

	protected String getProperty2String(SoapObject target, String property) {
		String result = null;
		try {
			if (target == null) {
				return null;
			}
			if (target.getProperty(property) != null) {
				result = target.getProperty(property).toString();
				if ("anyType{}".equals(result)) {
					result = null;
				}
			}
		} catch (RuntimeException e) {
			Log.d("illegal property", "没有[" + property + "]属性");
		}

		return result;
	}

	protected Boolean getProperty2Boolean(SoapObject target, String property) {
		return "true".equals(getProperty2String(target, property));
	}

	private SoapObject getSubPropertySoapObject(SoapObject target, String attr) {
		SoapObject result = null;
		try {
			result = (SoapObject) target.getProperty(attr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	protected Object decodeBase64(String base64String) {
		if (StringHelper.isEmpty(base64String)) {
			return new HashMap<String, Object>();
		}
		Object dto = null;
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			byte[] bytes = Base64.decode(base64String, Base64.DEFAULT);
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			dto = ois.readObject();
			return dto;
		} catch (IOException e) {
			Log.e(this.getClass().getName(), e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			Log.e(this.getClass().getName(), e.getMessage());
			e.printStackTrace();
		} finally {
			if (bais != null) {
				try {
					bais.close();
				} catch (IOException e) {
					//
				}
			}
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					//
				}
			}
		}
		return dto;
	}

	protected String encodeBase64(Object obj) {
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			oos.flush();
			return new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
		} catch (IOException e) {
			Log.e(this.getClass().getName(), e.getMessage());
			e.printStackTrace();
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					//
				}
			}
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					//
				}
			}
		}
		return null;
	}

	protected BzProductDto processBzProductDto(SoapObject target) {
		BzProductDto result = new BzProductDto();
		result.setId(getProperty2Long(target, "id"));
		result.setProductColor(getProperty2String(target, "productColor"));
		result.setBzMerchantId(getProperty2Long(target, "bzMerchantId"));
		result.setShelfNum(getProperty2Long(target, "shelfNum"));
		result.setStockNum(getProperty2Long(target, "stockNum"));
		result.setProductOnSale(getProperty2Long(target, "productOnSale"));
		result.setIsStockLimitless(getProperty2Long(target, "isStockLimitless"));
		result.setIsShelfLimitless(getProperty2Long(target, "isShelfLimitless"));
		result.setIsShelfSupport(getProperty2Long(target, "isShelfSupport"));
		result.setProductDescription(getProperty2String(target,
				"productDescription"));
		result.setProductName(getProperty2String(target, "productName"));
		result.setProductPrice(getProperty2BigDecimal(target, "productPrice"));
		result.setProductPurchasePrice(getProperty2BigDecimal(target,
				"productPurchasePrice"));
		result.setIsFavorited(getProperty2Boolean(target, "isFavorited"));
		SoapObject bzMerchantDtoSo = getSubPropertySoapObject(target,
				"bzMerchantDto");
		if (bzMerchantDtoSo != null) {
			result.setBzMerchantDto(processBzMerchantDto(bzMerchantDtoSo));
		}

		SoapObject bzProductCategoryIdsSo = getSubPropertySoapObject(target,
				"bzProductCategoryIdList");
		if (bzProductCategoryIdsSo != null) {
			for (int i = 0; i < bzProductCategoryIdsSo.getPropertyCount(); i++) {
				Object idObj = bzProductCategoryIdsSo.getProperty(i);
				result.getBzProductCategoryIds().add(
						Long.valueOf(idObj.toString()));
			}
		}

		SoapObject bzProductAttachmentIdsSo = getSubPropertySoapObject(target,
				"bzProductAttachmentIdList");
		if (bzProductAttachmentIdsSo != null) {
			for (int i = 0; i < bzProductAttachmentIdsSo.getPropertyCount(); i++) {
				Object idObj = bzProductAttachmentIdsSo.getProperty(i);
				result.getBzProductAttachmentIds().add(
						Long.valueOf(idObj.toString()));
			}
		}
		return result;
	}

	protected BzUserDeliveryAddressDto processBzUserDeliveryAddressDto(
			SoapObject target) {
		BzUserDeliveryAddressDto result = new BzUserDeliveryAddressDto();
		result.setId(getProperty2Long(target, "id"));
		result.setAddressTag(getProperty2String(target, "addressTag"));
		result.setAddress(getProperty2String(target, "address"));
		result.setContactPerson(getProperty2String(target, "contactPerson"));
		result.setContactNo(getProperty2String(target, "contactNo"));
		result.setPostcode(getProperty2String(target, "postcode"));
		result.setSysUserId(getProperty2Long(target, "sysUserId"));
		result.setBzPositionId(getProperty2Long(target, "bzPositionId"));
		SoapObject bzPositionDtoSo = getSubPropertySoapObject(target,
				"bzPositionDto");
		if (bzPositionDtoSo != null) {
			result.setBzPositionDto(processBzPositionDto(bzPositionDtoSo));
		}

		SoapObject sysUserDtoSo = getSubPropertySoapObject(target, "sysUserDto");
		if (sysUserDtoSo != null) {
			result.setSysUserDto(processSysUserDto(sysUserDtoSo));
		}
		return result;
	}

	protected BzUserAddressDto processBzUserAddressDto(SoapObject target) {
		BzUserAddressDto result = new BzUserAddressDto();
		result.setId(getProperty2Long(target, "id"));
		result.setAddressTag(getProperty2String(target, "addressTag"));
		result.setAddress(getProperty2String(target, "address"));
		result.setContactPerson(getProperty2String(target, "contactPerson"));
		result.setContactNo(getProperty2String(target, "contactNo"));
		result.setPostcode(getProperty2String(target, "postcode"));
		SoapObject bzPositionDtoSo = getSubPropertySoapObject(target,
				"bzPositionDto");
		result.setSysUserId(getProperty2Long(target, "sysUserId"));
		result.setBzPositionId(getProperty2Long(target, "bzPositionId"));
		if (bzPositionDtoSo != null) {
			result.setBzPositionDto(processBzPositionDto(bzPositionDtoSo));
		}

		SoapObject sysUserDtoSo = getSubPropertySoapObject(target, "sysUserDto");
		if (sysUserDtoSo != null) {
			result.setSysUserDto(processSysUserDto(sysUserDtoSo));
		}
		return result;
	}

	protected BzPositionDto processBzPositionDto(SoapObject bzPositionDtoSo) {
		BzPositionDto positionDto = new BzPositionDto();
		positionDto.setId(getProperty2Long(bzPositionDtoSo, "id"));
		positionDto.setX(getProperty2BigDecimal(bzPositionDtoSo, "x"));
		positionDto.setY(getProperty2BigDecimal(bzPositionDtoSo, "y"));
		positionDto.setZ(getProperty2BigDecimal(bzPositionDtoSo, "z"));
		return positionDto;
	}

	protected BzCartItemDto processBzCartItemDto(SoapObject target) {
		BzCartItemDto result = new BzCartItemDto();
		result.setId(getProperty2Long(target, "id"));
		result.setBzConsumerId(getProperty2Long(target, "bzConsumerId"));
		result.setBzProductId(getProperty2Long(target, "bzProductId"));
		result.setItemNum(getProperty2Long(target, "itemNum"));
		result.setItemUnitPrice(getProperty2BigDecimal(target, "itemUnitPrice"));
		result.setItemDiscount(getProperty2BigDecimal(target, "itemDiscount"));
		result.setItemPreferential(getProperty2BigDecimal(target,
				"itemPreferential"));
		result.setItemPrice(getProperty2BigDecimal(target, "itemPrice"));
		result.setItemStatus(getProperty2Long(target, "itemStatus"));

		SoapObject bzConsumerDtoSo = getSubPropertySoapObject(target,
				"bzConsumerDto");
		if (bzConsumerDtoSo != null) {
			result.setBzConsumerDto(processBzConsumerDto(bzConsumerDtoSo));
		}
		SoapObject bzProductDtoSo = getSubPropertySoapObject(target,
				"bzProductDto");
		if (bzProductDtoSo != null) {
			result.setBzProductDto(processBzProductDto(bzProductDtoSo));
		}

		SoapObject bzMerchantDtoSo = getSubPropertySoapObject(target,
				"bzMerchantDto");
		if (bzMerchantDtoSo != null) {
			result.setBzMerchantDto(processBzMerchantDto(bzMerchantDtoSo));
		}
		return result;
	}

	protected SysUserDto processSysUserDto(SoapObject target) {
		SysUserDto result = new SysUserDto();
		getSysUserDtoPrepertites(target, result);
		return result;
	}

	private void getSysUserDtoPrepertites(SoapObject target, SysUserDto result) {
		result.setId(getProperty2Long(target, "id"));
		result.setUsername(getProperty2String(target, "username"));
		result.setType(getProperty2Long(target, "type"));
		result.setRealnameStatus(getProperty2Long(target, "realnameStatus"));
		result.setSellingStatus(getProperty2Long(target, "sellingStatus"));
		result.setDistStatus(getProperty2Long(target, "distStatus"));
		result.setRealname(getProperty2String(target, "realname"));
		result.setNickname(getProperty2String(target, "nickname"));
		result.setMobile(getProperty2String(target, "mobile"));
		result.setEmail(getProperty2String(target, "email"));
		result.setAccountBalance(getProperty2BigDecimal(target,
				"accountBalance"));
		result.setHeadIco(getProperty2String(target, "headIco"));
	}

	protected SysAttachmentDto processSysAttachmentDto(SoapObject target) {
		SysAttachmentDto result = new SysAttachmentDto();
		result.setId(getProperty2Long(target, "id"));
		result.setSrcFileName(getProperty2String(target, "srcFileName"));
		result.setFileName(getProperty2String(target, "fileName"));
		result.setFileExt(getProperty2String(target, "fileExt"));
		result.setFileType(getProperty2Long(target, "fileType"));
		return result;
	}

	protected BzMerchantDto processBzMerchantDto(SoapObject target) {
		BzMerchantDto result = new BzMerchantDto();
		getSysUserDtoPrepertites(target, result);
		result.setCompanyName(getProperty2String(target, "companyName"));
		result.setCompanyAddress(getProperty2String(target, "companyAddress"));
		result.setServiceCoverage(getProperty2Long(target, "serviceCoverage"));
		result.setRatingScore(getProperty2Float(target, "ratingScore"));
		SoapObject bzPositionDtoSo = getSubPropertySoapObject(target,
				"bzPositionDto");
		if (bzPositionDtoSo != null) {
			result.setBzPositionDto(processBzPositionDto(bzPositionDtoSo));
		}
		result.setMerchantCharacteristics(getProperty2String(target,
				"merchantCharacteristics"));
		result.setMerchantCategory(getProperty2Long(target, "merchantCategory"));
		result.setCertificateStatus(getProperty2Long(target,
				"certificateStatus"));
		result.setIsFavorited(getProperty2Boolean(target, "isFavorited"));
		return result;
	}

	protected BzDistCompanyDto processBzDistCompanyDto(SoapObject target) {
		BzDistCompanyDto result = new BzDistCompanyDto();
		getSysUserDtoPrepertites(target, result);
		result.setCompanyName(getProperty2String(target, "companyName"));
		result.setCompanyAddress(getProperty2String(target, "companyAddress"));
		result.setServiceCoverage(getProperty2Long(target, "serviceCoverage"));
		result.setCertificateStatus(getProperty2Long(target,
				"certificateStatus"));
		SoapObject bzPositionDtoSo = getSubPropertySoapObject(target,
				"bzPositionDto");
		if (bzPositionDtoSo != null) {
			result.setBzPositionDto(processBzPositionDto(bzPositionDtoSo));
		}
		return result;
	}

	protected BzConsumerDto processBzConsumerDto(SoapObject target) {
		BzConsumerDto result = new BzConsumerDto();
		getSysUserDtoPrepertites(target, result);
		return result;
	}

	protected BzOrderInitDto processBzOrderInitDto(SoapObject target) {
		BzOrderInitDto result = new BzOrderInitDto();
		result.setDistPrice(getProperty2BigDecimal(target, "distPrice"));
		SoapObject addressSo = getSubPropertySoapObject(target,
				"bzUserAddressDto");
		if (addressSo != null) {
			result.setBzUserAddressDto(processBzUserAddressDto(addressSo));
		}

		SoapObject paymentTypeSo = getSubPropertySoapObject(target,
				"bzPaymentTypeDtoList");
		if (paymentTypeSo != null) {
			for (int i = 0; i < paymentTypeSo.getPropertyCount(); i++) {
				Object typeObj = paymentTypeSo.getProperty(i);
				if (typeObj instanceof SoapObject) {
					SoapObject typeSo = (SoapObject) typeObj;
					result.getBzPaymentTypeDtos().add(
							processBzPaymentTypeDto(typeSo));
				}
			}
		}
		return result;
	}

	protected BzPaymentTypeDto processBzPaymentTypeDto(SoapObject target) {
		BzPaymentTypeDto result = new BzPaymentTypeDto();
		result.setId(getProperty2Long(target, "id"));
		result.setTypeName(getProperty2String(target, "typeName"));
		result.setTypeDescription(getProperty2String(target, "typeDescription"));
		return result;
	}

	protected BzOrderDto processBzOrderDto(SoapObject target) {
		BzOrderDto result = new BzOrderDto();
		result.setId(getProperty2Long(target, "id"));
		result.setOrderSerialNo(getProperty2String(target, "orderSerialNo"));
		result.setOrderStatus(getProperty2Long(target, "orderStatus"));
		result.setOrderDiscount(getProperty2BigDecimal(target, "orderDiscount"));
		result.setOrderPreferential(getProperty2BigDecimal(target,
				"orderPreferential"));
		result.setOrderAmount(getProperty2BigDecimal(target, "orderAmount"));
		result.setOrderDesc(getProperty2String(target, "orderDesc"));
		result.setCreatedDate(getProperty2Date(target, "createdDate"));
		result.setBzConsumerId(getProperty2Long(target, "bzConsumerId"));
		result.setBzMerchantId(getProperty2Long(target, "bzMerchantId"));

		SoapObject bzConsumerDtoSo = getSubPropertySoapObject(target,
				"bzConsumerDto");
		if (bzConsumerDtoSo != null) {
			result.setBzConsumerDto(processBzConsumerDto(bzConsumerDtoSo));
		}

		SoapObject bzMerchantDtoSo = getSubPropertySoapObject(target,
				"bzMerchantDto");
		if (bzMerchantDtoSo != null) {
			result.setBzMerchantDto(processBzMerchantDto(bzMerchantDtoSo));
		}

		SoapObject bzUserAddressDtoSo = getSubPropertySoapObject(target,
				"bzUserAddressDto");
		if (bzUserAddressDtoSo != null) {
			result.setBzUserAddressDto(processBzUserAddressDto(bzUserAddressDtoSo));
		}

		SoapObject bzOrderPaymentSo = getSubPropertySoapObject(target,
				"bzOrderPayment");
		if (bzOrderPaymentSo != null) {
			result.setBzOrderPaymentDto(processBzOrderPaymentDto(bzOrderPaymentSo));
		}

		SoapObject bzOrderItemDtoSo = getSubPropertySoapObject(target,
				"bzOrderItemDtoList");
		if (bzOrderItemDtoSo != null) {
			for (int i = 0; i < bzOrderItemDtoSo.getPropertyCount(); i++) {
				Object itemObj = bzOrderItemDtoSo.getProperty(i);
				if (itemObj instanceof SoapObject) {
					SoapObject itemSo = (SoapObject) itemObj;
					result.getBzOrderItemDtos().add(
							processBzOrderItemDto(itemSo));
				}
			}
		}
		SoapObject bzInvoiceDtoSo = getSubPropertySoapObject(target,
				"bzInvoiceDto");
		if (bzInvoiceDtoSo != null) {
			result.setBzInvoiceDto(processBzInvoiceDto(bzInvoiceDtoSo));
		}
		return result;
	}

	protected BzInvoiceDto processBzInvoiceDto(SoapObject target) {
		BzInvoiceDto result = new BzInvoiceDto();
		result.setId(getProperty2Long(target, "id"));
		result.setInvoiceSerialNo(getProperty2String(target, "invoiceSerialNo"));
		result.setInvoiceStatus(getProperty2Long(target, "invoiceStatus"));
		result.setInvoiceNum(getProperty2Long(target, "invoiceNum"));
		result.setDistAddress(getProperty2String(target, "distAddress"));
		result.setDistContactPerson(getProperty2String(target,
				"distContactPerson"));
		result.setDistContactNo(getProperty2String(target, "distContactNo"));
		result.setDistPostcode(getProperty2String(target, "distPostcode"));
		SoapObject merchantSo = getSubPropertySoapObject(target,
				"bzMerchantDto");
		if (merchantSo != null) {
			result.setBzMerchantDto(processBzMerchantDto(merchantSo));
		}
		SoapObject bzDistCompanyDtoSo = getSubPropertySoapObject(target,
				"bzDistCompanyDto");
		if (bzDistCompanyDtoSo != null) {
			result.setBzDistCompanyDto(processBzDistCompanyDto(bzDistCompanyDtoSo));
		}
		SoapObject bzDistOrderDtoSo = getSubPropertySoapObject(target,
				"bzDistOrderDto");
		if (bzDistOrderDtoSo != null) {
			result.setBzDistOrderDto(processBzDistOrderDto(bzDistOrderDtoSo));
		}
		SoapObject bzInvoiceItemDtoSo = getSubPropertySoapObject(target,
				"bzInvoiceItemDtoList");
		if (bzInvoiceItemDtoSo != null) {
			for (int i = 0; i < bzInvoiceItemDtoSo.getPropertyCount(); i++) {
				Object itemObj = bzInvoiceItemDtoSo.getProperty(i);
				if (itemObj instanceof SoapObject) {
					SoapObject itemSo = (SoapObject) itemObj;
					result.getBzInvoiceItemDtos().add(
							processBzInvoiceItemDto(itemSo));
				}
			}
		}
		return result;
	}

	protected BzInvoiceItemDto processBzInvoiceItemDto(SoapObject target) {
		BzInvoiceItemDto result = new BzInvoiceItemDto();
		result.setId(getProperty2Long(target, "id"));
		result.setItemNum(getProperty2Long(target, "itemNum"));
		SoapObject bzProductDtoSo = getSubPropertySoapObject(target,
				"bzProductDto");
		if (bzProductDtoSo != null) {
			result.setBzProductDto(processBzProductDto(bzProductDtoSo));
		}
		return result;
	}

	protected BzDistOrderDto processBzDistOrderDto(SoapObject target) {
		BzDistOrderDto result = new BzDistOrderDto();
		result.setId(getProperty2Long(target, "id"));
		result.setOrderStatus(getProperty2Long(target, "orderStatus"));
		result.setOrderDistance(getProperty2BigDecimal(target, "orderDistance"));
		result.setOrderDirection(getProperty2Long(target, "orderDirection"));
		result.setOrderSerialNo(getProperty2String(target, "orderSerialNo"));
		result.setOrderNum(getProperty2Long(target, "orderNum"));
		result.setDistAddress(getProperty2String(target, "distAddress"));
		result.setDistContactPerson(getProperty2String(target,
				"distContactPerson"));
		result.setDistContactNo(getProperty2String(target, "distContactNo"));
		result.setDistPostcode(getProperty2String(target, "distPostcode"));
		SoapObject merchantSo = getSubPropertySoapObject(target,
				"bzMerchantDto");
		if (merchantSo != null) {
			result.setBzMerchantDto(processBzMerchantDto(merchantSo));
		}
		SoapObject bzDistCompanyDtoSo = getSubPropertySoapObject(target,
				"bzDistCompanyDto");
		if (bzDistCompanyDtoSo != null) {
			result.setBzDistCompanyDto(processBzDistCompanyDto(bzDistCompanyDtoSo));
		}
		SoapObject bzOrderDtoSo = getSubPropertySoapObject(target, "bzOrderDto");
		if (bzOrderDtoSo != null) {
			result.setBzOrderDto(processBzOrderDto(bzOrderDtoSo));
		}
		SoapObject bzCollectionOrderDtoSo = getSubPropertySoapObject(target,
				"bzCollectionOrderDto");
		if (bzCollectionOrderDtoSo != null) {
			result.setBzCollectionOrderDto(processBzCollectionOrderDto(bzCollectionOrderDtoSo));
		}
		SoapObject bzDistOrderItemDtoSo = getSubPropertySoapObject(target,
				"bzDistOrderItemDtoList");
		if (bzDistOrderItemDtoSo != null) {
			for (int i = 0; i < bzDistOrderItemDtoSo.getPropertyCount(); i++) {
				Object itemObj = bzDistOrderItemDtoSo.getProperty(i);
				if (itemObj instanceof SoapObject) {
					SoapObject itemSo = (SoapObject) itemObj;
					result.getBzDistOrderItemDtos().add(
							processBzDistOrderItemDto(itemSo));
				}
			}
		}
		return result;
	}

	protected BzDistOrderItemDto processBzDistOrderItemDto(SoapObject target) {
		BzDistOrderItemDto result = new BzDistOrderItemDto();
		result.setId(getProperty2Long(target, "id"));
		result.setItemNum(getProperty2Long(target, "itemNum"));
		SoapObject bzProductDtoSo = getSubPropertySoapObject(target,
				"bzProductDto");
		if (bzProductDtoSo != null) {
			result.setBzProductDto(processBzProductDto(bzProductDtoSo));
		}
		return result;
	}

	protected BzCollectionOrderDto processBzCollectionOrderDto(SoapObject target) {
		BzCollectionOrderDto result = new BzCollectionOrderDto();
		result.setId(getProperty2Long(target, "id"));
		result.setReceivableAmount(getProperty2BigDecimal(target,
				"receivableAmount"));
		result.setReceivedAmount(getProperty2BigDecimal(target,
				"receivedAmount"));
		result.setDistAmount(getProperty2BigDecimal(target, "distAmount"));
		return result;
	}

	protected BzOrderItemDto processBzOrderItemDto(SoapObject target) {
		BzOrderItemDto result = new BzOrderItemDto();
		result.setId(getProperty2Long(target, "id"));
		result.setItemNum(getProperty2Long(target, "itemNum"));
		result.setItemUnitPrice(getProperty2BigDecimal(target, "itemUnitPrice"));
		result.setItemDiscount(getProperty2BigDecimal(target, "itemDiscount"));
		result.setItemPreferential(getProperty2BigDecimal(target,
				"itemPreferential"));
		result.setItemAmount(getProperty2BigDecimal(target, "itemAmount"));
		result.setItemStockStatus(getProperty2Long(target, "itemStockStatus"));
		SoapObject bzProductDtoSo = getSubPropertySoapObject(target,
				"bzProductDto");
		if (bzProductDtoSo != null) {
			result.setBzProductDto(processBzProductDto(bzProductDtoSo));
		}
		return result;
	}

	protected BzOrderPaymentDto processBzOrderPaymentDto(SoapObject target) {
		BzOrderPaymentDto result = new BzOrderPaymentDto();
		result.setId(getProperty2Long(target, "id"));
		result.setPayStatus(getProperty2Long(target, "payStatus"));
		result.setPayAmount(getProperty2BigDecimal(target, "payAmount"));
		SoapObject typeSo = getSubPropertySoapObject(target, "bzPaymentTypeDto");
		if (typeSo != null) {
			result.setBzPaymentTypeDto(processBzPaymentTypeDto(typeSo));
		}
		return result;
	}

	protected BzProductFavoriteDto processBzProductFavoriteDto(SoapObject target) {
		BzProductFavoriteDto result = new BzProductFavoriteDto();
		result.setId(getProperty2Long(target, "id"));
		result.setType(getProperty2String(target, "type"));
		SoapObject consumerSo = getSubPropertySoapObject(target,
				"bzConsumerDto");
		if (consumerSo != null) {
			result.setBzConsumerDto(processBzConsumerDto(consumerSo));
		}
		SoapObject productSo = getSubPropertySoapObject(target, "bzProductDto");
		if (productSo != null) {
			result.setBzProductDto(processBzProductDto(productSo));
		}
		return result;
	}

	protected BzMerchantFavoriteDto processBzMerchantFavoriteDto(
			SoapObject target) {
		BzMerchantFavoriteDto result = new BzMerchantFavoriteDto();
		result.setId(getProperty2Long(target, "id"));
		result.setType(getProperty2String(target, "type"));
		SoapObject bzConsumerDtoSo = getSubPropertySoapObject(target,
				"bzConsumerDto");
		if (bzConsumerDtoSo != null) {
			result.setBzConsumerDto(processBzConsumerDto(bzConsumerDtoSo));
		}
		SoapObject bzMerchantDtoSo = getSubPropertySoapObject(target,
				"bzMerchantDto");
		if (bzMerchantDtoSo != null) {
			result.setBzMerchantDto(processBzMerchantDto(bzMerchantDtoSo));
		}
		return result;
	}

	protected BzApplyRealnameCertificateDto processBzApplyRealnameCertificateDto(
			SoapObject target) {
		BzApplyRealnameCertificateDto result = new BzApplyRealnameCertificateDto();
		result.setId(getProperty2Long(target, "id"));
		result.setBzAttachmentId(getProperty2Long(target, "bzAttachmentId"));
		result.setName(getProperty2String(target, "name"));
		result.setSex(getProperty2Long(target, "sex"));
		result.setBirthdate(getProperty2Timestamp(target, "birthdate"));
		result.setMobile(getProperty2String(target, "mobile"));
		result.setIdType(getProperty2Long(target, "idType"));
		result.setIdNo(getProperty2String(target, "idNo"));
		result.setApprovalStatus(getProperty2Long(target, "approvalStatus"));
		result.setBzWorkflowId(getProperty2Long(target, "bzWorkflowId"));
		SoapObject sysUserDtoSo = getSubPropertySoapObject(target, "sysUserDto");
		if (sysUserDtoSo != null) {
			result.setSysUserDto(processSysUserDto(sysUserDtoSo));
		}
		SoapObject bzAttachmentDtoSo = getSubPropertySoapObject(target,
				"bzAttachmentDto");
		if (bzAttachmentDtoSo != null) {
			result.setBzAttachmentDto(processBzAttachmentDto(bzAttachmentDtoSo));
		}
		SoapObject bzApprovalInfoListSo = getSubPropertySoapObject(target,
				"bzApprovalInfoList");
		if (bzApprovalInfoListSo != null) {
			result.setBzApprovalInfos(processBzApprovalInfoDto(bzApprovalInfoListSo));
		}
		return result;
	}

	protected BzAttachmentDto processBzAttachmentDto(SoapObject target) {
		BzAttachmentDto result = new BzAttachmentDto();
		result.setId(getProperty2Long(target, "id"));
		result.setSrcFileName(getProperty2String(target, "srcFileName"));
		result.setFileName(getProperty2String(target, "fileName"));
		result.setFileType(getProperty2Long(target, "fileType"));
		result.setFileExt(getProperty2String(target, "fileExt"));
		return result;
	}

	protected BzApplySellingCertificateDto processBzApplySellingCertificateDto(
			SoapObject target) {
		BzApplySellingCertificateDto result = new BzApplySellingCertificateDto();
		result.setId(getProperty2Long(target, "id"));
		result.setBzAttachmentId(getProperty2Long(target, "bzAttachmentId"));
		result.setCompanyName(getProperty2String(target, "companyName"));
		result.setServiceType(getProperty2Long(target, "serviceType"));
		result.setCompanyAddress(getProperty2String(target, "companyAddress"));
		result.setServiceCoverage(getProperty2Long(target, "serviceCoverage"));
		result.setApprovalStatus(getProperty2Long(target, "approvalStatus"));
		result.setBzWorkflowId(getProperty2Long(target, "bzWorkflowId"));

		SoapObject sysUserDtoSo = getSubPropertySoapObject(target, "sysUserDto");
		if (sysUserDtoSo != null) {
			result.setSysUserDto(processSysUserDto(sysUserDtoSo));
		}
		SoapObject bzAttachmentDtoSo = getSubPropertySoapObject(target,
				"bzAttachmentDto");
		if (bzAttachmentDtoSo != null) {
			result.setBzAttachmentDto(processBzAttachmentDto(bzAttachmentDtoSo));
		}
		SoapObject bzApprovalInfoListSo = getSubPropertySoapObject(target,
				"bzApprovalInfoList");
		if (bzApprovalInfoListSo != null) {
			result.setBzApprovalInfos(processBzApprovalInfoDto(bzApprovalInfoListSo));
		}
		return result;
	}

	protected BzApplyDistCertificateDto processBzApplyDistCertificateDto(
			SoapObject target) {
		BzApplyDistCertificateDto result = new BzApplyDistCertificateDto();
		result.setId(getProperty2Long(target, "id"));
		result.setBzAttachmentId(getProperty2Long(target, "bzAttachmentId"));
		result.setCompanyName(getProperty2String(target, "companyName"));
		result.setServiceType(getProperty2Long(target, "serviceType"));
		result.setCompanyAddress(getProperty2String(target, "companyAddress"));
		result.setServiceCoverage(getProperty2Long(target, "serviceCoverage"));
		result.setApprovalStatus(getProperty2Long(target, "approvalStatus"));
		result.setBzWorkflowId(getProperty2Long(target, "bzWorkflowId"));
		SoapObject sysUserDtoSo = getSubPropertySoapObject(target, "sysUserDto");
		if (sysUserDtoSo != null) {
			result.setSysUserDto(processSysUserDto(sysUserDtoSo));
		}
		SoapObject bzAttachmentDtoSo = getSubPropertySoapObject(target,
				"bzAttachmentDto");
		if (bzAttachmentDtoSo != null) {
			result.setBzAttachmentDto(processBzAttachmentDto(bzAttachmentDtoSo));
		}
		SoapObject bzApprovalInfoListSo = getSubPropertySoapObject(target,
				"bzApprovalInfoList");
		if (bzApprovalInfoListSo != null) {
			result.setBzApprovalInfos(processBzApprovalInfoDto(bzApprovalInfoListSo));
		}
		return result;
	}

	protected List<BzApprovalInfoDto> processBzApprovalInfoDto(SoapObject target) {
		List<BzApprovalInfoDto> result = new ArrayList<BzApprovalInfoDto>();
		for (int j = 0; j < target.getPropertyCount(); j++) {
			Object itemObj = target.getProperty(j);
			if (itemObj instanceof SoapObject) {
				SoapObject approvalItemSo = (SoapObject) itemObj;
				BzApprovalInfoDto approvalItem = new BzApprovalInfoDto();
				approvalItem.setProcessInstanceId(getProperty2String(
						approvalItemSo, "processInstanceId"));
				approvalItem.setTaskId(getProperty2String(approvalItemSo,
						"taskId"));
				approvalItem.setBussinessKey(getProperty2String(approvalItemSo,
						"bussinessKey"));
				approvalItem.setActivityName(getProperty2String(approvalItemSo,
						"activityName"));
				approvalItem.setApprovalName(getProperty2String(approvalItemSo,
						"approvalName"));
				approvalItem.setApprovalState(getProperty2String(
						approvalItemSo, "approvalState"));
				approvalItem.setApprovalInfo(getProperty2String(approvalItemSo,
						"approvalInfo"));
				approvalItem.setApprovalTime(getProperty2Date(approvalItemSo,
						"approvalTime", DateUtil.FORMAT_DATETIME_DEFAULT));
				result.add(approvalItem);
			}

		}
		return result;
	}

	protected BzStockOrderDto processBzStockOrderDto(SoapObject target) {
		BzStockOrderDto result = new BzStockOrderDto();
		result.setId(getProperty2Long(target, "id"));
		result.setOrderSerialNo(getProperty2String(target, "orderSerialNo"));
		result.setOrderStatus(getProperty2Long(target, "orderStatus"));
		result.setOrderEdc(getProperty2Date(target, "orderEdc"));
		SoapObject bzMerchantDtoSo = getSubPropertySoapObject(target,
				"bzMerchantDto");
		if (bzMerchantDtoSo != null) {
			result.setBzMerchantDto(processBzMerchantDto(bzMerchantDtoSo));
		}
		SoapObject bzStockOrderItemDtoListSo = getSubPropertySoapObject(target,
				"bzStockOrderItemDtoList");
		if (bzStockOrderItemDtoListSo != null) {
			for (int i = 0; i < bzStockOrderItemDtoListSo.getPropertyCount(); i++) {
				Object itemObj = bzStockOrderItemDtoListSo.getProperty(i);
				if (itemObj instanceof SoapObject) {
					SoapObject itemSo = (SoapObject) itemObj;
					result.getBzStockOrderItemDtos().add(
							processBzStockOrderItemDto(itemSo));
				}
			}
		}
		return result;
	}

	protected BzStockOrderItemDto processBzStockOrderItemDto(SoapObject target) {
		BzStockOrderItemDto result = new BzStockOrderItemDto();
		result.setId(getProperty2Long(target, "id"));
		result.setItemStatus(getProperty2Long(target, "itemStatus"));
		result.setItemNum(getProperty2Long(target, "itemNum"));
		SoapObject bzProductDtoSo = getSubPropertySoapObject(target,
				"bzProductDto");
		if (bzProductDtoSo != null) {
			result.setBzProductDto(processBzProductDto(bzProductDtoSo));
		}
		return result;
	}

	protected BzDistStatementSummaryDto processBzDistStatementSummaryDto(
			SoapObject target) {
		BzDistStatementSummaryDto result = new BzDistStatementSummaryDto();
		result.setUnprocessedReceivableAmount(getProperty2BigDecimal(target,
				"unprocessedReceivableAmount"));
		result.setUnprocessedRefundAmount(getProperty2BigDecimal(target,
				"unprocessedRefundAmount"));
		result.setProcessingReceivableAmount(getProperty2BigDecimal(target,
				"processingReceivableAmount"));
		result.setProcessingRefundAmount(getProperty2BigDecimal(target,
				"processingRefundAmount"));
		result.setProcessedAmount(getProperty2BigDecimal(target,
				"processedAmount"));
		return result;
	}

	protected BzDistStatementDto processBzDistStatementDto(SoapObject target) {
		BzDistStatementDto result = new BzDistStatementDto();
		result.setId(getProperty2Long(target, "id"));
		result.setStatementSerialNo(getProperty2String(target,
				"statementSerialNo"));
		result.setStatementStatus(getProperty2Long(target, "statementStatus"));
		result.setStatementTime(getProperty2Date(target, "statementTime"));
		result.setMerchantReceivableAmount(getProperty2BigDecimal(target,
				"merchantReceivableAmount"));
		result.setMerchantReceivedAmount(getProperty2BigDecimal(target,
				"merchantReceivedAmount"));
		result.setDistCompanyReceivableAmount(getProperty2BigDecimal(target,
				"distCompanyReceivableAmount"));
		result.setDistCompanyReceivedAmount(getProperty2BigDecimal(target,
				"distCompanyReceivedAmount"));
		SoapObject bzMerchantDtoSo = getSubPropertySoapObject(target,
				"bzMerchantDto");
		if (bzMerchantDtoSo != null) {
			result.setBzMerchantDto(processBzMerchantDto(bzMerchantDtoSo));
		}

		SoapObject bzDistCompanyDtoSo = getSubPropertySoapObject(target,
				"bzDistCompanyDto");
		if (bzDistCompanyDtoSo != null) {
			result.setBzDistCompanyDto(processBzDistCompanyDto(bzDistCompanyDtoSo));
		}
		SoapObject bzDistStatementItemDtoListSo = getSubPropertySoapObject(
				target, "bzDistStatementItemDtoList");
		if (bzDistStatementItemDtoListSo != null) {
			for (int i = 0; i < bzDistStatementItemDtoListSo.getPropertyCount(); i++) {
				Object itemObj = bzDistStatementItemDtoListSo.getProperty(i);
				if (itemObj instanceof SoapObject) {
					SoapObject itemSo = (SoapObject) itemObj;
					result.getBzDistStatementItemDtos().add(
							processBzDistStatementItemDto(itemSo));
				}
			}
		}
		return result;
	}

	protected BzDistStatementItemDto processBzDistStatementItemDto(
			SoapObject target) {
		BzDistStatementItemDto result = new BzDistStatementItemDto();
		result.setId(getProperty2Long(target, "id"));
		result.setStatementStatus(getProperty2Long(target, "statementStatus"));
		result.setStatementTime(getProperty2Date(target, "statementTime"));
		result.setMerchantReceivableAmount(getProperty2BigDecimal(target,
				"merchantReceivableAmount"));
		result.setMerchantReceivedAmount(getProperty2BigDecimal(target,
				"merchantReceivedAmount"));
		result.setDistCompanyReceivableAmount(getProperty2BigDecimal(target,
				"distCompanyReceivableAmount"));
		result.setDistCompanyReceivedAmount(getProperty2BigDecimal(target,
				"distCompanyReceivedAmount"));
		SoapObject bzDistStatementDtoSo = getSubPropertySoapObject(target,
				"bzDistStatementDto");
		if (bzDistStatementDtoSo != null) {
			result.setBzDistStatementDto(processBzDistStatementDto(bzDistStatementDtoSo));
		}
		SoapObject bzDistOrderDtoSo = getSubPropertySoapObject(target,
				"bzDistOrderDto");
		if (bzDistOrderDtoSo != null) {
			result.setBzDistOrderDto(processBzDistOrderDto(bzDistOrderDtoSo));
		}

		SoapObject bzMerchantDtoSo = getSubPropertySoapObject(target,
				"bzMerchantDto");
		if (bzMerchantDtoSo != null) {
			result.setBzMerchantDto(processBzMerchantDto(bzMerchantDtoSo));
		}

		SoapObject bzDistCompanyDtoSo = getSubPropertySoapObject(target,
				"bzDistCompanyDto");
		if (bzDistCompanyDtoSo != null) {
			result.setBzDistCompanyDto(processBzDistCompanyDto(bzDistCompanyDtoSo));
		}
		return result;
	}

	protected BzOrderRefundDto processBzOrderRefundDto(SoapObject target) {
		BzOrderRefundDto result = new BzOrderRefundDto();
		result.setId(getProperty2Long(target, "id"));
		result.setRefundSerialNo(getProperty2String(target, "refundSerialNo"));
		result.setRefundStatus(getProperty2Long(target, "refundStatus"));
		result.setRefundAmount(getProperty2BigDecimal(target, "refundAmount"));
		result.setRefundDesc(getProperty2String(target, "refundDesc"));
		result.setCreatedDate(getProperty2Date(target, "createdDate"));
		SoapObject bzOrderDtoSo = getSubPropertySoapObject(target, "bzOrderDto");
		if (bzOrderDtoSo != null) {
			result.setBzOrderDto(processBzOrderDto(bzOrderDtoSo));
		}
		SoapObject bzConsumerDtoSo = getSubPropertySoapObject(target,
				"bzConsumerDto");
		if (bzConsumerDtoSo != null) {
			result.setBzConsumerDto(processBzConsumerDto(bzConsumerDtoSo));
		}
		SoapObject bzMerchantDtoSo = getSubPropertySoapObject(target,
				"bzMerchantDto");
		if (bzMerchantDtoSo != null) {
			result.setBzMerchantDto(processBzMerchantDto(bzMerchantDtoSo));
		}
		return result;
	}
}
