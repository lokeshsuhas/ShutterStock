package lokesh.shutterstock.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum extends Item {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("aspect")
    @Expose
    private Double aspect;
    @SerializedName("assets")
    @Expose
    private Assets assets;
    @SerializedName("contributor")
    @Expose
    private Contributor contributor;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image_type")
    @Expose
    private String imageType;
    @SerializedName("media_type")
    @Expose
    private String mediaType;


    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The aspect
     */
    public Double getAspect() {
        return aspect;
    }

    /**
     * @param aspect The aspect
     */
    public void setAspect(Double aspect) {
        this.aspect = aspect;
    }

    /**
     * @return The assets
     */
    public Assets getAssets() {
        return assets;
    }

    /**
     * @param assets The assets
     */
    public void setAssets(Assets assets) {
        this.assets = assets;
    }

    /**
     * @return The contributor
     */
    public Contributor getContributor() {
        return contributor;
    }

    /**
     * @param contributor The contributor
     */
    public void setContributor(Contributor contributor) {
        this.contributor = contributor;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The imageType
     */
    public String getImageType() {
        return imageType;
    }

    /**
     * @param imageType The image_type
     */
    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    /**
     * @return The mediaType
     */
    public String getMediaType() {
        return mediaType;
    }

    /**
     * @param mediaType The media_type
     */
    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

}
