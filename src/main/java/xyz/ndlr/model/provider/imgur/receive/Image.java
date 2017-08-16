package xyz.ndlr.model.provider.imgur.receive;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.concurrent.CompletableFuture;

public class Image {
    @JsonProperty("id")
    private String id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("datetime")
    private long datetime;
    @JsonProperty("type")
    private String type;
    @JsonProperty("animated")
    private boolean animated;
    @JsonProperty("width")
    private int width;
    @JsonProperty("height")
    private int height;
    @JsonProperty("size")
    private int size;
    @JsonProperty("views")
    private int views;
    @JsonProperty("bandwidth")
    private int bandwidth;
    @JsonProperty("vote")
    private String vote;
    @JsonProperty("favorite")
    private boolean favorite;
    @JsonProperty("nsfw")
    private String nsfw;
    @JsonProperty("section")
    private String section;
    @JsonProperty("account_url")
    private String accountUrl;
    @JsonProperty("account_id")
    private long accountId;
    @JsonProperty("is_ad")
    private boolean isAd;
    @JsonProperty("in_most_viral")
    private boolean inMostViral;
    @JsonProperty("tags")
    private Object[] tags;
    @JsonProperty("ad_type")
    private int adType;
    @JsonProperty("ad_url")
    private String adUrl;
    @JsonProperty("in_gallery")
    private boolean inGallery;
    @JsonProperty("deletehash")
    private String deleteHash;
    @JsonProperty("name")
    private String name;
    @JsonProperty("link")
    private String link;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(int bandwidth) {
        this.bandwidth = bandwidth;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getNsfw() {
        return nsfw;
    }

    public void setNsfw(String nsfw) {
        this.nsfw = nsfw;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getAccountUrl() {
        return accountUrl;
    }

    public void setAccountUrl(String accountUrl) {
        this.accountUrl = accountUrl;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public boolean isAd() {
        return isAd;
    }

    public void setAd(boolean ad) {
        isAd = ad;
    }

    public boolean isInMostViral() {
        return inMostViral;
    }

    public void setInMostViral(boolean inMostViral) {
        this.inMostViral = inMostViral;
    }

    public Object[] getTags() {
        return tags;
    }

    public void setTags(Object[] tags) {
        this.tags = tags;
    }

    public int getAdType() {
        return adType;
    }

    public void setAdType(int adType) {
        this.adType = adType;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public boolean isInGallery() {
        return inGallery;
    }

    public void setInGallery(boolean inGallery) {
        this.inGallery = inGallery;
    }

    public String getDeleteHash() {
        return deleteHash;
    }

    public void setDeleteHash(String deleteHash) {
        this.deleteHash = deleteHash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
