
package com.vinamine.mc.rest.voucher;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("storeId")
    @Expose
    private Integer storeId;
    @SerializedName("store")
    @Expose
    private String store;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("storeAddress")
    @Expose
    private List<StoreAddress> storeAddress = null;
    @SerializedName("typeVoucher")
    @Expose
    private String typeVoucher;
    @SerializedName("nameUnAccent")
    @Expose
    private String nameUnAccent;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("codeSale")
    @Expose
    private String codeSale;
    @SerializedName("percent")
    @Expose
    private Integer percent;
    @SerializedName("maxSlot")
    @Expose
    private Integer maxSlot;
    @SerializedName("slotLeft")
    @Expose
    private Integer slotLeft;
    @SerializedName("startDay")
    @Expose
    private String startDay;
    @SerializedName("expiredDay")
    @Expose
    private String expiredDay;
    @SerializedName("promotionTimeDto")
    @Expose
    private PromotionTimeDto promotionTimeDto;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("updated")
    @Expose
    private String updated;
    @SerializedName("status")
    @Expose
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<StoreAddress> getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(List<StoreAddress> storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getTypeVoucher() {
        return typeVoucher;
    }

    public void setTypeVoucher(String typeVoucher) {
        this.typeVoucher = typeVoucher;
    }

    public String getNameUnAccent() {
        return nameUnAccent;
    }

    public void setNameUnAccent(String nameUnAccent) {
        this.nameUnAccent = nameUnAccent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCodeSale() {
        return codeSale;
    }

    public void setCodeSale(String codeSale) {
        this.codeSale = codeSale;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public Integer getMaxSlot() {
        return maxSlot;
    }

    public void setMaxSlot(Integer maxSlot) {
        this.maxSlot = maxSlot;
    }

    public Integer getSlotLeft() {
        return slotLeft;
    }

    public void setSlotLeft(Integer slotLeft) {
        this.slotLeft = slotLeft;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getExpiredDay() {
        return expiredDay;
    }

    public void setExpiredDay(String expiredDay) {
        this.expiredDay = expiredDay;
    }

    public PromotionTimeDto getPromotionTimeDto() {
        return promotionTimeDto;
    }

    public void setPromotionTimeDto(PromotionTimeDto promotionTimeDto) {
        this.promotionTimeDto = promotionTimeDto;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
