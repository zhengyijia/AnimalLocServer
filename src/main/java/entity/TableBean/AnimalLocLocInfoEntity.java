package entity.TableBean;

import java.io.Serializable;

public class AnimalLocLocInfoEntity implements Serializable{

    private String imei;
    private String time;
    private Integer longitude;
    private Integer latitude;
    private Short track;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public Short getTrack() {
        return track;
    }

    public void setTrack(Short track) {
        this.track = track;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnimalLocLocInfoEntity that = (AnimalLocLocInfoEntity) o;

        if (imei != null ? !imei.equals(that.imei) : that.imei != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false;
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false;
        if (track != null ? !track.equals(that.track) : that.track != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = imei != null ? imei.hashCode() : 0;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (track != null ? track.hashCode() : 0);
        return result;
    }

}
